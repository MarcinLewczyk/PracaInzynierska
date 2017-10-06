package lewczyk.pracainzynierska.UserExercise;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class ExecuteExerciseActivity extends AppCompatActivity implements SensorEventListener{
    @BindView(R.id.exerciseToExecuteTitleTextView) TextView exerciseTitle;
    @BindView(R.id.exerciseToExecuteSeriesTextView) TextView seriesTextView;
    @BindView(R.id.exerciseToExecuteRepeatsTextView) TextView repeatsTextView;
    @BindView(R.id.exerciseToExecuteTimerTextView) TextView timerTextView;
    @BindView(R.id.exerciseToExecuteBreakTimerTextView) TextView breakTimerTextView;
    @BindView(R.id.startStopExerciseButton) Button startStopExerciseButton;
    @BindView(R.id.endOfSetButton) Button endOfSetButton;
    private int fromActivity, currentSet, series, currentRepeat, repeats, timeParameter;
    private long exerciseToDoId, exerciseId, trainingPlanId;
    private ExerciseToDo exerciseToDo;
    private Exercise exercise;
    private ExerciseInTrainingPlan exerciseInTrainingPlan;
    private long startTime, timeInMilliseconds, timeBuffer, updatedTime, lastSensorUpdate;
    private long startBreakTime, breakTimeInMilliseconds, breakTimeBuffer, updatedBreakTime;
    private Handler timerHandler = new Handler();
    private Handler breakTimerHandler = new Handler();
    private double sensorParameter, load;
    private boolean exerciseContinues = false;
    private SensorManager sensorManager;
    private ArrayList<String> exercisesDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_exercise);
        ButterKnife.bind(this);
        setViewSettings();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_screen));
        startStopExerciseButton.setText(R.string.begin_exercise);
        loadIntentData();
        exerciseTitle.setText(exercise.getExerciseName());
        if(series == 0){
            seriesTextView.setVisibility(View.INVISIBLE);

        } else {
            updateSeriesTextView();
        }
        if(repeats == 0){
            repeatsTextView.setVisibility(View.INVISIBLE);
        } else {
            updateRepeatsTextView();
        }
        endOfSetButton.setEnabled(false);
        timerTextView.setText(getString(R.string.exercise_time) + ": " + "0:00:000");
        breakTimerTextView.setText(getString(R.string.breaks_time) + ": " +"0:00:000");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        fromActivity = intent.getIntExtra("from", -1);
        exercisesDone = intent.getStringArrayListExtra("exercisesDone");
        switch(fromActivity){
            case 0:
                exerciseId = intent.getLongExtra("exerciseId", -1);
                trainingPlanId = intent.getLongExtra("trainingPlan", -1);
                if(validateId(exerciseId) && validateId(trainingPlanId)){
                    exercise = loadExercise();
                    exerciseInTrainingPlan = loadExerciseInTrainingPlan();
                    series = exerciseInTrainingPlan.getSeries();
                    repeats = exerciseInTrainingPlan.getRepeats();
                    load = exerciseInTrainingPlan.getLoad();
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
            case 1:
                exerciseId = intent.getLongExtra("exerciseId", -1);
                if(validateId(exerciseId)){
                    exercise = loadExercise();
                    series = intent.getIntExtra("series", -1);
                    repeats = intent.getIntExtra("repeats", -1);
                    load = intent.getDoubleExtra("load", 0.0);
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
            case 2:
                exerciseToDoId = intent.getLongExtra("exerciseToDo", -1);
                if(validateId(exerciseToDoId)){
                    exerciseToDo = loadExerciseToDo();
                    exerciseId = exerciseToDo.getExercise().getId();
                    exercise = loadExercise();
                    series = exerciseToDo.getSeries();
                    repeats = exerciseToDo.getRepeats();
                    load = exerciseToDo.getLoad();
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
        }
    }

    private ExerciseInTrainingPlan loadExerciseInTrainingPlan() {
        return ExerciseInTrainingPlanRepository.findByGivenTrainingPlanAndExercise(this, TrainingPlanRepository.findById(this, trainingPlanId), exercise);
    }

    private Exercise loadExercise() {
        return ExerciseRepository.findById(this, exerciseId);
    }

    private ExerciseToDo loadExerciseToDo() {
        return ExerciseToDoRepository.findById(this, exerciseToDoId);
    }

    private boolean validateId(long id) {
        return id != -1;
    }

    @OnClick(R.id.endOfSetButton)
    public void endOfSetButtonPressed(){
        endOfSetButton.setEnabled(false);
        timerStops();
        breakTimerCounts();
        endOfSet();
        updateRepeatsTextView();
    }

    private void updateSeriesTextView() {
        seriesTextView.setText(getString(R.string.series) + " " + currentSet + "/" + series);
    }

    private void updateRepeatsTextView() {
        repeatsTextView.setText(getString(R.string.repeats) + " " + currentRepeat + "/" + repeats);
        if(repeats != 0 && currentRepeat == repeats){
            endOfSet();
            repeatsTextView.setText(getString(R.string.repeats) + " " + currentRepeat + "/" + repeats);
        }
    }

    private void endOfSet(){
        currentSet++;
        currentRepeat = 0;
        updateSeriesTextView();
        playMotivationSound();
        if(isEndOfSet()){
            endOfExercise();
        }
    }

    private boolean isEndOfSet(){
        return currentSet == series;
    }
    
    private void endOfExercise(){
        endOfSetButton.setEnabled(false);
        timerStops();
        startStopExerciseButton.setEnabled(false);
        unregisterSensor();
        confirmEndOfExercise();
    }

    private void playMotivationSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ta_da);
        mediaPlayer.start();
    }

    private void confirmEndOfExercise() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.popup_end_exercise_confirm);
        breakTimerStops();
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToPreviousActivity();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.startStopExerciseButton)
    public void startStopExerciseButtonPressed(){
        if(!exerciseContinues){
            timerCounts();
            breakTimerStops();
            endOfSetButton.setEnabled(true);
        } else {
            timerStops();
            breakTimerCounts();
            endOfSetButton.setEnabled(false);
        }
    }

    private void timerCounts(){
        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(updateTimerThread, 0);
        startStopExerciseButton.setText(R.string.stop_exercise);
        exerciseContinues = true;
    }

    private void timerStops(){
        timeBuffer += timeInMilliseconds;
        timerHandler.removeCallbacks(updateTimerThread);
        startStopExerciseButton.setText(R.string.begin_exercise);
        exerciseContinues = false;
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeBuffer + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerTextView.setText(getString(R.string.exercise_time) + ": " + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            timerHandler.postDelayed(this, 0);
        }
    };

    private void breakTimerCounts(){
        startBreakTime = SystemClock.uptimeMillis();
        breakTimerHandler.postDelayed(updateBreakTimerThread, 0);
    }

    private void breakTimerStops(){
        breakTimeBuffer += timeInMilliseconds;
        breakTimerHandler.removeCallbacks(updateBreakTimerThread);
    }

    private Runnable updateBreakTimerThread = new Runnable() {
        @Override
        public void run() {
            breakTimeInMilliseconds = SystemClock.uptimeMillis() - startBreakTime;
            updatedBreakTime = breakTimeBuffer + breakTimeInMilliseconds;
            int secs = (int) (updatedBreakTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedBreakTime % 1000);
            breakTimerTextView.setText(getString(R.string.breaks_time) + ": " + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            breakTimerHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onBackPressed(){
        timerStops();
        goToPreviousActivity();
    }

    private void goToPreviousActivity(){
        addExerciseToArchive();
        if(exercisesDone == null){
            exercisesDone = new ArrayList<>();
        }
        exercisesDone.add(String.valueOf(exerciseId));
        Intent intent;
        switch(fromActivity){
            case 0:
                intent = new Intent(this, UserExercisePlanExerciseListActivity.class);
                intent.putStringArrayListExtra("exercisesDone", exercisesDone);
                intent.putExtra("planId", trainingPlanId);
                startActivity(intent);
                finish();
                break;
            case 1:
                intent = new Intent(this, UserExerciseListActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                ExerciseToDoRepository.deleteExerciseToDo(this, exerciseToDo);
                intent = new Intent(this, UserExerciseToDoListActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void addExerciseToArchive() {
        int secs = (int) (updatedTime / 1000);
        ExerciseArchive exerciseArchive = new ExerciseArchive(currentSet, currentRepeat, load, getCurrentDateString(), secs, exercise);
        ExerciseArchiveRepository.addExerciseArchive(this, exerciseArchive);
    }

    private String getCurrentDateString(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(c.getTime());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensor();
    }

    private void unregisterSensor(){
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensor();
    }

    private void registerSensor(){
        if(isSensorRequired()){
            if(exercise.getExerciseName().equals(getString(R.string.push_up))){
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
                timeParameter = 2500;
            }
            if(exercise.getExerciseName().equals(getString(R.string.squat))){
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                timeParameter = 2500;
            }
            if(exercise.getExerciseName().equals(getString(R.string.table_pull))){
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
                timeParameter = 2500;
            }
            if(exercise.getExerciseName().equals(getString(R.string.arms_and_legs_raise_lie_on_belly))){
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
                timeParameter = 2500;
            }
            if(exercise.getExerciseName().equals(getString(R.string.pull_up))){
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                timeParameter = 2500;
            }
        }
    }

    private boolean isSensorRequired(){
        return sensorParameter != 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(exerciseContinues){
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                getAccelerometer(event);
            }
            if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
                getProximity(event);
            }
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = SystemClock.uptimeMillis();
        if(accelerationSquareRoot >= sensorParameter) {
            if(actualTime - lastSensorUpdate < timeParameter) {
                return;
            }
            lastSensorUpdate = actualTime;
            currentRepeat++;
            updateRepeatsTextView();
        }
    }

    private void getProximity(SensorEvent event) {
        float value = event.values[0];
        if(value <= sensorParameter){
            long actualTime = SystemClock.uptimeMillis();
            if(actualTime - lastSensorUpdate < timeParameter) {
                return;
            }
            lastSensorUpdate = actualTime;
            currentRepeat++;
            updateRepeatsTextView();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }
}
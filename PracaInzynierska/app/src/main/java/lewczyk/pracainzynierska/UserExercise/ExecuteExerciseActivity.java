package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class ExecuteExerciseActivity extends AppCompatActivity implements SensorEventListener{
    @BindView(R.id.exerciseToExecuteTitleTextView) TextView exerciseTitle;
    @BindView(R.id.exerciseToExecuteSeriesTextView) TextView seriesTextView;
    @BindView(R.id.exerciseToExecuteRepeatsTextView) TextView repeatsTextView;
    @BindView(R.id.exerciseToExecuteTimerTextView) TextView timerTextView;
    @BindView(R.id.startStopExerciseButton) Button startStopExerciseButton;
    @BindView(R.id.endOfSetButton) Button endOfSetButton;
    private int fromActivity, currentSet, series, currentRepeat, repeats, timeParameter;
    private long exerciseToDoId, exerciseId, trainingPlanId;
    private ExerciseToDo exerciseToDo;
    private Exercise exercise;
    private ExerciseInTrainingPlan exerciseInTrainingPlan;
    private long startTime, timeInMilliseconds, timeBuffer, updatedTime, lastSensorUpdate;
    private Handler timerHandler = new Handler();
    private double sensorParameter;
    private boolean exerciseContinues = false;
    private SensorManager sensorManager;

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
            endOfSetButton.setEnabled(false);
        } else {
            updateSeriesTextView();
        }
        if(repeats == 0){
            repeatsTextView.setVisibility(View.INVISIBLE);
        } else {
            updateRepeatsTextView();
        }
        timerTextView.setText("0:00:000");
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        fromActivity = intent.getIntExtra("from", -1);

        switch(fromActivity){
            case 0:
                exerciseId = intent.getLongExtra("exerciseId", -1);
                trainingPlanId = intent.getLongExtra("trainingPlan", -1);
                if(validateId(exerciseId) && validateId(trainingPlanId)){
                    exercise = loadExercise();
                    exerciseInTrainingPlan = loadExerciseInTrainingPlan();
                    series = exerciseInTrainingPlan.getSeries();
                    repeats = exerciseInTrainingPlan.getRepeats();
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
            case 1:
                exerciseId = intent.getLongExtra("exerciseId", -1);
                if(validateId(exerciseId)){
                    exercise = loadExercise();
                    series = intent.getIntExtra("series", -1);
                    repeats = intent.getIntExtra("repeats", -1);
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
        timerStops();
        currentSet++;
        currentRepeat = 0;
        updateSeriesTextView();
        updateRepeatsTextView();
        if(isEndOfSet()){
            endOfExercise();
        }
    }

    private void updateSeriesTextView() {
        seriesTextView.setText(getString(R.string.series) + " " + currentSet + "/" + series);
    }

    private void updateRepeatsTextView() {
        repeatsTextView.setText(getString(R.string.repeats) + " " + currentRepeat + "/" + repeats);
        if(currentRepeat == repeats){
            endOfSet();
        }
    }

    private void endOfSet(){
        currentSet++;
        currentRepeat = 0;
        updateSeriesTextView();
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
        // TODO: 24.09.2017 end of exercise logic 
    }

    @OnClick(R.id.startStopExerciseButton)
    public void startStopExerciseButtonPressed(){
        if(!exerciseContinues){
            timerCounts();
        } else {
            timerStops();
        }
    }

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeBuffer + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerTextView.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            timerHandler.postDelayed(this, 0);
        }
    };

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

    @Override
    public void onBackPressed(){
        super.onBackPressed(); // TODO: 24.09.2017 returning to previous screen 
        timerStops();
        finish();
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
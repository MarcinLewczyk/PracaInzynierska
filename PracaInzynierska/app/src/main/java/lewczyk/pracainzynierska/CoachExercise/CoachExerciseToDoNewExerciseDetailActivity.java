package lewczyk.pracainzynierska.CoachExercise;

import android.app.DatePickerDialog;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseToDoListActivity;

public class CoachExerciseToDoNewExerciseDetailActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int FIELDS_MINIMUM_LENGTH = 0;
    @BindView(R.id.coachExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseToDoDateTextView) TextView date;
    @BindView(R.id.coachExerciseToDoSeriesTitleTextView) TextView seriesTextView;
    @BindView(R.id.coachExerciseToDoRepeatsTitleTextView) TextView repeatsTextView;
    @BindView(R.id.coachExerciseToDoLoadTitleTextView) TextView loadTextView;
    @BindView(R.id.coachExerciseToDoSeriesEditText) EditText series;
    @BindView(R.id.coachExerciseToDoRepeatsEditText) EditText repeats;
    @BindView(R.id.coachExerciseToDoLoadEditText) EditText load;
    private Calendar myCalendar = Calendar.getInstance();
    private long exerciseId;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_to_do_new_exercise_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.set_exercise_to_do_params));
        loadIntent();
        setCalendarDatePicker();
        loadExerciseName();
        if(checkIfMapExercise()){
            setMapModeUI();
        }
    }

    private void loadIntent(){
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseToDoId", DEFAULT_ID);
        exercise = loadExercise();
    }

    private void loadExerciseName() {
        if(validateId()){
            exerciseName.setText(exercise.getExerciseName());
        }
    }

    private boolean validateId(){
        return exerciseId != DEFAULT_ID;
    }

    private void setCalendarDatePicker() {
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                };

        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(CoachExerciseToDoNewExerciseDetailActivity.this, datePicker, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });
    }

    private boolean checkIfMapExercise(){
        return exercise.getExerciseName().equals(getString(R.string.running)) ||  exercise.getExerciseName().equals(getString(R.string.cycling));
    }

    private void setMapModeUI(){
        seriesTextView.setEnabled(false);
        series.setEnabled(false);
        series.setHint("");
        repeatsTextView.setEnabled(false);
        repeats.setEnabled(false);
        repeats.setHint("");
        loadTextView.setText(R.string.enter_distance);
        load.setHint(R.string.route_distance);
    }

    private void updateLabel() {
        String myFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        date.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.coachExerciseToDoAddButton)
    public void addExerciseToDoButtonPressed(){
        if(checkIfMapExercise()){
            if(validateLoad()){
                ExerciseToDoRepository.addExerciseToDo(this,
                        new ExerciseToDo(
                                0, 0,
                                Double.parseDouble(load.getText().toString()),
                                transformDateToString(), exercise));
                addNotification();
                moveToExerciseToDoListActivity();
            }
        } else if(validateFields()){
            ExerciseToDoRepository.addExerciseToDo(this,
                    new ExerciseToDo(
                            Integer.parseInt(series.getText().toString()),
                            Integer.parseInt(repeats.getText().toString()),
                            Double.parseDouble(load.getText().toString()),
                            transformDateToString(), exercise));
            addNotification();
            moveToExerciseToDoListActivity();
        }
    }

    private Exercise loadExercise() {
        return ExerciseRepository.findById(this, exerciseId);
    }

    private String transformDateToString(){
        if(date.getText().toString().equals(getString(R.string.tap_to_choose_date))){
            return "";
        }
        return  date.getText().toString().substring(0,4) +
                date.getText().toString().substring(5,7) +
                date.getText().toString().substring(8,10);
    }

    private void addNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getString(R.string.exercises))
                        .setContentText(getString(R.string.exercise_reminder))
                        .setWhen(dateToLong());
        Intent notificationIntent = new Intent(this, UserExerciseToDoListActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private long dateToLong(){
        String stringDate = date.getText().toString();
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
        long milliseconds = 0;
        try {
            Date d = f.parse(stringDate);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    private boolean validateFields(){
        return validateSeries() && validateRepeats() && validateLoad();
    }

    private boolean validateSeries() {
        if(series.getText().toString().length() == FIELDS_MINIMUM_LENGTH){
            series.setText("0");
        }
        try{
            Integer.parseInt(series.getText().toString());
        }catch(NumberFormatException e){
            series.setError(getString(R.string.must_be_integer));
            return false;
        }
        return true;
    }

    private boolean validateRepeats() {
        if(repeats.getText().toString().length() == FIELDS_MINIMUM_LENGTH){
            repeats.setText("0");
        }
        try{
            Integer.parseInt(repeats.getText().toString());
        }catch(NumberFormatException e){
            repeats.setError(getString(R.string.must_be_integer));
            return false;
        }
        return true;
    }

    private boolean validateLoad() {
        if(load.getText().toString().length() == FIELDS_MINIMUM_LENGTH){
            load.setText("0.0");
        }
        try{
            Double.parseDouble(load.getText().toString());
        }catch(NumberFormatException e){
            load.setError(getString(R.string.must_be_floating_point));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        moveToExerciseToDoListActivity();
    }

    private void moveToExerciseToDoListActivity(){
        Intent intent = new Intent(this, CoachExerciseToDoListActivity.class);
        startActivity(intent);
        finish();
    }
}
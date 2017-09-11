package lewczyk.pracainzynierska.CoachExercise;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class CoachExerciseToDoNewExerciseDetailActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int FIELDS_MINIMUM_LENGTH = 0;
    @BindView(R.id.coachExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseToDoDateTextView) TextView date;
    @BindView(R.id.coachExerciseToDoSeriesEditText) EditText series;
    @BindView(R.id.coachExerciseToDoRepeatsEditText) EditText repeats;
    @BindView(R.id.coachExerciseToDoLoadEditText) EditText load;
    private Calendar myCalendar = Calendar.getInstance();
    private long exerciseId;

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
    }

    private void loadIntent(){
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseToDoId", DEFAULT_ID);
    }

    private void loadExerciseName() {
        if(validateId()){
            Exercise exercise = loadExercise();
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

    private void updateLabel() {
        String myFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        date.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.coachExerciseToDoAddButton)
    public void addExerciseToDoButtonPressed(){
        if(validateFields()){
            ExerciseToDoRepository.addExerciseToDo(this,
                    new ExerciseToDo(
                            Integer.parseInt(series.getText().toString()),
                            Integer.parseInt(repeats.getText().toString()),
                            Double.parseDouble(load.getText().toString()),
                            transformDateToString(), loadExercise()));
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
package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNewExerciseDetail;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoList.CoachExerciseToDoListActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseToDoList.UserExerciseToDoListActivity;

public class CoachExerciseToDoNewExerciseDetailActivity extends AppCompatActivity implements
        CoachExerciseToDoNewExerciseDetailView,
        CoachExerciseToDoNewExerciseNavigator {
    @BindView(R.id.coachExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseToDoDateTextView) TextView date;
    @BindView(R.id.coachExerciseToDoSeriesTitleTextView) TextView seriesTextView;
    @BindView(R.id.coachExerciseToDoRepeatsTitleTextView) TextView repeatsTextView;
    @BindView(R.id.coachExerciseToDoLoadTitleTextView) TextView loadTextView;
    @BindView(R.id.coachExerciseToDoSeriesEditText) EditText series;
    @BindView(R.id.coachExerciseToDoRepeatsEditText) EditText repeats;
    @BindView(R.id.coachExerciseToDoLoadEditText) EditText load;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int FIELDS_MINIMUM_LENGTH = 0;
    private Calendar myCalendar = Calendar.getInstance();
    private CoachExerciseToDoNewExerciseDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachExerciseToDoNewExerciseDetailPresenter(this, this);
        setContentView(R.layout.activity_coach_exercise_to_do_new_exercise_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadExercise();
        setTitle(getString(R.string.set_exercise_to_do_params));
        loadViewContent();
        setCalendarDatePicker();
        if(checkIfMapExercise()){
            setMapModeUI();
        }
    }

    @Override
    public long loadIntent(){
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseToDoId", DEFAULT_ID);
    }

    private void loadViewContent() {
        if(validateId()){
            exerciseName.setText(presenter.getExerciseName());
        }
    }

    private boolean validateId(){
        return presenter.validateId();
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
        String name = presenter.getExerciseName();
        return name.equals(getString(R.string.running)) ||  name.equals(getString(R.string.cycling));
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
                presenter.addMapExerciseToDo();
            }
        } else if(validateFields()){
            presenter.addExerciseToDo();
        }
    }

    @Override
    public String getDateString(){
        return date.getText().toString();
    }

    @Override
    public String getSeriesString(){
        return series.getText().toString();
    }

    @Override
    public String getRepeatsString(){
        return repeats.getText().toString();
    }

    @Override
    public String getLoadString(){
        return load.getText().toString();
    }

    @Override
    public void addNotification(long date){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getString(R.string.exercises))
                        .setContentText(getString(R.string.exercise_reminder))
                        .setWhen(date);
        Intent notificationIntent = new Intent(this, UserExerciseToDoListActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
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
        navigateToExerciseToDoListActivity();
    }

    @Override
    public void navigateToExerciseToDoListActivity(){
        Intent intent = new Intent(this, CoachExerciseToDoListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
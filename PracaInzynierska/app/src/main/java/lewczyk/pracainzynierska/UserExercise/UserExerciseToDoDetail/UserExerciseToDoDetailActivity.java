package lewczyk.pracainzynierska.UserExercise.UserExerciseToDoDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.ActivityOrigin;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.ExecuteExercise.ExecuteExerciseActivity;
import lewczyk.pracainzynierska.UserExercise.Tracker.TrackerActivity;
import lewczyk.pracainzynierska.UserExercise.UserExerciseToDoList.UserExerciseToDoListActivity;

public class UserExerciseToDoDetailActivity extends AppCompatActivity implements UserExerciseToDoDetailView{
    @BindView(R.id.userExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.userExerciseToDoMusclePartTextView) TextView musclePart;
    @BindView(R.id.userExerciseToDoDateTextView) TextView date;
    @BindView(R.id.userExerciseToDoSeriesTextView) TextView series;
    @BindView(R.id.userExerciseToDoRepeatsTextView) TextView repeats;
    @BindView(R.id.userExerciseToDoLoadTextView) TextView load;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserExerciseToDoDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserExerciseToDoDetailPresenter(this);
        setContentView(R.layout.activity_user_exercise_to_do_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadExerciseToDo();
        loadViewContent();
    }

    private void loadViewContent() {
        if(validateId()){
            exerciseName.setText(presenter.getExerciseName());
            musclePart.setText(presenter.getMusclePart());
            date.setText(presenter.getDate());
            series.setText(presenter.getSeries());
            repeats.setText(presenter.getRepeats());
            load.setText(presenter.getLoad());
        } else if(checkIfMapExercise()){
            setMapModeUI();
        } else {
            exerciseName.setText(R.string.no_data);
        }
    }

    private boolean validateId() {
        return presenter.validateId();
    }

    private void setMapModeUI(){
        exerciseName.setText(presenter.getExerciseName());
        musclePart.setText(presenter.getMusclePart());
        date.setText(presenter.getDate());
        series.setEnabled(false);
        repeats.setEnabled(false);
        load.setText(presenter.getLoad());
    }
    
    @OnClick(R.id.userExerciseToDoExecuteButton)
    public void executeExerciseButtonPressed(){
        Intent intent;
        if(checkIfMapExercise()){
            intent = new Intent(this, TrackerActivity.class);
        } else {
            intent = new Intent(this, ExecuteExerciseActivity.class);
        }
        intent.putExtra("exerciseToDo", presenter.getExerciseToDoId());
        intent.putExtra("from", ActivityOrigin.UserExerciseToDoDetailActivity.which);
        startActivity(intent);
        finish();
    }

    private boolean checkIfMapExercise(){
        String name = presenter.getExerciseName();
        return name.equals(getString(R.string.running)) ||  name.equals(getString(R.string.cycling));
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseToDoId", DEFAULT_ID);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExerciseToDoListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
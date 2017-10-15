package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoList.CoachExerciseToDoListActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoDetailActivity extends AppCompatActivity implements CoachExerciseToDoDetailView, CoachExerciseToDoDetailNavigator{
    @BindView(R.id.coachExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseToDoMusclePartTextView) TextView musclePart;
    @BindView(R.id.coachExerciseToDoDateTextView) TextView date;
    @BindView(R.id.coachExerciseToDoSeriesTextView) TextView series;
    @BindView(R.id.coachExerciseToDoRepeatsTextView) TextView repeats;
    @BindView(R.id.coachExerciseToDoLoadTextView) TextView load;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachExerciseToDoDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachExerciseToDoDetailPresenter(this, this);
        setContentView(R.layout.activity_coach_exercise_to_do_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadExerciseToDo();
        setTitle(getString(R.string.exercise_to_do_details));
        loadViewContent();
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseToDoId", DEFAULT_ID);
    }

    private void loadViewContent(){
        if(validateId()){
            exerciseName.setText(presenter.getExerciseName());
            musclePart.setText(presenter.getMusclePart());
            date.setText(presenter.getExerciseToDoDate());
            series.setText(presenter.getExerciseToDoSeries());
            repeats.setText(presenter.getExerciseToDoRepeats());
            load.setText(presenter.getExerciseToDoLoad());
        } else {
            exerciseName.setText(R.string.no_data);
        }
    }

    @OnClick(R.id.coachExerciseToDoDelButton)
    public void delExerciseToDo(){
        if(validateId()){
            presenter.deleteCurrentExerciseToDo();
        }
    }

    private boolean validateId() {
        return presenter.validateId();
    }

    @Override
    public void onBackPressed(){
        navigateToExerciseToDoList();
    }

    @Override
    public void navigateToExerciseToDoList(){
        Intent intent = new Intent(this, CoachExerciseToDoListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
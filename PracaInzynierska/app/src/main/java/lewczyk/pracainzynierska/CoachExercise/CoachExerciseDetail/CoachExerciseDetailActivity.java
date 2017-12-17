package lewczyk.pracainzynierska.CoachExercise.CoachExerciseDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseList.CoachExerciseListActivity;
import lewczyk.pracainzynierska.CoachExercise.CoachNewExercise.CoachNewExerciseActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachExerciseDetailActivity extends AppCompatActivity implements CoachExerciseDetailView, CoachExerciseDetailNavigator{
    @BindView(R.id.coachExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseMusclePartTextView) TextView musclePart;
    @BindView(R.id.coachExerciseDifficultLevelTextView) TextView difficultLevel;
    @BindView(R.id.coachExerciseEquipmentRequirementTextView) TextView equipmentRequirement;
    @BindView(R.id.coachExerciseExerciseTypeTextView) TextView exerciseType;
    @BindView(R.id.coachExerciseDemonstrationWebView) WebView demonstration;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachExerciseDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachExerciseDetailPresenter(this, this);
        setContentView(R.layout.activity_coach_exercise_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadExercise();
        setTitle(getString(R.string.exercise_details));
        loadViewContent();
    }

    private void loadViewContent() {
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(validateId()){
            exerciseName.setText(presenter.getExerciseName());
            musclePart.setText(presenter.getMusclePart());
            difficultLevel.setText(presenter.getDifficultLevel());
            equipmentRequirement.setText(presenter.getEquipmentRequirements());
            exerciseType.setText(presenter.getExerciseType());
            demonstration.loadDataWithBaseURL(null, String.format(htmlParam, presenter.getExerciseDemonstration()), "text/html", "utf-8", null);
        } else {
            demonstration.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    @OnClick(R.id.coachExerciseModButton)
    public void modificationButtonPressed(){
        if(validateId()){
            Intent intent = new Intent(this, CoachNewExerciseActivity.class);
            intent.putExtra("exerciseId", presenter.getExerciseId());
            startActivity(intent);
            finish();
        }
    }
/*
    @OnClick(R.id.coachExerciseDelButton)
    public void deleteButtonPressed(){
        if(validateId()){
            presenter.deleteCurrentExercise();
        }
    }
*/
    private boolean validateId() {
        return presenter.validateId();
    }

    @Override
    public void onBackPressed(){
        navigateToCoachExerciseListActivity();
    }

    @Override
    public void navigateToCoachExerciseListActivity(){
        Intent intent = new Intent(this, CoachExerciseListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }

    @Override
    public void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
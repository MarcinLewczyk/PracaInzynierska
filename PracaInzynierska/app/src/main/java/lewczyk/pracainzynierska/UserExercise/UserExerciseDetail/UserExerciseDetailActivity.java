package lewczyk.pracainzynierska.UserExercise.UserExerciseDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseList.UserExerciseListActivity;
import lewczyk.pracainzynierska.UserExercise.UserExerciseParameters.UserExerciseParametersActivity;

public class UserExerciseDetailActivity extends AppCompatActivity implements UserExerciseDetailView{
    @BindView(R.id.userExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.userExerciseMusclePartTextView) TextView musclePart;
    @BindView(R.id.userExerciseDifficultLevelTextView) TextView difficultLevel;
    @BindView(R.id.userExerciseEquipmentRequirementTextView) TextView equipmentRequirement;
    @BindView(R.id.userExerciseExerciseTypeTextView) TextView exerciseType;
    @BindView(R.id.userExerciseDemonstrationWebView) WebView demonstration;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserExerciseDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserExerciseDetailPresenter(this);
        setContentView(R.layout.activity_user_exercise_detail);
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

    @OnClick(R.id.userExerciseExecuteButton)
    public void exerciseExecuteButtonPressed(){
        if(validateId()){
            Intent intent = new Intent(this, UserExerciseParametersActivity.class);
            intent.putExtra("exerciseId", presenter.getExerciseId());
            startActivity(intent);
            finish();
        }
    }

    private boolean validateId() {
        return presenter.validateId();
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExerciseListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
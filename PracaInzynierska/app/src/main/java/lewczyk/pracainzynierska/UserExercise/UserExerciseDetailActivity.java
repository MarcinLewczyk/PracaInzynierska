package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class UserExerciseDetailActivity extends AppCompatActivity {
    private int DEFAULT_ID = -1;
    private long exerciseId;
    @BindView(R.id.userExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.userExerciseMusclePartTextView) TextView musclePart;
    @BindView(R.id.userExerciseDifficultLevelTextView) TextView difficultLevel;
    @BindView(R.id.userExerciseEquipmentRequirementTextView) TextView equipmentRequirement;
    @BindView(R.id.userExerciseExerciseTypeTextView) TextView exerciseType;
    @BindView(R.id.userExerciseDemonstrationWebView) WebView demonstration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_details));
        loadIntent();
        loadStrings();
    }

    private void loadStrings() {
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(!validateId()){
            demonstration.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        } else {
            Exercise exercise = loadExercise();
            exerciseName.setText(exercise.getExerciseName());
            musclePart.setText(exercise.getMusclePart());
            difficultLevel.setText(DifficultLevelRepository.findById(this, exercise.getDifficultLevel().getId()).getName());
            equipmentRequirement.setText(EquipmentRequirementRepository.findById(this, exercise.getEquipmentRequirement().getId()).getName());
            exerciseType.setText(ExerciseTypeRepository.findById(this, exercise.getExerciseType().getId()).getName());
            demonstration.loadDataWithBaseURL(null, String.format(htmlParam, exercise.getDemonstration()), "text/html", "utf-8", null);
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    private Exercise loadExercise(){
        return ExerciseRepository.findById(this, exerciseId);
    }

    private boolean validateId() {
        return exerciseId != DEFAULT_ID;
    }

    @OnClick(R.id.userExerciseExecuteButton)
    public void exerciseExecuteButtonPressed(){
        if(validateId()){
            Intent intent = new Intent(this, UserExerciseParametersActivity.class);
            intent.putExtra("exerciseId", exerciseId);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExerciseListActivity.class);
        startActivity(intent);
        finish();
    }
}
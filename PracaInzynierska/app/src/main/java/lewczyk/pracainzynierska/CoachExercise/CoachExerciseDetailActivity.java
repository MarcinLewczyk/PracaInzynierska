package lewczyk.pracainzynierska.CoachExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachExerciseDetailActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private long exerciseId;
    @BindView(R.id.coachExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseMusclePartTextView) TextView musclePart;
    @BindView(R.id.coachExerciseDifficultLevelTextView) TextView difficultLevel;
    @BindView(R.id.coachExerciseEquipmentRequirementTextView) TextView equipmentRequirement;
    @BindView(R.id.coachExerciseExerciseTypeTextView) TextView exerciseType;
    @BindView(R.id.coachExerciseDemonstrationWebView) WebView demonstration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_details));
        loadStrings();
    }

    private void loadStrings() {
        loadIntent();
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(validateId()){
            Exercise exercise = loadExercise();
            exerciseName.setText(exercise.getExerciseName());
            musclePart.setText(exercise.getMusclePart());
            difficultLevel.setText(DifficultLevelRepository.findById(this, exercise.getDifficultLevel().getId()).getName());
            equipmentRequirement.setText(EquipmentRequirementRepository.findById(this, exercise.getEquipmentRequirement().getId()).getName());
            exerciseType.setText(ExerciseTypeRepository.findById(this, exercise.getExerciseType().getId()).getName());
            demonstration.loadDataWithBaseURL(null, String.format(htmlParam, exercise.getDemonstration()), "text/html", "utf-8", null);
        } else {
            demonstration.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    @OnClick(R.id.coachExerciseModButton)
    public void modificationButtonPressed(){
        if(validateId()){
            Intent intent = new Intent(this, CoachNewExerciseActivity.class);
            intent.putExtra("exerciseId", exerciseId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.coachExerciseDelButton)
    public void deleteButtonPressed(){
        if(validateId()){
            ExerciseRepository.deleteExercise(this, loadExercise());
            moveToCoachExerciseListActivity();
        }
    }

    private Exercise loadExercise(){
        return ExerciseRepository.findById(this, exerciseId);
    }

    private boolean validateId() {
        return exerciseId != DEFAULT_ID;
    }

    @Override
    public void onBackPressed(){
        moveToCoachExerciseListActivity();
    }

    public void moveToCoachExerciseListActivity(){
        Intent intent = new Intent(this, CoachExerciseListActivity.class);
        startActivity(intent);
        finish();
    }
}
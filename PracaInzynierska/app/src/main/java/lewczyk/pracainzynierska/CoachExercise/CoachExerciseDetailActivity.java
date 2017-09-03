package lewczyk.pracainzynierska.CoachExercise;

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

public class CoachExerciseDetailActivity extends AppCompatActivity {
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
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", -1L);
        loadStrings();
    }

    private void loadStrings() {
        setTitle(getString(R.string.exercise_details));
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(exerciseId == -1L){
            demonstration.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        } else {
            Exercise exercise = ExerciseRepository.findById(this, exerciseId);
            exerciseName.setText(exercise.getExerciseName());
            musclePart.setText(exercise.getMusclePart());
            difficultLevel.setText(DifficultLevelRepository.findById(this, exercise.getDifficultLevel().getId()).getName());
            equipmentRequirement.setText(EquipmentRequirementRepository.findById(this, exercise.getEquipmentRequirement().getId()).getName());
            exerciseType.setText(ExerciseTypeRepository.findById(this, exercise.getExerciseType().getId()).getName());
            demonstration.loadData(String.format(htmlParam, exercise.getDemonstration()), "text/html", "utf-8");
        }
    }

    @OnClick(R.id.coachExerciseModButton)
    public void moveToExerciseMod(){
        if(exerciseId != -1){
            Intent intent = new Intent(getApplicationContext(), CoachNewExerciseActivity.class);
            intent.putExtra("noteId", exerciseId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.coachExerciseDelButton)
    public void delExercise(){
        if(exerciseId != -1){
            ExerciseRepository.deleteExercise(getApplicationContext(), ExerciseRepository.findById(this, exerciseId));
            Intent intent = new Intent(getApplicationContext(), CoachExerciseListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), CoachExerciseListActivity.class);
        startActivity(intent);
        finish();
    }
}
package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.ExerciseInTrainingPlanAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;
import lewczyk.pracainzynierska.R;

public class UserExercisePlanExerciseListActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private long planId;
    private ArrayList<String> exercisesDone;
    @BindView(R.id.userExerciseInTrainingPlanListView) ListView exercisesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_plan_exercise_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercises_in_training_plan));
        loadIntent();
        if(validateId()){
            setListViewContent();
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        planId = intent.getLongExtra("planId", DEFAULT_ID);
        exercisesDone = intent.getStringArrayListExtra("exercisesDone");
    }

    private boolean validateId() {
        return planId != DEFAULT_ID;
    }

    private void setListViewContent() {
        ArrayList<Exercise> exercisesInTrainingPlan = (ArrayList) ExerciseInTrainingPlanRepository.findAllWithGivenTrainingPlan(this, loadTrainingPlan());
        ExerciseInTrainingPlanAdapter adapter = new ExerciseInTrainingPlanAdapter(exercisesInTrainingPlan, loadTrainingPlan(), this, exercisesDone);
        exercisesListView.setAdapter(adapter);
    }

    private TrainingPlan loadTrainingPlan(){
        return TrainingPlanRepository.findById(this, planId);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExercisePlanListActivity.class);
        startActivity(intent);
        finish();
    }
}
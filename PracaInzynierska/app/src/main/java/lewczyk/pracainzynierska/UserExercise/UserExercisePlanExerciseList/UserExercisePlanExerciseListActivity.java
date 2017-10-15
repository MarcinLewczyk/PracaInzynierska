package lewczyk.pracainzynierska.UserExercise.UserExercisePlanExerciseList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.ExerciseInTrainingPlanAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExercisePlanList.UserExercisePlanListActivity;

public class UserExercisePlanExerciseListActivity extends AppCompatActivity implements UserExercisePlanExerciseListView{
    @BindView(R.id.userExerciseInTrainingPlanListView) ListView exercisesListView;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ArrayList<String> exercisesDone;
    private UserExercisePlanExerciseListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserExercisePlanExerciseListPresenter(this);
        setContentView(R.layout.activity_user_exercise_plan_exercise_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadTrainingPlan();
        setTitle(getString(R.string.exercises_in_training_plan));
        if(validateId()){
            setListViewContent(presenter.getExercisesInTrainingPlan());
        }
    }

    private boolean validateId() {
        return presenter.validateId();
    }

    private void setListViewContent(ArrayList content) {
        ExerciseInTrainingPlanAdapter adapter = new ExerciseInTrainingPlanAdapter(content, presenter.getTrainingPlan(), this, exercisesDone);
        exercisesListView.setAdapter(adapter);
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        exercisesDone = intent.getStringArrayListExtra("exercisesDone");
        return intent.getLongExtra("planId", DEFAULT_ID);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExercisePlanListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
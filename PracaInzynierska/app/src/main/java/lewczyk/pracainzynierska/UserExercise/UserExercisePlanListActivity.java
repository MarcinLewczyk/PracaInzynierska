package lewczyk.pracainzynierska.UserExercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.TrainingPlanAdapter;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;
import lewczyk.pracainzynierska.R;

public class UserExercisePlanListActivity extends AppCompatActivity {
    @BindView(R.id.userTrainingPlanListView) ListView trainingPlansListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_plan_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.training_plans));
        setListContent();
    }

    private void setListContent() {
        ArrayList<TrainingPlan> trainingPlans = (ArrayList) TrainingPlanRepository.findAll(this);
        TrainingPlanAdapter adapter = new TrainingPlanAdapter(trainingPlans , this);
        trainingPlansListView.setAdapter(adapter);
    }
}
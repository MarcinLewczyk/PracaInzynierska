package lewczyk.pracainzynierska.UserExercise.UserExercisePlanList;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.TrainingPlanAdapter;
import lewczyk.pracainzynierska.R;

public class UserExercisePlanListActivity extends AppCompatActivity implements UserExercisePlanListView{
    @BindView(R.id.userTrainingPlanListView) ListView trainingPlansListView;
    private UserExercisePlanListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserExercisePlanListPresenter(this);
        setContentView(R.layout.activity_user_exercise_plan_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.training_plans));
        setListContent(presenter.getAllTrainingPlans());
    }

    private void setListContent(ArrayList content) {
        TrainingPlanAdapter adapter = new TrainingPlanAdapter(content, this);
        trainingPlansListView.setAdapter(adapter);
    }

    @Override
    public Context getContext(){
        return this;
    }
}
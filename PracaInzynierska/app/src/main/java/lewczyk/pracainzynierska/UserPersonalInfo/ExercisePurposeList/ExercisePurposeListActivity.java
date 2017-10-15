package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.ExercisePurposeAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeEdit.ExercisePurposeEditActivity;

public class ExercisePurposeListActivity extends AppCompatActivity implements ExercisePurposeListView{
    @BindView(R.id.exercisePurposeListView) ListView exercisePurposesListView;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ExercisePurposeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExercisePurposeListPresenter(this);
        setContentView(R.layout.activity_exercise_purpose_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.exercise_purposes);
        showExercisePurposeList(presenter.getExercisePurposeList());
    }

    private void showExercisePurposeList(ArrayList content) {
        ExercisePurposeAdapter adapter = new ExercisePurposeAdapter(content, this);
        exercisePurposesListView.setAdapter(adapter);
    }

    @OnClick(R.id.exercisePurposeAddButton)
    public void navigateToExercisePurposeEditActivity(){
        Intent intent = new Intent(this, ExercisePurposeEditActivity.class);
        intent.putExtra("purposeId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.CoachExerciseToDoAdapter;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNew.CoachExerciseToDoNewActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoListActivity extends AppCompatActivity implements CoachExerciseToDoListView{
    @BindView(R.id.coachExerciseToDoListView)  ListView exercisesList;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachExerciseToDoListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachExerciseToDoListPresenter(this);
        setContentView(R.layout.activity_coach_exercise_to_do);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercises_list_to_do));
        setListViewContent(presenter.getExerciseToDoList());
    }

    private void setListViewContent(ArrayList content){
        CoachExerciseToDoAdapter adapter = new CoachExerciseToDoAdapter(content, this);
        exercisesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachExerciseToDoAddButton)
    public void addNewExerciseToDo(){
        Intent intent = new Intent(this, CoachExerciseToDoNewActivity.class);
        intent.putExtra("exerciseToDoId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
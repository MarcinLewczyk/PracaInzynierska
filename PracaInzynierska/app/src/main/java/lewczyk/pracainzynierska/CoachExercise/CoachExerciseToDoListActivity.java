package lewczyk.pracainzynierska.CoachExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.CoachExerciseToDoAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoListActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    @BindView(R.id.coachExerciseToDoListView)  ListView exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_to_do);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercises_list_to_do));
        setListViewContent();
    }

    private void setListViewContent(){
        ArrayList<ExerciseToDo> coachExerciseToDoList = (ArrayList) ExerciseToDoRepository.findAll(this);
        CoachExerciseToDoAdapter adapter = new CoachExerciseToDoAdapter(coachExerciseToDoList, this);
        exercisesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachExerciseToDoAddButton)
    public void addNewExerciseToDo(){
        Intent intent = new Intent(this, CoachExerciseToDoNewActivity.class);
        intent.putExtra("exerciseToDoId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }
}
package lewczyk.pracainzynierska.UserExercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.UserExerciseToDoAdapter;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class UserExerciseToDoListActivity extends AppCompatActivity {
    @BindView(R.id.userExerciseToDoListView) ListView exercisesToDoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_to_do_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercises_list_to_do));
        setListContent();
    }

    private void setListContent() {
        ArrayList<ExerciseToDo> exercises = (ArrayList) ExerciseToDoRepository.findAll(this);
        UserExerciseToDoAdapter adapter = new UserExerciseToDoAdapter(exercises , this);
        exercisesToDoListView.setAdapter(adapter);
    }
}
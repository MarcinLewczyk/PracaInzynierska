package lewczyk.pracainzynierska.UserExercise.UserExerciseToDoList;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.UserExerciseToDoAdapter;
import lewczyk.pracainzynierska.R;

public class UserExerciseToDoListActivity extends AppCompatActivity implements UserExerciseToDoListView{
    @BindView(R.id.userExerciseToDoListView) ListView exercisesToDoListView;
    private UserExerciseToDoListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserExerciseToDoListPresenter(this);
        setContentView(R.layout.activity_user_exercise_to_do_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercises_list_to_do));
        setListContent(presenter.getExerciseToDoList());
    }

    private void setListContent(ArrayList content) {
        UserExerciseToDoAdapter adapter = new UserExerciseToDoAdapter(content , this);
        exercisesToDoListView.setAdapter(adapter);
    }

    @Override
    public Context getContext(){
        return this;
    }
}
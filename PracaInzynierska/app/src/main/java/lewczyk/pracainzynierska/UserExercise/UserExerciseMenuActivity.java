package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.R;

public class UserExerciseMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_menu);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_menu));
    }

    @OnClick(R.id.userExerciseToDoButton)
    public void exerciseToDoButtonPressed(){
        Intent intent = new Intent(this, UserExerciseToDoListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.userExercisePlanButton)
    public void exercisePlanButtonPressed(){
        Intent intent = new Intent(this, UserExercisePlanListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.userExerciseListButton)
    public void exerciseListButtonPressed(){
        Intent intent = new Intent(this, UserExerciseListActivity.class);
        startActivity(intent);
    }
}
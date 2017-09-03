package lewczyk.pracainzynierska.UserExercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lewczyk.pracainzynierska.R;

public class UserExerciseMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_menu);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_menu));
    }
}
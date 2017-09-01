package lewczyk.pracainzynierska.CoachExercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_to_do);
        setTitle(getString(R.string.exercises_list_to_do));
    }
}
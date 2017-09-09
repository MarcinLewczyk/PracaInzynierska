package lewczyk.pracainzynierska.UserExercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import lewczyk.pracainzynierska.R;

public class UserExerciseParametersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_parameters);
        ButterKnife.bind(this);
    }
}
package lewczyk.pracainzynierska.CoachExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.CoachExerciseAdapter;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachExerciseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_list);
        setTitle(getString(R.string.exercises_list));

        ButterKnife.bind(this);

        ListView exercisesList = (ListView) findViewById(R.id.coachExerciseListView);

        ArrayList<Exercise> coachExerciseList = (ArrayList) ExerciseRepository.findAll(this);
        CoachExerciseAdapter adapter = new CoachExerciseAdapter(coachExerciseList, this);
        exercisesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachExerciseAddButton)
    public void addNewExercise(){
        Intent intent = new Intent(getApplicationContext(), CoachNewExerciseActivity.class);
        intent.putExtra("noteId", -1L);
        startActivity(intent);
        finish();
    }


    //// TODO: 01.09.2017 dodać przyciski do filtrów
}
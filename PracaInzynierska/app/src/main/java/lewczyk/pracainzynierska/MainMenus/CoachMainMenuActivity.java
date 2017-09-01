package lewczyk.pracainzynierska.MainMenus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseListActivity;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoActivity;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteListActivity;
import lewczyk.pracainzynierska.R;

public class CoachMainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main_menu);
        setTitle(R.string.coach_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.coachExerciseToDoButton)
    public void moveToExerciseToDo(){
        Intent intent = new Intent(getApplicationContext(), CoachExerciseToDoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coachExerciseListButton)
    public void moveToExerciseList(){
        Intent intent = new Intent(getApplicationContext(), CoachExerciseListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coachNotesListButton)
    public void moveToNotesList(){
        Intent intent = new Intent(getApplicationContext(), CoachNoteListActivity.class);
        startActivity(intent);
    }
}
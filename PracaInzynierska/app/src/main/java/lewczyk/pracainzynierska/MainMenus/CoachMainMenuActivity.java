package lewczyk.pracainzynierska.MainMenus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseListActivity;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoListActivity;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteListActivity;
import lewczyk.pracainzynierska.R;

public class CoachMainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main_menu);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.coach_menu);
    }

    @OnClick(R.id.coachExerciseToDoButton)
    public void exerciseToDoButtonPressed(){
        Intent intent = new Intent(this, CoachExerciseToDoListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coachExerciseListButton)
    public void exerciseListButtonPressed(){
        Intent intent = new Intent(this, CoachExerciseListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coachNotesListButton)
    public void notesListButtonPressed(){
        Intent intent = new Intent(this, CoachNoteListActivity.class);
        startActivity(intent);
    }
}
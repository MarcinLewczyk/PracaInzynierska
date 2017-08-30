package lewczyk.pracainzynierska;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;


public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);// doesn't work with ormlite?
        setTitle(getString(R.string.user_choice));
     //   addData();
    }

    private void addData() {
        UserNoteRepository.addUserNote(this, new UserNote("hhhhhhhhh hhhhhhhhhhhhhhhhhhh hhhhhhhhhhhh hhhhhhhhhhhh hhhhhhhhhhhh hhhhhhhhhh"));
        ExercisePurposeRepository.addExercisePurpose(this, new ExercisePurpose("150 na klate", "50 na klate"));
        ExercisePurposeRepository.addExercisePurpose(this, new ExercisePurpose("kaptury 50", "kaptury 25"));
        BodyParameterRepository.addBodyParameter(this, new BodyParameter("Udo", 25.5));
        BodyParameterRepository.addBodyParameter(this, new BodyParameter("Triceps", 5.0));
    }

    @OnClick(R.id.userButton)
    public void moveToUserMainMenu(){
        Intent intent = new Intent(getApplicationContext(), UserMainMenuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coachButton)
    public void moveToCoachMainMenu(){
        Intent intent = new Intent(getApplicationContext(), CoachMainMenuActivity.class);
        startActivity(intent);
    }
}
package lewczyk.pracainzynierska.MainMenus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseMenuActivity;
import lewczyk.pracainzynierska.UserFeatures.ArchiveListActivity;
import lewczyk.pracainzynierska.UserFeatures.UserNoteListActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.UserPersonalInfoMenuActivity;

public class UserMainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.user_menu);
    }

    @OnClick(R.id.userExerciseButton)
    public void userExerciseListButtonPressed(){
        Intent intent = new Intent(this, UserExerciseMenuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.userNotesMainMenuButton)
    public void userNotesListButtonPressed(){
        Intent intent = new Intent(this, UserNoteListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.userPersonalInfoButton)
    public void userPersonalInfoButtonPressed(){
        Intent intent = new Intent(this, UserPersonalInfoMenuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.userArchiveButton)
    public void userArchiveButtonPressed(){
        Intent intent = new Intent(this, ArchiveListActivity.class);
        startActivity(intent);
    }
}
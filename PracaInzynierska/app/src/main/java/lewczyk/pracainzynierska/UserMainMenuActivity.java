package lewczyk.pracainzynierska;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.UserPersonalInfo.UserNoteListActivity;

public class UserMainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.userNotesMainMenuButton)
    public void moveToUserNotesList(){
        Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
        startActivity(intent);
    }
}

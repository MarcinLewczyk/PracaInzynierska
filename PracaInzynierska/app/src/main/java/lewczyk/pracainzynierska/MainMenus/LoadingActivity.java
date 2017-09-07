package lewczyk.pracainzynierska.MainMenus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.R;


public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.user_choice));
    }

    @OnClick(R.id.userButton)
    public void userButtonPressed(){
        Intent intent = new Intent(this, UserMainMenuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coachButton)
    public void coachButtonPressed(){
        Intent intent = new Intent(this, CoachMainMenuActivity.class);
        startActivity(intent);
    }
}
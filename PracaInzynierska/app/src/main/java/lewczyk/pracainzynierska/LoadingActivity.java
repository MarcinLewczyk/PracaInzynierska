package lewczyk.pracainzynierska;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoadingActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings(){
        getSupportActionBar().hide();
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

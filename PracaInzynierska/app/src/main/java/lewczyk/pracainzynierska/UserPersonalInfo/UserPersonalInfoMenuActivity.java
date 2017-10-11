package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

public class UserPersonalInfoMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_personal_info_menu);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.personal_info);
    }

    @OnClick(R.id.exercisePurposeListButton)
    public void exercisePurposeButtonPressed(){
        Intent intent = new Intent(this, ExercisePurposeListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bodyParametersListButton)
    public void bodyParametersButtonPressed(){
        Intent intent = new Intent(this, BodyParametersListActivity.class);
        startActivity(intent);
    }
}
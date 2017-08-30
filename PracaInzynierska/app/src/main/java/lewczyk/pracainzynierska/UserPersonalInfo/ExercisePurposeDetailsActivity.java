package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeDetailsActivity extends AppCompatActivity {
    private long purposeId;
    private String msg;
    @BindView(R.id.exercisePurposeTextView)
    TextView purposeTitle;
    @BindView(R.id.exercisePurposeCurrTextView)
    TextView purposeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_details);
        loadStrings();

        ButterKnife.bind(this);

        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", -1);

        if(purposeId == -1){
            purposeTitle.setText(msg);
        } else {
            ExercisePurpose tmp = ExercisePurposeRepository.findById(this, purposeId);
            purposeTitle.setText(tmp.getExercisePurpose());
            purposeState.setText(tmp.getCurrentState());
        }
    }

    private void loadStrings() {
        setTitle(getString(R.string.purpose_details));
        msg  = getString(R.string.no_data);
    }

    @OnClick(R.id.exercisePurposeModButton)
    public void moveToExercisePurposeEdit(){
        if(purposeId != -1){
            Intent intent = new Intent(getApplicationContext(), ExercisePurposeEditActivity.class);
            intent.putExtra("purposeId", purposeId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.exercisePurposeDelButton)
    public void delExercisePurpose(){
        if(purposeId != -1){
            ExercisePurposeRepository.deleteExercisePurpose(getApplicationContext(), ExercisePurposeRepository.findById(this, purposeId));
            Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }
}
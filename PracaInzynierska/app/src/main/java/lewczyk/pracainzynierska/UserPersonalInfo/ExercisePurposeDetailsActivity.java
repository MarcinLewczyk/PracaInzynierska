package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeDetailsActivity extends AppCompatActivity {
    private long purposeId;
    private String msg;
    private String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
    @BindView(R.id.exercisePurposeWebView)
    WebView purposeTitle;
    @BindView(R.id.exercisePurposeCurrWebView)
    WebView purposeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_details);
        loadStrings();

        ButterKnife.bind(this);

        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", -1);

        if(purposeId == -1){
            purposeTitle.loadData(String.format(htmlParam, msg), "text/html", "utf-8");
        } else {
            ExercisePurpose tmp = ExercisePurposeRepository.findById(this, purposeId);
            purposeTitle.loadData(String.format(htmlParam, tmp.getExercisePurpose()), "text/html", "utf-8");
            purposeState.loadData(String.format(htmlParam, tmp.getCurrentState()), "text/html", "utf-8");
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
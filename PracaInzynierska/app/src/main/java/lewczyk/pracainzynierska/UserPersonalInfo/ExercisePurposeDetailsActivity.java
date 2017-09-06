package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.ExercisePurposeArchiveAdapter;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurposeArchive;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeDetailsActivity extends AppCompatActivity {
    private long purposeId;
    @BindView(R.id.exercisePurposeTextView) TextView purposeTitle;
    @BindView(R.id.exercisePurposeCurrTextView) TextView purposeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.purpose_details));
        loadIntent();
        loadViewContent();
    }

    private void loadViewContent() {
        if(!validatePurposeId()){
            purposeTitle.setText(getString(R.string.no_data));
        } else if(validatePurposeId()){
            ExercisePurpose loadedPurpose = loadExercisePurpose();
            ForeignCollection<ExercisePurposeArchive> archive = loadedPurpose.getPurposesArchive();
            //// TODO: 06.09.2017 more refactor 
            purposeTitle.setText(loadedPurpose.getExercisePurpose());
            purposeState.setText(loadedPurpose.getCurrentState());
            ArrayList<ExercisePurposeArchive> toAdapter = new ArrayList<>();
            for(ExercisePurposeArchive e: archive){
                toAdapter.add(e);
            }
            Collections.reverse(toAdapter);
            ListView exercisePurposesArchiveListView = (ListView) findViewById(R.id.exercisePurposeArchiveListView);
            ExercisePurposeArchiveAdapter adapter = new ExercisePurposeArchiveAdapter(toAdapter, this);
            exercisePurposesArchiveListView.setAdapter(adapter);
        }
    }

    private ExercisePurpose loadExercisePurpose() {
        return ExercisePurposeRepository.findById(this, purposeId);
    }

    private boolean validatePurposeId(){
        return purposeId != -1;
    }

    private void loadIntent() {
        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", -1);
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
            moveToPurposeList();
        }
    }

    @Override
    public void onBackPressed(){
        moveToPurposeList();
    }

    private void moveToPurposeList(){
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }
}
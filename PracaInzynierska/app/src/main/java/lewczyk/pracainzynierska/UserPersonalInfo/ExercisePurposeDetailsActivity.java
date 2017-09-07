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
    private int DEFAULT_ID = -1;
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
            purposeTitle.setText(loadedPurpose.getExercisePurpose());
            purposeState.setText(loadedPurpose.getCurrentState());
            loadArchiveList(loadedPurpose);
        }
    }

    private void loadArchiveList(ExercisePurpose loadedPurpose) {
        ForeignCollection<ExercisePurposeArchive> archive = loadedPurpose.getPurposesArchive();
        ArrayList<ExercisePurposeArchive> toAdapter = new ArrayList<>();
        for(ExercisePurposeArchive e: archive){
            toAdapter.add(e);
        }
        Collections.reverse(toAdapter);
        ListView exercisePurposesArchiveListView = (ListView) findViewById(R.id.exercisePurposeArchiveListView);
        ExercisePurposeArchiveAdapter adapter = new ExercisePurposeArchiveAdapter(toAdapter, this);
        exercisePurposesArchiveListView.setAdapter(adapter);
    }


    private ExercisePurpose loadExercisePurpose() {
        return ExercisePurposeRepository.findById(this, purposeId);
    }

    private boolean validatePurposeId(){
        return purposeId != DEFAULT_ID;
    }

    private void loadIntent() {
        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", DEFAULT_ID);
    }

    @OnClick(R.id.exercisePurposeModButton)
    public void editButtonPressed(){
        if(validatePurposeId()){
            Intent intent = new Intent(this, ExercisePurposeEditActivity.class);
            intent.putExtra("purposeId", purposeId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.exercisePurposeDelButton)
    public void delButtonPressed(){
        if(validatePurposeId()){
            ExercisePurposeRepository.deleteExercisePurpose(this, loadExercisePurpose());
            moveToPurposeList();
        }
    }

    @Override
    public void onBackPressed(){
        moveToPurposeList();
    }

    private void moveToPurposeList(){
        Intent intent = new Intent(this, ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }
}
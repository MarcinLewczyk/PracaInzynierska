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
    @BindView(R.id.exercisePurposeTextView)
    TextView purposeTitle;
    @BindView(R.id.exercisePurposeCurrTextView)
    TextView purposeState;
    private ForeignCollection<ExercisePurposeArchive> archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_details);
        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", -1);

        ButterKnife.bind(this);

        loadStrings();
    }

    private void loadStrings() {
        setTitle(getString(R.string.purpose_details));
        String msg = getString(R.string.no_data);

        if(purposeId == -1){
            purposeTitle.setText(msg);
        } else {
            ExercisePurpose tmp = ExercisePurposeRepository.findById(this, purposeId);
            purposeTitle.setText(tmp.getExercisePurpose());
            purposeState.setText(tmp.getCurrentState());
            archive = tmp.getPurposesArchive();
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
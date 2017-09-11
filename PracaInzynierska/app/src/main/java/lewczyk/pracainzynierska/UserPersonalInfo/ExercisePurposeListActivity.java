package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.ExercisePurposeAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeListActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    @BindView(R.id.exercisePurposeListView) ListView exercisePurposesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.exercise_purposes);
        loadExercisePurposeArchive();
    }

    private void loadExercisePurposeArchive() {
        ArrayList<ExercisePurpose> exercisePurposesList = (ArrayList) ExercisePurposeRepository.findAll(this);
        ExercisePurposeAdapter adapter = new ExercisePurposeAdapter(exercisePurposesList, this);
        exercisePurposesListView.setAdapter(adapter);
    }

    @OnClick(R.id.exercisePurposeAddButton)
    public void editButtonPressed(){
        Intent intent = new Intent(this, ExercisePurposeEditActivity.class);
        intent.putExtra("purposeId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }
}
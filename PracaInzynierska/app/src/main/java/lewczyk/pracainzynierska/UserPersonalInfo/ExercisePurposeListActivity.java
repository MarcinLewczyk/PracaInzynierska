package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.ExercisePurposeAdapter;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_list);
        setTitle(R.string.exercise_purposes);
        ButterKnife.bind(this);

        ListView exercisePurposesListView = (ListView) findViewById(R.id.exercisePurposeListView);

        ArrayList<ExercisePurpose> exercisePurposesList = (ArrayList) ExercisePurposeRepository.findAll(this);
        ExercisePurposeAdapter adapter = new ExercisePurposeAdapter(exercisePurposesList, this);
        exercisePurposesListView.setAdapter(adapter);
    }

    @OnClick(R.id.exercisePurposeAddButton)
    public void moveToEditExercisePurpose(){
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeEditActivity.class);
        intent.putExtra("purposeId", -1L);
        startActivity(intent);
        finish();
    }
}
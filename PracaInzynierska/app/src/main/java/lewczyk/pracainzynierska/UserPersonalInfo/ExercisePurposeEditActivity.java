package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeEditActivity extends AppCompatActivity {
    private long purposeId;
    @BindView(R.id.exercisePurposeEditText)
    EditText purpose;
    @BindView(R.id.exercisePurposeCurrentEditText)
    EditText currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_edit);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", -1);

        setViewSettings();

    }

    private void setViewSettings() {
        if(purposeId == -1){
            setTitle(getString(R.string.create_new_purpose));
        } else {
            setTitle(getString(R.string.edit_purpose));
            ExercisePurpose exercisePurpose = ExercisePurposeRepository.findById(getApplicationContext(), purposeId);
            purpose.setText(exercisePurpose.getExercisePurpose());
            currentState.setText(exercisePurpose.getCurrentState());
        }
    }

    @OnClick(R.id.exercisePurposeEditSaveButton)
    public void save(){
        if(purpose.toString().isEmpty()){
            purpose.setError(getString(R.string.more_than_0_characters_required));
        } else {
            purpose.setError(null);
        }
        if(currentState.toString().isEmpty()){
            currentState.setError(getString(R.string.more_than_0_characters_required));
        } else {
            currentState.setError(null);
        }

        if(purpose.length() > 0 && currentState.length() > 0 && purposeId == -1){
            ExercisePurposeRepository.addExercisePurpose(getApplicationContext(),
                    new ExercisePurpose(purpose.getText().toString(), currentState.getText().toString()));
        } else if(purpose.length() > 0 && currentState.length() > 0 && purposeId != -1){
            ExercisePurpose edited = ExercisePurposeRepository.findById(this, purposeId);
            edited.setExercisePurpose(purpose.getText().toString());
            edited.setCurrentState(currentState.getText().toString());
            ExercisePurposeRepository.updateExercisePurpose(getApplicationContext(), edited);
        }
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }
}
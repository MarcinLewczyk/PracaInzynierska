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
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurposeArchive;
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
        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", -1);
        ButterKnife.bind(this);
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
        validateFields();
        if(validateData() && purposeId == -1){
            ExercisePurposeRepository.addExercisePurpose(getApplicationContext(),
                    new ExercisePurpose(purpose.getText().toString(), currentState.getText().toString()));
            moveToExercisePurposeList();
        } else if(validateData() && purposeId != -1){
            ExercisePurpose edited = ExercisePurposeRepository.findById(this, purposeId);
            edited.getPurposesArchive().add(new ExercisePurposeArchive(edited.getCurrentState(), edited));
            edited.setExercisePurpose(purpose.getText().toString());
            edited.setCurrentState(currentState.getText().toString());
            ExercisePurposeRepository.updateExercisePurpose(getApplicationContext(), edited);
            moveToExercisePurposeList();
        }
    }

    private void validateFields() {
        if(purpose.length() == 0){
            purpose.setError(getString(R.string.more_than_0_characters_required));
        } else if(purpose.length() >= 50){
            purpose.setError(getString(R.string.less_than_50));
        } else {
            purpose.setError(null);
        }
        if(currentState.length() == 0){
            currentState.setError(getString(R.string.more_than_0_characters_required));
        } else if(currentState.length() >= 50){
            currentState.setError(getString(R.string.less_than_50));
        }else {
            currentState.setError(null);
        }
    }

    private void moveToExercisePurposeList(){
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateData(){
        return purpose.length() > 0 && currentState.length() > 0 && purpose.length() < 50 && currentState.length() < 50;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }
}
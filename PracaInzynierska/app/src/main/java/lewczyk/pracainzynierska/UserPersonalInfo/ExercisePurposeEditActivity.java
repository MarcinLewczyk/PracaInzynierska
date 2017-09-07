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
    private int DEFAULT_ID = -1;
    private int PURPOSE_AND_STATE_MINIMUM_LENGTH = 0;
    private int STATE_MAX_LENGTH = 50;
    private int PURPOSE_MAX_LENGTH = 100;
    private long purposeId;
    @BindView(R.id.exercisePurposeEditText) EditText purpose;
    @BindView(R.id.exercisePurposeCurrentEditText) EditText currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_purpose_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        if(!validatePurposeId()){
            setTitle(getString(R.string.create_new_purpose));
        } else {
            setTitle(getString(R.string.edit_purpose));
            ExercisePurpose exercisePurpose = loadExercisePurpose();
            purpose.setText(exercisePurpose.getExercisePurpose());
            currentState.setText(exercisePurpose.getCurrentState());
        }
    }

    private ExercisePurpose loadExercisePurpose(){
        return ExercisePurposeRepository.findById(this, purposeId);
    }

    private void loadIntent() {
        Intent intent = getIntent();
        purposeId = intent.getLongExtra("purposeId", DEFAULT_ID);
    }

    private boolean validatePurposeId(){
        return purposeId != DEFAULT_ID;
    }

    @OnClick(R.id.exercisePurposeEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validatePurposeId()){
            addExercisePurpose();
            moveToExercisePurposeList();
        } else if(validateFields() && validatePurposeId()){
            updateExercisePurpose();
            moveToExercisePurposeList();
        }
    }

    private void addExercisePurpose() {
        ExercisePurposeRepository.addExercisePurpose(this,
                new ExercisePurpose(purpose.getText().toString(), currentState.getText().toString()));
    }

    private void updateExercisePurpose() {
        ExercisePurpose edited = ExercisePurposeRepository.findById(this, purposeId);
        edited.getPurposesArchive().add(new ExercisePurposeArchive(edited.getCurrentState(), edited));
        edited.setExercisePurpose(purpose.getText().toString());
        edited.setCurrentState(currentState.getText().toString());
        ExercisePurposeRepository.updateExercisePurpose(this, edited);
    }

    private boolean validateFields() {
        return validatePurpose() && validateCurrentState();
    }

    private boolean validateCurrentState() {
        if(currentState.getText().length() == PURPOSE_AND_STATE_MINIMUM_LENGTH){
            currentState.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(currentState.getText().length() >= STATE_MAX_LENGTH){
            currentState.setError(getString(R.string.less_than_50));
            return false;
        }else {
            currentState.setError(null);
            return true;
        }
    }

    private boolean validatePurpose() {
        if(purpose.getText().length() == PURPOSE_AND_STATE_MINIMUM_LENGTH){
            purpose.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(purpose.getText().length() >= PURPOSE_MAX_LENGTH){
            purpose.setError(getString(R.string.less_than_50));
            return false;
        } else {
            purpose.setError(null);
            return true;
        }
    }

    private void moveToExercisePurposeList(){
        Intent intent = new Intent(this, ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        moveToExercisePurposeList();
    }
}
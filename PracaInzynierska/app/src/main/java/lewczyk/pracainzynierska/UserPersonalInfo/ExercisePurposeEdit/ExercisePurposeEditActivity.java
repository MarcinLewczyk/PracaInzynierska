package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeList.ExercisePurposeListActivity;

public class ExercisePurposeEditActivity extends AppCompatActivity implements
        ExercisePurposeEditNavigator,
        ExercisePurposeEditView{
    @BindView(R.id.exercisePurposeEditText) EditText purpose;
    @BindView(R.id.exercisePurposeCurrentEditText) EditText currentState;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int PURPOSE_AND_STATE_MINIMUM_LENGTH = 0;
    private int STATE_MAX_LENGTH = 50;
    private int PURPOSE_MAX_LENGTH = 100;
    private ExercisePurposeEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExercisePurposeEditPresenter(this, this);
        setContentView(R.layout.activity_exercise_purpose_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadExercisePurpose();
        if(validatePurposeId()){
            setTitle(getString(R.string.edit_purpose));
            loadViewContent();
        } else {
            setTitle(getString(R.string.create_new_purpose));
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("purposeId", DEFAULT_ID);
    }

    private void loadViewContent() {
        purpose.setText(presenter.getExercisePurpose());
        currentState.setText(presenter.getExerciseCurrentState());
    }

    @OnClick(R.id.exercisePurposeEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validatePurposeId()){
            addExercisePurpose();
        } else if(validateFields() && validatePurposeId()){
            updateExercisePurpose();
        }
    }

    private boolean validatePurposeId(){
        return presenter.validateId();
    }

    private void addExercisePurpose() {
        presenter.addExercisePurpose();
    }

    private void updateExercisePurpose() {
        presenter.updateExercisePurpose();
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

    @Override
    public String getPurposeStringEditText(){
        return purpose.getText().toString();
    }

    @Override
    public String getCurrentStateStringEditText(){
        return currentState.getText().toString();
    }

    @Override
    public void onBackPressed(){
        navigateToExercisePurposeList();
    }

    @Override
    public void navigateToExercisePurposeList() {
        Intent intent = new Intent(this, ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
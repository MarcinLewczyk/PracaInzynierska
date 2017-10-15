package lewczyk.pracainzynierska.CoachExercise.CoachNewExercise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseList.CoachExerciseListActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachNewExerciseActivity extends AppCompatActivity implements CoachNewExerciseView, CoachNewExerciseNavigator{
    @BindView(R.id.coachNewExerciseNameEditText) EditText exerciseName;
    @BindView(R.id.coachNewExerciseMusclePartEditText) EditText musclePart;
    @BindView(R.id.coachNewExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.coachNewExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.coachNewExerciseTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.coachNewExerciseDemonstrationEditText) EditText demonstration;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int FIELDS_MINIMUM_LENGTH = 0;
    private int DEMONSTRATION_MAXIMUM_LENGTH = 1000;
    private int MUSCLE_PART_MAXIMUM_LENGTH = 100;
    private int EXERCISE_NAME_MAXIMUM_LENGTH =100;
    private String selectedDifficult, selectedEquipment, selectedType;
    private CoachNewExercisePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachNewExercisePresenter(this, this);
        setContentView(R.layout.activity_coach_new_exercise);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadExercise();
        setSpinnersContent();
        if(validateId()){
            setTitle(getString(R.string.edit_exercise));
            loadViewContent();
        } else {
            setTitle(getString(R.string.create_new_exercise));
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    private void setSpinnersContent() {
        ArrayList<String> difficultCategories = presenter.getAllDifficultLevelNames();
        ArrayList<String> equipmentCategories = presenter.getAllEquipmentRequirementsNames();
        ArrayList<String> typeCategories = presenter.getAllExerciseTypesNames();

        ArrayAdapter<String> difficultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultCategories);
        ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipmentCategories);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeCategories);

        difficultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        difficultSpinner.setAdapter(difficultAdapter);
        equipmentSpinner.setAdapter(equipmentAdapter);
        typeSpinner.setAdapter(typeAdapter);
    }

    private void loadViewContent() {
        exerciseName.setText(presenter.getExerciseName());
        musclePart.setText(presenter.getMusclePart());
        demonstration.setText(presenter.getExerciseDemonstration());

        difficultSpinner.setSelection(presenter.difficultSpinnerPosition());
        equipmentSpinner.setSelection(presenter.equipmentSpinnerPosition());
        typeSpinner.setSelection(presenter.typeSpinnerPosition());
    }

    @OnClick(R.id.coachNewExerciseAddButton)
    public void saveExerciseButtonPressed(){
        if(validateFields() && !validateId()){
            presenter.addExercise();
        } else if(validateFields() && validateId()){
            presenter.updateExercise();
        }
    }

    private boolean validateId(){
        return presenter.validateId();
    }

    private boolean validateFields(){
        return validateExerciseName() && validateMusclePart() && validateDemonstration();
    }

    private boolean validateExerciseName() {
        if(exerciseName.getText().length() == FIELDS_MINIMUM_LENGTH){
            exerciseName.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(exerciseName.getText().length() >= EXERCISE_NAME_MAXIMUM_LENGTH) {
            exerciseName.setError(getString(R.string.less_than_100));
            return false;
        } else {
            exerciseName.setError(null);
            return true;
        }
    }

    private boolean validateMusclePart() {
        if(musclePart.getText().length() == FIELDS_MINIMUM_LENGTH){
            musclePart.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(musclePart.getText().length() >= MUSCLE_PART_MAXIMUM_LENGTH) {
            musclePart.setError(getString(R.string.less_than_100));
            return false;
        } else {
            musclePart.setError(null);
            return true;
        }
    }

    private boolean validateDemonstration() {
        if(demonstration.getText().length() == FIELDS_MINIMUM_LENGTH){
            demonstration.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(demonstration.getText().length() >= DEMONSTRATION_MAXIMUM_LENGTH) {
            demonstration.setError(getString(R.string.less_tahn_1000_characters));
            return false;
        } else {
            demonstration.setError(null);
            return true;
        }
    }

    @Override
    public String getExerciseNameString(){
        return exerciseName.getText().toString();
    }

    @Override
    public String getMusclePartString(){
        return musclePart.getText().toString();
    }

    @Override
    public String getDemonstrationString(){
        return demonstration.getText().toString();
    }

    @OnItemSelected(R.id.coachNewExerciseDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedDifficult(){
        return selectedDifficult;
    }

    @OnItemSelected(R.id.coachNewExerciseEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedEquipmentRequirements(){
        return selectedEquipment;
    }

    @OnItemSelected(R.id.coachNewExerciseTypeSpinner)
    public void typeSpinnerSelected(int position){
        selectedType = (String) typeSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedExerciseType(){
        return selectedType;
    }

    @Override
    public void onBackPressed(){
        navigateToCoachExerciseList();
    }

    @Override
    public void navigateToCoachExerciseList() {
        Intent intent = new Intent(this, CoachExerciseListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
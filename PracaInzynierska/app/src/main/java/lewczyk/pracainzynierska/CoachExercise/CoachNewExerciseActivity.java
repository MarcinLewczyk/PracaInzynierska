package lewczyk.pracainzynierska.CoachExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;
import lewczyk.pracainzynierska.R;

public class CoachNewExerciseActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int FIELDS_MINIMUM_LENGTH = 0;
    private int DEMONSTRATION_MAXIMUM_LENGTH = 1000;
    private int MUSCLE_PART_MAXIMUM_LENGTH = 100;
    private int EXERCISE_NAME_MAXIMUM_LENGTH =100;
    private long exerciseId;
    @BindView(R.id.coachNewExerciseNameEditText) EditText exerciseName;
    @BindView(R.id.coachNewExerciseMusclePartEditText) EditText musclePart;
    @BindView(R.id.coachNewExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.coachNewExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.coachNewExerciseTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.coachNewExerciseDemonstrationEditText) EditText demonstration;
    private String selectedDifficult, selectedEquipment, selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_new_exercise);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        setSpinnersContent();
        if(!validateId()){
            setTitle(getString(R.string.create_new_exercise));
        } else {
            setTitle(getString(R.string.edit_exercise));
            loadStrings();
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    private void loadStrings() {
        Exercise exercise = loadExercise();
        exerciseName.setText(exercise.getExerciseName());
        musclePart.setText(exercise.getMusclePart());
        demonstration.setText(exercise.getDemonstration());

        difficultSpinner.setSelection(difficultSpinnerPosition(exercise));
        equipmentSpinner.setSelection(equipmentSpinnerPosition(exercise));
        typeSpinner.setSelection(typeSpinnerPosition(exercise));
    }

    private void setSpinnersContent() {
        List<String> difficultCategories = loadDifficultNames();
        List<String> equipmentCategories = loadEquipmentRequirementsNames();
        List<String> typeCategories = loadExerciseTypeNames();

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

    private int difficultSpinnerPosition(Exercise exercise) {
        List<String> difficultCategories = loadDifficultNames();
        int position = 0;
        for(String d: difficultCategories){
            if(d.equals(DifficultLevelRepository.findById(this, exercise.getDifficultLevel().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    private ArrayList loadDifficultNames(){
        return (ArrayList) DifficultLevelRepository.findAllNames(this);
    }

    private int equipmentSpinnerPosition(Exercise exercise) {
        List<String> equipmentCategories = loadEquipmentRequirementsNames();
        int position = 0;
        for(String d: equipmentCategories){
            if(d.equals(EquipmentRequirementRepository.findById(this, exercise.getEquipmentRequirement().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    private ArrayList loadEquipmentRequirementsNames(){
        return (ArrayList) EquipmentRequirementRepository.findAllNames(this);
    }

    private int typeSpinnerPosition(Exercise exercise) {
        List<String> difficultTypes = loadExerciseTypeNames();
        int position = 0;
        for(String d: difficultTypes){
            if(d.equals(ExerciseTypeRepository.findById(this, exercise.getExerciseType().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    private ArrayList loadExerciseTypeNames(){
        return (ArrayList) ExerciseTypeRepository.findAllNames(this);
    }

    @OnItemSelected(R.id.coachNewExerciseDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @OnItemSelected(R.id.coachNewExerciseEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @OnItemSelected(R.id.coachNewExerciseTypeSpinner)
    public void typeSpinnerSelected(int position){
        selectedType = (String) typeSpinner.getItemAtPosition(position);
    }

    @OnClick(R.id.coachNewExerciseAddButton)
    public void saveExerciseButtonPressed(){
        if(validateFields() && !validateId()){
            saveExercise();
            moveToCoachExerciseList();
        } else if(validateFields() && validateId()){
            updateExercise();
            moveToCoachExerciseList();
        }
    }

    private boolean validateId(){
        return exerciseId != DEFAULT_ID;
    }

    private void saveExercise() {
        ExerciseRepository.addExercise(this, new Exercise(
                exerciseName.getText().toString(),
                musclePart.getText().toString(),
                demonstration.getText().toString(),
                loadDifficultLevel(),
                loadEquipmentRequirement(),
                loadExerciseType()));
    }

    private void updateExercise() {
        Exercise edited = loadExercise();
        edited.setExerciseName(exerciseName.getText().toString());
        edited.setMusclePart(musclePart.getText().toString());
        edited.setDifficultLevel(loadDifficultLevel());
        edited.setEquipmentRequirement(loadEquipmentRequirement());
        edited.setExerciseType(loadExerciseType());
        edited.setDemonstration(demonstration.getText().toString());
        ExerciseRepository.updateExercise(this, edited);
    }

    private Exercise loadExercise(){
        return ExerciseRepository.findById(this, exerciseId);
    }

    private DifficultLevel loadDifficultLevel(){
        return DifficultLevelRepository.findByName(this, selectedDifficult);
    }

    private EquipmentRequirement loadEquipmentRequirement(){
        return EquipmentRequirementRepository.findByName(this, selectedEquipment);
    }

    private ExerciseType loadExerciseType(){
        return ExerciseTypeRepository.findByName(this, selectedType);
    }

    @Override
    public void onBackPressed(){
        moveToCoachExerciseList();
    }

    private void moveToCoachExerciseList() {
        Intent intent = new Intent(this, CoachExerciseListActivity.class);
        startActivity(intent);
        finish();
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
}
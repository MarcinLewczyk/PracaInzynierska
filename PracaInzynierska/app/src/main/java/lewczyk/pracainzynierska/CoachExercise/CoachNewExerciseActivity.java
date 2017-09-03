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
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachNewExerciseActivity extends AppCompatActivity {
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
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", -1);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        if(exerciseId == -1){
            setTitle(getString(R.string.create_new_exercise));
        } else {
            setTitle(getString(R.string.edit_exercise));
            loadStrings();
        }
    }

    private void loadStrings() {
        Exercise exercise = ExerciseRepository.findById(this, exerciseId);
        exerciseName.setText(exercise.getExerciseName());
        musclePart.setText(exercise.getMusclePart());
        demonstration.setText(exercise.getDemonstration());
        setSpinnersContent();
        difficultSpinner.setSelection(difficultSpinnerPosition(exercise));
        equipmentSpinner.setSelection(equipmentSpinnerPosition(exercise));
        typeSpinner.setSelection(typeSpinnerPosition(exercise));
    }

    private int typeSpinnerPosition(Exercise exercise) {
        List<String> difficultTypes = (ArrayList) ExerciseTypeRepository.findAllNames(this);
        int position = 0;
        for(String d: difficultTypes){
            if(d.equals(ExerciseTypeRepository.findById(this, exercise.getExerciseType().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    private int equipmentSpinnerPosition(Exercise exercise) {
        List<String> equipmentCategories = (ArrayList) EquipmentRequirementRepository.findAllNames(this);
        int position = 0;
        for(String d: equipmentCategories){
            if(d.equals(EquipmentRequirementRepository.findById(this, exercise.getEquipmentRequirement().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    private int difficultSpinnerPosition(Exercise exercise) {
        List<String> difficultCategories = (ArrayList) DifficultLevelRepository.findAllNames(this);
        int position = 0;
        for(String d: difficultCategories){
            if(d.equals(DifficultLevelRepository.findById(this, exercise.getDifficultLevel().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    private void setSpinnersContent() {
        List<String> difficultCategories = (ArrayList) DifficultLevelRepository.findAllNames(this);
        List<String> equipmentCategories = (ArrayList) EquipmentRequirementRepository.findAllNames(this);
        List<String> typeCategories = (ArrayList) ExerciseTypeRepository.findAllNames(this);

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
    public void saveExercise(){
        if(validateFields() && exerciseId == -1){
            ExerciseRepository.addExercise(getApplicationContext(),
                    new Exercise(exerciseName.getText().toString(), musclePart.getText().toString(),
                            demonstration.getText().toString(), DifficultLevelRepository.findByName(this, selectedDifficult),
                            EquipmentRequirementRepository.findByName(this, selectedEquipment), ExerciseTypeRepository.findByName(this, selectedType)));
            moveToCoachExerciseList();
        } else if(validateFields() && exerciseId != -1){
            updateExercise();
            moveToCoachExerciseList();
        }
    }

    private void updateExercise() {
        Exercise edited = ExerciseRepository.findById(this, exerciseId);
        edited.setExerciseName(exerciseName.getText().toString());
        edited.setMusclePart(musclePart.getText().toString());
        edited.setDifficultLevel(DifficultLevelRepository.findByName(this, selectedDifficult));
        edited.setEquipmentRequirement(EquipmentRequirementRepository.findByName(this, selectedEquipment));
        edited.setExerciseType(ExerciseTypeRepository.findByName(this, selectedType));
        edited.setDemonstration(demonstration.getText().toString());
        ExerciseRepository.updateExercise(this, edited);
    }

    private void moveToCoachExerciseList() {
        Intent intent = new Intent(getApplicationContext(), CoachExerciseListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        moveToCoachExerciseList();
    }

    private boolean validateFields(){
        return validateExerciseName() && validateMusclePart() && validateDemonstration();
    }

    private boolean validateDemonstration() {
        if(demonstration.getText().length() == 0){
            demonstration.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(demonstration.getText().length() >= 1000) {
            demonstration.setError(getString(R.string.less_tahn_1000_characters));
            return false;
        } else {
            demonstration.setError(null);
            return true;
        }
    }

    private boolean validateMusclePart() {
        if(musclePart.getText().length() == 0){
            musclePart.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(musclePart.getText().length() >= 100) {
            musclePart.setError(getString(R.string.less_than_100));
            return false;
        } else {
            musclePart.setError(null);
            return true;
        }
    }

    private boolean validateExerciseName() {
        if(exerciseName.getText().length() == 0){
            exerciseName.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(exerciseName.getText().length() >= 100) {
            exerciseName.setError(getString(R.string.less_than_100));
            return false;
        } else {
            exerciseName.setError(null);
            return true;
        }
    }
}
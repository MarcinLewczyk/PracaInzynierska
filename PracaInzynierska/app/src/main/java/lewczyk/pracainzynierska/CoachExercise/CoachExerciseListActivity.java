package lewczyk.pracainzynierska.CoachExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import lewczyk.pracainzynierska.Adapters.CoachExerciseAdapter;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachExerciseListActivity extends AppCompatActivity{
    @BindView(R.id.coachExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.coachExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.coachExerciseTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.coachExerciseListView) ListView exercisesList;
    private String selectedDifficult, selectedEquipment, selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercises_list));
        setSpinnersContent();
    }

    private void setSpinnersContent() {
        String nothing = getString(R.string.no_filter);
        List<String> difficultCategories = (ArrayList) DifficultLevelRepository.findAllNames(this);
        List<String> equipmentCategories = (ArrayList) EquipmentRequirementRepository.findAllNames(this);
        List<String> typeCategories = (ArrayList) ExerciseTypeRepository.findAllNames(this);

        difficultCategories.add(nothing);
        equipmentCategories.add(nothing);
        typeCategories.add(nothing);

        ArrayAdapter<String> difficultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultCategories);
        ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipmentCategories);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeCategories);

        difficultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        difficultSpinner.setAdapter(difficultAdapter);
        equipmentSpinner.setAdapter(equipmentAdapter);
        typeSpinner.setAdapter(typeAdapter);

        // Setting "no filter" on spinners
        difficultSpinner.setSelection(difficultCategories.size() - 1);
        equipmentSpinner.setSelection(equipmentCategories.size() - 1);
        typeSpinner.setSelection(typeCategories.size() - 1);
    }

    private void setListViewContent(ArrayList<Exercise> coachExerciseList) {
        CoachExerciseAdapter adapter = new CoachExerciseAdapter(coachExerciseList, this);
        exercisesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachExerciseAddButton)
    public void addButtonPressed(){
        Intent intent = new Intent(this, CoachNewExerciseActivity.class);
        intent.putExtra("exerciseId", -1);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.coachExerciseListSearchButton)
    public void searchButtonPressed(){
        ArrayList<Exercise> coachExerciseList = (ArrayList) ExerciseRepository.filterList(
                ExerciseRepository.findAll(this),
                ExerciseTypeRepository.findByName(this, selectedType),
                DifficultLevelRepository.findByName(this, selectedDifficult),
                EquipmentRequirementRepository.findByName(this, selectedEquipment));
        setListViewContent(coachExerciseList);
    }

    @OnItemSelected(R.id.coachExerciseDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @OnItemSelected(R.id.coachExerciseEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @OnItemSelected(R.id.coachExerciseTypeSpinner)
    public void typeSpinnerSelected(int position){
       selectedType = (String) typeSpinner.getItemAtPosition(position);
    }
}
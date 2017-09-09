package lewczyk.pracainzynierska.UserExercise;

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
import lewczyk.pracainzynierska.Adapters.UserExerciseAdapter;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class UserExerciseListActivity extends AppCompatActivity {
    @BindView(R.id.userExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.userExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.userExerciseTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.userExerciseListView) ListView exercisesList;
    private String selectedDifficult, selectedEquipment, selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_list));
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

    @OnClick(R.id.userExerciseListSearchButton)
    public void searchButtonPressed(){
        ArrayList<Exercise> userExerciseList = (ArrayList) ExerciseRepository.filterList(
                ExerciseRepository.findAll(this),
                ExerciseTypeRepository.findByName(this, selectedType),
                DifficultLevelRepository.findByName(this, selectedDifficult),
                EquipmentRequirementRepository.findByName(this, selectedEquipment));
        setListViewContent(userExerciseList);
    }

    private void setListViewContent(ArrayList<Exercise> userExerciseList) {
        UserExerciseAdapter adapter = new UserExerciseAdapter(userExerciseList, this);
        exercisesList.setAdapter(adapter);
    }

    @OnItemSelected(R.id.userExerciseDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @OnItemSelected(R.id.userExerciseEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @OnItemSelected(R.id.userExerciseTypeSpinner)
    public void typeSpinnerSelected(int position){
        selectedType = (String) typeSpinner.getItemAtPosition(position);
    }
}
package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNew;

import android.content.Context;
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
import lewczyk.pracainzynierska.Adapters.CoachExerciseToDoExerciseListAdapter;

import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoNewActivity extends AppCompatActivity implements CoachExerciseToDoNewView{
    @BindView(R.id.coachExerciseToDoDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.coachExerciseToDoEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.coachExerciseToDoTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.coachExerciseToDoListView) ListView exercisesList;
    private String selectedDifficult, selectedEquipment, selectedType;
    private CoachExerciseToDoNewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachExerciseToDoNewPresenter(this);
        setContentView(R.layout.activity_coach_exercise_to_do_new);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.choose_exercise_to_do));
        setSpinnersContent();
    }

    private void setSpinnersContent() {
        String noFilter = getString(R.string.no_filter);
        List<String> difficultCategories = presenter.getAllDifficultLevelNames();
        List<String> equipmentCategories = presenter.getAllEquipmentRequirementsNames();
        List<String> typeCategories = presenter.getAllExerciseTypesNames();

        difficultCategories.add(noFilter);
        equipmentCategories.add(noFilter);
        typeCategories.add(noFilter);

        ArrayAdapter<String> difficultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultCategories);
        ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipmentCategories);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeCategories);

        difficultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        difficultSpinner.setAdapter(difficultAdapter);
        equipmentSpinner.setAdapter(equipmentAdapter);
        typeSpinner.setAdapter(typeAdapter);

        difficultSpinner.setSelection(difficultCategories.size() - 1);
        equipmentSpinner.setSelection(equipmentCategories.size() - 1);
        typeSpinner.setSelection(typeCategories.size() - 1);
    }

    @OnClick(R.id.coachExerciseToDoListSearchButton)
    public void searchButton(){
        setListViewContent(presenter.getFilteredExerciseList());
    }

    private void setListViewContent(ArrayList content) {
        CoachExerciseToDoExerciseListAdapter adapter = new CoachExerciseToDoExerciseListAdapter(content, this);
        exercisesList.setAdapter(adapter);
    }

    @OnItemSelected(R.id.coachExerciseToDoDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedDifficult(){
        return selectedDifficult;
    }

    @OnItemSelected(R.id.coachExerciseToDoEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedEquipmentRequirements(){
        return selectedEquipment;
    }

    @OnItemSelected(R.id.coachExerciseToDoTypeSpinner)
    public void typeSpinnerSelected(int position){
        selectedType = (String) typeSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedExerciseType(){
        return selectedType;
    }

    @Override
    public Context getContext(){
        return this;
    }
}
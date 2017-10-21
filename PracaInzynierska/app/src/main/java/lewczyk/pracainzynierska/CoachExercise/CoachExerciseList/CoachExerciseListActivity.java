package lewczyk.pracainzynierska.CoachExercise.CoachExerciseList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import lewczyk.pracainzynierska.CoachExercise.CoachNewExercise.CoachNewExerciseActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachExerciseListActivity extends AppCompatActivity implements CoachExerciseListView{
    @BindView(R.id.coachExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.coachExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.coachExerciseTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.coachExerciseListView) ListView exercisesList;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private String selectedDifficult, selectedEquipment, selectedType;
    private CoachExerciseListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachExerciseListPresenter(this);
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
        List<String> difficultCategories = presenter.getAllDifficultLevelNames();
        List<String> equipmentCategories = presenter.getAllEquipmentRequirementsNames();
        List<String> typeCategories = presenter.getAllExerciseTypesNames();

        difficultCategories.add(nothing);
        equipmentCategories.add(nothing);
        typeCategories.add(nothing);

        ArrayAdapter<String> difficultAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, difficultCategories);
        ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(this,  R.layout.spinner_layout, equipmentCategories);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,  R.layout.spinner_layout, typeCategories);

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

    @OnClick(R.id.coachExerciseAddButton)
    public void addButtonPressed(){
        Intent intent = new Intent(this, CoachNewExerciseActivity.class);
        intent.putExtra("exerciseId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.coachExerciseListSearchButton)
    public void searchButtonPressed(){
        setListViewContent(presenter.getFilteredExerciseList());
    }

    private void setListViewContent(ArrayList content) {
        CoachExerciseAdapter adapter = new CoachExerciseAdapter(content, this);
        exercisesList.setAdapter(adapter);
    }

    @OnItemSelected(R.id.coachExerciseDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedDifficult(){
        return selectedDifficult;
    }

    @OnItemSelected(R.id.coachExerciseEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedEquipmentRequirements(){
        return selectedEquipment;
    }

    @OnItemSelected(R.id.coachExerciseTypeSpinner)
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
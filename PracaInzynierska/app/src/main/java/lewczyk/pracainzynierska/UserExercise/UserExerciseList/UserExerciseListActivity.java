package lewczyk.pracainzynierska.UserExercise.UserExerciseList;

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
import lewczyk.pracainzynierska.Adapters.UserExerciseAdapter;
import lewczyk.pracainzynierska.R;

public class UserExerciseListActivity extends AppCompatActivity implements UserExerciseListView{
    @BindView(R.id.userExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.userExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.userExerciseTypeSpinner) Spinner typeSpinner;
    @BindView(R.id.userExerciseListView) ListView exercisesList;
    private String selectedDifficult, selectedEquipment, selectedType;
    private UserExerciseListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserExerciseListPresenter(this);
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
        List<String> difficultCategories = presenter.getAllDifficultLevelNames();
        List<String> equipmentCategories = presenter.getAllEquipmentRequirementsNames();
        List<String> typeCategories = presenter.getAllExerciseTypesNames();

        difficultCategories.add(nothing);
        equipmentCategories.add(nothing);
        typeCategories.add(nothing);

        ArrayAdapter<String> difficultAdapter = new ArrayAdapter<>(this,  R.layout.spinner_layout, difficultCategories);
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

    @OnClick(R.id.userExerciseListSearchButton)
    public void searchButtonPressed(){
        setListViewContent(presenter.getFilteredExerciseList());
    }

    private void setListViewContent(ArrayList content) {
        UserExerciseAdapter adapter = new UserExerciseAdapter(content, this);
        exercisesList.setAdapter(adapter);
    }

    @OnItemSelected(R.id.userExerciseDifficultLevelSpinner)
    public void difficultSpinnerSelected(int position){
        selectedDifficult = (String) difficultSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedDifficult(){
        return selectedDifficult;
    }

    @OnItemSelected(R.id.userExerciseEquipmentRequirementSpinner)
    public void equipmentSpinnerSelected(int position){
        selectedEquipment = (String) equipmentSpinner.getItemAtPosition(position);
    }

    @Override
    public String getSelectedEquipmentRequirements(){
        return selectedEquipment;
    }

    @OnItemSelected(R.id.userExerciseTypeSpinner)
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
package lewczyk.pracainzynierska.UserExercise.UserExerciseList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;

public class UserExerciseListPresenter {
    private UserExerciseListView view;
    private DifficultLevelRepository difficultLevelRepository;
    private EquipmentRequirementRepository equipmentRequirementRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private ExerciseRepository exerciseRepository;

    public UserExerciseListPresenter(UserExerciseListView view) {
        this.view = view;
        this.difficultLevelRepository = new DifficultLevelRepository(view.getContext());
        this.equipmentRequirementRepository = new EquipmentRequirementRepository(view.getContext());
        this.exerciseTypeRepository = new ExerciseTypeRepository(view.getContext());
        this.exerciseRepository = new ExerciseRepository(view.getContext());
    }

    ArrayList<String> getAllDifficultLevelNames(){
        return (ArrayList<String>) difficultLevelRepository.findAllNames();
    }

    ArrayList<String> getAllEquipmentRequirementsNames(){
        return (ArrayList<String>) equipmentRequirementRepository.findAllNames();
    }

    ArrayList<String> getAllExerciseTypesNames(){
        return (ArrayList<String>) exerciseTypeRepository.findAllNames();
    }

    ArrayList<Exercise> getFilteredExerciseList(){
        return (ArrayList<Exercise>) exerciseRepository.filterList(
                exerciseRepository.findAll(),
                exerciseTypeRepository.findByName(view.getSelectedExerciseType()),
                difficultLevelRepository.findByName(view.getSelectedDifficult()),
                equipmentRequirementRepository.findByName(view.getSelectedEquipmentRequirements()));
    }
}
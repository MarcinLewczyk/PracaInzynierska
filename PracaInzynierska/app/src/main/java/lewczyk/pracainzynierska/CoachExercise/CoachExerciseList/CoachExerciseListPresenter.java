package lewczyk.pracainzynierska.CoachExercise.CoachExerciseList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;

public class CoachExerciseListPresenter {
    private CoachExerciseListView coachExerciseListView;
    private DifficultLevelRepository difficultLevelRepository;
    private EquipmentRequirementRepository equipmentRequirementRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private ExerciseRepository exerciseRepository;

    public CoachExerciseListPresenter(CoachExerciseListView coachExerciseListView) {
        this.coachExerciseListView = coachExerciseListView;
        this.difficultLevelRepository = new DifficultLevelRepository(coachExerciseListView.getContext());
        this.equipmentRequirementRepository = new EquipmentRequirementRepository(coachExerciseListView.getContext());
        this.exerciseTypeRepository = new ExerciseTypeRepository(coachExerciseListView.getContext());
        this.exerciseRepository = new ExerciseRepository(coachExerciseListView.getContext());
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
                exerciseTypeRepository.findByName(coachExerciseListView.getSelectedExerciseType()),
                difficultLevelRepository.findByName(coachExerciseListView.getSelectedDifficult()),
                equipmentRequirementRepository.findByName(coachExerciseListView.getSelectedEquipmentRequirements()));
    }
}
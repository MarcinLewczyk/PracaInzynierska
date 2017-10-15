package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNew;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;

public class CoachExerciseToDoNewPresenter {
    private CoachExerciseToDoNewView coachExerciseToDoNewView;
    private DifficultLevelRepository difficultLevelRepository;
    private EquipmentRequirementRepository equipmentRequirementRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private ExerciseRepository exerciseRepository;

    public CoachExerciseToDoNewPresenter(CoachExerciseToDoNewView coachExerciseToDoNewView) {
        this.coachExerciseToDoNewView = coachExerciseToDoNewView;
        this.difficultLevelRepository = new DifficultLevelRepository(coachExerciseToDoNewView.getContext());
        this.equipmentRequirementRepository = new EquipmentRequirementRepository(coachExerciseToDoNewView.getContext());
        this.exerciseTypeRepository = new ExerciseTypeRepository(coachExerciseToDoNewView.getContext());
        this.exerciseRepository = new ExerciseRepository(coachExerciseToDoNewView.getContext());
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
                exerciseTypeRepository.findByName(coachExerciseToDoNewView.getSelectedExerciseType()),
                difficultLevelRepository.findByName(coachExerciseToDoNewView.getSelectedDifficult()),
                equipmentRequirementRepository.findByName(coachExerciseToDoNewView.getSelectedEquipmentRequirements()));
    }
}
package lewczyk.pracainzynierska.CoachExercise.CoachNewExercise;

import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class CoachNewExercisePresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private  CoachNewExerciseView view;
    private CoachNewExerciseNavigator navigator;
    private ExerciseRepository exerciseRepository;
    private DifficultLevelRepository difficultLevelRepository;
    private EquipmentRequirementRepository equipmentRequirementRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private Exercise exercise;
    private long exerciseId;

    public CoachNewExercisePresenter(CoachNewExerciseView view, CoachNewExerciseNavigator navigator) {
        this.view = view;
        this.navigator = navigator;
        this.exerciseRepository = new ExerciseRepository(view.getContext());
        this.equipmentRequirementRepository = new EquipmentRequirementRepository(view.getContext());
        this.exerciseTypeRepository = new ExerciseTypeRepository(view.getContext());
        this.difficultLevelRepository = new DifficultLevelRepository(view.getContext());
    }

    void loadExercise(){
        exerciseId = view.loadIntent();
        exercise = exerciseRepository.findById(exerciseId);
    }

    boolean validateId(){
        return exerciseId != DEFAULT_ID;
    }

    void addExercise(){
        exerciseRepository.addExercise(new Exercise(
                    view.getExerciseNameString(),
                    view.getMusclePartString(),
                    view.getDemonstrationString(),
                    loadDifficultLevel(),
                    loadEquipmentRequirement(),
                    loadExerciseType()));
        navigator.navigateToCoachExerciseList();
    }

    void updateExercise(){
        exercise.setExerciseName(view.getExerciseNameString());
        exercise.setMusclePart(view.getMusclePartString());
        exercise.setDifficultLevel(loadDifficultLevel());
        exercise.setEquipmentRequirement(loadEquipmentRequirement());
        exercise.setExerciseType(loadExerciseType());
        exercise.setDemonstration(view.getDemonstrationString());
        exerciseRepository.updateExercise(exercise);
        navigator.navigateToCoachExerciseList();
    }

    private DifficultLevel loadDifficultLevel(){
        return difficultLevelRepository.findByName(view.getSelectedDifficult());
    }

    private EquipmentRequirement loadEquipmentRequirement(){
        return equipmentRequirementRepository.findByName(view.getSelectedEquipmentRequirements());
    }

    private ExerciseType loadExerciseType(){
        return exerciseTypeRepository.findByName(view.getSelectedExerciseType());
    }

    String getExerciseName(){
        return exercise.getExerciseName();
    }

    String getMusclePart(){
        return exercise.getMusclePart();
    }

    int difficultSpinnerPosition(){
        List<String> difficultCategories = getAllDifficultLevelNames();
        int position = 0;
        for(String d: difficultCategories){
            if(d.equals(difficultLevelRepository.findById(exercise.getDifficultLevel().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    int equipmentSpinnerPosition(){
        List<String> equipmentCategories = getAllEquipmentRequirementsNames();
        int position = 0;
        for(String d: equipmentCategories){
            if(d.equals(equipmentRequirementRepository.findById(exercise.getEquipmentRequirement().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    int typeSpinnerPosition(){
        List<String> difficultTypes = getAllExerciseTypesNames();
        int position = 0;
        for(String d: difficultTypes){
            if(d.equals(exerciseTypeRepository.findById(exercise.getExerciseType().getId()).getName())){
                break;
            }
            position++;
        }
        return position;
    }

    String getExerciseDemonstration(){
        return exercise.getDemonstration();
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
}
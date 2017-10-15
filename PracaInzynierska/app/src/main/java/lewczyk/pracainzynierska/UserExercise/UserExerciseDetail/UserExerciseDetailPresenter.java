package lewczyk.pracainzynierska.UserExercise.UserExerciseDetail;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;

public class UserExerciseDetailPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserExerciseDetailView view;
    private ExerciseRepository exerciseRepository;
    private DifficultLevelRepository difficultLevelRepository;
    private EquipmentRequirementRepository equipmentRequirementRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private Exercise exercise;
    private long exerciseId;

    public UserExerciseDetailPresenter(UserExerciseDetailView view) {
        this.view = view;
        exerciseRepository = new ExerciseRepository(view.getContext());
        difficultLevelRepository = new DifficultLevelRepository(view.getContext());
        equipmentRequirementRepository = new EquipmentRequirementRepository(view.getContext());
        exerciseTypeRepository = new ExerciseTypeRepository(view.getContext());
    }

    void loadExercise(){
        exerciseId = view.loadIntent();
        exercise = exerciseRepository.findById(exerciseId);
    }

    boolean validateId(){
        return exerciseId != DEFAULT_ID;
    }

    long getExerciseId(){
        return exerciseId;
    }

    String getExerciseName(){
        return exercise.getExerciseName();
    }

    String getMusclePart(){
        return exercise.getMusclePart();
    }

    String getDifficultLevel(){
        return difficultLevelRepository.findById(exercise.getDifficultLevel().getId()).getName();
    }

    String getEquipmentRequirements(){
        return equipmentRequirementRepository.findById(exercise.getEquipmentRequirement().getId()).getName();
    }

    String getExerciseType(){
        return exerciseTypeRepository.findById(exercise.getExerciseType().getId()).getName();
    }

    String getExerciseDemonstration(){
        return exercise.getDemonstration();
    }
}
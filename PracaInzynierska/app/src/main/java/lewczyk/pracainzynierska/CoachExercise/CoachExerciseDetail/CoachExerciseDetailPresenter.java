package lewczyk.pracainzynierska.CoachExercise.CoachExerciseDetail;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.DifficultLevelRepository;
import lewczyk.pracainzynierska.Database.EquipmentRequirementRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseTypeRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachExerciseDetailPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachExerciseDetailView coachExerciseDetailView;
    private CoachExerciseDetailNavigator coachExerciseDetailNavigator;
    private ExerciseRepository exerciseRepository;
    private DifficultLevelRepository difficultLevelRepository;
    private EquipmentRequirementRepository equipmentRequirementRepository;
    private ExerciseTypeRepository exerciseTypeRepository;
    private Exercise exercise;
    private long exerciseId;

    public CoachExerciseDetailPresenter(CoachExerciseDetailView coachExerciseDetailView, CoachExerciseDetailNavigator coachExerciseDetailNavigator) {
        this.coachExerciseDetailView = coachExerciseDetailView;
        this.coachExerciseDetailNavigator = coachExerciseDetailNavigator;
        difficultLevelRepository = new DifficultLevelRepository(coachExerciseDetailView.getContext());
        equipmentRequirementRepository = new EquipmentRequirementRepository(coachExerciseDetailView.getContext());
        exerciseTypeRepository = new ExerciseTypeRepository(coachExerciseDetailView.getContext());
        exerciseRepository = new ExerciseRepository(coachExerciseDetailView.getContext());
    }

    void loadExercise(){
        exerciseId = coachExerciseDetailView.loadIntent();
        exercise = exerciseRepository.findById(exerciseId);
    }

    boolean validateId(){
        return exerciseId != DEFAULT_ID;
    }

    void deleteCurrentExercise(){
        if(exercise.getSensorParameter() == 0){
            exerciseRepository.deleteExercise(exercise);
            coachExerciseDetailNavigator.navigateToCoachExerciseListActivity();
        } else {
            coachExerciseDetailView.showToastMessage(coachExerciseDetailView.getContext().getString(R.string.cannot_delete_sensor_exercise_message));
        }
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
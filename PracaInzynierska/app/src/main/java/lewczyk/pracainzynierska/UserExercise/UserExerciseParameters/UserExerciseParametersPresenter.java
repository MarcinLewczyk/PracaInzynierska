package lewczyk.pracainzynierska.UserExercise.UserExerciseParameters;

import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;

public class UserExerciseParametersPresenter {
    private UserExerciseParametersView view;
    private ExerciseRepository exerciseRepository;
    private Exercise exercise;
    private long exerciseId;

    public UserExerciseParametersPresenter(UserExerciseParametersView view) {
        this.view = view;
        exerciseRepository = new ExerciseRepository(view.getContext());
    }

    void loadExercise(){
        exerciseId = view.loadIntent();
        exercise = exerciseRepository.findById(exerciseId);
    }

    String getExerciseName(){
        return exercise.getExerciseName();
    }

    long getExerciseId(){
        return exerciseId;
    }
}
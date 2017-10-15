package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoDetail;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;

public class CoachExerciseToDoDetailPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachExerciseToDoDetailView coachExerciseToDoDetailView;
    private CoachExerciseToDoDetailNavigator coachExerciseToDoDetailNavigator;
    private ExerciseToDoRepository exerciseToDoRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseToDo exerciseToDo;
    private Exercise exercise;
    private long exerciseToDoId;

    public CoachExerciseToDoDetailPresenter(CoachExerciseToDoDetailView coachExerciseToDoDetailView, CoachExerciseToDoDetailNavigator coachExerciseToDoDetailNavigator) {
        this.coachExerciseToDoDetailView = coachExerciseToDoDetailView;
        this.coachExerciseToDoDetailNavigator = coachExerciseToDoDetailNavigator;
        exerciseToDoRepository = new ExerciseToDoRepository(coachExerciseToDoDetailView.getContext());
        exerciseRepository = new ExerciseRepository(coachExerciseToDoDetailView.getContext());
    }

    void loadExerciseToDo(){
        exerciseToDoId = coachExerciseToDoDetailView.loadIntent();
        exerciseToDo = exerciseToDoRepository.findById(exerciseToDoId);
        loadExercise();
    }

    private void loadExercise(){
        exercise = exerciseRepository.findById(exerciseToDo.getExercise().getId());
    }

    boolean validateId(){
        return exerciseToDoId != DEFAULT_ID;
    }

    void deleteCurrentExerciseToDo(){
        exerciseToDoRepository.deleteExerciseToDo(exerciseToDo);
        coachExerciseToDoDetailNavigator.navigateToExerciseToDoList();
    }

    String getExerciseName(){
        return exercise.getExerciseName();
    }

    String getMusclePart(){
        return exercise.getMusclePart();
    }

    String getExerciseToDoDate(){
        return transformStringDateToDateFormat(exerciseToDo.getDate());
    }

    String getExerciseToDoSeries(){
        return String.valueOf(exerciseToDo.getSeries());
    }

    String getExerciseToDoRepeats(){
        return String.valueOf(exerciseToDo.getRepeats());
    }

    String getExerciseToDoLoad(){
        return String.valueOf(exerciseToDo.getLoad());
    }

    private String transformStringDateToDateFormat(String dateBeforeTransformation){
        if(dateBeforeTransformation.equals("")){
            return "";
        }
        return dateBeforeTransformation.substring(0,4) + "." +
                dateBeforeTransformation.substring(4,6) + "." +
                dateBeforeTransformation.substring(6,8);
    }
}
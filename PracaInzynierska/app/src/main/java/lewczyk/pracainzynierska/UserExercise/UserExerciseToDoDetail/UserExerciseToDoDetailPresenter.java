package lewczyk.pracainzynierska.UserExercise.UserExerciseToDoDetail;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;

public class UserExerciseToDoDetailPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserExerciseToDoDetailView view;
    private ExerciseRepository exerciseRepository;
    private ExerciseToDoRepository exerciseToDoRepository;
    private ExerciseToDo exerciseToDo;
    private Exercise exercise;
    private long exerciseToDoId;

    public UserExerciseToDoDetailPresenter(UserExerciseToDoDetailView view) {
        this.view = view;
        exerciseToDoRepository = new ExerciseToDoRepository(view.getContext());
        exerciseRepository = new ExerciseRepository(view.getContext());
    }

    void loadExerciseToDo(){
        exerciseToDoId = view.loadIntent();
        exerciseToDo = exerciseToDoRepository.findById(exerciseToDoId);
        exercise = exerciseRepository.findById(exerciseToDo.getExercise().getId());
    }

    boolean validateId(){
        return exerciseToDoId != DEFAULT_ID;
    }

    long getExerciseToDoId(){
        return exerciseToDoId;
    }

    String getExerciseName(){
        return exercise.getExerciseName();
    }

    String getMusclePart(){
        return exercise.getMusclePart();
    }

    String getSeries(){
        return String.valueOf(exerciseToDo.getSeries());
    }

    String getRepeats(){
        return String.valueOf(exerciseToDo.getRepeats());
    }

    String getLoad(){
        return String.valueOf(exerciseToDo.getLoad());
    }

    String getDate(){
        return transformStringDateToDateFormat(exerciseToDo.getDate());
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
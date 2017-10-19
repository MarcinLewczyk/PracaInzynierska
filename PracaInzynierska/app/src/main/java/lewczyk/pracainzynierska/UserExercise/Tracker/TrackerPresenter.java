package lewczyk.pracainzynierska.UserExercise.Tracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;

public class TrackerPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private TrackerView view;

    private Exercise exercise;
    private long exerciseId;
    private ExerciseToDo exerciseToDo;
    private long exerciseToDoId;
    private ExerciseInTrainingPlan exerciseInTrainingPlan;
    private long trainingPlanId;

    public TrackerPresenter(TrackerView view) {
        this.view = view;
    }

    void loadExercisePlanData(){
        exerciseId = view.getExerciseId();
        trainingPlanId = view.getTrainingPlanId();
        loadExercise();
        loadExerciseInTrainingPlan();
    }

    private void loadExerciseInTrainingPlan() {
        exerciseInTrainingPlan = new ExerciseInTrainingPlanRepository(view.getContext()).
                findByGivenTrainingPlanAndExercise(new TrainingPlanRepository(view.getContext()).findById(trainingPlanId), exercise);
    }

    void loadExerciseParametersActivityData(){
        exerciseId = view.getExerciseId();
        loadExercise();
    }

    void loadExerciseToDoData(){
        exerciseToDoId = view.getExerciseToDoId();
        loadExerciseToDo();
        this.exerciseId = exerciseToDo.getExercise().getId();
        loadExercise();
    }

    private void loadExerciseToDo(){
        exerciseToDo = new ExerciseToDoRepository(view.getContext()).findById(exerciseToDoId);
    }

    private void loadExercise(){
        exercise = new ExerciseRepository(view.getContext()).findById(exerciseId);
    }

    double getSensorParameter(){
        return  exercise.getSensorParameter();
    }

    double getTrainingPlanLoad(){
        return exerciseInTrainingPlan.getLoad();
    }

    double getExerciseToDoLoad(){
        return exerciseToDo.getLoad();
    }

    boolean validateId(long id){
        return id != DEFAULT_ID;
    }

    void addExerciseToArchive(){
        int secs = (int) (view.getUpdatedTime() / 1000);
        ExerciseArchive exerciseArchive = new ExerciseArchive(0, 0, Math.round(view.getDistanceReached() * 10)/ 10f, getCurrentDateString(), secs, exercise);
        new ExerciseArchiveRepository(view.getContext()).addExerciseArchive(exerciseArchive);
    }

    private String getCurrentDateString(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(c.getTime());
    }

    void deleteCurrentExerciseFromExerciseToDo(){
        new ExerciseToDoRepository(view.getContext()).deleteExerciseToDo(exerciseToDo);
    }
}
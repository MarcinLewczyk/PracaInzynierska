package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNewExerciseDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoNewExerciseDetailPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachExerciseToDoNewExerciseDetailView view;
    private CoachExerciseToDoNewExerciseNavigator navigator;
    private ExerciseRepository exerciseRepository;
    private ExerciseToDoRepository exerciseToDoRepository;
    private Exercise exercise;
    private long exerciseId;

    public CoachExerciseToDoNewExerciseDetailPresenter(CoachExerciseToDoNewExerciseDetailView view, CoachExerciseToDoNewExerciseNavigator navigator) {
        this.view = view;
        this.navigator = navigator;
        exerciseRepository = new ExerciseRepository(view.getContext());
        exerciseToDoRepository = new ExerciseToDoRepository(view.getContext());
    }

    void loadExercise() {
        exerciseId = view.loadIntent();
        if(validateId()){
            exercise = exerciseRepository.findById(exerciseId);
        }
    }

    String getExerciseName() {
        return exercise.getExerciseName();
    }

    boolean validateId() {
        return exerciseId != DEFAULT_ID;
    }

    void addMapExerciseToDo() {
        exerciseToDoRepository.addExerciseToDo(new ExerciseToDo(
                0, 0,
                Double.parseDouble(view.getLoadString()),
                transformDateToString(view.getDateString()), exercise));
        view.addNotification(dateToLong());
        navigator.navigateToExerciseToDoListActivity();
    }

    void addExerciseToDo(){
        exerciseToDoRepository.addExerciseToDo(new ExerciseToDo(
                        Integer.parseInt(view.getSeriesString()),
                        Integer.parseInt(view.getRepeatsString()),
                        Double.parseDouble(view.getLoadString()),
                        transformDateToString(view.getDateString()), exercise));
        view.addNotification(dateToLong());
        navigator.navigateToExerciseToDoListActivity();
    }

    private String transformDateToString(String date) {
        if (date.equals(view.getContext().getString(R.string.tap_to_choose_date))) {
            return "";
        }
        return  date.substring(0, 4) +
                date.substring(5, 7) +
                date.substring(8, 10);
    }

    private long dateToLong(){
        String stringDate = view.getDateString();
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
        long milliseconds = 0;
        try {
            Date d = f.parse(stringDate);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}
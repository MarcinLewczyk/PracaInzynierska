package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;

public class CoachExerciseToDoListPresenter {
    private ExerciseToDoRepository exerciseToDoRepository;

    public CoachExerciseToDoListPresenter(CoachExerciseToDoListView coachExerciseToDoListView) {
        exerciseToDoRepository = new ExerciseToDoRepository(coachExerciseToDoListView.getContext());
    }

    ArrayList<ExerciseToDo> getExerciseToDoList(){
        return (ArrayList<ExerciseToDo>) exerciseToDoRepository.findAll();
    }
}
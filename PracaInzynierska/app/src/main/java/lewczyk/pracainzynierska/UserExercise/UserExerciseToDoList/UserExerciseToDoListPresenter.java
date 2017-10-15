package lewczyk.pracainzynierska.UserExercise.UserExerciseToDoList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;

public class UserExerciseToDoListPresenter {
    private ExerciseToDoRepository exerciseToDoRepository;

    public UserExerciseToDoListPresenter(UserExerciseToDoListView view) {
        exerciseToDoRepository = new ExerciseToDoRepository(view.getContext());
    }

    ArrayList<ExerciseToDo> getExerciseToDoList(){
        return (ArrayList<ExerciseToDo>) exerciseToDoRepository.findAll();
    }
}
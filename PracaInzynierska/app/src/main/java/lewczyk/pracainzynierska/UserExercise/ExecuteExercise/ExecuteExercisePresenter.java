package lewczyk.pracainzynierska.UserExercise.ExecuteExercise;

import lewczyk.pracainzynierska.Data.DefaultId;

public class ExecuteExercisePresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ExecuteExerciseView view;

    public ExecuteExercisePresenter(ExecuteExerciseView view) {
        this.view = view;
    }

    boolean validateId(long id){
        return id != DEFAULT_ID;
    }
}
package lewczyk.pracainzynierska.UserExercise.ExecuteExercise;

import android.content.Context;

public interface ExecuteExerciseView {
    long getExerciseId();
    long getExerciseToDoId();
    long getTrainingPlanId();
    long getUpdatedTime();
    int getCurrentSet();
    int getCurrentRepeat();
    double getLoad();
    Context getContext();
}
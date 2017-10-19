package lewczyk.pracainzynierska.UserExercise.Tracker;

import android.content.Context;

public interface TrackerView {
    long getExerciseId();
    long getExerciseToDoId();
    long getTrainingPlanId();
    long getUpdatedTime();
    float getDistanceReached();
    Context getContext();
}
package lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNewExerciseDetail;

import android.content.Context;

public interface CoachExerciseToDoNewExerciseDetailView {
    long loadIntent();
    String getDateString();
    String getSeriesString();
    String getRepeatsString();
    String getLoadString();
    void addNotification(long date);
    Context getContext();
}
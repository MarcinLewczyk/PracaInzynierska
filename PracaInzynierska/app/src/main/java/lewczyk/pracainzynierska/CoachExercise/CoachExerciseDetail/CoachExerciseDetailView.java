package lewczyk.pracainzynierska.CoachExercise.CoachExerciseDetail;

import android.content.Context;

public interface CoachExerciseDetailView {
    long loadIntent();
    void showToastMessage(String message);
    Context getContext();
}
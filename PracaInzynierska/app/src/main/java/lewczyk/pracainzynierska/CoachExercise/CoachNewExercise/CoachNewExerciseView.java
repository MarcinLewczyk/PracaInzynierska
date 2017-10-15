package lewczyk.pracainzynierska.CoachExercise.CoachNewExercise;

import android.content.Context;

public interface CoachNewExerciseView {
    long loadIntent();
    String getSelectedDifficult();
    String getSelectedEquipmentRequirements();
    String getSelectedExerciseType();
    String getExerciseNameString();
    String getMusclePartString();
    String getDemonstrationString();
    Context getContext();
}
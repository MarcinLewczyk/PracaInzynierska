package lewczyk.pracainzynierska.CoachExercise.CoachExerciseList;

import android.content.Context;

public interface CoachExerciseListView {
    String getSelectedDifficult();
    String getSelectedEquipmentRequirements();
    String getSelectedExerciseType();
    Context getContext();
}
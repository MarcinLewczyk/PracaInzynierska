package lewczyk.pracainzynierska.UserExercise.UserExerciseList;

import android.content.Context;

public interface UserExerciseListView {
    String getSelectedDifficult();
    String getSelectedEquipmentRequirements();
    String getSelectedExerciseType();
    Context getContext();
}
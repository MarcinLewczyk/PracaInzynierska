package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeEdit;

import android.content.Context;

public interface ExercisePurposeEditView {
    long loadIntent();
    String getPurposeStringEditText();
    String getCurrentStateStringEditText();
    Context getContext();
}
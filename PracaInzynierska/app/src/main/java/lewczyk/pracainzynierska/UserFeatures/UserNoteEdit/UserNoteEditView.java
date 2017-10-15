package lewczyk.pracainzynierska.UserFeatures.UserNoteEdit;

import android.content.Context;

public interface UserNoteEditView {
    long loadIntent();
    String getUserNoteStringEditText();
    Context getContext();
}
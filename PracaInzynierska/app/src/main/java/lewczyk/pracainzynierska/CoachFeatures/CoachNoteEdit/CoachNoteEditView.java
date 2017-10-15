package lewczyk.pracainzynierska.CoachFeatures.CoachNoteEdit;

import android.content.Context;

public interface CoachNoteEditView {
    long loadIntent();
    String getCoachNoteStringEditText();
    Context getContext();
}
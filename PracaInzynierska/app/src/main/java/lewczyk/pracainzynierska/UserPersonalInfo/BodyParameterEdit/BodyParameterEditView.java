package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterEdit;

import android.content.Context;

public interface BodyParameterEditView {
    long loadIntent();
    double getParameterStateEditText();
    String getParameterNameStringEditText();
    Context getContext();
}
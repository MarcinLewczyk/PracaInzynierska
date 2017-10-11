package lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList;

import android.content.Context;

import java.util.ArrayList;

public interface BodyParametersListView {
    void setActivityTitle(String title);
    void showBodyParameterList(ArrayList content);
    Context getContext();
}
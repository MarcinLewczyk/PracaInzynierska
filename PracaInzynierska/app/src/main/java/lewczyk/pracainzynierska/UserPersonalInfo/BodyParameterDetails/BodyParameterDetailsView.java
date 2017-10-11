package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails;

import android.content.Context;

import java.util.ArrayList;

public interface BodyParameterDetailsView {
    void setActivityTitle(String title);
    void loadIntent();
    void loadViewContent();
    void showArchiveList(ArrayList content);
    Context getContext();
}
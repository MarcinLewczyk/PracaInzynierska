package lewczyk.pracainzynierska.MainMenus;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import lewczyk.pracainzynierska.BuildConfig;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseMenuActivity;
import lewczyk.pracainzynierska.UserFeatures.ArchiveListActivity;
import lewczyk.pracainzynierska.UserFeatures.UserNoteListActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.UserPersonalInfoMenuActivity;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UserMainMenuActivityTest {
    private UserMainMenuActivity activity;

    @Before
    public void setUp() throws Exception{
        activity = Robolectric.setupActivity(UserMainMenuActivity.class);
    }

    @Test
    public void title() throws Exception{
        assertEquals(activity.getTitle(), activity.getString(R.string.user_menu));
    }

    @Test
    public void clickExerciseMenuButton() throws Exception{
        activity.findViewById(R.id.userExerciseButton).performClick();
        Intent expectedIntent = new Intent(activity, UserExerciseMenuActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickNotesListButton() throws Exception{
        activity.findViewById(R.id.userNotesMainMenuButton).performClick();
        Intent expectedIntent = new Intent(activity, UserNoteListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickPersonalInfoButton() throws Exception{
        activity.findViewById(R.id.userPersonalInfoButton).performClick();
        Intent expectedIntent = new Intent(activity, UserPersonalInfoMenuActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickArchiveListButton() throws Exception{
        activity.findViewById(R.id.userArchiveButton).performClick();
        Intent expectedIntent = new Intent(activity, ArchiveListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
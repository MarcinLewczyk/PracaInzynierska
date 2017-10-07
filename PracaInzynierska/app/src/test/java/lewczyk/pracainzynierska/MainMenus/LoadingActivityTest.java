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

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoadingActivityTest {
    private LoadingActivity activity;

    @Before
    public void setUp() throws Exception{
        activity = Robolectric.setupActivity(LoadingActivity.class);
    }

    @Test
    public void title() throws Exception{
        assertEquals(activity.getTitle(), activity.getString(R.string.user_choice));
    }

    @Test
    public void clickingUserButton() throws Exception{
        activity.findViewById(R.id.userButton).performClick();
        Intent expectedIntent = new Intent(activity, UserMainMenuActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingCoachButton() throws Exception{
        activity.findViewById(R.id.coachButton).performClick();
        Intent expectedIntent = new Intent(activity, CoachMainMenuActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
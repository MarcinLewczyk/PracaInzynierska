package lewczyk.pracainzynierska.CoachExercise;

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
import static junit.framework.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CoachExerciseDetailActivityTest {
    private CoachExerciseDetailActivity activity;
    private Intent intent;
    private long exerciseId = 1L;

    @Before
    public void setUp() throws Exception{
        intent = new Intent();
        activity = Robolectric.setupActivity(CoachExerciseDetailActivity.class);
    }

    @Test
    public void title() throws Exception{
        assertEquals(activity.getTitle(), activity.getString(R.string.exercise_details));
    }

    @Test
    public void modificationButtonPressedWithExercise(){
        intent.putExtra("exerciseId", exerciseId);
        activity = Robolectric.buildActivity(CoachExerciseDetailActivity.class).withIntent(intent).create().get();

        activity.findViewById(R.id.coachExerciseModButton).performClick();
        Intent expectedIntent = new Intent(activity, CoachNewExerciseActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void modificationButtonPressedWithoutExercise(){
        activity.findViewById(R.id.coachExerciseModButton).performClick();
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertNull(actual);
    }
/*  not working couse i have not idea how to mock or prepare database to tests
    @Test
    public void deleteButtonPressedWithExercise(){
        intent.putExtra("exerciseId", exerciseId);
        activity = Robolectric.buildActivity(CoachExerciseDetailActivity.class).withIntent(intent).create().get();

        activity.findViewById(R.id.coachExerciseDelButton).performClick();
        Intent expectedIntent = new Intent(activity, CoachExerciseListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
*/
    @Test
    public void deleteButtonPressedWithoutExercise(){
        activity.findViewById(R.id.coachExerciseDelButton).performClick();
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertNull(actual);
    }

    @Test
    public void onBackPressed(){
        activity.onBackPressed();
        Intent expectedIntent = new Intent(activity, CoachExerciseListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
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
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseListActivity;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoListActivity;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteListActivity;
import lewczyk.pracainzynierska.R;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CoachMainMenuActivityTest {
    private CoachMainMenuActivity activity;

    @Before
    public void setUp() throws Exception{
        activity = Robolectric.setupActivity(CoachMainMenuActivity.class);
    }

    @Test
    public void title() throws Exception{
        assertEquals(activity.getTitle(), activity.getString(R.string.coach_menu));
    }

    @Test
    public void clickExerciseToDoButton() throws Exception{
        activity.findViewById(R.id.coachExerciseToDoButton).performClick();
        Intent expectedIntent = new Intent(activity, CoachExerciseToDoListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickExerciseListButton() throws Exception{
        activity.findViewById(R.id.coachExerciseListButton).performClick();
        Intent expectedIntent = new Intent(activity, CoachExerciseListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickNotesListButton(){
        activity.findViewById(R.id.coachNotesListButton).performClick();
        Intent expectedIntent = new Intent(activity, CoachNoteListActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
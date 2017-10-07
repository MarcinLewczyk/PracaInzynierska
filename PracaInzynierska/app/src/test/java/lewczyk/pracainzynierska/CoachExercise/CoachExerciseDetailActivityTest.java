package lewczyk.pracainzynierska.CoachExercise;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import lewczyk.pracainzynierska.BuildConfig;
import lewczyk.pracainzynierska.R;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CoachExerciseDetailActivityTest {
    private CoachExerciseDetailActivity activity;

    @Before
    public void setUp(){
        activity = Robolectric.setupActivity(CoachExerciseDetailActivity.class);
    }

    @Test
    public void title() throws Exception{
        assertEquals(activity.getTitle(), activity.getString(R.string.exercise_details));
    }


}
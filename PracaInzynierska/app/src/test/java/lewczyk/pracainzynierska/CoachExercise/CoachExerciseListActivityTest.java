package lewczyk.pracainzynierska.CoachExercise;


import android.content.Intent;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.BuildConfig;
import lewczyk.pracainzynierska.R;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CoachExerciseListActivityTest {
    private CoachExerciseListActivity activity;
    private Intent intent;
    @BindView(R.id.coachExerciseDifficultLevelSpinner) Spinner difficultSpinner;
    @BindView(R.id.coachExerciseEquipmentRequirementSpinner) Spinner equipmentSpinner;
    @BindView(R.id.coachExerciseTypeSpinner) Spinner typeSpinner;

    //not working- still database problem
    @Before
    public void setUp() throws Exception{
        intent = new Intent();
        activity = Robolectric.setupActivity(CoachExerciseListActivity.class);
        ButterKnife.bind(activity);
    }

    @Test
    public void title() throws Exception{
        assertEquals(activity.getTitle(), activity.getString(R.string.exercises_list));
    }
/*
    @Test
    public void addButtonPressed(){
        activity.findViewById(R.id.coachExerciseAddButton).performClick();
        Intent expectedIntent = new Intent(activity,  CoachNewExerciseActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void spinnersContent(){
        assertEquals(activity.getString(R.string.no_filter), difficultSpinner.getSelectedItem());
        assertEquals(activity.getString(R.string.no_filter), equipmentSpinner.getSelectedItem());
        assertEquals(activity.getString(R.string.no_filter), typeSpinner.getSelectedItem());
    }*/
}
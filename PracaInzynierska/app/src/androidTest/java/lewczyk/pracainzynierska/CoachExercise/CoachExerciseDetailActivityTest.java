package lewczyk.pracainzynierska.CoachExercise;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import butterknife.BindView;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseDetail.CoachExerciseDetailActivity;
import lewczyk.pracainzynierska.R;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CoachExerciseDetailActivityTest {
    private long exerciseId = 1L;

    @BindView(R.id.coachExerciseToDoMusclePartTextView) TextView musclePart;
    @BindView(R.id.coachExerciseToDoDateTextView) TextView date;
    @BindView(R.id.coachExerciseToDoSeriesTextView) TextView series;
    @BindView(R.id.coachExerciseToDoRepeatsTextView) TextView repeats;
    @BindView(R.id.coachExerciseToDoLoadTextView) TextView load;

    @Rule
    public ActivityTestRule<CoachExerciseDetailActivity> rule = new ActivityTestRule(CoachExerciseDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("exerciseId", exerciseId);
            return intent;
        }
    };

    @Test
    public void title() throws Exception{
        CoachExerciseDetailActivity activity = rule.getActivity();
        Assert.assertEquals(activity.getTitle(), activity.getString(R.string.exercise_details));
    }

/*
    @Test
    public void content() throws Exception {
        CoachExerciseDetailActivity activity = rule.getActivity();
        TextView exerciseName = (TextView) activity.findViewById(R.id.coachExerciseToDoNameTextView);
        assertEquals("Kaptury", exerciseName.getText().toString());

    }
*/
    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("lewczyk.pracainzynierska", appContext.getPackageName());
    }
}
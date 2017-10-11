package lewczyk.pracainzynierska.CoachExercise.UserPersonalInfo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class BodyParameterListActivityTest {

    @Rule
    public ActivityTestRule<BodyParametersListActivity> rule  = new  ActivityTestRule<>(BodyParametersListActivity.class);

    @Test
    public void ensureListViewIsPresent() throws Exception {
        BodyParametersListActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.bodyParametersListView);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(ListView.class));
        ListView listView = (ListView) viewById;
        ListAdapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(ArrayAdapter.class));
        assertThat(adapter.getCount(), greaterThan(0));
    }
}

package lewczyk.pracainzynierska.UserPersonalInfo;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListPresenter;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListView;

public class BodyParameterListTest {
    @Mock
    private BodyParametersListView bodyParametersListView;

    private BodyParametersListPresenter presenter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenter = new BodyParametersListPresenter(bodyParametersListView);
    }
}
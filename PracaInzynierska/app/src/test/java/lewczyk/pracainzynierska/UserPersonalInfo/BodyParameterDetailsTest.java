package lewczyk.pracainzynierska.UserPersonalInfo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails.BodyParameterDetailsPresenter;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails.BodyParameterDetailsView;

public class BodyParameterDetailsTest {

    @Mock
    private BodyParameterDetailsView bodyParameterDetailsView;

    private BodyParameterDetailsPresenter presenter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        presenter = new BodyParameterDetailsPresenter(bodyParameterDetailsView);
    }

    @Test
    public void idValidation(){
        boolean shouldFalse = presenter.validateId(-1);
        boolean shouldTrue = presenter.validateId(0);
        Assert.assertFalse(shouldFalse);
        Assert.assertTrue(shouldTrue);
    }

}
package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BodyParameterDetailsTest {

    @Mock
    private BodyParameterDetailsView bodyParameterDetailsView;

    @Mock
    private BodyParameterRepository repository;

    @InjectMocks
    private BodyParameterDetailsPresenter presenter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void idValidation(){
        boolean shouldFalse = presenter.validateId(-1);
        boolean shouldTrue = presenter.validateId(0);
        Assert.assertFalse(shouldFalse);
        Assert.assertTrue(shouldTrue);
    }

    @Test
    public void isBodyParameterNull(){
        BodyParameter bd = new BodyParameter("XY", 1.5);
        when(repository.findById(bd.getId())).thenReturn(bd);
        presenter.getBodyParameter(bd.getId());
      //  Assert.assertNull(presenter.);
    }

}
package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BodyParameterDetailsTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private BodyParameterRepository repository;

    @Mock
    private BodyParameterDetailsView view;

    @Mock
    private Context context;

    @Mock
    private BodyParameterDetailsNavigator navigator;

    @InjectMocks
    private BodyParameterDetailsPresenter presenter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        presenter = new BodyParameterDetailsPresenter(view, navigator);
    }

   /* @Test
    public void idValidation(){
        boolean shouldFalse = presenter.validateId();
        boolean shouldTrue = presenter.validateId();
        Assert.assertFalse(shouldFalse);
        Assert.assertTrue(shouldTrue);
    }*/

    @Test
    public void isBodyParameterNull(){
        BodyParameter bp = new BodyParameter("xyz", 1.5);
     //   when(view.loadIntent()).thenReturn(anyLong());
        when(repository.findById(anyLong())).thenReturn(bp);
        Assert.assertTrue(presenter.getParameterId() != 0);
      //  Assert.assertNotNull(;
       // Assert.assertNotNull(presenter.getParameterId());

     //   Assert.assertEquals(bd.getId(), presenter.getParameterId());

    }
}
package lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.R;

public class BodyParametersListPresenter {

    private BodyParametersListView bodyParametersListView;

    public BodyParametersListPresenter(BodyParametersListView bodyParametersListView){
        this.bodyParametersListView = bodyParametersListView;
    }

    public String getTitle(){
        return bodyParametersListView.getContext().getString(R.string.parameters);
    }

    public ArrayList getBodyParametersList(){
        return (ArrayList) BodyParameterRepository.findAll(bodyParametersListView.getContext());
    }

}
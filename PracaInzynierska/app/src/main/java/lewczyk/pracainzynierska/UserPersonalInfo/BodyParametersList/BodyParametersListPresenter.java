package lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.BodyParameterRepository;

public class BodyParametersListPresenter {
    private BodyParameterRepository bodyParameterRepository;

    public BodyParametersListPresenter(BodyParametersListView bodyParametersListView){
        this.bodyParameterRepository = new BodyParameterRepository(bodyParametersListView.getContext());
    }

    ArrayList getBodyParametersList(){
        return (ArrayList) bodyParameterRepository.findAll();
    }
}
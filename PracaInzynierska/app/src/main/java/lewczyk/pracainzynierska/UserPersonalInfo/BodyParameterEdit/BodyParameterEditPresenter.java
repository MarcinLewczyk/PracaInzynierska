package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterEdit;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;

public class BodyParameterEditPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private BodyParameterRepository bodyParameterRepository;
    private BodyParameterEditView bodyParameterEditView;
    private BodyParameterEditNavigator bodyParameterEditNavigator;
    private BodyParameter bodyParameter;
    private long parameterId;

    public BodyParameterEditPresenter(BodyParameterEditView bodyParameterEditView, BodyParameterEditNavigator bodyParameterEditNavigator) {
        this.bodyParameterEditView = bodyParameterEditView;
        this.bodyParameterEditNavigator = bodyParameterEditNavigator;
        this.bodyParameterRepository = new BodyParameterRepository(bodyParameterEditView.getContext());
    }

    void loadBodyParameter() {
        parameterId = bodyParameterEditView.loadIntent();
        if(validateId()){
            bodyParameter = bodyParameterRepository.findById(parameterId);
        }
    }

    String getParametersMuscleName(){
        return bodyParameter.getMuscleName();
    }

    String getParametersCircumference(){
        return Double.toString(bodyParameter.getCircumference());
    }

    boolean validateId(){
        return parameterId != DEFAULT_ID;
    }

    void addBodyParameter(){
        bodyParameterRepository.addBodyParameter(new BodyParameter(
                bodyParameterEditView.getParameterNameStringEditText(),
                bodyParameterEditView.getParameterStateEditText()));
        bodyParameterEditNavigator.navigateToBodyParameterList();
    }

    void updateBodyParameter(){
        bodyParameter.getParametersArchive().add(new BodyParameterArchive(bodyParameter.getCircumference(), bodyParameter));
        bodyParameter.setMuscleName(bodyParameterEditView.getParameterNameStringEditText());
        bodyParameter.setCircumference(bodyParameterEditView.getParameterStateEditText());
        bodyParameterRepository.updateBodyParameter(bodyParameter);
        bodyParameterEditNavigator.navigateToBodyParameterList();
    }
}
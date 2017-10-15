package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;

public class BodyParameterDetailsPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private BodyParameterRepository bodyParameterRepository;
    private BodyParameter bodyParameter;
    private BodyParameterDetailsView bodyParameterDetailsView;
    private BodyParameterDetailsNavigator bodyParameterDetailsNavigator;
    private long parameterId;

    public BodyParameterDetailsPresenter(BodyParameterDetailsView bodyParameterDetailsView, BodyParameterDetailsNavigator bodyParameterDetailsNavigator) {
        this.bodyParameterDetailsView = bodyParameterDetailsView;
        this.bodyParameterDetailsNavigator = bodyParameterDetailsNavigator;
        this.bodyParameterRepository = new BodyParameterRepository(bodyParameterDetailsView.getContext());
    }

    void loadBodyParameter() {
        parameterId = bodyParameterDetailsView.loadIntent();
        bodyParameter = bodyParameterRepository.findById(parameterId);
    }

    String getParametersMuscleName(){
        return bodyParameter.getMuscleName();
    }

    String getParametersCircumference(){
        return Double.toString(bodyParameter.getCircumference());
    }

    ArrayList getBodyParameterArchive(){
        return getReversedArchive(bodyParameter.getParametersArchive());
    }

    private ArrayList getReversedArchive(ForeignCollection<BodyParameterArchive> bodyParameterArchive) {
        ArrayList<BodyParameterArchive> listToReverse = new ArrayList<>();
        for(BodyParameterArchive e: bodyParameterArchive){
            listToReverse.add(e);
        }
        Collections.reverse(listToReverse);
        return listToReverse;
    }

    void deleteCurrentBodyParameter(){
        bodyParameterRepository.deleteBodyParameter(bodyParameter);
        bodyParameterDetailsNavigator.navigateBack();
    }

    boolean validateId(){
        return parameterId != DEFAULT_ID;
    }

    long getParameterId(){
        return parameterId;
    }
}
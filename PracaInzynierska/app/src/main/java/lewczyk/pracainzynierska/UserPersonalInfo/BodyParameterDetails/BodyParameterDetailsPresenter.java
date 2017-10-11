package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;
import lewczyk.pracainzynierska.R;

public class BodyParameterDetailsPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private BodyParameterDetailsView bodyParameterDetailsView;
    private BodyParameter bodyParameter;

    public BodyParameterDetailsPresenter(BodyParameterDetailsView bodyParameterDetailsView) {
        this.bodyParameterDetailsView = bodyParameterDetailsView;
    }

    public String getTitle(){
        return bodyParameterDetailsView.getContext().getString(R.string.parameter_detail);
    }

    public void getBodyParameter(long parameterId) {
         bodyParameter = BodyParameterRepository.findById(bodyParameterDetailsView.getContext(), parameterId);
    }

    public String getParametersMuscleName(){
        return bodyParameter.getMuscleName();
    }

    public String getParametersCircumference(){
        return Double.toString(bodyParameter.getCircumference());
    }

    public ArrayList getBodyParameterArchive(){
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

    public void deleteGivenBodyParameter(long parameterId){
        BodyParameterRepository.deleteBodyParameter(bodyParameterDetailsView.getContext(), BodyParameterRepository.findById(bodyParameterDetailsView.getContext(), parameterId));
    }

    public boolean validateId(long id){
        return  id != DEFAULT_ID;
    }
}

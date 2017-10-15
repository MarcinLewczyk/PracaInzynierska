package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeDetails;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurposeArchive;

public class ExercisePurposeDetailsPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ExercisePurposeRepository exercisePurposeRepository;
    private ExercisePurpose exercisePurpose;
    private ExercisePurposeDetailsView exercisePurposeDetailsView;
    private ExercisePurposeDetailsNavigator exercisePurposeDetailsNavigator;
    private long purposeId;

    public ExercisePurposeDetailsPresenter(ExercisePurposeDetailsView exercisePurposeDetailsView, ExercisePurposeDetailsNavigator exercisePurposeDetailsNavigator) {
        this.exercisePurposeDetailsView = exercisePurposeDetailsView;
        this.exercisePurposeDetailsNavigator = exercisePurposeDetailsNavigator;
        this.exercisePurposeRepository = new ExercisePurposeRepository(exercisePurposeDetailsView.getContext());
    }

    void loadExercisePurpose(){
        purposeId = exercisePurposeDetailsView.loadIntent();
        exercisePurpose = exercisePurposeRepository.findById(purposeId);
    }

    String getExercisePurpose(){
        return exercisePurpose.getExercisePurpose();
    }

    String getExerciseCurrentState(){
        return exercisePurpose.getCurrentState();
    }

    ArrayList getExercisePurposeArchive(){
        return getReversedArchive(exercisePurpose.getPurposesArchive());
    }

    boolean validateId(){
        return purposeId != DEFAULT_ID;
    }

    private ArrayList getReversedArchive(ForeignCollection<ExercisePurposeArchive> archive){
        ArrayList<ExercisePurposeArchive> reversedList = new ArrayList<>();
        for(ExercisePurposeArchive e: archive){
            reversedList.add(e);
        }
        Collections.reverse(reversedList);
        return reversedList;
    }

    void deleteCurrentExercisePurpose(){
        exercisePurposeRepository.deleteExercisePurpose(exercisePurpose);
        exercisePurposeDetailsNavigator.navigateBack();
    }

    long getPurposeId(){
        return purposeId;
    }
}
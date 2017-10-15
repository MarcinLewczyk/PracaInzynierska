package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;

public class ExercisePurposeListPresenter{
    private ExercisePurposeRepository exercisePurposeRepository;

    public ExercisePurposeListPresenter(ExercisePurposeListView exercisePurposeListView){
        exercisePurposeRepository = new ExercisePurposeRepository(exercisePurposeListView.getContext());
    }

    ArrayList getExercisePurposeList(){
        return (ArrayList) exercisePurposeRepository.findAll();
    }
}
package lewczyk.pracainzynierska.UserExercise.UserExercisePlanList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;

public class UserExercisePlanListPresenter {
    private TrainingPlanRepository trainingPlanRepository;

    public UserExercisePlanListPresenter(UserExercisePlanListView view) {
        trainingPlanRepository = new TrainingPlanRepository(view.getContext());
    }

    ArrayList<TrainingPlan> getAllTrainingPlans(){
        return (ArrayList<TrainingPlan>) trainingPlanRepository.findAll();
    }
}
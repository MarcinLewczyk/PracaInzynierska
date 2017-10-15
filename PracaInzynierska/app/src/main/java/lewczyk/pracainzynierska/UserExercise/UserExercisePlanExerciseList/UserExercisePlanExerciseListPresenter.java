package lewczyk.pracainzynierska.UserExercise.UserExercisePlanExerciseList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;

public class UserExercisePlanExerciseListPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserExercisePlanExerciseListView view;
    private TrainingPlanRepository trainingPlanRepository;
    private ExerciseInTrainingPlanRepository exerciseInTrainingPlanRepository;
    private TrainingPlan trainingPlan;
    private long planId;


    public UserExercisePlanExerciseListPresenter(UserExercisePlanExerciseListView view) {
        this.view = view;
        trainingPlanRepository = new TrainingPlanRepository(view.getContext());
        exerciseInTrainingPlanRepository = new ExerciseInTrainingPlanRepository(view.getContext());
    }

    void loadTrainingPlan(){
        planId = view.loadIntent();
        trainingPlan = trainingPlanRepository.findById(planId);
    }

    ArrayList<Exercise> getExercisesInTrainingPlan(){
        return (ArrayList<Exercise>) exerciseInTrainingPlanRepository.findAllWithGivenTrainingPlan(trainingPlan);
    }

    boolean validateId(){
        return planId != DEFAULT_ID;
    }

    TrainingPlan getTrainingPlan(){
        return trainingPlan;
    }
}
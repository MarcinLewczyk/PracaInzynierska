package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;

public class ExerciseInTrainingPlanRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public ExerciseInTrainingPlanRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<ExerciseInTrainingPlan> findAll(){
        List<ExerciseInTrainingPlan> list = null;
        try {
            list = databaseHelper.getExerciseInTrainingPlanDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExerciseInTrainingPlan findById(long exerciseInTrainingPlanId){
        ExerciseInTrainingPlan exerciseInTrainingPlan = null;
        try {
            exerciseInTrainingPlan = databaseHelper.getExerciseInTrainingPlanDao().queryForId(exerciseInTrainingPlanId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseInTrainingPlan;
    }

    public void addExerciseInTrainingPlan(ExerciseInTrainingPlan exerciseInTrainingPlan){
        try {
            databaseHelper.getExerciseInTrainingPlanDao().create(exerciseInTrainingPlan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExerciseInTrainingPlan(ExerciseInTrainingPlan exerciseInTrainingPlan){
        try {
            databaseHelper.getExerciseInTrainingPlanDao().update(exerciseInTrainingPlan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExerciseInTrainingPlan(ExerciseInTrainingPlan exerciseInTrainingPlan){
        try {
            databaseHelper.getExerciseInTrainingPlanDao().delete(exerciseInTrainingPlan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ExerciseInTrainingPlan findByGivenTrainingPlanAndExercise(TrainingPlan trainingPlan, Exercise exercise){
        List<ExerciseInTrainingPlan> exerciseInTrainingPlan = findAll();
        for(ExerciseInTrainingPlan e: exerciseInTrainingPlan){
            if(e.getExercise().getId() == exercise.getId() && e.getTrainingPlan().getId() == trainingPlan.getId()){
                return e;
            }
        }
        return null;
    }

    public List<Exercise> findAllWithGivenTrainingPlan(TrainingPlan trainingPlan){
        List<Exercise> filteredList = new ArrayList<>();
        List<ExerciseInTrainingPlan> tmp = findAll();
        for(ExerciseInTrainingPlan e: tmp){
            if(e.getTrainingPlan().getId() == trainingPlan.getId()){
                filteredList.add(e.getExercise());
            }
        }
        return filteredList;
    }
}
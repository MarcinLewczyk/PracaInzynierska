package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;

public class ExerciseInTrainingPlanRepository {

    public static List<ExerciseInTrainingPlan> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseInTrainingPlanDao().queryForAll();
    }

    public static ExerciseInTrainingPlan findById(Context context, long exerciseInTrainingPlanId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseInTrainingPlanDao().queryForId(exerciseInTrainingPlanId);
    }

    public static void addExerciseInTrainingPlan(Context context, ExerciseInTrainingPlan exerciseInTrainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseInTrainingPlanDao().create(exerciseInTrainingPlan);
    }

    public static void updateExerciseInTrainingPlan(Context context, ExerciseInTrainingPlan exerciseInTrainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseInTrainingPlanDao().update(exerciseInTrainingPlan);
    }

    public static void deleteExerciseInTrainingPlan(Context context, ExerciseInTrainingPlan exerciseInTrainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseInTrainingPlanDao().delete(exerciseInTrainingPlan);
    }

    public static List<Exercise> findAllWithGivenTrainingPlan(Context context, TrainingPlan trainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        List<Exercise> filteredList = new ArrayList<>();
        List<ExerciseInTrainingPlan> tmp = databaseHelper.getExerciseInTrainingPlanDao().queryForAll();
        for(ExerciseInTrainingPlan e: tmp){
            if(e.getTrainingPlan().getId() == trainingPlan.getId()){
                filteredList.add(e.getExercise());
            }
        }
        return filteredList;
    }
}
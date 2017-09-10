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

    public static ExerciseInTrainingPlan findByGivenTrainingPlanAndExercise(Context context, TrainingPlan trainingPlan, Exercise exercise){
        List<ExerciseInTrainingPlan> exerciseInTrainingPlan = findAll(context);
        for(ExerciseInTrainingPlan e: exerciseInTrainingPlan){
            if(e.getExercise().getId() == exercise.getId() && e.getTrainingPlan().getId() == trainingPlan.getId()){
                return e;
            }
        }
        return null;
    }

    public static List<Exercise> findAllWithGivenTrainingPlan(Context context, TrainingPlan trainingPlan){
        List<Exercise> filteredList = new ArrayList<>();
        List<ExerciseInTrainingPlan> tmp = findAll(context);
        for(ExerciseInTrainingPlan e: tmp){
            if(e.getTrainingPlan().getId() == trainingPlan.getId()){
                filteredList.add(e.getExercise());
            }
        }
        return filteredList;
    }
}
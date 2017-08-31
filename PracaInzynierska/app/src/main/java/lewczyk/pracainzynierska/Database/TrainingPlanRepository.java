package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;

public class TrainingPlanRepository {

    public static List<TrainingPlan> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getTrainingPlanDao().queryForAll();
    }

    public static TrainingPlan findById(Context context, long trainingPlanId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getTrainingPlanDao().queryForId(trainingPlanId);
    }

    public static void addTrainingPlan(Context context, TrainingPlan trainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getTrainingPlanDao().create(trainingPlan);
    }

    public static void updateTrainingPlan(Context context, TrainingPlan trainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getTrainingPlanDao().update(trainingPlan);
    }

    public static void deleteTrainingPlan(Context context, TrainingPlan trainingPlan){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getTrainingPlanDao().delete(trainingPlan);
    }
}
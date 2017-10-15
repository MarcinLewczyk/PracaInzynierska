package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;

public class TrainingPlanRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public TrainingPlanRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<TrainingPlan> findAll(){
        List<TrainingPlan> list = null;
        try {
            list = databaseHelper.getTrainingPlanDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TrainingPlan findById(long trainingPlanId){
        TrainingPlan trainingPlan = null;
        try {
            trainingPlan = databaseHelper.getTrainingPlanDao().queryForId(trainingPlanId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainingPlan;
    }

    public void addTrainingPlan(TrainingPlan trainingPlan){
        try {
            databaseHelper.getTrainingPlanDao().create(trainingPlan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTrainingPlan(TrainingPlan trainingPlan){
        try {
            databaseHelper.getTrainingPlanDao().update(trainingPlan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrainingPlan(TrainingPlan trainingPlan){
        try {
            databaseHelper.getTrainingPlanDao().delete(trainingPlan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
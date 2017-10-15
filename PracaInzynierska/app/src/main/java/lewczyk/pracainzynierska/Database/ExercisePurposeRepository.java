package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;

public class ExercisePurposeRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public ExercisePurposeRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<ExercisePurpose> findAll(){
        List<ExercisePurpose> list = null;
        try {
            list = databaseHelper.getExercisePurposeDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExercisePurpose findById(long exercisePurposeId){
       ExercisePurpose exercisePurpose = null;
        try {
            exercisePurpose = databaseHelper.getExercisePurposeDao().queryForId(exercisePurposeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercisePurpose;
    }

    public void addExercisePurpose(ExercisePurpose exercisePurpose){
        try {
            databaseHelper.getExercisePurposeDao().create(exercisePurpose);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExercisePurpose(ExercisePurpose exercisePurpose){
        try {
            databaseHelper.getExercisePurposeDao().update(exercisePurpose);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExercisePurpose(ExercisePurpose exercisePurpose){
        try {
            databaseHelper.getExercisePurposeDao().delete(exercisePurpose);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package lewczyk.pracainzynierska.Database;


import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;

public class ExerciseArchiveRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public ExerciseArchiveRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<ExerciseArchive> findAll(){
        List<ExerciseArchive> list = null;
        try {
            list = databaseHelper.getExerciseArchiveDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExerciseArchive findById(long exerciseArchiveId){
        ExerciseArchive exerciseArchive = null;
        try {
            exerciseArchive = databaseHelper.getExerciseArchiveDao().queryForId(exerciseArchiveId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseArchive;
    }

    public void addExerciseArchive(ExerciseArchive exerciseArchive){
        try {
            databaseHelper.getExerciseArchiveDao().create(exerciseArchive);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExerciseArchive(ExerciseArchive exerciseArchive){
        try {
            databaseHelper.getExerciseArchiveDao().update(exerciseArchive);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExerciseArchive(ExerciseArchive exerciseArchive){
        try {
            databaseHelper.getExerciseArchiveDao().delete(exerciseArchive);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package lewczyk.pracainzynierska.Database;


import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;

public class ExerciseArchiveRepository {

    public static List<ExerciseArchive> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseArchiveDao().queryForAll();
    }

    public static ExerciseArchive findById(Context context, long exerciseArchiveId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseArchiveDao().queryForId(exerciseArchiveId);
    }

    public static void addExerciseArchive(Context context, ExerciseArchive exerciseArchive){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseArchiveDao().create(exerciseArchive);
    }

    public static void updateExerciseArchive(Context context, ExerciseArchive exerciseArchive){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseArchiveDao().update(exerciseArchive);
    }

    public static void deleteExerciseArchive(Context context, ExerciseArchive exerciseArchive){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseArchiveDao().delete(exerciseArchive);
    }
}
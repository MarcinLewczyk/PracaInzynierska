package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;

public class ExercisePurposeRepository {

    public static List<ExercisePurpose> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExercisePurposeDao().queryForAll();
    }

    public static ExercisePurpose findById(Context context, long exercisePurposeId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExercisePurposeDao().queryForId(exercisePurposeId);
    }

    public static void addExercisePurpose(Context context, ExercisePurpose exercisePurpose){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExercisePurposeDao().create(exercisePurpose);
    }

    public static void updateExercisePurpose(Context context, ExercisePurpose exercisePurpose){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExercisePurposeDao().update(exercisePurpose);
    }

    public static void deleteExercisePurpose(Context context, ExercisePurpose exercisePurpose){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExercisePurposeDao().delete(exercisePurpose);
    }
}
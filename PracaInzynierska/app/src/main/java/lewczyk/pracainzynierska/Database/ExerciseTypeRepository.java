package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class ExerciseTypeRepository {

    public static List<ExerciseType> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseTypeDao().queryForAll();
    }

    public static ExerciseType findById(Context context, long exerciseTypeId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseTypeDao().queryForId(exerciseTypeId);
    }

    public static void addExerciseType(Context context, ExerciseType exerciseType){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseTypeDao().create(exerciseType);
    }

    public static void updateExerciseType(Context context, ExerciseType exerciseType){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseTypeDao().update(exerciseType);
    }

    public static void deleteExerciseType(Context context, ExerciseType exerciseType){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseTypeDao().delete(exerciseType);
    }

    public static List<String> findAllNames(Context context){
        List<ExerciseType> tmp = findAll(context);
        List<String> categoriesName = new ArrayList<>();
        for(ExerciseType t: tmp){
            categoriesName.add(t.getName());
        }
        return categoriesName;
    }

    public static ExerciseType findByName(Context context, String name){
        List<ExerciseType> tmp = findAll(context);
        ExerciseType exerciseType = null;
        for(ExerciseType e: tmp){
            if(e.getName().equals(name)){
                exerciseType = e;
                break;
            }
        }
        return exerciseType;
    }
}
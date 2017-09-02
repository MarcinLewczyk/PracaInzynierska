package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;

public class DifficultLevelRepository {

    public static List<DifficultLevel> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getDifficultLevelDao().queryForAll();
    }

    public static DifficultLevel findById(Context context, long difficultLevelId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getDifficultLevelDao().queryForId(difficultLevelId);
    }

    public static void addDifficultLevel(Context context, DifficultLevel difficultLevel){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getDifficultLevelDao().create(difficultLevel);
    }

    public static void updateDifficultLevel(Context context, DifficultLevel difficultLevel){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getDifficultLevelDao().update(difficultLevel);
    }

    public static void deleteDifficultLevel(Context context, DifficultLevel difficultLevel){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getDifficultLevelDao().delete(difficultLevel);
    }

    public static List<String> findAllNames(Context context){
        List<DifficultLevel> tmp = findAll(context);
        List<String> categoriesName = new ArrayList<>();
        for(DifficultLevel d: tmp){
            categoriesName.add(d.getName());
        }
        return categoriesName;
    }

    public static DifficultLevel findByName(Context context, String name){
        List<DifficultLevel> tmp = findAll(context);
        DifficultLevel difficultLevel = null;
        for(DifficultLevel e: tmp){
            if(e.getName().equals(name)){
                difficultLevel = e;
                break;
            }
        }
        return difficultLevel;
    }
}
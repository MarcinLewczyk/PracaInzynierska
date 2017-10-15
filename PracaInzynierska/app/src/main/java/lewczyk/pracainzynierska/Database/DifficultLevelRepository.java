package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;

public class DifficultLevelRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public DifficultLevelRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<DifficultLevel> findAll(){
        List<DifficultLevel> list = null;
        try {
            list = databaseHelper.getDifficultLevelDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DifficultLevel findById(long difficultLevelId){
        DifficultLevel difficultLevel = null;
        try {
            difficultLevel = databaseHelper.getDifficultLevelDao().queryForId(difficultLevelId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return difficultLevel;
    }

    public void addDifficultLevel(DifficultLevel difficultLevel){
        try {
            databaseHelper.getDifficultLevelDao().create(difficultLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDifficultLevel(DifficultLevel difficultLevel){
        try {
            databaseHelper.getDifficultLevelDao().update(difficultLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDifficultLevel(DifficultLevel difficultLevel){
        try {
            databaseHelper.getDifficultLevelDao().delete(difficultLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllNames(){
        List<DifficultLevel> tmp = findAll();
        List<String> categoriesName = new ArrayList<>();
        for(DifficultLevel d: tmp){
            categoriesName.add(d.getName());
        }
        return categoriesName;
    }

    public DifficultLevel findByName(String name){
        List<DifficultLevel> tmp = findAll();
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
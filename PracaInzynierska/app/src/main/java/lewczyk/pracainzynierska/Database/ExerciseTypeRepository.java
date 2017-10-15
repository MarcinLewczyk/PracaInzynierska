package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class ExerciseTypeRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public ExerciseTypeRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<ExerciseType> findAll(){
        List<ExerciseType> list = null;
        try {
            list = databaseHelper.getExerciseTypeDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExerciseType findById(long exerciseTypeId){
        ExerciseType exerciseType = null;
        try {
            exerciseType = databaseHelper.getExerciseTypeDao().queryForId(exerciseTypeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseType;
    }

    public void addExerciseType(ExerciseType exerciseType){
        try {
            databaseHelper.getExerciseTypeDao().create(exerciseType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExerciseType(ExerciseType exerciseType){
        try {
            databaseHelper.getExerciseTypeDao().update(exerciseType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExerciseType(ExerciseType exerciseType){
        try {
            databaseHelper.getExerciseTypeDao().delete(exerciseType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllNames(){
        List<ExerciseType> tmp = findAll();
        List<String> categoriesName = new ArrayList<>();
        for(ExerciseType t: tmp){
            categoriesName.add(t.getName());
        }
        return categoriesName;
    }

    public ExerciseType findByName(String name){
        List<ExerciseType> tmp = findAll();
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
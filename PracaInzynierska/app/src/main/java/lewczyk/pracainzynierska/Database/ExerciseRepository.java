package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class ExerciseRepository {

    public static List<Exercise> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseDao().queryForAll();
    }

    public static Exercise findById(Context context, long exerciseId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseDao().queryForId(exerciseId);
    }

    public static void addExercise(Context context, Exercise exercise){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseDao().create(exercise);
    }

    public static void deleteExercise(Context context, Exercise exercise){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseDao().delete(exercise);
    }

    public static List<Exercise> findAllWithGivenExerciseType(Context context, ExerciseType exerciseType){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        List<Exercise> filteredList = new ArrayList<>();
        List<Exercise> tmp = databaseHelper.getExerciseDao().queryForAll();
        for(Exercise e: tmp){
            if(e.getExerciseType().getId() == exerciseType.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<Exercise> findAllWithGivenDifficultLevel(Context context, DifficultLevel difficultLevel){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        List<Exercise> filteredList = new ArrayList<>();
        List<Exercise> tmp = databaseHelper.getExerciseDao().queryForAll();
        for(Exercise e: tmp){
            if(e.getDifficultLevel().getId() == difficultLevel.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<Exercise> findAllWithGivenEquimpentRequirement(Context context, EquipmentRequirement equipmentRequirement){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        List<Exercise> filteredList = new ArrayList<>();
        List<Exercise> tmp = databaseHelper.getExerciseDao().queryForAll();
        for(Exercise e: tmp){
            if(e.getEquipmentRequirement().getId() == equipmentRequirement.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }
    
    //// TODO: 31.08.2017 better filters 
}
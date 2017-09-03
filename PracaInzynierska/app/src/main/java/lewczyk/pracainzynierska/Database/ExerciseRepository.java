package lewczyk.pracainzynierska.Database;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
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

    public static void updateExercise(Context context, Exercise exercise){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseDao().update(exercise);
    }

    public static void deleteExercise(Context context, Exercise exercise){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseDao().delete(exercise);
    }

    public static List<Exercise> findAllWithGivenExerciseType(List<Exercise> preFilteredList, ExerciseType exerciseType){
        List<Exercise> filteredList = new ArrayList<>();
        for(Exercise e: preFilteredList){
            if(e.getExerciseType().getId() == exerciseType.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<Exercise> findAllWithGivenDifficultLevel(List<Exercise> preFilteredList, DifficultLevel difficultLevel){
        List<Exercise> filteredList = new ArrayList<>();
        for(Exercise e: preFilteredList){
            if(e.getDifficultLevel().getId() == difficultLevel.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<Exercise> findAllWithGivenEquimpentRequirement(List<Exercise> preFilteredList, EquipmentRequirement equipmentRequirement){
        List<Exercise> filteredList = new ArrayList<>();
        for(Exercise e: preFilteredList){
            if(e.getEquipmentRequirement().getId() == equipmentRequirement.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<Exercise> filterList(List<Exercise> preFilterList, ExerciseType exerciseType,
                                                   DifficultLevel difficultLevel, EquipmentRequirement equipmentRequirement){
        List<Exercise> filteredList = preFilterList;

        if(exerciseType != null){
            filteredList = findAllWithGivenExerciseType(filteredList, exerciseType);
        }
        if(difficultLevel != null){
            filteredList = findAllWithGivenDifficultLevel(filteredList, difficultLevel);
        }
        if(equipmentRequirement != null){
            filteredList = findAllWithGivenEquimpentRequirement(filteredList, equipmentRequirement);
        }
        return filteredList;
    }
}
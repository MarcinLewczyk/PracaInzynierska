package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class ExerciseToDoRepository {

    public static List<ExerciseToDo> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseToDoDao().queryForAll();
    }

    public static ExerciseToDo findById(Context context, long exerciseToDoId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getExerciseToDoDao().queryForId(exerciseToDoId);
    }

    public static void addExerciseToDo(Context context, ExerciseToDo exerciseToDo){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseToDoDao().create(exerciseToDo);
    }

    public static void deleteExerciseToDo(Context context, ExerciseToDo exerciseToDo){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getExerciseToDoDao().delete(exerciseToDo);
    }

    public static List<ExerciseToDo> findAllWithGivenExerciseType(Context context, List<ExerciseToDo> preFilteredList, ExerciseType exerciseType){
        List<ExerciseToDo> filteredList = new ArrayList<>();
        for(ExerciseToDo e: preFilteredList){
            Exercise exercise = ExerciseRepository.findById(context, e.getExercise().getId());
            if(exercise.getExerciseType().getId() == exerciseType.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<ExerciseToDo> findAllWithGivenDifficultLevel(Context context, List<ExerciseToDo> preFilteredList, DifficultLevel difficultLevel){
        List<ExerciseToDo> filteredList = new ArrayList<>();
        for(ExerciseToDo e: preFilteredList){
            Exercise exercise = ExerciseRepository.findById(context, e.getExercise().getId());
            if(exercise.getDifficultLevel().getId() == difficultLevel.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<ExerciseToDo> findAllWithGivenEquimpentRequirement(Context context, List<ExerciseToDo> preFilteredList, EquipmentRequirement equipmentRequirement){
        List<ExerciseToDo> filteredList = new ArrayList<>();
        for(ExerciseToDo e: preFilteredList){
            Exercise exercise = ExerciseRepository.findById(context, e.getExercise().getId());
            if(exercise.getEquipmentRequirement().getId() == equipmentRequirement.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public static List<ExerciseToDo> filterList(Context context, List<ExerciseToDo> preFilterList, ExerciseType exerciseType,
                                                DifficultLevel difficultLevel, EquipmentRequirement equipmentRequirement){
        List<ExerciseToDo> filteredList = preFilterList;

        if(exerciseType != null){
            filteredList = findAllWithGivenExerciseType(context, filteredList, exerciseType);
        }
        if(difficultLevel != null){
            filteredList = findAllWithGivenDifficultLevel(context, filteredList, difficultLevel);
        }
        if(equipmentRequirement != null){
            filteredList = findAllWithGivenEquimpentRequirement(context, filteredList, equipmentRequirement);
        }
        return filteredList;
    }
}
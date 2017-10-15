package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class ExerciseToDoRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public ExerciseToDoRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<ExerciseToDo> findAll(){
        List<ExerciseToDo> list = null;
        try {
            list = databaseHelper.getExerciseToDoDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ExerciseToDo findById(long exerciseToDoId){
        ExerciseToDo exerciseToDo = null;
        try {
            exerciseToDo = databaseHelper.getExerciseToDoDao().queryForId(exerciseToDoId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseToDo;
    }

    public void addExerciseToDo(ExerciseToDo exerciseToDo){
        try {
            databaseHelper.getExerciseToDoDao().create(exerciseToDo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExerciseToDo(ExerciseToDo exerciseToDo){
        try {
            databaseHelper.getExerciseToDoDao().delete(exerciseToDo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ExerciseToDo> findAllWithGivenExerciseType(Context context, List<ExerciseToDo> preFilteredList, ExerciseType exerciseType){
        List<ExerciseToDo> filteredList = new ArrayList<>();
        ExerciseRepository exerciseRepository = new ExerciseRepository(context);
        for(ExerciseToDo e: preFilteredList){
            Exercise exercise = exerciseRepository.findById(e.getExercise().getId());
            if(exercise.getExerciseType().getId() == exerciseType.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<ExerciseToDo> findAllWithGivenDifficultLevel(Context context, List<ExerciseToDo> preFilteredList, DifficultLevel difficultLevel){
        List<ExerciseToDo> filteredList = new ArrayList<>();
        ExerciseRepository exerciseRepository = new ExerciseRepository(context);
        for(ExerciseToDo e: preFilteredList){
            Exercise exercise = exerciseRepository.findById(e.getExercise().getId());
            if(exercise.getDifficultLevel().getId() == difficultLevel.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<ExerciseToDo> findAllWithGivenEquipmentRequirement(Context context, List<ExerciseToDo> preFilteredList, EquipmentRequirement equipmentRequirement){
        List<ExerciseToDo> filteredList = new ArrayList<>();
        ExerciseRepository exerciseRepository = new ExerciseRepository(context);
        for(ExerciseToDo e: preFilteredList){
            Exercise exercise = exerciseRepository.findById(e.getExercise().getId());
            if(exercise.getEquipmentRequirement().getId() == equipmentRequirement.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<ExerciseToDo> filterList(Context context, List<ExerciseToDo> preFilterList, ExerciseType exerciseType,
                                                DifficultLevel difficultLevel, EquipmentRequirement equipmentRequirement){
        List<ExerciseToDo> filteredList = preFilterList;

        if(exerciseType != null){
            filteredList = findAllWithGivenExerciseType(context, filteredList, exerciseType);
        }
        if(difficultLevel != null){
            filteredList = findAllWithGivenDifficultLevel(context, filteredList, difficultLevel);
        }
        if(equipmentRequirement != null){
            filteredList = findAllWithGivenEquipmentRequirement(context, filteredList, equipmentRequirement);
        }
        return filteredList;
    }
}
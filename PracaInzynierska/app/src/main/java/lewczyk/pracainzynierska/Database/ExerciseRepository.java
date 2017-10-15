package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;

public class ExerciseRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public ExerciseRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<Exercise> findAll(){
        List<Exercise> list = null;
        try {
            list = databaseHelper.getExerciseDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }

    public Exercise findById(long exerciseId){
        Exercise exercise = null;
        try {
            exercise = databaseHelper.getExerciseDao().queryForId(exerciseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercise;
    }

    public void addExercise(Exercise exercise){
        try {
            databaseHelper.getExerciseDao().create(exercise);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExercise(Exercise exercise){
        try {
            databaseHelper.getExerciseDao().update(exercise);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExercise(Exercise exercise){
        try {
            databaseHelper.getExerciseDao().delete(exercise);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exercise> findAllWithGivenExerciseType(List<Exercise> preFilteredList, ExerciseType exerciseType){
        List<Exercise> filteredList = new ArrayList<>();
        for(Exercise e: preFilteredList){
            if(e.getExerciseType().getId() == exerciseType.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<Exercise> findAllWithGivenDifficultLevel(List<Exercise> preFilteredList, DifficultLevel difficultLevel){
        List<Exercise> filteredList = new ArrayList<>();
        for(Exercise e: preFilteredList){
            if(e.getDifficultLevel().getId() == difficultLevel.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<Exercise> findAllWithGivenEquimpentRequirement(List<Exercise> preFilteredList, EquipmentRequirement equipmentRequirement){
        List<Exercise> filteredList = new ArrayList<>();
        for(Exercise e: preFilteredList){
            if(e.getEquipmentRequirement().getId() == equipmentRequirement.getId()){
                filteredList.add(e);
            }
        }
        return filteredList;
    }

    public List<Exercise> filterList(List<Exercise> preFilterList, ExerciseType exerciseType,
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
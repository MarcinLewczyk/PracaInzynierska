package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;

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

    //// TODO: 31.08.2017 better exercise filters 
}
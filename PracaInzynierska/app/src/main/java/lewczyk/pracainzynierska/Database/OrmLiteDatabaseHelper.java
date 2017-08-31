package lewczyk.pracainzynierska.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;
import lewczyk.pracainzynierska.DatabaseTables.DifficultLevel;
import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurposeArchive;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseType;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;

public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static OrmLiteDatabaseHelper instance;

    private static final String DATABASE_NAME = "SportApp.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<ExercisePurpose, Long> exercisePurposeDao;
    private RuntimeExceptionDao<BodyParameter, Long> bodyParameterDao;
    private RuntimeExceptionDao<UserNote, Long> userNoteDao;
    private RuntimeExceptionDao<CoachNote, Long> coachNoteDao;
    private RuntimeExceptionDao<DifficultLevel, Long> difficultLevelDao;
    private RuntimeExceptionDao<ExerciseType, Long> exerciseTypeDao;
    private RuntimeExceptionDao<EquipmentRequirement, Long> equipmentRequirementDao;
    private RuntimeExceptionDao<Exercise, Long> exerciseDao;
    private RuntimeExceptionDao<ExerciseArchive, Long> exerciseArchiveDao;
    private RuntimeExceptionDao<ExerciseToDo, Long> exerciseToDoDao;
    private RuntimeExceptionDao<TrainingPlan, Long> trainingPlanDao;
    private RuntimeExceptionDao<ExerciseInTrainingPlan, Long> exerciseInTrainingPlanDao;


    private OrmLiteDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static OrmLiteDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new OrmLiteDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d("database", "onCreate start");
        recreateTables();
        Log.d("database", "onCreate end");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.d("database", "onUpgrade start");
        recreateTables();
        Log.d("database", "onUpgrade end");
    }

    private void recreateTables() {
        try {
            TableUtils.dropTable(connectionSource, ExercisePurpose.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ExercisePurpose.class);

            TableUtils.dropTable(connectionSource, BodyParameter.class, true);
            TableUtils.createTableIfNotExists(connectionSource, BodyParameter.class);

            TableUtils.dropTable(connectionSource, UserNote.class, true);
            TableUtils.createTableIfNotExists(connectionSource, UserNote.class);

            TableUtils.dropTable(connectionSource, CoachNote.class, true);
            TableUtils.createTableIfNotExists(connectionSource, CoachNote.class);

            TableUtils.dropTable(connectionSource, ExercisePurposeArchive.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ExercisePurposeArchive.class);

            TableUtils.dropTable(connectionSource, BodyParameterArchive.class, true);
            TableUtils.createTableIfNotExists(connectionSource, BodyParameterArchive.class);

            TableUtils.dropTable(connectionSource, DifficultLevel.class, true);
            TableUtils.createTableIfNotExists(connectionSource, DifficultLevel.class);

            TableUtils.dropTable(connectionSource, EquipmentRequirement.class, true);
            TableUtils.createTableIfNotExists(connectionSource, EquipmentRequirement.class);

            TableUtils.dropTable(connectionSource, ExerciseType.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseType.class);

            TableUtils.dropTable(connectionSource, Exercise.class, true);
            TableUtils.createTableIfNotExists(connectionSource, Exercise.class);

            TableUtils.dropTable(connectionSource, ExerciseArchive.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseArchive.class);

            TableUtils.dropTable(connectionSource, ExerciseToDo.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseToDo.class);

            TableUtils.dropTable(connectionSource, TrainingPlan.class, true);
            TableUtils.createTableIfNotExists(connectionSource, TrainingPlan.class);

            TableUtils.dropTable(connectionSource, ExerciseInTrainingPlan.class, true);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseInTrainingPlan.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<ExercisePurpose, Long> getExercisePurposeDao() {
        if (exercisePurposeDao == null) {
            exercisePurposeDao = getRuntimeExceptionDao(ExercisePurpose.class);
        }
        return exercisePurposeDao;
    }

    public RuntimeExceptionDao<BodyParameter, Long> getBodyParameterDao(){
        if(bodyParameterDao == null){
            bodyParameterDao = getRuntimeExceptionDao(BodyParameter.class);
        }
        return bodyParameterDao;
    }

    public RuntimeExceptionDao<UserNote, Long> getUserNoteDao() {
        if(userNoteDao == null){
            userNoteDao = getRuntimeExceptionDao(UserNote.class);
        }
        return userNoteDao;
    }

    public RuntimeExceptionDao<CoachNote, Long> getCoachNoteDao() {
        if(coachNoteDao == null) {
            coachNoteDao = getRuntimeExceptionDao(CoachNote.class);
        }
        return coachNoteDao;
    }

    public RuntimeExceptionDao<DifficultLevel, Long> getDifficultLevelDao() {
        if(difficultLevelDao == null){
            difficultLevelDao = getRuntimeExceptionDao(DifficultLevel.class);
        }
        return difficultLevelDao;
    }

    public RuntimeExceptionDao<ExerciseType, Long> getExerciseTypeDao() {
        if(exerciseTypeDao == null){
            exerciseTypeDao = getRuntimeExceptionDao(ExerciseType.class);
        }
        return exerciseTypeDao;
    }

    public RuntimeExceptionDao<EquipmentRequirement, Long> getEquipmentRequirementDao() {
        if(equipmentRequirementDao == null){
            equipmentRequirementDao = getRuntimeExceptionDao(EquipmentRequirement.class);
        }
        return equipmentRequirementDao;
    }

    public RuntimeExceptionDao<Exercise, Long> getExerciseDao() {
        if (exerciseDao == null) {
            exerciseDao = getRuntimeExceptionDao(Exercise.class);
        }
        return exerciseDao;
    }

    public RuntimeExceptionDao<ExerciseArchive, Long> getExerciseArchiveDao() {
        if(exerciseArchiveDao == null){
            exerciseArchiveDao = getRuntimeExceptionDao(ExerciseArchive.class);
        }
        return exerciseArchiveDao;
    }

    public RuntimeExceptionDao<ExerciseToDo, Long> getExerciseToDoDao() {
        if(exerciseToDoDao == null){
            exerciseToDoDao = getRuntimeExceptionDao(ExerciseToDo.class);
        }
        return exerciseToDoDao;
    }

    public RuntimeExceptionDao<TrainingPlan, Long> getTrainingPlanDao() {
        if(trainingPlanDao == null){
            trainingPlanDao = getRuntimeExceptionDao(TrainingPlan.class);
        }
        return trainingPlanDao;
    }

    public RuntimeExceptionDao<ExerciseInTrainingPlan, Long> getExerciseInTrainingPlanDao() {
        if(exerciseInTrainingPlanDao == null){
            exerciseInTrainingPlanDao = getRuntimeExceptionDao(ExerciseInTrainingPlan.class);
        }
        return exerciseInTrainingPlanDao;
    }
}
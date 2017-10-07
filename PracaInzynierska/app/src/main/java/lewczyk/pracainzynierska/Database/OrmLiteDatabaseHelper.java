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
import lewczyk.pracainzynierska.R;

public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static OrmLiteDatabaseHelper instance;
    private Context context;

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
        this.context = context;
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
        fillTables();
        Log.d("database", "onCreate end");
    }

    private void fillTables() {
        /*  Exercise Types  */
        ExerciseType warmup = new ExerciseType(context.getString(R.string.warmup));
        ExerciseType cardio = new ExerciseType(context.getString(R.string.cardio));
        ExerciseType strength_exercise = new ExerciseType(context.getString(R.string.strength_exercise));

        ExerciseTypeRepository.addExerciseType(context, warmup);
        ExerciseTypeRepository.addExerciseType(context, cardio);
        ExerciseTypeRepository.addExerciseType(context, strength_exercise);

        /*  Equipment Requirements  */
        EquipmentRequirement noEq = new EquipmentRequirement(context.getString(R.string.no_equipment));
        EquipmentRequirement smallEq = new EquipmentRequirement(context.getString(R.string.small_equipment));
        EquipmentRequirement machine = new EquipmentRequirement(context.getString(R.string.exercise_machines));

        EquipmentRequirementRepository.addEquipmentRequirement(context, noEq);
        EquipmentRequirementRepository.addEquipmentRequirement(context, smallEq);
        EquipmentRequirementRepository.addEquipmentRequirement(context, machine);

        /*  Difficult Levels    */
        DifficultLevel beginer = new DifficultLevel(context.getString(R.string.beginer));
        DifficultLevel intermediate = new DifficultLevel(context.getString(R.string.intermediate));
        DifficultLevel advanced = new DifficultLevel(context.getString(R.string.advanced));

        DifficultLevelRepository.addDifficultLevel(context, beginer);
        DifficultLevelRepository.addDifficultLevel(context, intermediate);
        DifficultLevelRepository.addDifficultLevel(context, advanced);

        /*  test exercises  */
        Exercise ex1 = new Exercise("Kaptury", "Szyja", "Hantle w dłonie i wzruszaj ramionami", beginer, smallEq, strength_exercise);
        Exercise ex2 = new Exercise("Drążek", "Plecy", "Podciągnięcia na drążku", intermediate, noEq, warmup);
        Exercise ex3 = new Exercise("Laweczka", "Klata", "Machanie gryfem", advanced, machine, cardio);

        ExerciseRepository.addExercise(context, ex1);
        ExerciseRepository.addExercise(context, ex2);
        ExerciseRepository.addExercise(context, ex3);

        TrainingPlan trainingPlan = new TrainingPlan(context.getString(R.string.Back_Muscle));

        TrainingPlanRepository.addTrainingPlan(context, trainingPlan);

        ExerciseInTrainingPlan exer1 = new ExerciseInTrainingPlan(trainingPlan, ex1, 3, 12, 15.0);
        ExerciseInTrainingPlan exer2 = new ExerciseInTrainingPlan(trainingPlan, ex2, 2, 15, 0.0);

        ExerciseInTrainingPlanRepository.addExerciseInTrainingPlan(context, exer1);
        ExerciseInTrainingPlanRepository.addExerciseInTrainingPlan(context, exer2);

        /*  End of test */

        /*  Exercises using sensors */

        Exercise pushup = new Exercise(context.getString(R.string.push_up), context.getString(R.string.Chest_Muscle),
                context.getString(R.string.Push_up_demonstration), beginer, noEq, strength_exercise);
        pushup.setSensorParameter(5.0);

        Exercise squat = new Exercise(context.getString(R.string.squat), context.getString(R.string.thighs_muscle),
                context.getString(R.string.squat_demonstration), beginer, noEq, strength_exercise);
        squat.setSensorParameter(1.5);

        Exercise tablePull = new Exercise(context.getString(R.string.table_pull),
                context.getString(R.string.Back_Muscle) + " " + context.getString(R.string.and) + " "  + context.getString(R.string.Chest_Muscle),
                context.getString(R.string.table_pull_demonstration), beginer, smallEq, strength_exercise);
        tablePull.setSensorParameter(5.0);

        Exercise armsAndLegsRaise = new Exercise(context.getString(R.string.arms_and_legs_raise_lie_on_belly),
                context.getString(R.string.Back_Muscle) + " " + context.getString(R.string.and) + " " + context.getString(R.string.abs_muscle),
                context.getString(R.string.arms_legs_raise_demonstration), beginer, noEq, strength_exercise);
        armsAndLegsRaise.setSensorParameter(5.0);

        Exercise pullUp = new Exercise(context.getString(R.string.pull_up),
                context.getString(R.string.Back_Muscle) + " " + context.getString(R.string.and) + " " + context.getString(R.string.arms),
                context.getString(R.string.pull_up_demonstration), intermediate, smallEq, strength_exercise);
        pullUp.setSensorParameter(1.0);

        Exercise running = new Exercise(context.getString(R.string.running),
                context.getString(R.string.legs_muscle), context.getString(R.string.running), beginer, noEq, cardio);
        running.setSensorParameter(30.0);

        Exercise cycling = new Exercise(context.getString(R.string.cycling),
                context.getString(R.string.legs_muscle), context.getString(R.string.cycling), beginer, smallEq, cardio);
        cycling.setSensorParameter(15.0);

        ExerciseRepository.addExercise(context, pushup);
        ExerciseRepository.addExercise(context, squat);
        ExerciseRepository.addExercise(context, tablePull);
        ExerciseRepository.addExercise(context, armsAndLegsRaise);
        ExerciseRepository.addExercise(context, pullUp);
        ExerciseRepository.addExercise(context, running);
        ExerciseRepository.addExercise(context, cycling);
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
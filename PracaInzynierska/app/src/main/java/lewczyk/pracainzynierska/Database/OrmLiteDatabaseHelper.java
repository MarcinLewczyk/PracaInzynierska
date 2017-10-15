package lewczyk.pracainzynierska.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
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
    private Context context;

    private static final String DATABASE_NAME = "SportApp.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<ExercisePurpose, Long> exercisePurposeDao = null;
    private Dao<BodyParameter, Long> bodyParameterDao = null;
    private Dao<UserNote, Long> userNoteDao = null;
    private Dao<CoachNote, Long> coachNoteDao = null;
    private Dao<DifficultLevel, Long> difficultLevelDao = null;
    private Dao<ExerciseType, Long> exerciseTypeDao = null;
    private Dao<EquipmentRequirement, Long> equipmentRequirementDao = null;
    private Dao<Exercise, Long> exerciseDao = null;
    private Dao<ExerciseArchive, Long> exerciseArchiveDao = null;
    private Dao<ExerciseToDo, Long> exerciseToDoDao = null;
    private Dao<TrainingPlan, Long> trainingPlanDao = null;
    private Dao<ExerciseInTrainingPlan, Long> exerciseInTrainingPlanDao = null;

    public OrmLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d("database", "onCreate start");
        createTables();
        fillTables();
        Log.d("database", "onCreate end");
    }

    private void fillTables() {
        /*  Exercise Types  */
        ExerciseTypeRepository exerciseTypeRepository = new ExerciseTypeRepository(context);
        ExerciseType warmup = new ExerciseType(context.getString(R.string.warmup));
        ExerciseType cardio = new ExerciseType(context.getString(R.string.cardio));
        ExerciseType strength_exercise = new ExerciseType(context.getString(R.string.strength_exercise));

        exerciseTypeRepository.addExerciseType(warmup);
        exerciseTypeRepository.addExerciseType(cardio);
        exerciseTypeRepository.addExerciseType(strength_exercise);

        /*  Equipment Requirements  */
        EquipmentRequirementRepository equipmentRequirementRepository = new EquipmentRequirementRepository(context);
        EquipmentRequirement noEq = new EquipmentRequirement(context.getString(R.string.no_equipment));
        EquipmentRequirement smallEq = new EquipmentRequirement(context.getString(R.string.small_equipment));
        EquipmentRequirement machine = new EquipmentRequirement(context.getString(R.string.exercise_machines));

        equipmentRequirementRepository.addEquipmentRequirement(noEq);
        equipmentRequirementRepository.addEquipmentRequirement(smallEq);
        equipmentRequirementRepository.addEquipmentRequirement(machine);

        /*  Difficult Levels    */
        DifficultLevelRepository difficultLevelRepository = new DifficultLevelRepository(context);
        DifficultLevel beginer = new DifficultLevel(context.getString(R.string.beginer));
        DifficultLevel intermediate = new DifficultLevel(context.getString(R.string.intermediate));
        DifficultLevel advanced = new DifficultLevel(context.getString(R.string.advanced));

        difficultLevelRepository.addDifficultLevel(beginer);
        difficultLevelRepository.addDifficultLevel(intermediate);
        difficultLevelRepository.addDifficultLevel(advanced);

        /*  test exercises  */
        ExerciseRepository exerciseRepository = new ExerciseRepository(context);
        Exercise ex1 = new Exercise("Kaptury", "Szyja", "Hantle w dłonie i wzruszaj ramionami", beginer, smallEq, strength_exercise);
        Exercise ex2 = new Exercise("Drążek", "Plecy", "Podciągnięcia na drążku", intermediate, noEq, warmup);
        Exercise ex3 = new Exercise("Laweczka", "Klata", "Machanie gryfem", advanced, machine, cardio);

        exerciseRepository.addExercise(ex1);
        exerciseRepository.addExercise(ex2);
        exerciseRepository.addExercise(ex3);

        TrainingPlanRepository trainingPlanRepository = new TrainingPlanRepository(context);
        TrainingPlan trainingPlan = new TrainingPlan(context.getString(R.string.Back_Muscle));

        trainingPlanRepository.addTrainingPlan(trainingPlan);

        ExerciseInTrainingPlan exer1 = new ExerciseInTrainingPlan(trainingPlan, ex1, 3, 12, 15.0);
        ExerciseInTrainingPlan exer2 = new ExerciseInTrainingPlan(trainingPlan, ex2, 2, 15, 0.0);

        ExerciseInTrainingPlanRepository exerciseInTrainingPlanRepository = new ExerciseInTrainingPlanRepository(context);
        exerciseInTrainingPlanRepository.addExerciseInTrainingPlan(exer1);
        exerciseInTrainingPlanRepository.addExerciseInTrainingPlan(exer2);

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

        exerciseRepository.addExercise(pushup);
        exerciseRepository.addExercise(squat);
        exerciseRepository.addExercise(tablePull);
        exerciseRepository.addExercise(armsAndLegsRaise);
        exerciseRepository.addExercise(pullUp);
        exerciseRepository.addExercise(running);
        exerciseRepository.addExercise(cycling);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.d("database", "onUpgrade start");
        dropTables();
        Log.d("database", "onUpgrade end");
    }

    private void createTables(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, ExercisePurpose.class);
            TableUtils.createTableIfNotExists(connectionSource, BodyParameter.class);
            TableUtils.createTableIfNotExists(connectionSource, UserNote.class);
            TableUtils.createTableIfNotExists(connectionSource, CoachNote.class);
            TableUtils.createTableIfNotExists(connectionSource, ExercisePurposeArchive.class);
            TableUtils.createTableIfNotExists(connectionSource, BodyParameterArchive.class);
            TableUtils.createTableIfNotExists(connectionSource, DifficultLevel.class);
            TableUtils.createTableIfNotExists(connectionSource, EquipmentRequirement.class);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseType.class);
            TableUtils.createTableIfNotExists(connectionSource, Exercise.class);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseArchive.class);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseToDo.class);
            TableUtils.createTableIfNotExists(connectionSource, TrainingPlan.class);
            TableUtils.createTableIfNotExists(connectionSource, ExerciseInTrainingPlan.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTables() {
        try {
            TableUtils.dropTable(connectionSource, ExercisePurpose.class, true);
            TableUtils.dropTable(connectionSource, BodyParameter.class, true);
            TableUtils.dropTable(connectionSource, UserNote.class, true);
            TableUtils.dropTable(connectionSource, CoachNote.class, true);
            TableUtils.dropTable(connectionSource, ExercisePurposeArchive.class, true);
            TableUtils.dropTable(connectionSource, BodyParameterArchive.class, true);
            TableUtils.dropTable(connectionSource, DifficultLevel.class, true);
            TableUtils.dropTable(connectionSource, EquipmentRequirement.class, true);
            TableUtils.dropTable(connectionSource, ExerciseType.class, true);
            TableUtils.dropTable(connectionSource, Exercise.class, true);
            TableUtils.dropTable(connectionSource, ExerciseArchive.class, true);
            TableUtils.dropTable(connectionSource, ExerciseToDo.class, true);
            TableUtils.dropTable(connectionSource, TrainingPlan.class, true);
            TableUtils.dropTable(connectionSource, ExerciseInTrainingPlan.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<ExercisePurpose, Long> getExercisePurposeDao() throws SQLException{
        if (exercisePurposeDao == null) {
            exercisePurposeDao = getDao(ExercisePurpose.class);
        }
        return exercisePurposeDao;
    }

    public Dao<BodyParameter, Long> getBodyParameterDao() throws SQLException{
        if (bodyParameterDao == null) {
            bodyParameterDao = getDao(BodyParameter.class);
        }
        return bodyParameterDao;
    }

    public Dao<UserNote, Long> getUserNoteDao() throws SQLException{
        if(userNoteDao == null){
            userNoteDao = getDao(UserNote.class);
        }
        return userNoteDao;
    }

    public Dao<CoachNote, Long> getCoachNoteDao() throws SQLException{
    if(coachNoteDao == null) {
        coachNoteDao = getDao(CoachNote.class);
    }
    return coachNoteDao;
}

    public Dao<DifficultLevel, Long> getDifficultLevelDao() throws SQLException{
        if(difficultLevelDao == null){
            difficultLevelDao = getDao(DifficultLevel.class);
        }
        return difficultLevelDao;
    }

    public Dao<ExerciseType, Long> getExerciseTypeDao() throws SQLException{
        if(exerciseTypeDao == null){
            exerciseTypeDao = getDao(ExerciseType.class);
        }
        return exerciseTypeDao;
    }

    public Dao<EquipmentRequirement, Long> getEquipmentRequirementDao() throws SQLException{
        if(equipmentRequirementDao == null){
            equipmentRequirementDao = getDao(EquipmentRequirement.class);
        }
        return equipmentRequirementDao;
    }

    public Dao<Exercise, Long> getExerciseDao() throws SQLException{
        if (exerciseDao == null) {
            exerciseDao = getDao(Exercise.class);
        }
        return exerciseDao;
    }

    public Dao<ExerciseArchive, Long> getExerciseArchiveDao() throws SQLException{
        if(exerciseArchiveDao == null){
            exerciseArchiveDao = getDao(ExerciseArchive.class);
        }
        return exerciseArchiveDao;
    }

    public Dao<ExerciseToDo, Long> getExerciseToDoDao() throws SQLException{
        if(exerciseToDoDao == null){
            exerciseToDoDao = getDao(ExerciseToDo.class);
        }
        return exerciseToDoDao;
    }

    public Dao<TrainingPlan, Long> getTrainingPlanDao() throws SQLException{
        if(trainingPlanDao == null){
            trainingPlanDao = getDao(TrainingPlan.class);
        }
        return trainingPlanDao;
    }

    public Dao<ExerciseInTrainingPlan, Long> getExerciseInTrainingPlanDao() throws SQLException{
        if(exerciseInTrainingPlanDao == null){
            exerciseInTrainingPlanDao = getDao(ExerciseInTrainingPlan.class);
        }
        return exerciseInTrainingPlanDao;
    }

    @Override
    public void close() {
        super.close();
        exercisePurposeDao = null;
        bodyParameterDao = null;
        userNoteDao = null;
        coachNoteDao = null;
        difficultLevelDao = null;
        exerciseTypeDao = null;
        equipmentRequirementDao = null;
        exerciseDao = null;
        exerciseArchiveDao = null;
        exerciseToDoDao = null;
        trainingPlanDao = null;
        exerciseInTrainingPlanDao = null;
    }
}
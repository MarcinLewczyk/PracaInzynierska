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
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;

public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static OrmLiteDatabaseHelper instance;

    private static final String DATABASE_NAME = "SportApp.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<ExercisePurpose, Long> exercisePurposeDao;
    private RuntimeExceptionDao<BodyParameter, Long> bodyParameterDao;
    private RuntimeExceptionDao<UserNote, Long> userNoteDao;


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
}
package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.UserNote;

public class UserNoteRepository {

    public static List<UserNote> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getUserNoteDao().queryForAll();
    }

    public static UserNote findById(Context context, long userNoteId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getUserNoteDao().queryForId(userNoteId);
    }

    public static void addUserNote(Context context, UserNote userNote){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getUserNoteDao().create(userNote);
    }

    public static void updateUserNote(Context context, UserNote userNote){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getUserNoteDao().update(userNote);
    }

    public static void deleteUserNote(Context context, UserNote userNote){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getUserNoteDao().delete(userNote);
    }
}
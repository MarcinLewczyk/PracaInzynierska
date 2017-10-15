package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.UserNote;

public class UserNoteRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public UserNoteRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<UserNote> findAll(){
        List<UserNote> list = null;
        try {
            list = databaseHelper.getUserNoteDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public UserNote findById(long userNoteId){
        UserNote userNote = null;
        try {
            userNote = databaseHelper.getUserNoteDao().queryForId(userNoteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNote;
    }

    public void addUserNote(UserNote userNote){
        try {
            databaseHelper.getUserNoteDao().create(userNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserNote(UserNote userNote){
        try {
            databaseHelper.getUserNoteDao().update(userNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserNote(UserNote userNote){
        try {
            databaseHelper.getUserNoteDao().delete(userNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
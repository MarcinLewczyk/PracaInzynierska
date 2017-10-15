package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.CoachNote;

public class CoachNoteRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public CoachNoteRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<CoachNote> findAll(){
        List<CoachNote> list = null;
        try {
            list = databaseHelper.getCoachNoteDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public CoachNote findById(long coachNoteId){
        CoachNote coachNote = null;
        try {
            coachNote = databaseHelper.getCoachNoteDao().queryForId(coachNoteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coachNote;
    }

    public void addCoachNote(CoachNote coachNote){
        try {
            databaseHelper.getCoachNoteDao().create(coachNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCoachNote(CoachNote coachNote){
        try {
            databaseHelper.getCoachNoteDao().update(coachNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoachNote(CoachNote coachNote){
        try {
            databaseHelper.getCoachNoteDao().delete(coachNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
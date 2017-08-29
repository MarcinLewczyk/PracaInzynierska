package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.CoachNote;

public class CoachNoteRepository {

    public static List<CoachNote> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getCoachNoteDao().queryForAll();
    }

    public static CoachNote findById(Context context, long coachNoteId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getCoachNoteDao().queryForId(coachNoteId);
    }

    public static void addCoachNote(Context context, CoachNote coachNote){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getCoachNoteDao().create(coachNote);
    }

    public static void updateCoachNote(Context context, CoachNote coachNote){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getCoachNoteDao().update(coachNote);
    }

    public static void deleteCoachNote(Context context, CoachNote coachNote){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getCoachNoteDao().delete(coachNote);
    }
}
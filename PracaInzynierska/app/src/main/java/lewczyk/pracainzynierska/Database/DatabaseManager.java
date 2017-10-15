package lewczyk.pracainzynierska.Database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DatabaseManager {
    private static OrmLiteDatabaseHelper databaseHelper = null;

    public static OrmLiteDatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, OrmLiteDatabaseHelper.class);
        }

        return databaseHelper;
    }

    public static void releaseHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
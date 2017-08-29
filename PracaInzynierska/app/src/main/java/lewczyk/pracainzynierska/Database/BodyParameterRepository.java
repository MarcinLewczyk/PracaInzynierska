package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;

public class BodyParameterRepository {

    public static List<BodyParameter> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getBodyParameterDao().queryForAll();
    }

    public static BodyParameter findById(Context context, long bodyParameterId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getBodyParameterDao().queryForId(bodyParameterId);
    }

    public static void addBodyParameter(Context context, BodyParameter bodyParameter){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getBodyParameterDao().create(bodyParameter);
    }

    public static void updateBodyParameter(Context context, BodyParameter bodyParameter){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getBodyParameterDao().update(bodyParameter);
    }

    public static void deleteBodyParameter(Context context, BodyParameter bodyParameter){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getBodyParameterDao().delete(bodyParameter);
    }
}
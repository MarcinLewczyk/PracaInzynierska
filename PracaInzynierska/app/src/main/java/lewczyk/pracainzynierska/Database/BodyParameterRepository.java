package lewczyk.pracainzynierska.Database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;

public class BodyParameterRepository{
    private OrmLiteDatabaseHelper databaseHelper;

    public BodyParameterRepository(Context context){
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<BodyParameter> findAll(){
        List<BodyParameter> list = null;
        try {
            list = databaseHelper.getBodyParameterDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list ;
    }

    public BodyParameter findById(long bodyParameterId){
        BodyParameter bodyParameter = null;
        try {
            bodyParameter = databaseHelper.getBodyParameterDao().queryForId(bodyParameterId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bodyParameter;
    }

    public void addBodyParameter(BodyParameter bodyParameter){
        try {
            databaseHelper.getBodyParameterDao().create(bodyParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBodyParameter(BodyParameter bodyParameter){
        try {
            databaseHelper.getBodyParameterDao().update(bodyParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBodyParameter(BodyParameter bodyParameter){
        try {
            databaseHelper.getBodyParameterDao().delete(bodyParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package lewczyk.pracainzynierska.Database;


import android.content.Context;

import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;

public class EquipmentRequirementRepository {

    public static List<EquipmentRequirement> findAll(Context context){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getEquipmentRequirementDao().queryForAll();
    }

    public static EquipmentRequirement findById(Context context, long equipmentRequirementId){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getEquipmentRequirementDao().queryForId(equipmentRequirementId);
    }

    public static void addEquipmentRequirement(Context context, EquipmentRequirement equipmentRequirement){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getEquipmentRequirementDao().create(equipmentRequirement);
    }

    public static void updateEquipmentRequirement(Context context, EquipmentRequirement equipmentRequirement){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getEquipmentRequirementDao().update(equipmentRequirement);
    }

    public static void deleteEquipmentRequirement(Context context, EquipmentRequirement equipmentRequirement){
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getEquipmentRequirementDao().delete(equipmentRequirement);
    }
}
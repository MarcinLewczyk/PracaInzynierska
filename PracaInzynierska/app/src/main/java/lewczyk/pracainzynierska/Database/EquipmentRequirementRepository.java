package lewczyk.pracainzynierska.Database;


import android.content.Context;

import java.util.ArrayList;
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

    public static List<String> findAllNames(Context context){
        List<EquipmentRequirement> tmp = findAll(context);
        List<String> categoriesName = new ArrayList<>();
        for(EquipmentRequirement e: tmp){
            categoriesName.add(e.getName());
        }
        return categoriesName;
    }

    public static EquipmentRequirement findByName(Context context, String name){
        List<EquipmentRequirement> tmp = findAll(context);
        EquipmentRequirement equipmentRequirement = null;
        for(EquipmentRequirement e: tmp){
            if(e.getName().equals(name)){
                equipmentRequirement = e;
                break;
            }
        }
        return equipmentRequirement;
    }
}
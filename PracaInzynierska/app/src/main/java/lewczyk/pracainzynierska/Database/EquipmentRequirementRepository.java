package lewczyk.pracainzynierska.Database;


import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lewczyk.pracainzynierska.DatabaseTables.EquipmentRequirement;

public class EquipmentRequirementRepository {
    private OrmLiteDatabaseHelper databaseHelper;

    public EquipmentRequirementRepository(Context context) {
        databaseHelper = DatabaseManager.getHelper(context);
    }

    public List<EquipmentRequirement> findAll(){
        List<EquipmentRequirement> list = null;
        try {
            list = databaseHelper.getEquipmentRequirementDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public EquipmentRequirement findById(long equipmentRequirementId){
        EquipmentRequirement equipmentRequirement = null;
        try {
            equipmentRequirement = databaseHelper.getEquipmentRequirementDao().queryForId(equipmentRequirementId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentRequirement;
    }

    public void addEquipmentRequirement(EquipmentRequirement equipmentRequirement){
        try {
            databaseHelper.getEquipmentRequirementDao().create(equipmentRequirement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEquipmentRequirement(EquipmentRequirement equipmentRequirement){
        try {
            databaseHelper.getEquipmentRequirementDao().update(equipmentRequirement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEquipmentRequirement(EquipmentRequirement equipmentRequirement){
        try {
            databaseHelper.getEquipmentRequirementDao().delete(equipmentRequirement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllNames(){
        List<EquipmentRequirement> tmp = findAll();
        List<String> categoriesName = new ArrayList<>();
        for(EquipmentRequirement e: tmp){
            categoriesName.add(e.getName());
        }
        return categoriesName;
    }

    public EquipmentRequirement findByName(String name){
        List<EquipmentRequirement> tmp = findAll();
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
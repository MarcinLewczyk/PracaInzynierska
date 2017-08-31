package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "equipmentRequirements")
public class EquipmentRequirement {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, width = 100)
    private String name;

    public EquipmentRequirement() {
    }

    public EquipmentRequirement(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
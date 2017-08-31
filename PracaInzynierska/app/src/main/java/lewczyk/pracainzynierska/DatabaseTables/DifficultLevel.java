package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "difficultLevels")
public class DifficultLevel {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, width = 100)
    private String name;

    public DifficultLevel() {
    }

    public DifficultLevel(String name) {
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
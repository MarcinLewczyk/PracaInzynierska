package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bodyParameters")
public class BodyParameter {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String muscleName;

    @DatabaseField
    private double circumference;

    public BodyParameter(){

    }

    public BodyParameter(String muscleName, double circumference) {
        this.muscleName = muscleName;
        this.circumference = circumference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public double getCircumference() {
        return circumference;
    }

    public void setCircumference(double circumference) {
        this.circumference = circumference;
    }
}
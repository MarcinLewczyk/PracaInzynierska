package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bodyParameters")
public class BodyParameter {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, width = 100)
    private String muscleName;

    @DatabaseField(canBeNull = false)
    private double circumference;

    @ForeignCollectionField
    private ForeignCollection<BodyParameterArchive> parametersArchive;

    public BodyParameter(){

    }

    public BodyParameter(String muscleName, double circumference) {
        this.muscleName = muscleName;
        this.circumference = circumference;
    }

    public long getId() {
        return id;
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

    public ForeignCollection<BodyParameterArchive> getParametersArchive() {
        return parametersArchive;
    }
}
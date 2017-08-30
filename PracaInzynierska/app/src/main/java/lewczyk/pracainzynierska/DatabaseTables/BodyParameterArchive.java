package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bodyParametersArchive")
public class BodyParameterArchive {

    @DatabaseField(canBeNull = false)
    private double oldCircumference;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private BodyParameter bodyParameter;

    public BodyParameterArchive() {
    }

    public BodyParameterArchive(double oldCircumference, BodyParameter bodyParameter) {
        this.oldCircumference = oldCircumference;
        this.bodyParameter = bodyParameter;
    }

    public double getOldCircumference() {
        return oldCircumference;
    }

    public void setOldCircumference(double oldCircumference) {
        this.oldCircumference = oldCircumference;
    }

    public BodyParameter getBodyParameter() {
        return bodyParameter;
    }

    public void setBodyParameter(BodyParameter bodyParameter) {
        this.bodyParameter = bodyParameter;
    }
}
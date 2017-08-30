package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercisePurposesArchive")
public class ExercisePurposeArchive {

    @DatabaseField(canBeNull = false, width = 50)
    private String oldPurpose;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private ExercisePurpose exercisePurpose;

    public ExercisePurposeArchive(){

    }

    public ExercisePurposeArchive(String oldPurpose, ExercisePurpose exercisePurpose) {
        this.oldPurpose = oldPurpose;
        this.exercisePurpose = exercisePurpose;
    }

    public ExercisePurpose getExercisePurpose() {
        return exercisePurpose;
    }

    public void setExercisePurpose(ExercisePurpose exercisePurpose) {
        this.exercisePurpose = exercisePurpose;
    }

    public String getOldPurpose() {
        return oldPurpose;
    }

    public void setOldPurpose(String oldPurpose) {
        this.oldPurpose = oldPurpose;
    }
}
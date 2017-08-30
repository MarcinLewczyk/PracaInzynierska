package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "exercisePurposes")
public class ExercisePurpose {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String exercisePurpose;

    @DatabaseField
    private String currentState;

    public ExercisePurpose(){

    }

    public ExercisePurpose(String exercisePurpose, String currentState) {
        this.exercisePurpose = exercisePurpose;
        this.currentState = currentState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExercisePurpose() {
        return exercisePurpose;
    }

    public void setExercisePurpose(String exercisePurpose) {
        this.exercisePurpose = exercisePurpose;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
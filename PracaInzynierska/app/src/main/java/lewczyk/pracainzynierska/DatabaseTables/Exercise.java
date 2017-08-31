package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercises")
public class Exercise {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, width = 100)
    private String exerciseName;

    @DatabaseField(canBeNull = false, width = 100)
    private String musclePart;

    @DatabaseField(canBeNull = false, width = 1000)
    private String demonstration;

    @DatabaseField(foreign = true, canBeNull = false)
    private DifficultLevel difficultLevel;

    @DatabaseField(foreign = true, canBeNull = false)
    private EquipmentRequirement equipmentRequirement;

    @DatabaseField(foreign = true, canBeNull = false)
    private ExerciseType exerciseType;

    public Exercise() {
    }

    public Exercise(String exerciseName, String musclePart, String demonstration, DifficultLevel difficultLevel, EquipmentRequirement equipmentRequirement, ExerciseType exerciseType) {
        this.exerciseName = exerciseName;
        this.musclePart = musclePart;
        this.demonstration = demonstration;
        this.difficultLevel = difficultLevel;
        this.equipmentRequirement = equipmentRequirement;
        this.exerciseType = exerciseType;
    }

    public long getId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMusclePart() {
        return musclePart;
    }

    public void setMusclePart(String musclePart) {
        this.musclePart = musclePart;
    }

    public String getDemonstration() {
        return demonstration;
    }

    public void setDemonstration(String demonstration) {
        this.demonstration = demonstration;
    }

    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public EquipmentRequirement getEquipmentRequirement() {
        return equipmentRequirement;
    }

    public void setEquipmentRequirement(EquipmentRequirement equipmentRequirement) {
        this.equipmentRequirement = equipmentRequirement;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }
}
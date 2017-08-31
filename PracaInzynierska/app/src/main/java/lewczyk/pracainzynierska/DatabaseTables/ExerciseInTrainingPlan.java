package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercisesInTrainingPlans")
public class ExerciseInTrainingPlan {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(foreign = true, canBeNull = false)
    private TrainingPlan trainingPlan;

    @DatabaseField(foreign = true, canBeNull = false)
    private Exercise exercise;

    @DatabaseField(canBeNull = false)
    private int series;

    @DatabaseField(canBeNull = false)
    private int repeats;

    @DatabaseField(canBeNull = false)
    private double load;

    public ExerciseInTrainingPlan() {
    }

    public ExerciseInTrainingPlan(TrainingPlan trainingPlan, Exercise exercise, int series, int repeats, double load) {
        this.trainingPlan = trainingPlan;
        this.exercise = exercise;
        this.series = series;
        this.repeats = repeats;
        this.load = load;
    }

    public long getId() {
        return id;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }
}
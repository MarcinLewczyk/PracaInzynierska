package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercisesToDo")
public class ExerciseToDo {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private int series;

    @DatabaseField
    private int repeats;

    @DatabaseField
    private double load;

    @DatabaseField
    private String date;

    @DatabaseField(foreign = true, canBeNull = false)
    private Exercise exercise;

    public ExerciseToDo() {
    }

    public ExerciseToDo(int series, int repeats, double load, String date, Exercise exercise) {
        this.series = series;
        this.repeats = repeats;
        this.load = load;
        this.date = date;
        this.exercise = exercise;
    }

    public long getId() {
        return id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
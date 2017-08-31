package lewczyk.pracainzynierska.DatabaseTables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "exercisesToDo")
public class ExerciseToDo {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private int series;

    @DatabaseField
    private int repeats;

    @DatabaseField
    private int load;

    @DatabaseField
    private Date date;

    @DatabaseField(foreign = true, canBeNull = false)
    private Exercise exercise;

    public ExerciseToDo() {
    }

    public ExerciseToDo(int series, int repeats, int load, Date date, Exercise exercise) {
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

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
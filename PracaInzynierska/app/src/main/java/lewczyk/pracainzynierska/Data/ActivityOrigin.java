package lewczyk.pracainzynierska.Data;

public enum ActivityOrigin {
    UserExercisePlanExerciseListActivity(0),
    UserExerciseParametersActivity(1),
    UserExerciseToDoDetailActivity(2);

    public int which;

    ActivityOrigin(int which){
        this.which = which;
    }
}
package lewczyk.pracainzynierska.CoachExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoDetailActivity extends AppCompatActivity {
    private int DEFAULT_ID = -1;
    private long exerciseToDoId;
    @BindView(R.id.coachExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.coachExerciseToDoMusclePartTextView) TextView musclePart;
    @BindView(R.id.coachExerciseToDoDateTextView) TextView date;
    @BindView(R.id.coachExerciseToDoSeriesTextView) TextView series;
    @BindView(R.id.coachExerciseToDoRepeatsTextView) TextView repeats;
    @BindView(R.id.coachExerciseToDoLoadTextView) TextView load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_exercise_to_do_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_to_do_details));
        loadIntent();
        if(!validateId()){
            exerciseName.setText(R.string.no_data);
        } else {
            ExerciseToDo exerciseToDo = loadExerciseToDo();
            Exercise exercise = ExerciseRepository.findById(this, exerciseToDo.getExercise().getId());
            exerciseName.setText(exercise.getExerciseName());
            musclePart.setText(exercise.getMusclePart());
            date.setText(transformStringDateToDateFormat(exerciseToDo.getDate()));
            series.setText(String.valueOf(exerciseToDo.getSeries()));
            repeats.setText(String.valueOf(exerciseToDo.getRepeats()));
            load.setText(String.valueOf(exerciseToDo.getLoad()));
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        exerciseToDoId = intent.getLongExtra("exerciseToDoId", DEFAULT_ID);
    }

    @OnClick(R.id.coachExerciseToDoDelButton)
    public void delExerciseToDo(){
        if(validateId()){
            ExerciseToDoRepository.deleteExerciseToDo(this, loadExerciseToDo());
            moveToExerciseToDoList();
        }
    }

    private ExerciseToDo loadExerciseToDo() {
        return ExerciseToDoRepository.findById(this, exerciseToDoId);
    }

    private boolean validateId() {
        return exerciseToDoId != DEFAULT_ID;
    }

    @Override
    public void onBackPressed(){
        moveToExerciseToDoList();
    }

    private void moveToExerciseToDoList(){
        Intent intent = new Intent(this, CoachExerciseToDoListActivity.class);
        startActivity(intent);
        finish();
    }

    private String transformStringDateToDateFormat(String dateBeforeTransformation){
        if(dateBeforeTransformation.equals("")){
            return "";
        }
        return dateBeforeTransformation.substring(0,4) + "." +
                dateBeforeTransformation.substring(4,6) + "." +
                dateBeforeTransformation.substring(6,8);
    }
}
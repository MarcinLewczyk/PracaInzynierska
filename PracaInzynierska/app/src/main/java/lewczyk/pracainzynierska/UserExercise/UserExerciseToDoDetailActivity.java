package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.ActivityOrigin;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class UserExerciseToDoDetailActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private long exerciseToDoId;
    private Exercise exercise;
    private ExerciseToDo exerciseToDo;
    @BindView(R.id.userExerciseToDoNameTextView) TextView exerciseName;
    @BindView(R.id.userExerciseToDoMusclePartTextView) TextView musclePart;
    @BindView(R.id.userExerciseToDoDateTextView) TextView date;
    @BindView(R.id.userExerciseToDoSeriesTextView) TextView series;
    @BindView(R.id.userExerciseToDoRepeatsTextView) TextView repeats;
    @BindView(R.id.userExerciseToDoLoadTextView) TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_to_do_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();

        if(!validateId()){
            exerciseName.setText(R.string.no_data);
        } else if(checkIfMapExercise()){
            setMapModeUI();
        } else {
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
        exerciseToDo = loadExerciseToDo();
        exercise = loadExercise();
    }

    private Exercise loadExercise(){
        return  ExerciseRepository.findById(this, exerciseToDo.getExercise().getId());
    }

    private void setMapModeUI(){
        exerciseName.setText(exercise.getExerciseName());
        musclePart.setText(exercise.getMusclePart());
        date.setText(transformStringDateToDateFormat(exerciseToDo.getDate()));
        series.setEnabled(false);
        repeats.setEnabled(false);
        load.setText(String.valueOf(exerciseToDo.getLoad()));
    }

    private ExerciseToDo loadExerciseToDo() {
        return ExerciseToDoRepository.findById(this, exerciseToDoId);
    }

    private boolean validateId() {
        return exerciseToDoId != DEFAULT_ID;
    }

    private String transformStringDateToDateFormat(String dateBeforeTransformation){
        if(dateBeforeTransformation.equals("")){
            return "";
        }
        return dateBeforeTransformation.substring(0,4) + "." +
                dateBeforeTransformation.substring(4,6) + "." +
                dateBeforeTransformation.substring(6,8);
    }
    
    @OnClick(R.id.userExerciseToDoExecuteButton)
    public void executeExerciseButtonPressed(){
        Intent intent;
        if(checkIfMapExercise()){
            intent = new Intent(this, TrackerActivity.class);
        } else {
            intent = new Intent(this, ExecuteExerciseActivity.class);
        }
        intent.putExtra("exerciseToDo", exerciseToDoId);
        intent.putExtra("from", ActivityOrigin.UserExerciseToDoDetailActivity.which);
        startActivity(intent);
        finish();
    }

    private boolean checkIfMapExercise(){
        return exercise.getExerciseName().equals(getString(R.string.running)) ||  exercise.getExerciseName().equals(getString(R.string.cycling));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExerciseToDoListActivity.class);
        startActivity(intent);
        finish();
    }
}
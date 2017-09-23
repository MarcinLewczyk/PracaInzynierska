package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.Database.ExerciseToDoRepository;
import lewczyk.pracainzynierska.Database.TrainingPlanRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class ExecuteExerciseActivity extends AppCompatActivity {
    @BindView(R.id.exerciseToExecuteTitleTextView) TextView exerciseTitle;
    @BindView(R.id.exerciseToExecuteSeriesTextView) TextView seriesTextView;
    @BindView(R.id.exerciseToExecuteRepeatsTextView) TextView repeatsTextView;
    @BindView(R.id.exerciseToExecuteTimerTextView) TextView timerTextView;
    @BindView(R.id.startStopExerciseButton) Button startStopExerciseButton;
    private int fromActivity, currentSet, series, currentRepeat, repeats;
    private long exerciseToDoId, exerciseId, trainingPlanId;
    private ExerciseToDo exerciseToDo;
    private Exercise exercise;
    private ExerciseInTrainingPlan exerciseInTrainingPlan;
    private double sensorParameter;
    private boolean exerciseStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_exercise);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_screen));
        startStopExerciseButton.setText(R.string.begin_exercise);
        loadIntentData();
        exerciseTitle.setText(exercise.getExerciseName());
        if(series == 0){
            seriesTextView.setVisibility(View.INVISIBLE);
        } else {
            seriesTextView.setText(getString(R.string.series) + " " + 0 + "/" + series);
        }
        if(repeats == 0){
            repeatsTextView.setVisibility(View.INVISIBLE);
        } else {
            repeatsTextView.setText(getString(R.string.repeats) + " " + 0 + "/" + repeats);
        }
        setTimer();
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        fromActivity = intent.getIntExtra("from", -1);

        switch(fromActivity){
            case 0:
                exerciseId = intent.getLongExtra("exerciseId", -1);
                trainingPlanId = intent.getLongExtra("trainingPlan", -1);
                if(validateId(exerciseId) && validateId(trainingPlanId)){
                    exercise = loadExercise();
                    exerciseInTrainingPlan = loadExerciseInTrainingPlan();
                    series = exerciseInTrainingPlan.getSeries();
                    repeats = exerciseInTrainingPlan.getRepeats();
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
            case 1:
                exerciseId = intent.getLongExtra("exerciseId", -1);
                if(validateId(exerciseId)){
                    exercise = loadExercise();
                    series = intent.getIntExtra("series", -1);
                    repeats = intent.getIntExtra("repeats", -1);
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
            case 2:
                exerciseToDoId = intent.getLongExtra("exerciseToDo", -1);
                if(validateId(exerciseToDoId)){
                    exerciseToDo = loadExerciseToDo();
                    exerciseId = exerciseToDo.getExercise().getId();
                    exercise = loadExercise();
                    sensorParameter = exercise.getSensorParameter();
                }
                break;
        }
    }

    private void setTimer() {

    }

    private ExerciseInTrainingPlan loadExerciseInTrainingPlan() {
        return ExerciseInTrainingPlanRepository.findByGivenTrainingPlanAndExercise(this, TrainingPlanRepository.findById(this, trainingPlanId), exercise);
    }

    private Exercise loadExercise() {
        return ExerciseRepository.findById(this, exerciseId);
    }

    private ExerciseToDo loadExerciseToDo() {
        return ExerciseToDoRepository.findById(this, exerciseToDoId);
    }

    private boolean validateId(long id) {
        return id != -1;
    }

    //sensor logic - if sensorParameter != 0 then sensors logic. 0 means that exercise does not required sensors.
    //// TODO: 23.09.2017 button logic
    @OnClick(R.id.endOfSetButton)
    public void endOfSetButtonPressed(){

    }

    @OnClick(R.id.startStopExerciseButton)
    public void endOfExerciseButtonPressed(){

    }

    @Override
    public void onBackPressed(){

    }
}
package lewczyk.pracainzynierska.UserExercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
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

public class UserExerciseParametersActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private long exerciseId;
    private int FIELDS_MINIMUM_LENGTH = 0;
    @BindView(R.id.userExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.userExerciseSeriesEditText) EditText seriesEditText;
    @BindView(R.id.userExerciseRepeatsEditText) EditText repeatsEditText;
    @BindView(R.id.userExerciseLoadEditText) EditText loadEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_parameters);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.set_exercise_parameters));
        loadIntent();
        Exercise exercise = loadExercise();
        exerciseName.setText(exercise.getExerciseName());
    }

    private Exercise loadExercise() {
        return ExerciseRepository.findById(this, exerciseId);
    }

    private void loadIntent() {
        Intent intent = getIntent();
        exerciseId = intent.getLongExtra("exerciseId", DEFAULT_ID);
    }

    @OnClick(R.id.userExerciseAddButton)
    public void executeExerciseButtonPressed(){
        if(validateFields()){
            Intent intent = new Intent(this, ExecuteExerciseActivity.class);
            intent.putExtra("series", Integer.valueOf(seriesEditText.getText().toString()));
            intent.putExtra("repeats", Integer.valueOf(repeatsEditText.getText().toString()));
            intent.putExtra("load", Double.valueOf(loadEditText.getText().toString()));
            intent.putExtra("exerciseId", exerciseId);
            intent.putExtra("from", ActivityOrigin.UserExerciseParametersActivity.which);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateFields() {
        return validateSeries() && validateRepeats() & validateLoads();
    }

    private boolean validateSeries() {
        if(seriesEditText.getText().toString().length() == FIELDS_MINIMUM_LENGTH){
            seriesEditText.setText("0");
        }
        try{
            Integer.parseInt(seriesEditText.getText().toString());
        }catch(NumberFormatException e){
            seriesEditText.setError(getString(R.string.must_be_integer));
            return false;
        }
        return true;
    }

    private boolean validateRepeats() {
        if(repeatsEditText.getText().toString().length() == FIELDS_MINIMUM_LENGTH){
            repeatsEditText.setText("0");
        }
        try{
            Integer.parseInt(repeatsEditText.getText().toString());
        }catch(NumberFormatException e){
            repeatsEditText.setError(getString(R.string.must_be_integer));
            return false;
        }
        return true;
    }

    private boolean validateLoads() {
        if(loadEditText.getText().toString().length() == FIELDS_MINIMUM_LENGTH){
            loadEditText.setText("0.0");
        }
        try{
            Double.parseDouble( loadEditText.getText().toString());
        }catch(NumberFormatException e){
            loadEditText.setError(getString(R.string.must_be_floating_point));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserExerciseListActivity.class);
        startActivity(intent);
        finish();
    }
}
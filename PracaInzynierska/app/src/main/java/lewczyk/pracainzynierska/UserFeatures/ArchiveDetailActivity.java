package lewczyk.pracainzynierska.UserFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.R;

public class ArchiveDetailActivity extends AppCompatActivity {
    private long archiveId;
    @BindView(R.id.exerciseArchiveExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.exerciseArchiveMusclePartTextView) TextView musclePart;
    @BindView(R.id.exerciseArchiveExerciseDateTextView) TextView date;
    @BindView(R.id.exerciseArchiveExerciseTimeTextView) TextView time;
    @BindView(R.id.exerciseArchiveExerciseSeriesTextView) TextView series;
    @BindView(R.id.exerciseArchiveExerciseRepeatsTextView) TextView repeats;
    @BindView(R.id.exerciseArchiveExerciseLoadTextView) TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.exercise_archive_details));
        loadStrings();
    }

    private void loadStrings() {
        loadIntent();
        if(validateArchiveId()){
            loadTextViewsContent();
        }
    }

    private void loadTextViewsContent() {
        ExerciseArchive exerciseArchive = loadExerciseArchive();
        Exercise exercise = loadExercise(exerciseArchive);
        exerciseName.setText(exercise.getExerciseName());
        musclePart.setText(exercise.getMusclePart());
        date.setText(transformStringDateToDateFormat(exerciseArchive.getDate()));
        time.setText(String.valueOf(exerciseArchive.getTime()) + getString(R.string.seconds));
        series.setText(String.valueOf(exerciseArchive.getSeries()));
        repeats.setText(String.valueOf(exerciseArchive.getRepeats()));
        load.setText(String.valueOf(exerciseArchive.getLoad()));
    }

    private void loadIntent() {
        Intent intent = getIntent();
        archiveId = intent.getLongExtra("exerciseArchiveId", -1);
    }

    private boolean validateArchiveId(){
        return archiveId != -1;
    }

    private Exercise loadExercise(ExerciseArchive exerciseArchive){
        return ExerciseRepository.findById(this, exerciseArchive.getExercise().getId());
    }

    private ExerciseArchive loadExerciseArchive(){
        return ExerciseArchiveRepository.findById(this, archiveId);
    }

    private String transformStringDateToDateFormat(String dateBeforeTransformation){
        return dateBeforeTransformation.substring(0,4) +
                "." + dateBeforeTransformation.substring(4,6) + "." + dateBeforeTransformation.substring(6,8);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),ArchiveListActivity.class);
        startActivity(intent);
        finish();
    }
}
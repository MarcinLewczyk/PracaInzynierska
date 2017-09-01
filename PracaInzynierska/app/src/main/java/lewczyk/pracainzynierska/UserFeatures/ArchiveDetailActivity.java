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
        setTitle(getString(R.string.exercise_archive_details));
        ButterKnife.bind(this);
        loadStrings();
    }

    private void loadStrings() {
        Intent intent = getIntent();
        Long archiveId = intent.getLongExtra("exerciseArchiveId", -1);
        if(archiveId != -1){
            ExerciseArchive exerciseArchive = ExerciseArchiveRepository.findById(this, archiveId);
            Exercise exercise = ExerciseRepository.findById(this, exerciseArchive.getExercise().getId());

            exerciseName.setText(exercise.getExerciseName());
            musclePart.setText(exercise.getMusclePart());
            String datePreFormat = exerciseArchive.getDate();
            date.setText(datePreFormat.substring(0,4)+"."+datePreFormat.substring(4,6)+"."+datePreFormat.substring(6,8));
            time.setText(String.valueOf(exerciseArchive.getTime()) + "sec");
            series.setText(String.valueOf(exerciseArchive.getSeries()));
            repeats.setText(String.valueOf(exerciseArchive.getRepeats()));
            load.setText(String.valueOf(exerciseArchive.getLoad()));
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),ArchiveListActivity.class);
        startActivity(intent);
        finish();
    }
}
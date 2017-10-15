package lewczyk.pracainzynierska.UserFeatures.ArchiveDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.ArchiveList.ArchiveListActivity;

public class ArchiveDetailActivity extends AppCompatActivity implements ArchiveDetailView{
    @BindView(R.id.exerciseArchiveExerciseNameTextView) TextView exerciseName;
    @BindView(R.id.exerciseArchiveMusclePartTextView) TextView musclePart;
    @BindView(R.id.exerciseArchiveExerciseDateTextView) TextView date;
    @BindView(R.id.exerciseArchiveExerciseTimeTextView) TextView time;
    @BindView(R.id.exerciseArchiveExerciseSeriesTextView) TextView series;
    @BindView(R.id.exerciseArchiveExerciseRepeatsTextView) TextView repeats;
    @BindView(R.id.exerciseArchiveExerciseLoadTextView) TextView load;
    @BindView(R.id.setNumberArchiveDetailTextView) TextView seriesTextView;
    @BindView(R.id.repeatsNumberArchiveDetailTextView) TextView repeatsTextView;
    @BindView(R.id.exerciseArchiveLoadTextView) TextView loadTextView;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ArchiveDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ArchiveDetailPresenter(this);
        setContentView(R.layout.activity_archive_detail);
        ButterKnife.bind(this);
        setViewSettings();
    }

    public void setViewSettings() {
        presenter.loadExerciseArchive();
        setTitle(getString(R.string.exercise_archive_details));
        if(validateArchiveId()){
            loadViewContent();
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("exerciseArchiveId", DEFAULT_ID);
    }

    private boolean validateArchiveId(){
        return presenter.validateId();
    }

    private void loadViewContent() {
        exerciseName.setText(presenter.getExerciseName());
        musclePart.setText(presenter.getMusclePart());
        date.setText(presenter.getDate());
        time.setText(presenter.getTime() + getString(R.string.seconds));
        if(checkIfMapExercise()){
            setMapModeUI();
        } else {
            series.setText(presenter.getSeries());
            repeats.setText(presenter.getRepeats());
        }
        load.setText(presenter.getLoad());
    }

    private boolean checkIfMapExercise(){
        String name = presenter.getExerciseName();
        return name.equals(getString(R.string.running)) ||  name.equals(getString(R.string.cycling));
    }

    private void setMapModeUI(){
        seriesTextView.setEnabled(false);
        series.setEnabled(false);
        repeatsTextView.setEnabled(false);
        repeats.setEnabled(false);
        loadTextView.setText(getString(R.string.route_distance) + getString(R.string.distance_unit));
    }

    @Override
    public void onBackPressed(){
        navigateToArchiveListActivity();
    }

    private void navigateToArchiveListActivity(){
        Intent intent = new Intent(this, ArchiveListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
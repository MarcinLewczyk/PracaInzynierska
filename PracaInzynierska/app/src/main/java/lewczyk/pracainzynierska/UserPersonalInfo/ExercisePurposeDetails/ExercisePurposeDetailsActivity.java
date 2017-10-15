package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.ExercisePurposeArchiveAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeEdit.ExercisePurposeEditActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeList.ExercisePurposeListActivity;

public class ExercisePurposeDetailsActivity extends AppCompatActivity implements
        ExercisePurposeDetailsView,
        ExercisePurposeDetailsNavigator{
    @BindView(R.id.exercisePurposeTextView) TextView purposeTitle;
    @BindView(R.id.exercisePurposeCurrTextView) TextView purposeState;
    @BindView(R.id.exercisePurposeArchiveListView) ListView exercisePurposesArchiveListView;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ExercisePurposeDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExercisePurposeDetailsPresenter(this, this);
        setContentView(R.layout.activity_exercise_purpose_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.purpose_details));
        presenter.loadExercisePurpose();
        loadViewContent();
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("purposeId", DEFAULT_ID);
    }

    private void loadViewContent() {
        if(validatePurposeId()){
            purposeTitle.setText(presenter.getExercisePurpose());
            purposeState.setText(presenter.getExerciseCurrentState());
            showArchiveList(presenter.getExercisePurposeArchive());
        } else{
            purposeTitle.setText(getString(R.string.no_data));
        }
    }

    private void showArchiveList(ArrayList content) {
        ExercisePurposeArchiveAdapter adapter = new ExercisePurposeArchiveAdapter(content, this);
        exercisePurposesArchiveListView.setAdapter(adapter);
    }

    @OnClick(R.id.exercisePurposeModButton)
    public void editButtonPressed(){
        if(validatePurposeId()){
            navigateToExercisePurposeEditActivity();
        }
    }

    private void navigateToExercisePurposeEditActivity() {
        Intent intent = new Intent(this, ExercisePurposeEditActivity.class);
        intent.putExtra("purposeId", presenter.getPurposeId());
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.exercisePurposeDelButton)
    public void navigateBackDeleteButtonPressed(){
        if(validatePurposeId()){
            presenter.deleteCurrentExercisePurpose();
        }
    }

    private boolean validatePurposeId(){
        return presenter.validateId();
    }

    @Override
    public void onBackPressed(){
        navigateBack();
    }

    @Override
    public void navigateBack() {
        Intent intent = new Intent(this, ExercisePurposeListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
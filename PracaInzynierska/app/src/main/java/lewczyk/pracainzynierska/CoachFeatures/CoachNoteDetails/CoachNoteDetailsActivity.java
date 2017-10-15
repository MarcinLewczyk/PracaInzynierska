package lewczyk.pracainzynierska.CoachFeatures.CoachNoteDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteEdit.CoachNoteEditActivity;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteList.CoachNoteListActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachNoteDetailsActivity extends AppCompatActivity implements CoachNoteDetailsView, CoachNoteDetailsNavigator{
    @BindView(R.id.coachNoteDetailsWebView) WebView noteDetails;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachNoteDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachNoteDetailsPresenter(this, this);
        setContentView(R.layout.activity_coach_note_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadCoachNote();
        setTitle(R.string.note_details);
        loadViewContent();
    }

    private void loadViewContent() {
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(validateId()){
            noteDetails.loadDataWithBaseURL(null, String.format(htmlParam, presenter.getNoteText()), "text/html", "utf-8", null);
        } else {
            noteDetails.loadDataWithBaseURL(null, String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8", null);
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("noteId", DEFAULT_ID);
    }

    @OnClick(R.id.coachNoteDetailsModButton)
    public void modificationButtonPressed(){
        if(validateId()){
            Intent intent = new Intent(this, CoachNoteEditActivity.class);
            intent.putExtra("noteId", presenter.getNoteId());
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.coachNoteDetailsDelButton)
    public void deleteNoteButtonPressed(){
        if(validateId()){
            presenter.deleteCurrentNote();
        }
    }

    private boolean validateId() {
        return presenter.validateId();
    }

    @Override
    public void onBackPressed(){
        navigateToCoachNoteListActivity();
    }

    @Override
    public void navigateToCoachNoteListActivity(){
        Intent intent = new Intent(this, CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
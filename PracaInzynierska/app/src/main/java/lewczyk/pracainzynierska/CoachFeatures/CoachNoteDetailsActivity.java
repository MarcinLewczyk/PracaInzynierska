package lewczyk.pracainzynierska.CoachFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;
import lewczyk.pracainzynierska.R;

public class CoachNoteDetailsActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private long noteId;
    @BindView(R.id.coachNoteDetailsWebView) WebView noteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_note_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.note_details);
        loadStrings();
    }

    private void loadStrings() {
        loadIntent();
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(!validateId()){
            noteDetails.loadDataWithBaseURL(null, String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8", null);
        } else {
            noteDetails.loadDataWithBaseURL(null, String.format(htmlParam, loadNote().getText()), "text/html", "utf-8", null);
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", DEFAULT_ID);
    }

    @OnClick(R.id.coachNoteDetailsModButton)
    public void modificationButtonPressed(){
        if(validateId()){
            Intent intent = new Intent(this, CoachNoteEditActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.coachNoteDetailsDelButton)
    public void deleteNoteButtonPressed(){
        if(validateId()){
            CoachNoteRepository.deleteCoachNote(this, loadNote());
            moveToCoachNoteList();
        }
    }

    private CoachNote loadNote() {
        return CoachNoteRepository.findById(this, noteId);
    }

    private boolean validateId() {
        return noteId != DEFAULT_ID;
    }

    @Override
    public void onBackPressed(){
        moveToCoachNoteList();
    }

    private void moveToCoachNoteList(){
        Intent intent = new Intent(this,CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
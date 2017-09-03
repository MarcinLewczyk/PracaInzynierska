package lewczyk.pracainzynierska.CoachFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.R;

public class CoachNoteDetailsActivity extends AppCompatActivity {
    private long noteId;
    @BindView(R.id.coachNoteDetailsWebView) WebView noteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_note_details);
        ButterKnife.bind(this);
        loadStrings();
    }

    private void loadStrings() {
        setTitle(R.string.note_details);
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(noteId == -1){
            noteDetails.loadDataWithBaseURL(null, String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8", null);
        } else {
            noteDetails.loadDataWithBaseURL(null, String.format(htmlParam, CoachNoteRepository.findById(this, noteId).getText()), "text/html", "utf-8", null);
        }
    }

    @OnClick(R.id.coachNoteDetailsModButton)
    public void moveToNoteMod(){
        if(noteId != -1){
            Intent intent = new Intent(getApplicationContext(), CoachNoteEditActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.coachNoteDetailsDelButton)
    public void delNote(){
        if(noteId != -1){
            CoachNoteRepository.deleteCoachNote(getApplicationContext(), CoachNoteRepository.findById(this, noteId));
            moveToCoachNoteList();
        }
    }

    @Override
    public void onBackPressed(){
        moveToCoachNoteList();
    }

    public void moveToCoachNoteList(){
        Intent intent = new Intent(getApplicationContext(),CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
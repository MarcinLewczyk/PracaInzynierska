package lewczyk.pracainzynierska.UserFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.R;

public class UserNoteDetailsActivity extends AppCompatActivity {
    private long noteId;
    @BindView(R.id.noteDetailsWebView) WebView noteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note_details);
        ButterKnife.bind(this);
        setViewSettings();
        loadStrings();
    }

    private void setViewSettings() {
        setTitle(R.string.note_details);
        loadStrings();
    }

    private void loadStrings() {
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);

        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(noteId == -1){
            noteDetails.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        } else {
            noteDetails.loadData(String.format(htmlParam, UserNoteRepository.findById(this, noteId).getText()), "text/html", "utf-8");
        }
    }

    @OnClick(R.id.noteDetailsModButton)
    public void moveToNoteMod(){
        if(noteId != -1){
            Intent intent = new Intent(getApplicationContext(), UserNoteEditActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.noteDetailsDelButton)
    public void delNote(){
        if(noteId != -1){
            UserNoteRepository.deleteUserNote(getApplicationContext(), UserNoteRepository.findById(this, noteId));
            moveToUserNoteList();
        }
    }

    @Override
    public void onBackPressed(){
        moveToUserNoteList();
    }

    private void moveToUserNoteList(){
        Intent intent = new Intent(getApplicationContext(),UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
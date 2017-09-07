package lewczyk.pracainzynierska.UserFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;

public class UserNoteDetailsActivity extends AppCompatActivity {
    private int DEFAULT_ID = -1;
    private long noteId;
    @BindView(R.id.noteDetailsWebView) WebView noteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note_details);
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
        if(!validateNoteId()){
            noteDetails.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        } else {
            noteDetails.loadData(String.format(htmlParam, loadUserNote().getText()), "text/html", "utf-8");
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", DEFAULT_ID);
    }

    @OnClick(R.id.noteDetailsModButton)
    public void modButtonPressed(){
        if(validateNoteId()){
            Intent intent = new Intent(this, UserNoteEditActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.noteDetailsDelButton)
    public void deleteNoteButtonPressed(){
        if(validateNoteId()){
            UserNoteRepository.deleteUserNote(this, loadUserNote());
            moveToUserNoteList();
        }
    }

    private UserNote loadUserNote(){
        return UserNoteRepository.findById(this, noteId);
    }

    private boolean validateNoteId(){
        return noteId != DEFAULT_ID;
    }

    @Override
    public void onBackPressed(){
        moveToUserNoteList();
    }

    private void moveToUserNoteList(){
        Intent intent = new Intent(this, UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
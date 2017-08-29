package lewczyk.pracainzynierska.DataDetailsPresentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.EditUserNoteActivity;
import lewczyk.pracainzynierska.UserFeatures.UserNoteListActivity;

public class NoteDetailsActivity extends AppCompatActivity {
    private long noteId;
    private String msg;
    private String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
    @BindView(R.id.noteDetailsWebView) WebView noteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        loadStrings();
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);
        ButterKnife.bind(this);
        if(noteId == -1){
            noteDetails.loadData(String.format(htmlParam, msg), "text/html", "utf-8");
        } else {
            msg = UserNoteRepository.findById(this, noteId).getText();
            noteDetails.loadData(String.format(htmlParam, msg), "text/html", "utf-8");
        }
    }

    private void loadStrings() {
        msg  = getString(R.string.no_data);
    }

    @OnClick(R.id.noteDetailsModButton)
    public void moveToNoteMod(){
        if(noteId != -1){
            Intent intent = new Intent(getApplicationContext(), EditUserNoteActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.noteDetailsDelButton)
    public void delNote(){
        if(noteId != -1){
            UserNoteRepository.deleteUserNote(getApplicationContext(), UserNoteRepository.findById(this, noteId));
            Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
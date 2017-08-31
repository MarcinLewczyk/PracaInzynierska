package lewczyk.pracainzynierska.UserFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;

public class UserNoteEditActivity extends AppCompatActivity {
    private long noteId;
    @BindView(R.id.userNoteEditText)
    EditText userNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note_edit);
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        if(noteId == -1){
            setTitle(getString(R.string.create_new_note));
        } else {
            setTitle(getString(R.string.edit_note));
            UserNote userNote = UserNoteRepository.findById(getApplicationContext(), noteId);
            userNoteEditText.setText(userNote.getText());
        }
    }

    @OnClick(R.id.userNoteEditSaveButton)
    public void save(){
        validateFields();
        if(userNoteEditText.length() > 0 && noteId == -1 && userNoteEditText.length() < 300){
            UserNoteRepository.addUserNote(getApplicationContext(), new UserNote(userNoteEditText.toString()));
            moveToUserNoteList();
        } else if(userNoteEditText.length() > 0 && noteId != -1){
            UserNote edited = UserNoteRepository.findById(this, noteId);
            edited.setText(userNoteEditText.toString());
            UserNoteRepository.updateUserNote(getApplicationContext(), edited);
            moveToUserNoteList();
        }
    }

    private void moveToUserNoteList(){
        Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    private void validateFields() {
        if(userNoteEditText.length() == 0){
            userNoteEditText.setError(getString(R.string.more_than_0_characters_required));
        } else if(userNoteEditText.length() >= 300) {
            userNoteEditText.setError(getString(R.string.less_than_300));
        } else {
            userNoteEditText.setError(null);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
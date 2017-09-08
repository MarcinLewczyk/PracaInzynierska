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
    private int DEFAULT_ID = -1;
    private int NOTE_MINIMUM_LENGTH = 0;
    private int NOTE_MAXIMUM_LENGTH = 300;
    private long noteId;
    @BindView(R.id.userNoteEditText) EditText userNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        if(!validateNoteId()){
            setTitle(getString(R.string.create_new_note));
        } else {
            setTitle(getString(R.string.edit_note));
            userNoteEditText.setText(loadUserNote().getText());
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", DEFAULT_ID);
    }

    @OnClick(R.id.userNoteEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validateNoteId()){
            UserNoteRepository.addUserNote(this, new UserNote(userNoteEditText.getText().toString()));
            moveToUserNoteList();
        } else if(validateFields() && validateNoteId()){
            UserNote edited = loadUserNote();
            edited.setText(userNoteEditText.getText().toString());
            UserNoteRepository.updateUserNote(this, edited);
            moveToUserNoteList();
        }
    }

    private UserNote loadUserNote(){
        return UserNoteRepository.findById(this, noteId);
    }

    private boolean validateNoteId(){
        return noteId != DEFAULT_ID;
    }

    private void moveToUserNoteList(){
        Intent intent = new Intent(this, UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateFields() {
        if(userNoteEditText.getText().length() == NOTE_MINIMUM_LENGTH){
            userNoteEditText.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(userNoteEditText.getText().length() >= NOTE_MAXIMUM_LENGTH) {
            userNoteEditText.setError(getString(R.string.less_than_300));
            return false;
        } else {
            userNoteEditText.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        moveToUserNoteList();
    }
}
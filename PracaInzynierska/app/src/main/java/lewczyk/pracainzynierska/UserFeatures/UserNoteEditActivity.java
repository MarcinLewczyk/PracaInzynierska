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
    @BindView(R.id.userNoteEditText) EditText userNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);
        if(noteId == -1){
            setTitle(getString(R.string.create_new_note));
        } else {
            setTitle(getString(R.string.edit_note));
            userNoteEditText.setText(UserNoteRepository.findById(getApplicationContext(), noteId).getText());
        }
    }

    @OnClick(R.id.userNoteEditSaveButton)
    public void save(){
        if(validateFields() && noteId == -1){
            UserNoteRepository.addUserNote(getApplicationContext(), new UserNote(userNoteEditText.getText().toString()));
            moveToUserNoteList();
        } else if(validateFields() && noteId != -1){
            UserNote edited = UserNoteRepository.findById(this, noteId);
            edited.setText(userNoteEditText.getText().toString());
            UserNoteRepository.updateUserNote(getApplicationContext(), edited);
            moveToUserNoteList();
        }
    }

    private void moveToUserNoteList(){
        Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateFields() {
        if(userNoteEditText.getText().length() == 0){
            userNoteEditText.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(userNoteEditText.getText().length() >= 300) {
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
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
        ButterKnife.bind(this);

        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);

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
        if(userNoteEditText.toString().isEmpty()){
            userNoteEditText.setError(getString(R.string.more_than_0_characters_required));
        } else {
            userNoteEditText.setError(null);
        }

        if(userNoteEditText.length() > 0 && noteId == -1){
            UserNoteRepository.addUserNote(getApplicationContext(), new UserNote(userNoteEditText.toString()));
        } else if(userNoteEditText.length() > 0 && noteId != -1){
            UserNote edited = UserNoteRepository.findById(this, noteId);
            edited.setText(userNoteEditText.toString());
            UserNoteRepository.updateUserNote(getApplicationContext(), edited);
        }
        Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
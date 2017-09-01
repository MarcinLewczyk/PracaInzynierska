package lewczyk.pracainzynierska.CoachFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;
import lewczyk.pracainzynierska.R;

public class CoachNoteEditActivity extends AppCompatActivity {
    private long noteId;
    @BindView(R.id.coachNoteEditText)
    EditText coachNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_note_edit);
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
            CoachNote coachNote = CoachNoteRepository.findById(getApplicationContext(), noteId);
            coachNoteEditText.setText(coachNote.getText());
        }
    }

    @OnClick(R.id.coachNoteEditSaveButton)
    public void save(){
        if(validateFields() && noteId == -1){
            CoachNoteRepository.addCoachNote(getApplicationContext(), new CoachNote(coachNoteEditText.getText().toString()));
            moveToCoachNoteList();
        } else if(validateFields() && noteId != -1){
            CoachNote edited = CoachNoteRepository.findById(this, noteId);
            edited.setText(coachNoteEditText.getText().toString());
            CoachNoteRepository.updateCoachNote(getApplicationContext(), edited);
            moveToCoachNoteList();
        }
    }

    private void moveToCoachNoteList(){
        Intent intent = new Intent(getApplicationContext(), CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateFields() {
        if(coachNoteEditText.getText().length() == 0){
            coachNoteEditText.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(coachNoteEditText.getText().length() >= 300) {
            coachNoteEditText.setError(getString(R.string.less_than_300));
            return false;
        } else {
            coachNoteEditText.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
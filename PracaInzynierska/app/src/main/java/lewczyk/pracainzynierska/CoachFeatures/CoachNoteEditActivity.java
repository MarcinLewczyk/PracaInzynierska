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
    private int DEFAULT_ID = -1;
    private int COACH_NOTE_MINIMUM_LENGTH = 0;
    private int COACH_NOTE_MAXIMUM_LENGTH = 300;
    private long noteId;
    @BindView(R.id.coachNoteEditText) EditText coachNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_note_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        if(!validateId()){
            setTitle(getString(R.string.create_new_note));
        } else {
            setTitle(getString(R.string.edit_note));
            coachNoteEditText.setText(loadCoachNote().getText());
        }
    }

    private void loadIntent() {
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", DEFAULT_ID);
    }

    @OnClick(R.id.coachNoteEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validateId()){
            CoachNoteRepository.addCoachNote(this, new CoachNote(coachNoteEditText.getText().toString()));
            moveToCoachNoteList();
        } else if(validateFields() && validateId()){
            CoachNote edited = loadCoachNote();
            edited.setText(coachNoteEditText.getText().toString());
            CoachNoteRepository.updateCoachNote(this, edited);
            moveToCoachNoteList();
        }
    }

    private CoachNote loadCoachNote() {
        return CoachNoteRepository.findById(this, noteId);
    }

    private boolean validateId() {
        return noteId != DEFAULT_ID;
    }

    private void moveToCoachNoteList(){
        Intent intent = new Intent(this, CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateFields() {
        if(coachNoteEditText.getText().length() == COACH_NOTE_MINIMUM_LENGTH){
            coachNoteEditText.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(coachNoteEditText.getText().length() >= COACH_NOTE_MAXIMUM_LENGTH) {
            coachNoteEditText.setError(getString(R.string.less_than_300));
            return false;
        } else {
            coachNoteEditText.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        moveToCoachNoteList();
    }
}
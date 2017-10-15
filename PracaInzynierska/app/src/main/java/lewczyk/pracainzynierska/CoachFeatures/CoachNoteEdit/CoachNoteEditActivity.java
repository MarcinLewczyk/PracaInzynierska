package lewczyk.pracainzynierska.CoachFeatures.CoachNoteEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteList.CoachNoteListActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachNoteEditActivity extends AppCompatActivity implements CoachNoteEditView, CoachNoteEditNavigator{
    @BindView(R.id.coachNoteEditText) EditText coachNoteEditText;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int COACH_NOTE_MINIMUM_LENGTH = 0;
    private int COACH_NOTE_MAXIMUM_LENGTH = 300;
    private CoachNoteEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachNoteEditPresenter(this, this);
        setContentView(R.layout.activity_coach_note_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadCoachNote();
        if(validateId()){
            setTitle(getString(R.string.edit_note));
            loadViewContent();
        } else {
            setTitle(getString(R.string.create_new_note));
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("noteId", DEFAULT_ID);
    }

    private void loadViewContent(){
        coachNoteEditText.setText(presenter.getNoteText());
    }

    @OnClick(R.id.coachNoteEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validateId()){
            presenter.addNote();
        } else if(validateFields() && validateId()){
            presenter.updateNote();
        }
    }

    private boolean validateId() {
        return presenter.validateId();
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
    public String getCoachNoteStringEditText(){
        return coachNoteEditText.getText().toString();
    }

    @Override
    public void onBackPressed(){
        navigateToCoachNoteList();
    }

    @Override
    public void navigateToCoachNoteList(){
        Intent intent = new Intent(this, CoachNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
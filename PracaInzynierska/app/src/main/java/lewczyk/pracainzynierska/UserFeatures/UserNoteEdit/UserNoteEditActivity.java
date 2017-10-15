package lewczyk.pracainzynierska.UserFeatures.UserNoteEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.UserNoteList.UserNoteListActivity;

public class UserNoteEditActivity extends AppCompatActivity implements UserNoteEditNavigator, UserNoteEditView{
    @BindView(R.id.userNoteEditText) EditText userNoteEditText;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int NOTE_MINIMUM_LENGTH = 0;
    private int NOTE_MAXIMUM_LENGTH = 300;
    private UserNoteEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserNoteEditPresenter(this, this);
        setContentView(R.layout.activity_user_note_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadUserNote();
        if(validateNoteId()){
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
        userNoteEditText.setText(presenter.getNoteText());
    }

    @OnClick(R.id.userNoteEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validateNoteId()){
            addNote();
        } else if(validateFields() && validateNoteId()){
            updateNote();
        }
    }

    private boolean validateNoteId(){
        return presenter.validateId();
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

    private void addNote(){
        presenter.addUserNote();
    }

    private void updateNote(){
        presenter.updateUserNote();
    }

    @Override
    public String getUserNoteStringEditText(){
        return userNoteEditText.getText().toString();
    }

    @Override
    public void onBackPressed(){
        navigateToUserNoteListActivity();
    }

    @Override
    public void navigateToUserNoteListActivity(){
        Intent intent = new Intent(this, UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
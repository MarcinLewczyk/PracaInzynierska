package lewczyk.pracainzynierska.UserFeatures.UserNoteEdit;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;

public class UserNoteEditPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserNoteRepository userNoteRepository;
    private UserNoteEditView userNoteEditView;
    private UserNoteEditNavigator userNoteEditNavigator;
    private UserNote userNote;
    private long noteId;

    public UserNoteEditPresenter(UserNoteEditView userNoteEditView, UserNoteEditNavigator userNoteEditNavigator) {
        this.userNoteEditNavigator = userNoteEditNavigator;
        this.userNoteEditView = userNoteEditView;
        userNoteRepository = new UserNoteRepository(userNoteEditView.getContext());
    }

    void loadUserNote(){
        noteId = userNoteEditView.loadIntent();
        userNote = userNoteRepository.findById(noteId);
    }

    String getNoteText(){
        return userNote.getText();
    }

    void addUserNote(){
        userNoteRepository.addUserNote(new UserNote(userNoteEditView.getUserNoteStringEditText()));
        userNoteEditNavigator.navigateToUserNoteListActivity();
    }

    void updateUserNote(){
        userNote.setText(userNoteEditView.getUserNoteStringEditText());
        userNoteRepository.updateUserNote(userNote);
        userNoteEditNavigator.navigateToUserNoteListActivity();
    }

    boolean validateId(){
        return noteId != DEFAULT_ID;
    }
}
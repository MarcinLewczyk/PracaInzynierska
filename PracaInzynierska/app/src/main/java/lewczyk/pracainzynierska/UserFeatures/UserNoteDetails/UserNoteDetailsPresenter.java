package lewczyk.pracainzynierska.UserFeatures.UserNoteDetails;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;

public class UserNoteDetailsPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserNoteRepository userNoteRepository;
    private UserNoteDetailsView userNoteDetailsView;
    private UserNoteDetailsNavigator userNoteDetailsNavigator;
    private UserNote userNote;
    private long noteId;

    public UserNoteDetailsPresenter(UserNoteDetailsView userNoteDetailsView, UserNoteDetailsNavigator userNoteDetailsNavigator){
        this.userNoteDetailsNavigator = userNoteDetailsNavigator;
        this.userNoteDetailsView = userNoteDetailsView;
        userNoteRepository = new UserNoteRepository(userNoteDetailsView.getContext());
    }

    void loadUserNote(){
        noteId = userNoteDetailsView.loadIntent();
        if(validateId()){
            userNote = userNoteRepository.findById(noteId);
        }
    }

    String getNoteText(){
        return  userNote.getText();
    }

    void deleteCurrentNote(){
        userNoteRepository.deleteUserNote(userNote);
        userNoteDetailsNavigator.navigateToUserNoteListActivity();
    }

    boolean validateId(){
        return noteId != DEFAULT_ID;
    }

    long getNoteId(){
        return noteId;
    }
}
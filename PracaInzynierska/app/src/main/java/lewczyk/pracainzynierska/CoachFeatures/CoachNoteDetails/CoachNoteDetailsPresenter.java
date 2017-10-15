package lewczyk.pracainzynierska.CoachFeatures.CoachNoteDetails;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;

public class CoachNoteDetailsPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachNoteDetailsView coachNoteDetailsView;
    private CoachNoteDetailsNavigator coachNoteDetailsNavigator;
    private CoachNoteRepository coachNoteRepository;
    private CoachNote coachNote;
    private long noteId;


    public CoachNoteDetailsPresenter(CoachNoteDetailsView coachNoteDetailsView, CoachNoteDetailsNavigator coachNoteDetailsNavigator) {
        this.coachNoteDetailsView = coachNoteDetailsView;
        this.coachNoteDetailsNavigator = coachNoteDetailsNavigator;
        this.coachNoteRepository = new CoachNoteRepository(coachNoteDetailsView.getContext());
    }

    void loadCoachNote(){
        noteId = coachNoteDetailsView.loadIntent();
        coachNote = coachNoteRepository.findById(noteId);
    }

    boolean validateId(){
        return noteId != DEFAULT_ID;
    }

    String getNoteText(){
        return coachNote.getText();
    }

    void deleteCurrentNote(){
        coachNoteRepository.deleteCoachNote(coachNote);
        coachNoteDetailsNavigator.navigateToCoachNoteListActivity();
    }

    long getNoteId(){
        return noteId;
    }
}
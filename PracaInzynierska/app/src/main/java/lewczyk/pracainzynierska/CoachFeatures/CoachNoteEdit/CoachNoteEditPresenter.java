package lewczyk.pracainzynierska.CoachFeatures.CoachNoteEdit;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;

public class CoachNoteEditPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachNoteEditView coachNoteEditView;
    private CoachNoteEditNavigator coachNoteEditNavigator;
    private CoachNoteRepository coachNoteRepository;
    private long noteId;
    private CoachNote coachNote;

    public CoachNoteEditPresenter(CoachNoteEditView coachNoteEditView, CoachNoteEditNavigator coachNoteEditNavigator) {
        this.coachNoteEditView = coachNoteEditView;
        this.coachNoteEditNavigator = coachNoteEditNavigator;
        this.coachNoteRepository = new CoachNoteRepository(coachNoteEditView.getContext());
    }

    void loadCoachNote(){
        noteId = coachNoteEditView.loadIntent();
        coachNote = coachNoteRepository.findById(noteId);
    }

    String getNoteText(){
        return coachNote.getText();
    }

    boolean validateId(){
        return noteId != DEFAULT_ID;
    }

    void addNote(){
        coachNoteRepository.addCoachNote(new CoachNote(coachNoteEditView.getCoachNoteStringEditText()));
        coachNoteEditNavigator.navigateToCoachNoteList();
    }

    void updateNote(){
        coachNote.setText(coachNoteEditView.getCoachNoteStringEditText());
        coachNoteRepository.updateCoachNote(coachNote);
        coachNoteEditNavigator.navigateToCoachNoteList();
    }
}
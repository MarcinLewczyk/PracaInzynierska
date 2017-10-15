package lewczyk.pracainzynierska.CoachFeatures.CoachNoteList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.CoachNoteRepository;

public class CoachNoteListPresenter {
    private CoachNoteRepository coachNoteRepository;

    public CoachNoteListPresenter(CoachNoteListView coachNoteListView) {
        coachNoteRepository = new CoachNoteRepository(coachNoteListView.getContext());

    }

    ArrayList getCoachNotesList(){
        return (ArrayList) coachNoteRepository.findAll();
    }
}
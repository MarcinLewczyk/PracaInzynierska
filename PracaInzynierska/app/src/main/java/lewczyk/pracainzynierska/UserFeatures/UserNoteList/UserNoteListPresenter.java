package lewczyk.pracainzynierska.UserFeatures.UserNoteList;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Database.UserNoteRepository;

public class UserNoteListPresenter {
    private UserNoteRepository userNoteRepository;

    public UserNoteListPresenter(UserNoteListView userNoteListView) {
        userNoteRepository = new UserNoteRepository(userNoteListView.getContext());
    }

    ArrayList getUserNotesList(){
        return (ArrayList) userNoteRepository.findAll();
    }
}
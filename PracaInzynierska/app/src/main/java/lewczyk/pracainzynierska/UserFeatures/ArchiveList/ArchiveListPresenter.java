package lewczyk.pracainzynierska.UserFeatures.ArchiveList;

import java.util.ArrayList;
import java.util.Collections;

import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;

public class ArchiveListPresenter {
    private ExerciseArchiveRepository exerciseArchiveRepository;

    public ArchiveListPresenter(ArchiveListView archiveListView){
        exerciseArchiveRepository = new ExerciseArchiveRepository(archiveListView.getContext());
    }

    ArrayList getArchiveList(){
        return reverseList((ArrayList) exerciseArchiveRepository.findAll());
    }

    private ArrayList reverseList(ArrayList list){
        Collections.reverse(list);
        return list;
    }
}
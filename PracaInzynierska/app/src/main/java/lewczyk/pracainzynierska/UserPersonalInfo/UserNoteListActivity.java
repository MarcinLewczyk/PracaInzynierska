package lewczyk.pracainzynierska.UserPersonalInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Adapters.UserNoteAdapter;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;

public class UserNoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes_list);

        ListView notesList = (ListView) findViewById(R.id.userNotesListView);

        ArrayList<UserNote> userNoteList = (ArrayList) UserNoteRepository.findAll(this);
        UserNoteAdapter adapter = new UserNoteAdapter(userNoteList, this);
        notesList.setAdapter(adapter);
    }
}
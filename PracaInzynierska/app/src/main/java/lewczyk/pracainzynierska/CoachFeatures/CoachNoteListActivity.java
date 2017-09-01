package lewczyk.pracainzynierska.CoachFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.CoachNoteAdapter;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;
import lewczyk.pracainzynierska.R;

public class CoachNoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_note_list);
        setTitle(getString(R.string.notes));
        ButterKnife.bind(this);

        ListView notesList = (ListView) findViewById(R.id.coachNoteListView);

        ArrayList<CoachNote> coachNoteList = (ArrayList) CoachNoteRepository.findAll(this);
        CoachNoteAdapter adapter = new CoachNoteAdapter(coachNoteList, this);
        notesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachNoteAddButton)
    public void addNewNote(){
        Intent intent = new Intent(getApplicationContext(), CoachNoteEditActivity.class);
        intent.putExtra("noteId", -1L);
        startActivity(intent);
        finish();
    }
}
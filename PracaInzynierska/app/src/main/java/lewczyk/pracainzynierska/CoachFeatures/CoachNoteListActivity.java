package lewczyk.pracainzynierska.CoachFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.CoachNoteAdapter;
import lewczyk.pracainzynierska.Database.CoachNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;
import lewczyk.pracainzynierska.R;

public class CoachNoteListActivity extends AppCompatActivity {
    private int DEFAULT_ID = 1;
    @BindView(R.id.coachNoteListView) ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_note_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.notes));
        loadNotesList();
    }

    private void loadNotesList(){
        ArrayList<CoachNote> coachNoteList = (ArrayList) CoachNoteRepository.findAll(this);
        CoachNoteAdapter adapter = new CoachNoteAdapter(coachNoteList, this);
        notesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachNoteAddButton)
    public void addNewNoteButtonPressed(){
        Intent intent = new Intent(getApplicationContext(), CoachNoteEditActivity.class);
        intent.putExtra("noteId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }
}
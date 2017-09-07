package lewczyk.pracainzynierska.UserFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.UserNoteAdapter;
import lewczyk.pracainzynierska.Database.UserNoteRepository;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;

public class UserNoteListActivity extends AppCompatActivity {
    private int DEFAULT_ID = -1;
    @BindView(R.id.userNoteListView) ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.notes);
        loadUserNotesList();
    }

    private void loadUserNotesList() {
        ArrayList<UserNote> userNoteList = (ArrayList) UserNoteRepository.findAll(this);
        UserNoteAdapter adapter = new UserNoteAdapter(userNoteList, this);
        notesList.setAdapter(adapter);
    }

    @OnClick(R.id.userNoteAddButton)
    public void addNoteButtonPressed(){
        Intent intent = new Intent(this, UserNoteEditActivity.class);
        intent.putExtra("noteId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }
}
package lewczyk.pracainzynierska.CoachFeatures.CoachNoteList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.CoachNoteAdapter;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteEdit.CoachNoteEditActivity;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;

public class CoachNoteListActivity extends AppCompatActivity implements CoachNoteListView{
    @BindView(R.id.coachNoteListView) ListView notesList;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private CoachNoteListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoachNoteListPresenter(this);
        setContentView(R.layout.activity_coach_note_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.notes));
        showCoachNotesList(presenter.getCoachNotesList());
    }

    private void showCoachNotesList(ArrayList content){
        CoachNoteAdapter adapter = new CoachNoteAdapter(content, this);
        notesList.setAdapter(adapter);
    }

    @OnClick(R.id.coachNoteAddButton)
    public void addNewNoteButtonPressed(){
        Intent intent = new Intent(getApplicationContext(), CoachNoteEditActivity.class);
        intent.putExtra("noteId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
package lewczyk.pracainzynierska.UserFeatures.UserNoteList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.UserNoteAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.UserNoteEdit.UserNoteEditActivity;

public class UserNoteListActivity extends AppCompatActivity implements UserNoteListView{
    @BindView(R.id.userNoteListView) ListView notesList;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserNoteListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserNoteListPresenter(this);
        setContentView(R.layout.activity_user_note_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.notes);
        showUserNotesList(presenter.getUserNotesList());
    }

    private void showUserNotesList(ArrayList content) {
        UserNoteAdapter adapter = new UserNoteAdapter(content, this);
        notesList.setAdapter(adapter);
    }

    @OnClick(R.id.userNoteAddButton)
    public void navigateToUserNoteEditActivity(){
        Intent intent = new Intent(this, UserNoteEditActivity.class);
        intent.putExtra("noteId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
package lewczyk.pracainzynierska.UserFeatures.UserNoteDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.UserNoteEdit.UserNoteEditActivity;
import lewczyk.pracainzynierska.UserFeatures.UserNoteList.UserNoteListActivity;

public class UserNoteDetailsActivity extends AppCompatActivity implements UserNoteDetailsView, UserNoteDetailsNavigator{
    @BindView(R.id.noteDetailsWebView) WebView noteDetails;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private UserNoteDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserNoteDetailsPresenter(this, this);
        setContentView(R.layout.activity_user_note_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadUserNote();
        setTitle(R.string.note_details);
        if(validateNoteId()){
            loadViewContent();
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("noteId", DEFAULT_ID);
    }

    private void loadViewContent() {
        String htmlParam = "<html><body style=\"text-align:justify;column-fill: balance;column-count: 1;column-width: 50px\"> %s </body></Html>";
        if(validateNoteId()){
            noteDetails.loadData(String.format(htmlParam, presenter.getNoteText()), "text/html", "utf-8");
        } else {
            noteDetails.loadData(String.format(htmlParam, getString(R.string.no_data)), "text/html", "utf-8");
        }
    }

    @OnClick(R.id.noteDetailsModButton)
    public void navigateToUserNoteEditActivity(){
        if(validateNoteId()){
            Intent intent = new Intent(this, UserNoteEditActivity.class);
            intent.putExtra("noteId", presenter.getNoteId());
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.noteDetailsDelButton)
    public void deleteNoteButtonPressed(){
        if(validateNoteId()){
            presenter.deleteCurrentNote();
        }
    }

    private boolean validateNoteId(){
        return presenter.validateId();
    }

    @Override
    public void onBackPressed(){
        navigateToUserNoteListActivity();
    }

    @Override
    public void navigateToUserNoteListActivity(){
        Intent intent = new Intent(this, UserNoteListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
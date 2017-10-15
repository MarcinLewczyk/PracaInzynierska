package lewczyk.pracainzynierska.UserFeatures.ArchiveList;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewczyk.pracainzynierska.Adapters.ExerciseArchiveAdapter;
import lewczyk.pracainzynierska.R;

public class ArchiveListActivity extends AppCompatActivity implements ArchiveListView{
    @BindView(R.id.exerciseArchiveListView) ListView archiveList;
    private ArchiveListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ArchiveListPresenter(this);
        setContentView(R.layout.activity_archive_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.archive);
        showArchiveList(presenter.getArchiveList());
    }

    private void showArchiveList(ArrayList content) {
        ExerciseArchiveAdapter adapter = new ExerciseArchiveAdapter(content, this);
        archiveList.setAdapter(adapter);
    }

    @Override
    public Context getContext(){
        return this;
    }
}
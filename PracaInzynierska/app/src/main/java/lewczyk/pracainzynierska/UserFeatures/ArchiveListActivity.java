package lewczyk.pracainzynierska.UserFeatures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import lewczyk.pracainzynierska.Adapters.ExerciseArchiveAdapter;
import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.MainMenus.UserMainMenuActivity;
import lewczyk.pracainzynierska.R;

public class ArchiveListActivity extends AppCompatActivity {
    @BindView(R.id.exerciseArchiveListView) ListView archiveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_list);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(R.string.archive);
        loadArchiveList();
    }

    private void loadArchiveList() {
        ArrayList<ExerciseArchive> exerciseArchives = (ArrayList)ExerciseArchiveRepository.findAll(this);
        Collections.reverse(exerciseArchives);
        ExerciseArchiveAdapter adapter = new ExerciseArchiveAdapter(exerciseArchives, this);
        archiveList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserMainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
package lewczyk.pracainzynierska.UserFeatures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import lewczyk.pracainzynierska.Adapters.ExerciseArchiveAdapter;
import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.R;

public class ArchiveListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_list);
        setTitle(R.string.archive);

        ListView archiveList = (ListView) findViewById(R.id.exerciseArchiveListView);

        ArrayList<ExerciseArchive> exerciseArchives = (ArrayList)ExerciseArchiveRepository.findAll(this);
        Collections.reverse(exerciseArchives);
        ExerciseArchiveAdapter adapter = new ExerciseArchiveAdapter(exerciseArchives, this);
        archiveList.setAdapter(adapter);
    }
}
package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.BodyParameterArchiveAdapter;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;
import lewczyk.pracainzynierska.R;

public class BodyParameterDetailsActivity extends AppCompatActivity {
    private long parameterId;
    private String msg;
    @BindView(R.id.bodyParameterTextView)
    TextView parameterTitle;
    @BindView(R.id.bodyParameterCurrTextView)
    TextView parameterState;
    ForeignCollection<BodyParameterArchive> archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parameter_details);
        Intent intent = getIntent();
        parameterId = intent.getLongExtra("parameterId", -1);

        ButterKnife.bind(this);

        loadStrings();

    }

    private void loadStrings() {
        msg  = getString(R.string.no_data);
        setTitle(getString(R.string.parameter_detail));

        if(parameterId == -1){
            parameterTitle.setText(msg);
        } else {
            BodyParameter tmp = BodyParameterRepository.findById(this, parameterId);
            parameterTitle.setText(tmp.getMuscleName());
            parameterState.setText(Double.toString(tmp.getCircumference()));
            archive = tmp.getParametersArchive();
            ArrayList<BodyParameterArchive> toAdapter = new ArrayList<>();
            for(BodyParameterArchive e: archive){
                toAdapter.add(e);
            }
            Collections.reverse(toAdapter);
            ListView bodyParametersArchiveListView = (ListView) findViewById(R.id.bodyParameterArchiveListView);
            BodyParameterArchiveAdapter adapter = new BodyParameterArchiveAdapter(toAdapter, this);
            bodyParametersArchiveListView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.bodyParameterModButton)
    public void moveToBodyParameterEdit(){
        if(parameterId != -1){
            Intent intent = new Intent(getApplicationContext(), BodyParameterEditActivity.class);
            intent.putExtra("parameterId", parameterId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.bodyParameterDelButton)
    public void delBodyParameter(){
        if(parameterId != -1){
            BodyParameterRepository.deleteBodyParameter(getApplicationContext(), BodyParameterRepository.findById(this, parameterId));
            Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
        startActivity(intent);
        finish();
    }
}
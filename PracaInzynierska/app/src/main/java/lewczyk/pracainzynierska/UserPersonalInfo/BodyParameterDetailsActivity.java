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
    private int DEFAULT_ID = -1;
    private long parameterId;
    @BindView(R.id.bodyParameterTextView) TextView parameterTitle;
    @BindView(R.id.bodyParameterCurrTextView) TextView parameterState;
    @BindView(R.id.bodyParameterArchiveListView) ListView archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parameter_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        loadViewContent();
    }

    private void loadIntent() {
        Intent intent = getIntent();
        parameterId = intent.getLongExtra("parameterId", DEFAULT_ID);
    }

    private void loadViewContent() {
        setTitle(getString(R.string.parameter_detail));
        if(!validateParameter()){
            parameterTitle.setText(getString(R.string.no_data));
        } else {
            BodyParameter loadedBodyParameter = loadBodyParameter();
            parameterTitle.setText(loadedBodyParameter.getMuscleName());
            parameterState.setText(Double.toString(loadedBodyParameter.getCircumference()));
            loadArchiveList(loadedBodyParameter);
        }
    }

    private BodyParameter loadBodyParameter() {
        return BodyParameterRepository.findById(this, parameterId);
    }

    private void loadArchiveList(BodyParameter loadedBodyParameter) {
        ForeignCollection<BodyParameterArchive> bodyParameterArchive = loadedBodyParameter.getParametersArchive();
        BodyParameterArchiveAdapter adapter = new BodyParameterArchiveAdapter(getReversedArchive(bodyParameterArchive), this);
        archive.setAdapter(adapter);
    }

    private ArrayList getReversedArchive(ForeignCollection<BodyParameterArchive> bodyParameterArchive) {
        ArrayList<BodyParameterArchive> listToReverse = new ArrayList<>();
        for(BodyParameterArchive e: bodyParameterArchive){
            listToReverse.add(e);
        }
        Collections.reverse(listToReverse);
        return listToReverse;
    }

    @OnClick(R.id.bodyParameterModButton)
    public void modificationButtonPressed(){
        if(validateParameter()){
            Intent intent = new Intent(this, BodyParameterEditActivity.class);
            intent.putExtra("parameterId", parameterId);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.bodyParameterDelButton)
    public void deleteButtonPressed(){
        if(validateParameter()){
            deleteCurrentBodyParameter();
            moveToBodyParametersList();
        }
    }

    private boolean validateParameter(){
        return parameterId != DEFAULT_ID;
    }

    private void deleteCurrentBodyParameter() {
        BodyParameterRepository.deleteBodyParameter(this, BodyParameterRepository.findById(this, parameterId));
    }


    @Override
    public void onBackPressed(){
        moveToBodyParametersList();
    }

    private void moveToBodyParametersList(){
        Intent intent = new Intent(this, BodyParameterListActivity.class);
        startActivity(intent);
        finish();
    }
}
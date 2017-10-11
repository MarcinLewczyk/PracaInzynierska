package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.BodyParameterArchiveAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterEditActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

public class BodyParameterDetailsActivity extends AppCompatActivity implements BodyParameterDetailsView, BodyParameterDetailsNavigator{
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private long parameterId;
    private BodyParameterDetailsPresenter presenter;
    @BindView(R.id.bodyParameterTextView) TextView parameterTitle;
    @BindView(R.id.bodyParameterCurrTextView) TextView parameterState;
    @BindView(R.id.bodyParameterArchiveListView) ListView archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BodyParameterDetailsPresenter(this);
        setContentView(R.layout.activity_body_parameter_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        setActivityTitle(presenter.getTitle());
        loadViewContent();
    }

    @Override
    public void setActivityTitle(String title){
        setTitle(title);
    }

    @Override
    public void loadIntent() {
        Intent intent = getIntent();
        parameterId = intent.getLongExtra("parameterId", DEFAULT_ID);
    }

    @Override
    public void loadViewContent() {
        if(validateParameter()){
            presenter.getBodyParameter(parameterId);
            parameterTitle.setText(presenter.getParametersMuscleName());
            parameterState.setText(presenter.getParametersCircumference());
            showArchiveList(presenter.getBodyParameterArchive());
        } else {
            parameterTitle.setText(getString(R.string.no_data));
        }
    }

    @Override
    public void showArchiveList(ArrayList content) {
        BodyParameterArchiveAdapter adapter = new BodyParameterArchiveAdapter(content, this);
        archive.setAdapter(adapter);
    }

    @Override
    @OnClick(R.id.bodyParameterModButton)
    public void navigateToBodyParameterEditActivity(){
        if(validateParameter()){
            Intent intent = new Intent(this, BodyParameterEditActivity.class);
            intent.putExtra("parameterId", parameterId);
            startActivity(intent);
            finish();
        }
    }

    @Override
    @OnClick(R.id.bodyParameterDelButton)
    public void navigateBackDeleteButtonPressed(){
        if(validateParameter()){
            presenter.deleteGivenBodyParameter(parameterId);
            navigateBack();
        }
    }

    private boolean validateParameter(){
        return presenter.validateId(parameterId);
    }

    @Override
    public void onBackPressed(){
        navigateBack();
    }

    @Override
    public void navigateBack() {
        Intent intent = new Intent(this, BodyParametersListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
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
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterEdit.BodyParameterEditActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

public class BodyParameterDetailsActivity extends AppCompatActivity implements BodyParameterDetailsView, BodyParameterDetailsNavigator{
    @BindView(R.id.bodyParameterTextView) TextView parameterTitle;
    @BindView(R.id.bodyParameterCurrTextView) TextView parameterState;
    @BindView(R.id.bodyParameterArchiveListView) ListView archive;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private BodyParameterDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BodyParameterDetailsPresenter(this, this);
        setContentView(R.layout.activity_body_parameter_details);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadBodyParameter();
        setTitle(getString(R.string.parameter_detail));
        loadViewContent();
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("parameterId", DEFAULT_ID);
    }

    private void loadViewContent() {
        if(validateParameter()){
            parameterTitle.setText(presenter.getParametersMuscleName());
            parameterState.setText(presenter.getParametersCircumference());
            showArchiveList(presenter.getBodyParameterArchive());
        } else {
            parameterTitle.setText(getString(R.string.no_data));
        }
    }

    private void showArchiveList(ArrayList content) {
        BodyParameterArchiveAdapter adapter = new BodyParameterArchiveAdapter(content, this);
        archive.setAdapter(adapter);
    }

    @OnClick(R.id.bodyParameterModButton)
    public void navigateToBodyParameterEditActivity(){
        if(validateParameter()){
            Intent intent = new Intent(this, BodyParameterEditActivity.class);
            intent.putExtra("parameterId", presenter.getParameterId());
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.bodyParameterDelButton)
    public void navigateBackDeleteButtonPressed(){
        if(validateParameter()){
            presenter.deleteCurrentBodyParameter();
        }
    }

    private boolean validateParameter(){
        return presenter.validateId();
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
        return this;
    }
}
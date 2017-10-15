package lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

public class BodyParameterEditActivity extends AppCompatActivity implements BodyParameterEditView, BodyParameterEditNavigator{
    @BindView(R.id.bodyParameterEditText) EditText parameterName;
    @BindView(R.id.bodyParameterCurrentEditText) EditText parameterState;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int PARAMETERS_MINIMUM_LENGTH = 0;
    private int PARAMETER_STATE_MAX_LENGTH = 10;
    private int PARAMETER_NAME_MAX_LENGTH = 100;
    private BodyParameterEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BodyParameterEditPresenter(this, this);
        setContentView(R.layout.activity_body_parameter_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        presenter.loadBodyParameter();
        if(validateParameterId()){
            setTitle(getString(R.string.edit_parameter));
            loadViewContent();
        } else {
            setTitle(getString(R.string.add_new_body_parameter));
        }
    }

    @Override
    public long loadIntent() {
        Intent intent = getIntent();
        return intent.getLongExtra("parameterId", DEFAULT_ID);
    }

    private void loadViewContent(){
        parameterName.setText(presenter.getParametersMuscleName());
        parameterState.setText(presenter.getParametersCircumference());
    }

    @OnClick(R.id.bodyParameterEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validateParameterId()){
            try{
                addBodyParameter();
            }catch(NumberFormatException e){
                parameterState.setError(getString(R.string.must_be_floating_point));
            }
        } else if(validateFields() && validateParameterId()){
            try{
                updateBodyParameter();
            } catch (NumberFormatException e){
                parameterState.setError(getString(R.string.must_be_floating_point));
            }
        }
    }

    private void addBodyParameter(){
        presenter.addBodyParameter();
    }

    private void updateBodyParameter(){
        presenter.updateBodyParameter();
    }

    @Override
    public double getParameterStateEditText(){
        return Double.parseDouble(parameterState.getText().toString());
    }

    @Override
    public String getParameterNameStringEditText(){
        return parameterName.getText().toString();
    }

    private boolean validateParameterId() {
        return presenter.validateId();
    }

    private boolean validateFields() {
        return validateParameterName() && validateParameterState();
    }

    private boolean validateParameterState() {
        if(parameterState.getText().length() == PARAMETERS_MINIMUM_LENGTH){
            parameterState.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(parameterState.getText().length() >= PARAMETER_STATE_MAX_LENGTH) {
            parameterState.setError(getString(R.string.less_than_10));
            return false;
        } else {
            parameterState.setError(null);
            return true;
        }
    }

    private boolean validateParameterName() {
        if(parameterName.getText().length() == PARAMETERS_MINIMUM_LENGTH){
            parameterName.setError(getString(R.string.more_than_0_characters_required));
            return false;
        } else if(parameterName.getText().length() >= PARAMETER_NAME_MAX_LENGTH) {
            parameterName.setError(getString(R.string.less_than_100));
            return false;
        } else {
            parameterName.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        navigateToBodyParameterList();
    }

    @Override
    public void navigateToBodyParameterList(){
        Intent intent = new Intent(this, BodyParametersListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

public class BodyParameterEditActivity extends AppCompatActivity {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private int PARAMETERS_MINIMUM_LENGTH = 0;
    private int PARAMETER_STATE_MAX_LENGTH = 10;
    private int PARAMETER_NAME_MAX_LENGTH = 100;
    private long parameterId;
    @BindView(R.id.bodyParameterEditText) EditText parameterName;
    @BindView(R.id.bodyParameterCurrentEditText) EditText parameterState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parameter_edit);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        loadIntent();
        if(!validateParameterId()){
            setTitle(R.string.add_new_body_parameter);
        } else {
            setTitle(getString(R.string.edit_parameter));
            BodyParameter bodyParameter = loadBodyParameter();
            parameterName.setText(bodyParameter.getMuscleName());
            parameterState.setText(Double.toString(bodyParameter.getCircumference()));
        }
    }

    private BodyParameter loadBodyParameter() {
        return BodyParameterRepository.findById(this, parameterId);
    }

    private boolean validateParameterId() {
        return parameterId != DEFAULT_ID;
    }

    private void loadIntent() {
        Intent intent = getIntent();
        parameterId = intent.getLongExtra("parameterId", DEFAULT_ID);
    }

    @OnClick(R.id.bodyParameterEditSaveButton)
    public void saveButtonPressed(){
        if(validateFields() && !validateParameterId()){
            try{
                addBodyParameter();
                moveToBodyParameterListActivity();
            }catch(NumberFormatException e){
                parameterState.setError(getString(R.string.must_be_floating_point));
            }
        } else if(validateFields() && validateParameterId()){
            try{
                updateBodyParameter();
                moveToBodyParameterListActivity();
            } catch (NumberFormatException e){
                parameterState.setError(getString(R.string.must_be_floating_point));
            }
        }
    }

    private void addBodyParameter() {
        BodyParameterRepository.addBodyParameter(this,
                new BodyParameter(parameterName.getText().toString(),
                        Double.parseDouble(parameterState.getText().toString())));
    }

    private void updateBodyParameter(){
        BodyParameter edited = loadBodyParameter();
        edited.getParametersArchive().add(new BodyParameterArchive(edited.getCircumference(), edited));
        edited.setMuscleName(parameterName.getText().toString());
        edited.setCircumference(Double.parseDouble(parameterState.getText().toString()));
        BodyParameterRepository.updateBodyParameter(this, edited);
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
        moveToBodyParameterListActivity();
    }

    private void moveToBodyParameterListActivity(){
        Intent intent = new Intent(this, BodyParametersListActivity.class);
        startActivity(intent);
        finish();
    }
}
package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;
import lewczyk.pracainzynierska.R;

public class BodyParameterEditActivity extends AppCompatActivity {
    private long parameterId;
    @BindView(R.id.bodyParameterEditText)
    EditText parameterName;
    @BindView(R.id.bodyParameterCurrentEditText)
    EditText parameterState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parameter_edit);
        Intent intent = getIntent();
        parameterId = intent.getLongExtra("parameterId", -1);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        if(parameterId == -1){
            setTitle(R.string.add_new_body_parameter);
        } else {
            setTitle(getString(R.string.edit_parameter));
            BodyParameter bodyparameter = BodyParameterRepository.findById(getApplicationContext(), parameterId);
            parameterName.setText(bodyparameter.getMuscleName());
            parameterState.setText(Double.toString(bodyparameter.getCircumference()));
        }
    }

    @OnClick(R.id.bodyParameterEditSaveButton)
    public void save(){
        validateFields();
        if(validate() && parameterId == -1){
            try{
                BodyParameterRepository.addBodyParameter(getApplicationContext(),
                        new BodyParameter(parameterName.getText().toString(), Double.parseDouble(parameterState.getText().toString())));
                Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
                startActivity(intent);
                finish();
            }catch(NumberFormatException e){
                parameterState.setError(getString(R.string.must_be_floating_point));
            }

        } else if(validate() && parameterId != -1){
            try{
                BodyParameter edited = BodyParameterRepository.findById(this, parameterId);
                edited.getParametersArchive().add(new BodyParameterArchive(edited.getCircumference(), edited));
                edited.setMuscleName(parameterName.getText().toString());
                edited.setCircumference(Double.parseDouble(parameterState.getText().toString()));
                BodyParameterRepository.updateBodyParameter(getApplicationContext(), edited);
                Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
                startActivity(intent);
                finish();
            } catch (NumberFormatException e){
                parameterState.setError(getString(R.string.must_be_floating_point));
            }
        }
    }

    private void validateFields() {
        if(parameterName.length() == 0){
            parameterName.setError(getString(R.string.more_than_0_characters_required));
        } else if(parameterName.length() >= 100) {
            parameterName.setError(getString(R.string.less_than_100));
        } else {
            parameterName.setError(null);
        }
        if(parameterState.length() == 0){
            parameterState.setError(getString(R.string.more_than_0_characters_required));
        } else if(parameterState.length() >= 10) {
            parameterState.setError(getString(R.string.less_than_10));
        } else {
            parameterState.setError(null);
        }
    }

    private boolean validate(){
        return parameterName.length() > 0 && parameterState.length() > 0 && parameterName.length() < 100 && parameterState.length() < 10;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
        startActivity(intent);
        finish();
    }
}
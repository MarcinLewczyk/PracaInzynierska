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

        ButterKnife.bind(this);
        Intent intent = getIntent();
        parameterId = intent.getLongExtra("parameterId", -1);

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
        if(parameterName.toString().isEmpty()){
            parameterName.setError(getString(R.string.more_than_0_characters_required));
        } else {
            parameterName.setError(null);
        }
        if(parameterState.toString().isEmpty()){
            parameterState.setError(getString(R.string.more_than_0_characters_required));
        } else {
            parameterState.setError(null);
        }

        if(parameterName.length() > 0 && parameterState.length() > 0 && parameterId == -1){
            BodyParameterRepository.addBodyParameter(getApplicationContext(),
                    new BodyParameter(parameterName.toString(), Double.parseDouble(parameterState.toString())));
        } else if(parameterName.length() > 0 && parameterState.length() > 0 && parameterId != -1){
            BodyParameter edited = BodyParameterRepository.findById(this, parameterId);
            edited.setMuscleName(parameterName.getText().toString());
            edited.setCircumference(Double.parseDouble(parameterState.toString()));
            BodyParameterRepository.updateBodyParameter(getApplicationContext(), edited);
        }
        Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), BodyParameterListActivity.class);
        startActivity(intent);
        finish();
    }
}
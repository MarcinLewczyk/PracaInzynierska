package lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.BodyParameterAdapter;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterEdit.BodyParameterEditActivity;

public class BodyParametersListActivity extends AppCompatActivity implements BodyParametersListView{
    @BindView(R.id.bodyParametersListView) ListView bodyParameterListView;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private BodyParametersListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BodyParametersListPresenter(this);
        setContentView(R.layout.activity_body_parameter_list);
        ButterKnife.bind(this);
        setViewSettings();
    }

    private void setViewSettings() {
        setTitle(getString(R.string.parameters));
        showBodyParameterList(presenter.getBodyParametersList());
    }

    private void showBodyParameterList(ArrayList content){
        BodyParameterAdapter adapter = new BodyParameterAdapter(content, this);
        bodyParameterListView.setAdapter(adapter);
    }

    @OnClick(R.id.bodyParameterAddButton)
    public void navigateToBodyParameterEditActivity(){
        Intent intent = new Intent(this, BodyParameterEditActivity.class);
        intent.putExtra("parameterId", DEFAULT_ID);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
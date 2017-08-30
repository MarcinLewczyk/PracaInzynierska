package lewczyk.pracainzynierska.UserPersonalInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Adapters.BodyParameterAdapter;
import lewczyk.pracainzynierska.Database.BodyParameterRepository;
import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.R;

public class BodyParameterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parameter_list);
        setTitle(getString(R.string.parameters));

        ButterKnife.bind(this);

        ListView bodyParameterListView = (ListView) findViewById(R.id.bodyParameterListView);

        ArrayList<BodyParameter> bodyParametersList = (ArrayList) BodyParameterRepository.findAll(this);
        BodyParameterAdapter adapter = new BodyParameterAdapter(bodyParametersList, this);
        bodyParameterListView.setAdapter(adapter);
    }

    @OnClick(R.id.bodyParameterAddButton)
    public void moveToEditBodyParameter(){
        Intent intent = new Intent(getApplicationContext(), BodyParameterEditActivity.class);
        intent.putExtra("parameterId", -1L);
        startActivity(intent);
        finish();
    }
}
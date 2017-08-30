package lewczyk.pracainzynierska.UserPersonalInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lewczyk.pracainzynierska.R;

public class BodyParameterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parameter_list);
        setTitle(getString(R.string.parameters));
    }
}

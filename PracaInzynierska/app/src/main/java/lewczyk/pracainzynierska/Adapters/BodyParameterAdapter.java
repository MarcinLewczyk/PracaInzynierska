package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.DatabaseTables.BodyParameter;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParameterDetails.BodyParameterDetailsActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.BodyParametersList.BodyParametersListActivity;

public class BodyParameterAdapter extends ArrayAdapter<BodyParameter> {
    private Context context;

    public BodyParameterAdapter(ArrayList<BodyParameter> bodyParameter, Context context) {
        super(context, R.layout.single_list_text_view, bodyParameter);
        this.context = context;
    }

    private static class ViewHolder{
        TextView bodyParameterTextView;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BodyParameter dataModel = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.bodyParameterTextView = convertView.findViewById(R.id.listTitleTextView);
            viewHolder.layout = convertView.findViewById(R.id.notesLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getMuscleName().length() >= 25){
            viewHolder.bodyParameterTextView.setText(dataModel.getMuscleName().substring(0,25));
        } else {
            viewHolder.bodyParameterTextView.setText(dataModel.getMuscleName());
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BodyParameterDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("parameterId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((BodyParametersListActivity)context).finish();
            }
        });
        return convertView;
    }
}
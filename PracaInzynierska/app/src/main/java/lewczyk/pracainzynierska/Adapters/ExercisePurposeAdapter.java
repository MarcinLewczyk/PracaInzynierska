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

import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeDetailsActivity;
import lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeListActivity;

public class ExercisePurposeAdapter extends ArrayAdapter<ExercisePurpose> {
    private Context context;

    public ExercisePurposeAdapter(ArrayList<ExercisePurpose> exercisePurposes, Context context){
        super(context, R.layout.single_list_text_view,exercisePurposes);
        this.context = context;
    }

    private static class ViewHolder{
        TextView exercisePurposeTextView;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ExercisePurpose dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.exercisePurposeTextView = convertView.findViewById(R.id.listTitleTextView);
            viewHolder.layout = convertView.findViewById(R.id.notesLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getExercisePurpose().length() >= 25){
            viewHolder.exercisePurposeTextView.setText(dataModel.getExercisePurpose().substring(0,25));
        } else {
            viewHolder.exercisePurposeTextView.setText(dataModel.getExercisePurpose());
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExercisePurposeDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("purposeId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((ExercisePurposeListActivity)context).finish();
            }
        });
        return convertView;
    }
}
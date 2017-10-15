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

import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExercisePlanExerciseList.UserExercisePlanExerciseListActivity;
import lewczyk.pracainzynierska.UserExercise.UserExercisePlanList.UserExercisePlanListActivity;

public class TrainingPlanAdapter extends ArrayAdapter<TrainingPlan> {
    private Context context;

    public TrainingPlanAdapter(ArrayList<TrainingPlan> plans, Context context){
        super(context, R.layout.single_list_text_view, plans);
        this.context = context;
    }

    private static class ViewHolder{
        TextView planName;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TrainingPlan dataModel = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.planName = convertView.findViewById(R.id.listTitleTextView);
            viewHolder.layout = convertView.findViewById(R.id.notesLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getName().length() >= 25){
            viewHolder.planName.setText(dataModel.getName().substring(0,25));
        } else {
            viewHolder.planName.setText(dataModel.getName());
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserExercisePlanExerciseListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("planId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((UserExercisePlanListActivity)context).finish();
            }
        });
        return convertView;
    }
}
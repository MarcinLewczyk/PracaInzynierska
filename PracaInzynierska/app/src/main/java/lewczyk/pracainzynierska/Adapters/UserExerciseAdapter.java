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

import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseDetailActivity;
import lewczyk.pracainzynierska.UserExercise.UserExerciseListActivity;

public class UserExerciseAdapter extends ArrayAdapter<Exercise> {
    private Context context;

    public UserExerciseAdapter(ArrayList<Exercise> exercises, Context context){
        super(context, R.layout.single_list_text_view, exercises);
        this.context = context;
    }

    private static class ViewHolder{
        TextView exerciseDesc;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Exercise dataModel = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.exerciseDesc = convertView.findViewById(R.id.listTitleTextView);
            viewHolder.layout = convertView.findViewById(R.id.notesLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getExerciseName().length() >= 25){
            viewHolder.exerciseDesc.setText(dataModel.getExerciseName().substring(0,25));
        } else {
            viewHolder.exerciseDesc.setText(dataModel.getExerciseName());
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserExerciseDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("exerciseId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((UserExerciseListActivity)context).finish();
            }
        });
        return convertView;
    }
}
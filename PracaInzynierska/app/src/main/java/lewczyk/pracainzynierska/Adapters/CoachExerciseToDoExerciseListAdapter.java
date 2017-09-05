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

import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNewActivity;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoNewExerciseDetailActivity;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoExerciseListAdapter extends ArrayAdapter<Exercise> {
    private Context context;

    public CoachExerciseToDoExerciseListAdapter(ArrayList<Exercise> filteredExerciseToDo, Context context){
        super(context, R.layout.single_list_text_view, filteredExerciseToDo);
        this.context = context;
    }

    private static class ViewHolder{
        LinearLayout layout;
        TextView exerciseDesc;
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
                Intent intent = new Intent(view.getContext(), CoachExerciseToDoNewExerciseDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("exerciseToDoId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((CoachExerciseToDoNewActivity)context).finish();
            }
        });
        return convertView;
    }
}
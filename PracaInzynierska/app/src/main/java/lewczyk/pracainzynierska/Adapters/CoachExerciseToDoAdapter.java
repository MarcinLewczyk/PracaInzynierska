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

import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoListActivity;
import lewczyk.pracainzynierska.CoachExercise.CoachExerciseToDoDetailActivity;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;

public class CoachExerciseToDoAdapter extends ArrayAdapter<ExerciseToDo>{
    private Context context;

    public CoachExerciseToDoAdapter(ArrayList<ExerciseToDo> bodyParameter, Context context){
        super(context, R.layout.double_list_text_view, bodyParameter);
        this.context = context;
    }

    private static class ViewHolder{
        TextView exerciseTextView;
        TextView dateTextView;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ExerciseToDo dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.double_list_text_view, parent, false);
            viewHolder.exerciseTextView = convertView.findViewById(R.id.listTitleDoubleTextView);
            viewHolder.dateTextView = convertView.findViewById(R.id.listSecondPlaceDoubleTextView);
            viewHolder.layout = convertView.findViewById(R.id.doubleListLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Exercise exercise = ExerciseRepository.findById(context, dataModel.getExercise().getId());
        if(exercise.getExerciseName().length() >= 25){
            viewHolder.exerciseTextView.setText(exercise.getExerciseName().substring(0,25));
        } else {
            viewHolder.exerciseTextView.setText(exercise.getExerciseName());
        }
        String datePreFormat = dataModel.getDate();
        if(datePreFormat.length() == 0){
            viewHolder.dateTextView.setText("");
        } else {
            viewHolder.dateTextView.setText(datePreFormat.substring(0,4)+"."+datePreFormat.substring(4,6)+"."+datePreFormat.substring(6,8));
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CoachExerciseToDoDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("exerciseToDoId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((CoachExerciseToDoListActivity)context).finish();
            }
        });
        return convertView;
    }
}
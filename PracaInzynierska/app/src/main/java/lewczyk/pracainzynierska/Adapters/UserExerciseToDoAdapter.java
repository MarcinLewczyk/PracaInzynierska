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

import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseToDo;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseToDoDetailActivity;
import lewczyk.pracainzynierska.UserExercise.UserExerciseToDoListActivity;

public class UserExerciseToDoAdapter extends ArrayAdapter<ExerciseToDo>{
    private Context context;

    public UserExerciseToDoAdapter(ArrayList<ExerciseToDo> plans, Context context){
        super(context, R.layout.double_list_text_view, plans);
        this.context = context;
    }

    private static class ViewHolder{
        TextView exerciseName;
        TextView exerciseDate;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ExerciseToDo dataModel = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.double_list_text_view, parent, false);
            viewHolder.exerciseName = convertView.findViewById(R.id.listTitleDoubleTextView);
            viewHolder.exerciseDate = convertView.findViewById(R.id.listSecondPlaceDoubleTextView);
            viewHolder.layout = convertView.findViewById(R.id.doubleListLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Exercise exercise = ExerciseRepository.findById(context, dataModel.getExercise().getId());
        if(exercise.getExerciseName().length() >= 25){
            viewHolder.exerciseName.setText(exercise.getExerciseName().substring(0,25));
        } else {
            viewHolder.exerciseName.setText(exercise.getExerciseName());
        }
        String datePreFormat = dataModel.getDate();
        if(datePreFormat.length() > 0){
            viewHolder.exerciseDate.setText(transformStringDateToDateFormat(datePreFormat));
        } else if(datePreFormat.length() == 0){
            viewHolder.exerciseDate.setText(R.string.any);
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserExerciseToDoDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("exerciseToDoId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((UserExerciseToDoListActivity)context).finish();
            }
        });
        return convertView;
    }

    private String transformStringDateToDateFormat(String dateBeforeTransformation){
        return dateBeforeTransformation.substring(0,4)+"."+dateBeforeTransformation.substring(4,6)+"."+dateBeforeTransformation.substring(6,8);
    }
}
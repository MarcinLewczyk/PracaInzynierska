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
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.ArchiveDetailActivity;
import lewczyk.pracainzynierska.UserFeatures.ArchiveListActivity;

public class ExerciseArchiveAdapter extends ArrayAdapter<ExerciseArchive>{
    private Context context;

    public ExerciseArchiveAdapter(ArrayList<ExerciseArchive> bodyParameter, Context context){
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
        final ExerciseArchive dataModel = getItem(position);

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
        viewHolder.dateTextView.setText(datePreFormat.substring(0,4)+"."+datePreFormat.substring(4,6)+"."+datePreFormat.substring(6,8));

        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArchiveDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("exerciseArchiveId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((ArchiveListActivity)context).finish();
            }
        });
        return convertView;
    }
}
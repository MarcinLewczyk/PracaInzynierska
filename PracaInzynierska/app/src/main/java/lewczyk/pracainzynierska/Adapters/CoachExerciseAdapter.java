package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.R;

public class CoachExerciseAdapter extends ArrayAdapter<Exercise> {

    public CoachExerciseAdapter(ArrayList<Exercise> userNotes, Context context){
        super(context, R.layout.single_list_text_view, userNotes);
    }

    private static class ViewHolder{
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getExerciseName().length() >= 25){
            viewHolder.exerciseDesc.setText(dataModel.getExerciseName().substring(0,30));
        } else {
            viewHolder.exerciseDesc.setText(dataModel.getExerciseName());
        }
        return convertView;
    }
}
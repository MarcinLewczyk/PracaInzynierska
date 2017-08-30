package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.DatabaseTables.ExercisePurposeArchive;
import lewczyk.pracainzynierska.R;

public class ExercisePurposeArchiveAdapter extends ArrayAdapter<ExercisePurposeArchive> {
    private Context context;

    public ExercisePurposeArchiveAdapter(ArrayList<ExercisePurposeArchive> exercisePurposes, Context context){
        super(context, R.layout.single_list_text_view,exercisePurposes);
        this.context = context;
    }

    private static class ViewHolder{
        TextView exercisePurposeArchiveTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ExercisePurposeArchive dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.exercisePurposeArchiveTextView = convertView.findViewById(R.id.listTitleTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.exercisePurposeArchiveTextView.setText(dataModel.getOldPurpose());
        return convertView;
    }
}
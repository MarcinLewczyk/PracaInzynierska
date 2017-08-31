package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.DatabaseTables.BodyParameterArchive;
import lewczyk.pracainzynierska.R;

public class BodyParameterArchiveAdapter extends ArrayAdapter<BodyParameterArchive> {

    public BodyParameterArchiveAdapter(ArrayList<BodyParameterArchive> exercisePurposes, Context context){
        super(context, R.layout.single_list_text_view,exercisePurposes);
    }

    private static class ViewHolder{
        TextView bodyParameterArchiveTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BodyParameterArchive dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.bodyParameterArchiveTextView = convertView.findViewById(R.id.listTitleTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bodyParameterArchiveTextView.setText(Double.toString(dataModel.getOldCircumference()));
        return convertView;
    }
}
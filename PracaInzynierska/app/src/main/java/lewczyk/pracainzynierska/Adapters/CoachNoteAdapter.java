package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.CoachFeatures.CoachNoteDetailsActivity;
import lewczyk.pracainzynierska.CoachFeatures.CoachNoteListActivity;
import lewczyk.pracainzynierska.DatabaseTables.CoachNote;
import lewczyk.pracainzynierska.R;

public class CoachNoteAdapter extends ArrayAdapter<CoachNote> {
    private Context context;

    public CoachNoteAdapter(ArrayList<CoachNote> userNotes, Context context){
        super(context, R.layout.single_list_text_view, userNotes);
        this.context = context;
    }

    private static class ViewHolder{
        TextView noteDesc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CoachNote dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.noteDesc = convertView.findViewById(R.id.listTitleTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getText().length() >= 25){
            viewHolder.noteDesc.setText(dataModel.getText().substring(0,30));
        } else {
            viewHolder.noteDesc.setText(dataModel.getText());
        }

        viewHolder.noteDesc.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CoachNoteDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noteId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((CoachNoteListActivity)context).finish();
            }
        });
        return convertView;
    }
}
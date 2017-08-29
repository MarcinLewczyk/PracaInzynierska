package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.DataDetailsPresentation.NoteDetailsActivity;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;

public class UserNoteAdapter extends ArrayAdapter<UserNote>{

    public UserNoteAdapter(ArrayList<UserNote> userNotes, Context context){
        super(context, R.layout.list_notes, userNotes);
    }

    private static class ViewHolder{
        TextView noteDesc;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserNote dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_notes, parent, false);
            viewHolder.noteDesc = convertView.findViewById(R.id.noteDescTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.noteDesc.setText(dataModel.getText().substring(0,30));

        viewHolder.noteDesc.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NoteDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noteId", dataModel.getId());
                view.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
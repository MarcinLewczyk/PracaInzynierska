package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;

public class UserNoteAdapter extends ArrayAdapter<UserNote>{

    public UserNoteAdapter(ArrayList<UserNote> userNotes, Context context){
        super(context, R.layout.list_notes, userNotes);
    }
    //// TODO: 29.08.2017 clicklistenery na elementy listy

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserNote dataModel = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_notes, parent, false);
        }

        TextView noteDesc = convertView.findViewById(R.id.noteDescTextView);
        noteDesc.setText(dataModel.getText());

        return convertView;
    }
}
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

import lewczyk.pracainzynierska.UserFeatures.UserNoteDetailsActivity;
import lewczyk.pracainzynierska.DatabaseTables.UserNote;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserFeatures.UserNoteListActivity;

public class UserNoteAdapter extends ArrayAdapter<UserNote>{
    private Context context;

    public UserNoteAdapter(ArrayList<UserNote> userNotes, Context context){
        super(context, R.layout.single_list_text_view, userNotes);
        this.context = context;
    }

    private static class ViewHolder{
        TextView noteDesc;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserNote dataModel = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_text_view, parent, false);
            viewHolder.noteDesc = convertView.findViewById(R.id.listTitleTextView);
            viewHolder.layout = convertView.findViewById(R.id.notesLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(dataModel.getText().length() >= 25){
            viewHolder.noteDesc.setText(dataModel.getText().substring(0,25));
        } else {
            viewHolder.noteDesc.setText(dataModel.getText());
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserNoteDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noteId", dataModel.getId());
                view.getContext().startActivity(intent);

                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((UserNoteListActivity)context).finish();
            }
        });
        return convertView;
    }
}
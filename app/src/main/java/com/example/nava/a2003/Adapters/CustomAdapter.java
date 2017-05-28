package com.example.nava.a2003.Adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.nava.a2003.General.Event;
        import com.example.nava.a2003.My_Events.InviteActivity;
        import com.example.nava.a2003.Invited_To.OperationActivity;
        import com.example.nava.a2003.R;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.List;


/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapter extends BaseAdapter{
    Context c;
    List<Event> eventsList;
    int idOfFragment;
    DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("My Events");


    public CustomAdapter(Context c, List<Event> eventsList, int idOfFragment) {
        this.c = c;
        this.eventsList = eventsList;
        this.idOfFragment = idOfFragment;
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }
    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.nameView);
        TextView timeTxt= (TextView) convertView.findViewById(R.id.timeView);
        TextView dateTxt= (TextView) convertView.findViewById(R.id.dateView);

        final Event  event = (Event) this.getItem(position);

        nameTxt.setText(event.getName());
        dateTxt.setText(event.getDate());
        timeTxt.setText(event.getTime());

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("","go:"+event.getName());
                Log.d("FB ref",databaseEvents.getKey());
              //  Log.d("FB ref",databaseEvents);


                if(0 == idOfFragment) {
                    c.startActivity(new Intent(c,OperationActivity.class));
                    //which item is pressed? then send its id in db.
                    //send id of event to the intent

                }
                else {
                    //when an item is  pressed so get its id from db and send it in the intent
                    c.startActivity(new Intent(c,InviteActivity.class).putExtra("CurrentIdEvnet",event.getIdEvent()
                    ));

                }

            }
        });

        return convertView;
    }
}
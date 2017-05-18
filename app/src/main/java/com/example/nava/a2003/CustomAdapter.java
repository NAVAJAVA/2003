package com.example.nava.a2003;


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.ArrayList;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapter extends BaseAdapter{
    Context c;
    ArrayList<Event> eventsList;

    public CustomAdapter(Context c, ArrayList<Event> eventsList) {
        this.c = c;
        this.eventsList = eventsList;
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

        TextView nameTxt= (TextView) convertView.findViewById(R.id.nameTxt);
        //chage desc to desc and create date txt for date
        TextView dateTxt= (TextView) convertView.findViewById(R.id.descTxt);
        TextView timeTxt= (TextView) convertView.findViewById(R.id.dateTxt);

        final Event  event = (Event) this.getItem(position);

        nameTxt.setText(event.getName());
        dateTxt.setText(event.getDate());
        timeTxt.setText(event.getTime());

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,event.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
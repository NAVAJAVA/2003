package com.example.nava.a2003.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nava.a2003.General.Guest;
import com.example.nava.a2003.R;

import java.util.List;

/**
 * Created by Nava on 23/05/2017.
 */


public class GuestAdapter extends BaseAdapter {
    Context c;
    List<Guest> guestsList;

    public GuestAdapter(Context c, List<Guest> guestsList) {
        this.c = c;
        this.guestsList = guestsList;
    }

    @Override
    public int getCount() {
        return guestsList.size();
    }

    @Override
    public Object getItem(int position) {
        return guestsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.layout_guests_list,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.textViewName);
        TextView emailTxt= (TextView) convertView.findViewById(R.id.textViewEmail);

        final Guest guest = (Guest) this.getItem(position);

        nameTxt.setText(guest.getName());
        emailTxt.setText(guest.getEmail());


        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        return convertView;
    }
}

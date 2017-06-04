package com.example.nava.a2003.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nava.a2003.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class GalleryAdapter extends BaseAdapter {

        Context c;
        List<String> ImageList;


        @Override
        public int getCount () {
        return ImageList.size();
    }
        @Override
        public Object getItem ( int position){
        return ImageList.get(position);
    }

        @Override
        public long getItemId ( int position){
        return position;
    }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        }
        //hide all
        TextView nameTxt = (TextView) convertView.findViewById(R.id.nameView);
        TextView timeTxt = (TextView) convertView.findViewById(R.id.timeView);
        TextView dateTxt = (TextView) convertView.findViewById(R.id.dateView);
            nameTxt.setVisibility(View.GONE);
            timeTxt.setVisibility(View.GONE);
            dateTxt.setVisibility(View.GONE);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);

            final String stringImage = (String) this.getItem(position);

        if (stringImage != null && 0 != stringImage.compareTo("")) {
            Picasso.with(c).load(Uri.parse(stringImage)).noPlaceholder().centerCrop().fit()
                    .into(img);

        }
/*
        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 == idOfFragment) {
                    c.startActivity(new Intent(c, OperationActivity.class));
                    //which item is pressed? then send its id in db.
                    //send id of event to the intent

                } else {
                    //when an item is  pressed so get its id from db and send it in the intent
                    c.startActivity(new Intent(c, InviteActivity.class).putExtra("CurrentIdEvnet", event.getIdEvent()
                    ));

                }

            }
        });*/

        return convertView;
    }
    }
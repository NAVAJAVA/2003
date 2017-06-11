package com.example.nava.a2003.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nava.a2003.General.Image;
import com.example.nava.a2003.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ImageAdapter extends ArrayAdapter<Image> {

        Context c;
        List<Image> ImageList;

    public ImageAdapter(Context c, List<Image> imageList) {
        super(c,R.layout.fragment_gallery,imageList);
        this.c = c;
        ImageList = imageList;
    }

        @Override
        public long getItemId ( int position){
        return position;
    }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){

            convertView = LayoutInflater.from(c).inflate(R.layout.fragment_gallery, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.ImageName);
            ImageView img = (ImageView) convertView.findViewById(R.id.imgViewGalleryUPload);
            textView.setText(ImageList.get(position).getName());
            Glide.with(c).load(ImageList.get(position).getUrl()).into(img);


            return convertView;

    }
    }
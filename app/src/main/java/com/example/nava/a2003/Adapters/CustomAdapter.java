package com.example.nava.a2003.Adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.example.nava.a2003.General.Event;
        import com.example.nava.a2003.General.Image;
        import com.example.nava.a2003.My_Events.InviteActivity;
        import com.example.nava.a2003.Invited_To.OperationActivity;
        import com.example.nava.a2003.R;
        import com.squareup.picasso.NetworkPolicy;
        import com.squareup.picasso.Picasso;

        import java.util.List;



public class CustomAdapter extends BaseAdapter{
    Context c;
    List<Event> eventsList;
    int idOfFragment;

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
        ImageView img =(ImageView) convertView.findViewById(R.id.img);


        final Event event = (Event) this.getItem(position);
        nameTxt.setText(event.getName());
        dateTxt.setText(event.getDate());
        timeTxt.setText(event.getTime());
        //Picasso.with(c).load("http://www.grafix.co.il/wp-content/media/2016/09/weddinginvitationsgrafix1.jpg").into(img);
        Image image = event.getInvitaion();

        if(image!= null && 0!= image.getUrl().compareTo("")) {
            Glide.with(c).load(image.getUrl()).into(img);
            Picasso.with(c).load(image.getUrl()).noPlaceholder().centerCrop().fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(img);
/*
            Picasso.with(c).load(image.getUrl()).noPlaceholder().centerCrop().fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(img);*/
        }
        /*
        if(event.getInvitaion()!= null && 0!=event.getInvitaion().getUrl().compareTo("")) {
            Picasso.with(c).load(event.getInvitaion().getUrl()).noPlaceholder().centerCrop().fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(img);
            Glide.with(c).load(eventsList.get(position).getInvitaion().getUrl()).into(img);

        }*/

        //OnEventClick
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(0 == idOfFragment) {
                   // c.startActivity(new Intent(c,OperationActivity.class));
                    c.startActivity(new Intent(c,OperationActivity.class).putExtra("CurrentIdEvnet",event.getIdEvent()));
                }
                else {
                    //when an item is  pressed so get its id from db and send it in the intent
                    c.startActivity(new Intent(c,InviteActivity.class).putExtra("CurrentIdEvnet",event.getIdEvent()));

                }

            }
        });


      //  convertView.setLongClickable(true);
/*
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
                                               @Override
                                               public boolean onLongClick(View v) {
                                                   Log.v("long clicked","pos: " + getCount());

                                                   return false;
                                               }
                                           }
        );*/

        return convertView;
    }
}
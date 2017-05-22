package com.example.nava.a2003;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nava on 22/05/2017.
 */

/**
 * Created by Nava on 22/05/2017.
 */

public class  MyEvents extends Fragment {

//TODO: same as ListEvent class just diaplay data from differnt jason tree
    //// TODO: 22/05/2017: how to be added to a gruop in whatapp
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_events, container, false);

        return rootView;
    }

}
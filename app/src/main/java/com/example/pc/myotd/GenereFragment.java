package com.example.pc.myotd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class GenereFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GenereFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static GenereFragment newInstance(String param1, String param2) {
        GenereFragment fragment = new GenereFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static boolean uomo=false;
    private static boolean donna=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.activity_genere4, container, false);
        final RadioButton radioButton1 =(RadioButton)rootView.findViewById(R.id.uomo);
        final RadioButton radioButton2 =(RadioButton)rootView.findViewById(R.id.donna);


        ImageButton fabdx = (ImageButton) rootView.findViewById(R.id.fabdx);
        fabdx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.benvenuto_main_layout_id, new ConfermaFragment()).addToBackStack(null).commit();
                if(radioButton1.isChecked()){
                    uomo=true;
                    donna=false;}
                if(radioButton2.isChecked()){
                    uomo=false;
                    donna=true;}
            }
        });

        ImageButton fabsx = (ImageButton) rootView.findViewById(R.id.fabsx);
        fabsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.benvenuto_main_layout_id, new EtaFragment()).addToBackStack(null).commit();
            }
        });
        return rootView;
    }


    public static boolean getUomo(){
        return uomo;
    }
    public static boolean getDonna(){
        return donna;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

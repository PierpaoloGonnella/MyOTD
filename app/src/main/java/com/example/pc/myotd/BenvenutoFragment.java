package com.example.pc.myotd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BenvenutoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /*public static BenvenutoFragment newInstance(int sectionNumber) {
        BenvenutoFragment fragment = new BenvenutoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }*/

    // TODO: Rename and change types and number of parameters
    public static BenvenutoFragment newInstance(String param1, String param2) {
        BenvenutoFragment fragment = new BenvenutoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_benvenuto1, container, false);

        FileReader fr = null;
        String verifica = null;
        try {
            String filePath = getActivity().getFilesDir() + "/DATI_UTENTE.txt";
            fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            verifica = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (verifica==null) {
            ImageButton fab = (ImageButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.benvenuto_main_layout_id, new NomeFragment()).addToBackStack(null).commit();
                }
            });
        }
        else {
            Intent openArmadiodalBenvenuto = new Intent(getActivity(), MainActivity.class);
            getActivity().finish();
            startActivity(openArmadiodalBenvenuto);
        }
        return rootView;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        MenuInflater menuInflater = inflater;
        menuInflater.inflate(R.menu.weather_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

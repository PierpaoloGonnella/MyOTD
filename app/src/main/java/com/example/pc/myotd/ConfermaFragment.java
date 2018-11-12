package com.example.pc.myotd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfermaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ConfermaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ConfermaFragment newInstance(String param1, String param2) {
        ConfermaFragment fragment = new ConfermaFragment();
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
        final View rootView = inflater.inflate(R.layout.activity_conferma, container, false);
        final String nome=NomeFragment.getNome();
        final int eta=EtaFragment.getEta();
        boolean uomo=GenereFragment.getUomo();
        boolean donna=GenereFragment.getDonna();
        String sesso="";
        if(donna==true)
            sesso="woman";
        else
        if(uomo==true)
            sesso=" man";

        TextView textView=( TextView)rootView.findViewById(R.id.domanda);
        textView.setText("Welldone! Your name is " + nome + " and you are a" + sesso + " of " + eta + " years old"
                + getString(R.string.domanda));

        ImageButton fabsx = (ImageButton)rootView.findViewById(R.id.fabsx);
        fabsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.benvenuto_main_layout_id, new GenereFragment()).addToBackStack(null).commit();
            }
        });

        Button button=(Button)rootView.findViewById(R.id.conferma);
        final String finalSesso = sesso;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileWriter fw = null;

                try {
                    String filePath = getActivity().getFilesDir() + "/DATI_UTENTE.txt";
                    fw = new FileWriter(filePath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter printout = new PrintWriter(bw);
                    printout.println(nome);
                    printout.println(eta);
                    printout.println(finalSesso);
                    printout.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent openArmadio = new Intent(getActivity(), MainActivity.class);
                startActivity(openArmadio);
                getActivity().finish();
            }
        });

        DatabaseHelper myDb = new DatabaseHelper(this.getContext());

        myDb.insertData("Testa", "Cappello", "Nero", "Nero", 0, 15, 15, 7, "cappello esempio");
        myDb.insertData("Testa", "Sciarpa", "Nero", "Nero", 0, 15, 15, 7, "sciarpa esempio");
        myDb.insertData("Busto", "T-shirt", "Nero", "Nero", 0,15, 15, 7, "tshirt esempio");
        myDb.insertData("Busto", "Maglione", "Nero", "Nero", 0, 15, 15, 7, "maglione esempio");
        myDb.insertData("Busto", "Felpa", "Nero", "Nero", 0, 15, 15, 7, "felpa esempio");
        myDb.insertData("Busto", "Cappotto", "Nero", "Nero", 0, 15, 15, 7, "cappotto esempio");
        myDb.insertData("Gambe", "Pantaloni", "Nero", "Nero", 0, 15, 15, 7, "pantaloni esempio");
        myDb.insertData("Gambe", "Jeans", "Nero", "Nero", 0, 15, 15, 7, "jeans esempio");
        myDb.insertData("Piedi", "Scarpe", "Nero", "Nero", 0, 15, 15, 7, "scarpe esempio");
        myDb.insertData("Busto", "T-shirt", "Nero", "Nero", 0, 15, 15, 7, "tshirt esempio2");


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

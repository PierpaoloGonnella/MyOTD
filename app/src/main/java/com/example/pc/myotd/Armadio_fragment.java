package com.example.pc.myotd;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.myotd.DatabaseClasses.CustomCursorAdapter;
import com.example.pc.myotd.DatabaseClasses.CustomCursorLoader;
import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;
import com.example.pc.myotd.Model.Capo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Armadio_fragment extends Fragment {


    DatabaseHelper myDb;
    Cursor mCursor;
    CustomCursorLoader ccl;
    FloatingActionButton addButton;
    static ListView listView;
    static int id_pass;

    public static int getID(){return id_pass;}

    public static ListView getlistview() {return listView;}

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Armadio_fragment.
     */
    // TODO: Rename and change types and number of parameters
    //public static Armadio_fragment newInstance(String param1, String param2) {Da qua ho iniziato a mettere //
    //Armadio_fragment fragment = new Armadio_fragment();
    //Bundle args = new Bundle();
    //args.putString(ARG_PARAM1, param1);
    //args.putString(ARG_PARAM2, param2);
    //fragment.setArguments(args);
    //return fragment;
    //}qua ho finito di mettere //

    public Armadio_fragment() {
        // Required empty public constructor
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
        View rootView= inflater.inflate(R.layout.armadio_layout_fragment, container, false);
        ccl=new CustomCursorLoader(getContext());
        mCursor=ccl.loadInBackground();
        //myDb=new DatabaseHelper(getContext());
        //mCursor=myDb.getAllData();
        CustomCursorAdapter cca=new CustomCursorAdapter(getContext(),mCursor,2);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(cca);
        listView.setLongClickable(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToFirst();
                mCursor.move(position);
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.customdialog);
                dialog.setTitle(mCursor.getString(mCursor.getColumnIndex("nome_immagine")));
                int stagioni_numero = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex("stagioni4")));
                int[] stagioni_array = stagioni_e_meteo_fromint(stagioni_numero);
                String[] stagioni_strings = String_stagioni_from_array(stagioni_array);
                String lista_stagioni = stagioni_strings[0] + " " + stagioni_strings[1] + "\n" + stagioni_strings[2] + " " + stagioni_strings[3];

                int meteo_numero = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex("meteo6")));
                int[] meteo_array = stagioni_e_meteo_fromint(meteo_numero);
                String[] meteo_strings = String_meteo_from_array(meteo_array);
                String lista_meteo = meteo_strings[0] + " " + meteo_strings[1] + "\n" + meteo_strings[2] + " " + meteo_strings[3];

                int occasioni_numero = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex("occasioni3")));
                int[] occasioni_array = occasioni_from_int(occasioni_numero);
                String[] occasioni_strings = String_occasioni_from_array(occasioni_array);
                String lista_occasioni = occasioni_strings[0] + " " + occasioni_strings[1] + "\n" + occasioni_strings[2];

                TextView show_type = (TextView) dialog.findViewById(R.id.mostra_tipo);
                show_type.setText(mCursor.getString(mCursor.getColumnIndex("tipo")));
                String stringatipo = mCursor.getString(mCursor.getColumnIndex("tipo"));
                TextView show_colors = (TextView) dialog.findViewById(R.id.mostra_colori);
                show_colors.setText(mCursor.getString(mCursor.getColumnIndex("pcolor")) + " e " + mCursor.getString(mCursor.getColumnIndex("scolor")));
                TextView show_seasons = (TextView) dialog.findViewById(R.id.mostra_stagioni);
                show_seasons.setText(lista_stagioni);
                TextView show_weather = (TextView) dialog.findViewById(R.id.mostra_cond_meteo);
                show_weather.setText(lista_meteo);
                TextView show_occasion = (TextView) dialog.findViewById(R.id.mostra_occasioni);
                show_occasion.setText(lista_occasioni);
                ImageView show_picture = (ImageView) dialog.findViewById(R.id.image_preview);
                ImageView show_picture_shape = (ImageView) dialog.findViewById(R.id.image_preview_shape);
                show_picture.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + mCursor.getString(mCursor.getColumnIndex("nome_immagine")) + ".png"));

                if (stringatipo.equals("Cravatta")) {
                    //        show_picture_shape.setImageResource(R.drawable.contorno_cravatta);
                }
                if (stringatipo.equals("Cappello")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_cappello);
                }
                if (stringatipo.equals("Sciarpa")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_sciarpa);
                }
                if (stringatipo.equals("Cappotto")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_cappotto);
                }
                if (stringatipo.equals("T-shirt")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_tshirt);
                }
                if (stringatipo.equals("Maglia")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_maglia);
                }
                if (stringatipo.equals("Maglione")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_maglione);
                }
                if (stringatipo.equals("Felpa")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_felpa);
                }
                if (stringatipo.equals("Cardigan")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_cardigan);
                }
                if (stringatipo.equals("Polo")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_polo);
                }
                if (stringatipo.equals("Jeans")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_jeans);
                }
                if (stringatipo.equals("Pantaloni")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_pantaloni);
                }
                if (stringatipo.equals("Tuta")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_tuta);
                }
                if (stringatipo.equals("Pantaloncini")) {
                    //       show_picture_shape.setImageResource(R.drawable.contorno_pantaloncini);
                }
                if (stringatipo.equals("Scarpe")) {
                    show_picture_shape.setImageResource(R.drawable.contorno_scarpe);
                }
                if (stringatipo.equals("Calzini")) {
                    //       show_picture_shape.setImageResource(R.drawable.contorno_calzini);
                }
                dialog.show();
            }
        });

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { //here u set u rute
                id_pass = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex("_id")));
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
            }
        });

        addButton = (FloatingActionButton) rootView.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openAggiungiCapo = new Intent(getActivity(), Main_aggiungi_capo.class);
                startActivity(openAggiungiCapo);


            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //ho tolto il metodo onAttach

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public static int[] stagioni_e_meteo_fromint (int input) {
        int resto=0;
        int index=0;
        int[] stagioni=new int[]{0,0,0,0};

        while (input>=1)
        {
            resto=input%2;
            input/=2;
            stagioni[index]=resto;
            index=index+1;

        }
        return stagioni;
    }
    public static int[] occasioni_from_int (int input){
        int resto=0;
        int index=0;
        int[] occasioni=new int[]{0,0,0};
        while(input>=1)
        {
            resto=input%2;
            input/=2;
            occasioni[index]=resto;
            index=index+1;
        }
        return occasioni;
    }
    public String[] String_stagioni_from_array(int[] input) {
        String[] output=new String[]{"","","",""};
        if(input[0]==1)
            output[0]="Inverno";
        if(input[1]==1)
            output[1]="Autunno";
        if(input[2]==1)
            output[2]="Primavera";
        if(input[3]==1)
            output[3]="Estate";
        return output;

    }
    public String[] String_meteo_from_array(int [] input){
        String[] output=new String[]{"","","",""};
        if(input[0]==1)
            output[0]="Sole";
        if(input[1]==1)
            output[1]="Pioggia";
        if(input[2]==1)
            output[2]="Vento";
        if(input[3]==1)
            output[3]="Neve";
        return output;
    }
    public String[] String_occasioni_from_array (int[] input){
        String[] output=new String[]{"","",""};
        if(input[0]==1)
            output[0]="Sportivo";
        if(input[1]==1)
            output[1]="Casual";
        if(input[2]==1)
            output[2]="Elegante";

        return output;
    }

    @Override
    public void onResume() {
        if(Camera_fragment.getCursorfromCamera()==null){
        }
        //nothing
        else {
            mCursor=Camera_fragment.getCursorfromCamera();
        }
        super.onResume();
    }
}


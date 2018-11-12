package com.example.pc.myotd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class Aggiungi_capo_fragment extends Fragment {
    private String categoria="";
    int indice_di_riscaldamento=0;
    public TextView seekBarText;
    ImageView preview, previewMan;
    DatabaseHelper myDb; //dichiaro la variabile per il database
    public int[] stagioni=new int[]{0,0,0,0}; //array da passare alla funzione che lo codifica in un intero, poi va al database
    int stagioni_per_database=0;
    public int[] arraymeteo=new int[]{0,0,0,0}; //array da passare alla funzione che lo codifica in un intero, poi va al database
    int meteo_per_database=0;
    public int[] arrayoccasioni=new int[]{0,0,0}; //array da passare alla funzione che lo codifica in un intero, poi va al database
    int occasioni_per_database=0;

    private static String tag = "";

    private String ittenColor="";
    private String ittenColor2="";
    private static String tipo="";
    public Aggiungi_capo_fragment() {
    }

    public static String getTipo(){
        return tipo;
    }

    public static String getNome(){
        return tag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.aggiungi_capo_fragment_layout, container, false);
        myDb=new DatabaseHelper(getContext());  //istanzio il database
        preview = (ImageView)rootView.findViewById(R.id.preViewCapo);
        previewMan = (ImageView)rootView.findViewById(R.id.preViewMan);

        previewMan.setImageResource(R.drawable.avatar);
        final EditText nome = (EditText) rootView.findViewById(R.id.nomeCapo);



        final CheckBox inverno = (CheckBox)  rootView.findViewById(R.id.inverno);
        final CheckBox  primavera = (CheckBox)  rootView.findViewById(R.id.primavera);
        final CheckBox  estate = (CheckBox)  rootView.findViewById(R.id.estate);
        final CheckBox  autunno = (CheckBox)  rootView.findViewById(R.id.autunno);

        final CheckBox  sole = (CheckBox)  rootView.findViewById(R.id.sole);
        final CheckBox  pioggia = (CheckBox)  rootView.findViewById(R.id.pioggia);
        final CheckBox  vento = (CheckBox)  rootView.findViewById(R.id.vento);
        final CheckBox  neve = (CheckBox)  rootView.findViewById(R.id.neve);


        final CheckBox  casual = (CheckBox)  rootView.findViewById(R.id.casual);
        final CheckBox  sportivo = (CheckBox)  rootView.findViewById(R.id.sportivo);
        final CheckBox  elegante = (CheckBox)  rootView.findViewById(R.id.elegante);

        final Spinner spinnerColore=(Spinner)rootView.findViewById(R.id.spinnerColorePrimario);
        final Spinner spinnerColoreSecond=(Spinner)rootView.findViewById(R.id.spinnerColoreSecondario);

        final Spinner spinnerTipo=(Spinner)rootView.findViewById(R.id.spinner2);
        final Spinner spinnerCategoria=(Spinner)rootView.findViewById(R.id.spinner3);

        final ArrayAdapter<CharSequence> adapterTesta;
        adapterTesta = ArrayAdapter.createFromResource(this.getContext(), R.array.testa, android.R.layout.simple_spinner_item);
        adapterTesta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapterBusto;
        adapterBusto = ArrayAdapter.createFromResource(this.getContext(), R.array.busto, android.R.layout.simple_spinner_item);
        adapterBusto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapterGambe;
        adapterGambe = ArrayAdapter.createFromResource(this.getContext(), R.array.gambe, android.R.layout.simple_spinner_item);
        adapterGambe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapterPiedi;
        adapterPiedi = ArrayAdapter.createFromResource(this.getContext(), R.array.piedi, android.R.layout.simple_spinner_item);
        adapterPiedi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //SERVE A FAR FUNZIONARE LA SEEKBAR E A PRENDERE I SUOI VALORI.
        //I VALORI SONO CONTENUTI IN INDICE_DI_RISCALDAMENTO



        spinnerColore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ittenColor = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerColoreSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ittenColor2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo=parent.getItemAtPosition(position).toString();


                if (tipo.equals("Necktie")) {
                    //      preview.setImageResource(R.drawable.cravatta);
                }
                if (tipo.equals("Hat")) {
                    preview.setImageResource(R.drawable.cappello);
                }
                if (tipo.equals("Scarf")) {
                    preview.setImageResource(R.drawable.sciarpa);
                }
                if (tipo.equals("Coat")) {
                    preview.setImageResource(R.drawable.cappotto);
                }
                if (tipo.equals("T-shirt")) {
                    preview.setImageResource(R.drawable.tshirt);
                }
                if (tipo.equals("Shirt")) {
                    preview.setImageResource(R.drawable.maglia);
                }
                if (tipo.equals("Sweater")) {
                    preview.setImageResource(R.drawable.maglione);
                }
                if (tipo.equals("Sweatshirt")) {
                    preview.setImageResource(R.drawable.felpa);
                }
                if (tipo.equals("Cardigan")) {
                    preview.setImageResource(R.drawable.cardigan);
                }
                if (tipo.equals("Polo-shirt")) {
                    preview.setImageResource(R.drawable.polo);
                }
                if (tipo.equals("Jeans")) {
                    preview.setImageResource(R.drawable.jeans);
                }
                if (tipo.equals("Trausers")) {
                    preview.setImageResource(R.drawable.pantaloni);
                }
                if (tipo.equals("Suit")) {
                    preview.setImageResource(R.drawable.tuta);
                }
                if (tipo.equals("Pants")) {
                    //       preview.setImageResource(R.drawable.pantaloncini);
                }
                if (tipo.equals("Shoes")) {
                    preview.setImageResource(R.drawable.scarpe);
                }
                if (tipo.equals("Socks")) {
                    //       preview.setImageResource(R.drawable.calzini);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Head")) {
                    spinnerTipo.setAdapter(adapterTesta);
                    categoria = "Head";
                }
                if (parent.getItemAtPosition(position).toString().equals("Bust")) {
                    spinnerTipo.setAdapter(adapterBusto);
                    categoria = "Bust";
                }
                if (parent.getItemAtPosition(position).toString().equals("Legs")) {
                    spinnerTipo.setAdapter(adapterGambe);
                    categoria = "Legs";
                }
                if (parent.getItemAtPosition(position).toString().equals("Shoes")) {
                    spinnerTipo.setAdapter(adapterPiedi);
                    categoria = "Shoes";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        FloatingActionButton aggiungitrama = (FloatingActionButton) rootView.findViewById(R.id.aggiungitrama);
        aggiungitrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inverno.isChecked()==true)
                    stagioni[0]=1;
                if(autunno.isChecked()==true)
                    stagioni[1]=1;
                if(primavera.isChecked()==true)
                    stagioni[2]=1;
                if(estate.isChecked()==true)
                    stagioni[3]=1;
                //fine controllo checkbox stagioni
                if(sole.isChecked()==true)
                    arraymeteo[0]=1;
                if(pioggia.isChecked()==true)
                    arraymeteo[1]=1;
                if(vento.isChecked()==true)
                    arraymeteo[2]=1;
                if(neve.isChecked()==true)
                    arraymeteo[3]=1;

                //fine controllo checkbox meteo
                if(sportivo.isChecked()==true)
                    arrayoccasioni[0]=1;
                if(casual.isChecked()==true)
                    arrayoccasioni[1]=1;
                if(elegante.isChecked()==true)
                    arrayoccasioni[2]=1;
                //fine controllo checkbox occasioni
                stagioni_per_database=getDecimalStagioni(stagioni[0], stagioni[1], stagioni[2], stagioni[3]);
                meteo_per_database=getDecimalMeteo(arraymeteo[0], arraymeteo[1], arraymeteo[2], arraymeteo[3]);
                occasioni_per_database=getDecimalOccasioni(arrayoccasioni[0], arrayoccasioni[1], arrayoccasioni[2]);
                tag=nome.getText().toString();
                if ((stagioni_per_database*meteo_per_database*occasioni_per_database==0)|| (tag.isEmpty()==true)) {
                    Toast.makeText(getContext(), "Complete it before continue please", Toast.LENGTH_LONG).show();

                }
                else {
                    boolean verifica = myDb.insertData(categoria, tipo, ittenColor, ittenColor2, indice_di_riscaldamento, stagioni_per_database, meteo_per_database, occasioni_per_database, tag);
                    if (verifica == false) {
                        Toast.makeText(getContext(), "Data base error", Toast.LENGTH_LONG).show();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_aggiungi_capo_id, new Camera_fragment()).addToBackStack(null).commit();
                    }
                }
            }
        });
        return rootView;
    }
    // prendo inverno come bit meno significativo
    public int getDecimalStagioni(int inverno, int autunno, int primavera, int estate) {
        return inverno + autunno*2 + primavera*4 + estate*8;
    }
    // prendo sole come bit meno significativo
    public int getDecimalMeteo(int sole,int pioggia, int vento, int neve ) {
        return sole + pioggia*2 + vento*4 + neve*8  ;
    }
    //prendo sportivo come bit meno significativo
    public int getDecimalOccasioni(int sportivo, int casual, int elegante) {
        return sportivo+casual*2+elegante*4;
    }
}

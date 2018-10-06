package com.example.pc.myotd;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.pc.myotd.DatabaseClasses.CustomCursorLoader;
import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Manichino_activity extends Activity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper mydb;
    Cursor mCursorfeet, mCursorleg, mCursorbody3, mCursorbody2, mCursorbody1, mCursorhead2, mCursorhead1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb=new DatabaseHelper(this);
        setContentView(R.layout.manichino_layout_activity);
        ImageView man = (ImageView) findViewById(R.id.manichino);
        final ImageView body1 = (ImageView) findViewById(R.id.bodyManichino1);
        final ImageView body2 = (ImageView) findViewById(R.id.bodyManichino2);
        final ImageView body3 = (ImageView) findViewById(R.id.bodyManichino3);
        final ImageView bodyShape1 = (ImageView) findViewById(R.id.bodyShapeManichino1);
        final ImageView bodyShape2 = (ImageView) findViewById(R.id.bodyShapeManichino2);
        final ImageView bodyShape3 = (ImageView) findViewById(R.id.bodyShapeManichino3);
        final ImageView head1 = (ImageView) findViewById(R.id.headManichino1);
        final ImageView head2 = (ImageView) findViewById(R.id.headManichino2);
        final ImageView headShape1 = (ImageView) findViewById(R.id.headShapeManichino1);
        final ImageView headShape2 = (ImageView) findViewById(R.id.headShapeManichino2);
        final ImageView leg = (ImageView) findViewById(R.id.legManichino);
        final ImageView legShape = (ImageView) findViewById(R.id.legShapeManichino);
        final ImageView feet = (ImageView) findViewById(R.id.feetManichino);
        final ImageView feetShape = (ImageView) findViewById(R.id.feetShapeManichino);

        man.setImageResource(R.drawable.avatar);


        final Spinner spinnerbody1 = (Spinner) findViewById(R.id.spinnerBody1);
        final Spinner spinnerbody2 = (Spinner) findViewById(R.id.spinnerBody2);
        final Spinner spinnerbody3 = (Spinner) findViewById(R.id.spinnerBody3);
        final Spinner spinnerhead1 = (Spinner) findViewById(R.id.spinnerHead1);
        final Spinner spinnerhead2 = (Spinner) findViewById(R.id.spinnerHead2);
        final Spinner spinnerleg = (Spinner) findViewById(R.id.spinnerLeg);
        final Spinner spinnerfeet = (Spinner) findViewById(R.id.spinnerFeet);

        final Button buttonSpoglia = (Button) findViewById(R.id.buttonSpoglia);

//SPINNER HEAD1
        mCursorhead1=mydb.getDataFilteringByCategory("testa");
        final List<String> categorieshead1 = new ArrayList<String>();
        mCursorhead1.moveToFirst();
        categorieshead1.add("Niente");
        do {
            categorieshead1.add(mCursorhead1.getString(mCursorhead1.getColumnIndex("nome_immagine")));
        } while(mCursorhead1.moveToNext());

        spinnerhead1.findViewById(R.id.spinnerHead1);
        spinnerhead1.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterhead1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorieshead1);
        // Drop down layout style - list view with radio button
        dataAdapterhead1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerhead1.setAdapter(dataAdapterhead1);
        spinnerhead1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String shead1 = parent.getItemAtPosition(position).toString();
                String shead1shape;
                if(shead1.equals("Niente"))
                    head1.setImageDrawable(null);
                else {
                    head1.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + shead1 + ".png"));
                    mCursorhead1.moveToFirst();
                    mCursorhead1.move(position-1);
                    shead1shape=mCursorhead1.getString(mCursorhead1.getColumnIndex("tipo"));
                    if(shead1shape.equalsIgnoreCase("sciarpa"))
                        headShape1.setImageResource(R.drawable.contorno_sciarpa);
                    if(shead1shape.equalsIgnoreCase("cravatta")){}
                        //   headShape1.setImageResource(R.drawable.contorno_cravatta);
                    if(shead1shape.equalsIgnoreCase("cappello")){}
                        headShape1.setImageResource(R.drawable.contorno_cappello);

                            buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    feet.setImageDrawable(null);
                                    feetShape.setImageResource(0);
                                    leg.setImageDrawable(null);
                                    legShape.setImageResource(0);
                                    body1.setImageDrawable(null);
                                    bodyShape1.setImageResource(0);
                                    body2.setImageDrawable(null);
                                    bodyShape2.setImageResource(0);
                                    body3.setImageDrawable(null);
                                    bodyShape3.setImageResource(0);
                                    head1.setImageDrawable(null);
                                    headShape1.setImageResource(0);
                                    head2.setImageDrawable(null);
                                    headShape2.setImageResource(0);
                                }
                            });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//SPINNER HEAD2
        mCursorhead2=mydb.getDataFilteringByCategory("testa");
        final List<String> categorieshead2 = new ArrayList<String>();
        mCursorhead2.moveToFirst();
        categorieshead2.add("Niente");
        do {
            categorieshead2.add(mCursorhead2.getString(mCursorhead2.getColumnIndex("nome_immagine")));
        } while(mCursorhead2.moveToNext());

        spinnerhead2.findViewById(R.id.spinnerHead2);
        spinnerhead2.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterhead2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorieshead2);
        // Drop down layout style - list view with radio button
        dataAdapterhead2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerhead2.setAdapter(dataAdapterhead2);
        spinnerhead2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String shead2 = parent.getItemAtPosition(position).toString();
                String shead2shape;
                if(shead2.equals("Niente"))
                    head2.setImageDrawable(null);
                else {
                    head2.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + shead2 + ".png"));
                    mCursorhead2.moveToFirst();
                    mCursorhead2.move(position-1);
                    shead2shape=mCursorhead2.getString(mCursorhead2.getColumnIndex("tipo"));
                    if(shead2shape.equalsIgnoreCase("sciarpa"))
                          headShape2.setImageResource(R.drawable.contorno_sciarpa);
                    if(shead2shape.equalsIgnoreCase("cravatta")){}
                        //   headShape2.setImageResource(R.drawable.contorno_cravatta);
                    if(shead2shape.equalsIgnoreCase("cappello"))
                           headShape2.setImageResource(R.drawable.contorno_cappello);

                        buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                feet.setImageDrawable(null);
                                feetShape.setImageResource(0);
                                leg.setImageDrawable(null);
                                legShape.setImageResource(0);
                                body1.setImageDrawable(null);
                                bodyShape1.setImageResource(0);
                                body2.setImageDrawable(null);
                                bodyShape2.setImageResource(0);
                                body3.setImageDrawable(null);
                                bodyShape3.setImageResource(0);
                                head1.setImageDrawable(null);
                                headShape1.setImageResource(0);
                                head2.setImageDrawable(null);
                                headShape2.setImageResource(0);
                            }
                        });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //SPINNERBODY1

        mCursorbody1=mydb.getDataFilteringByCategory("busto");
        final List<String> categoriesbody1 = new ArrayList<String>();
        mCursorbody1.moveToFirst();
        categoriesbody1.add("Niente");
        do {
            categoriesbody1.add(mCursorbody1.getString(mCursorbody1.getColumnIndex("nome_immagine")));
        } while(mCursorbody1.moveToNext());

        spinnerbody1.findViewById(R.id.spinnerBody1);
        spinnerbody1.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterbody1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesbody1);
        // Drop down layout style - list view with radio button
        dataAdapterbody1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerbody1.setAdapter(dataAdapterbody1);
        spinnerbody1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sbody1 = parent.getItemAtPosition(position).toString();
                String sbody1shape;
                if(sbody1.equals("Niente")) {
                    body1.setImageDrawable(null);
                    bodyShape1.setImageResource(0);
                }
                else {
                    body1.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + sbody1 + ".png"));
                    mCursorbody1.moveToFirst();
                    mCursorbody1.move(position-1);
                    sbody1shape=mCursorbody1.getString(mCursorbody1.getColumnIndex("tipo"));
                    if(sbody1shape.equalsIgnoreCase("cappotto"))
                        bodyShape1.setImageResource(R.drawable.contorno_cappotto);
                    if(sbody1shape.equalsIgnoreCase("t-shirt"))
                        bodyShape1.setImageResource(R.drawable.contorno_tshirt);
                    if(sbody1shape.equalsIgnoreCase("maglia"))
                        bodyShape1.setImageResource(R.drawable.contorno_maglia);
                    if(sbody1shape.equalsIgnoreCase("maglione"))
                        bodyShape1.setImageResource(R.drawable.contorno_maglione);
                    if(sbody1shape.equalsIgnoreCase("felpa"))
                        bodyShape1.setImageResource(R.drawable.contorno_felpa);
                    if(sbody1shape.equalsIgnoreCase("cardigan"))
                        bodyShape1.setImageResource(R.drawable.contorno_cardigan);
                    if(sbody1shape.equalsIgnoreCase("polo"))
                        bodyShape1.setImageResource(R.drawable.contorno_polo);

                    buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            feet.setImageDrawable(null);
                            feetShape.setImageResource(0);
                            leg.setImageDrawable(null);
                            legShape.setImageResource(0);
                            body1.setImageDrawable(null);
                            bodyShape1.setImageResource(0);
                            body2.setImageDrawable(null);
                            bodyShape2.setImageResource(0);
                            body3.setImageDrawable(null);
                            bodyShape3.setImageResource(0);
                            head1.setImageDrawable(null);
                            headShape1.setImageResource(0);
                            head2.setImageDrawable(null);
                            headShape2.setImageResource(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //SPINNERBODY2

        mCursorbody2=mydb.getDataFilteringByCategory("busto");
        final List<String> categoriesbody2 = new ArrayList<String>();
        mCursorbody2.moveToFirst();
        categoriesbody2.add("Niente");
        do {
            categoriesbody2.add(mCursorbody2.getString(mCursorbody2.getColumnIndex("nome_immagine")));
        } while(mCursorbody2.moveToNext());

        spinnerbody2.findViewById(R.id.spinnerBody2);
        spinnerbody2.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterbody2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesbody2);
        // Drop down layout style - list view with radio button
        dataAdapterbody2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerbody2.setAdapter(dataAdapterbody2);
        spinnerbody2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sbody2 = parent.getItemAtPosition(position).toString();
                String sbody2shape;
                if(sbody2.equals("Niente")) {
                    body2.setImageDrawable(null);
                    bodyShape2.setImageResource(0);
                }
                else {
                    body2.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + sbody2 + ".png"));
                    mCursorbody2.moveToFirst();
                    mCursorbody2.move(position-1);
                    sbody2shape=mCursorbody2.getString(mCursorbody2.getColumnIndex("tipo"));
                    if(sbody2shape.equalsIgnoreCase("cappotto"))
                        bodyShape2.setImageResource(R.drawable.contorno_cappotto);
                    if(sbody2shape.equalsIgnoreCase("t-shirt"))
                        bodyShape2.setImageResource(R.drawable.contorno_tshirt);
                    if(sbody2shape.equalsIgnoreCase("maglia"))
                        bodyShape2.setImageResource(R.drawable.contorno_maglia);
                    if(sbody2shape.equalsIgnoreCase("maglione"))
                        bodyShape2.setImageResource(R.drawable.contorno_maglione);
                    if(sbody2shape.equalsIgnoreCase("felpa"))
                        bodyShape2.setImageResource(R.drawable.contorno_felpa);
                    if(sbody2shape.equalsIgnoreCase("cardigan"))
                        bodyShape2.setImageResource(R.drawable.contorno_cardigan);
                    if(sbody2shape.equalsIgnoreCase("polo"))
                        bodyShape2.setImageResource(R.drawable.contorno_polo);

                    buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            feet.setImageDrawable(null);
                            feetShape.setImageResource(0);
                            leg.setImageDrawable(null);
                            legShape.setImageResource(0);
                            body1.setImageDrawable(null);
                            bodyShape1.setImageResource(0);
                            body2.setImageDrawable(null);
                            bodyShape2.setImageResource(0);
                            body3.setImageDrawable(null);
                            bodyShape3.setImageResource(0);
                            head1.setImageDrawable(null);
                            headShape1.setImageResource(0);
                            head2.setImageDrawable(null);
                            headShape2.setImageResource(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//SPINNERBODY3

        mCursorbody3=mydb.getDataFilteringByCategory("busto");
        final List<String> categoriesbody3 = new ArrayList<String>();
        mCursorbody3.moveToFirst();
        categoriesbody3.add("Niente");
        do {
            categoriesbody3.add(mCursorbody3.getString(mCursorbody3.getColumnIndex("nome_immagine")));
        } while(mCursorbody3.moveToNext());

        spinnerbody3.findViewById(R.id.spinnerBody3);
        spinnerbody3.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterbody3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesbody3);
        // Drop down layout style - list view with radio button
        dataAdapterbody3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerbody3.setAdapter(dataAdapterbody3);
        spinnerbody3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sbody3 = parent.getItemAtPosition(position).toString();
                String sbody3shape;
                if(sbody3.equals("Niente")) {
                    body3.setImageDrawable(null);
                    bodyShape3.setImageResource(0);
                }
                else {
                    body3.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + sbody3 + ".png"));
                    mCursorbody3.moveToFirst();
                    mCursorbody3.move(position-1);
                    sbody3shape=mCursorbody3.getString(mCursorbody3.getColumnIndex("tipo"));
                    if(sbody3shape.equalsIgnoreCase("cappotto"))
                        bodyShape3.setImageResource(R.drawable.contorno_cappotto);
                    if(sbody3shape.equalsIgnoreCase("t-shirt"))
                           bodyShape3.setImageResource(R.drawable.contorno_tshirt);
                    if(sbody3shape.equalsIgnoreCase("maglia"))
                           bodyShape3.setImageResource(R.drawable.contorno_maglia);
                    if(sbody3shape.equalsIgnoreCase("maglione"))
                           bodyShape3.setImageResource(R.drawable.contorno_maglione);
                    if(sbody3shape.equalsIgnoreCase("felpa"))
                           bodyShape3.setImageResource(R.drawable.contorno_felpa);
                    if(sbody3shape.equalsIgnoreCase("cardigan"))
                           bodyShape3.setImageResource(R.drawable.contorno_cardigan);
                    if(sbody3shape.equalsIgnoreCase("polo"))
                           bodyShape3.setImageResource(R.drawable.contorno_polo);

                    buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            feet.setImageDrawable(null);
                            feetShape.setImageResource(0);
                            leg.setImageDrawable(null);
                            legShape.setImageResource(0);
                            body1.setImageDrawable(null);
                            bodyShape1.setImageResource(0);
                            body2.setImageDrawable(null);
                            bodyShape2.setImageResource(0);
                            body3.setImageDrawable(null);
                            bodyShape3.setImageResource(0);
                            head1.setImageDrawable(null);
                            headShape1.setImageResource(0);
                            head2.setImageDrawable(null);
                            headShape2.setImageResource(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//SPINNER LEG
        mCursorleg=mydb.getDataFilteringByCategory("gambe");
        final List<String> categoriesleg = new ArrayList<String>();
        mCursorleg.moveToFirst();
        categoriesleg.add("Niente");
        do {
            categoriesleg.add(mCursorleg.getString(mCursorleg.getColumnIndex("nome_immagine")));
        } while(mCursorleg.moveToNext());

        spinnerleg.findViewById(R.id.spinnerLeg);
        spinnerleg.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterleg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesleg);
        // Drop down layout style - list view with radio button
        dataAdapterleg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerleg.setAdapter(dataAdapterleg);
        spinnerleg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sleg = parent.getItemAtPosition(position).toString();
                String slegshape;
                if(sleg.equals("Niente"))
                    leg.setImageDrawable(null);
                else {
                    leg.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + sleg + ".png"));
                    mCursorleg.moveToFirst();
                    mCursorleg.move(position-1);
                    slegshape=mCursorleg.getString(mCursorleg.getColumnIndex("tipo"));
                    if(slegshape.equalsIgnoreCase("pantaloni"))
                        legShape.setImageResource(R.drawable.contorno_pantaloni);
                    if(slegshape.equalsIgnoreCase("jeans"))
                        legShape.setImageResource(R.drawable.contorno_jeans);
                    if(slegshape.equalsIgnoreCase("tuta"))
                        legShape.setImageResource(R.drawable.contorno_tuta);
                    if(slegshape.equalsIgnoreCase("pantaloncini"))
                     //   legShape.setImageResource(R.drawable.contorno_pantaloncini);

                    buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            feet.setImageDrawable(null);
                            feetShape.setImageResource(0);
                            leg.setImageDrawable(null);
                            legShape.setImageResource(0);
                            body1.setImageDrawable(null);
                            bodyShape1.setImageResource(0);
                            body2.setImageDrawable(null);
                            bodyShape2.setImageResource(0);
                            body3.setImageDrawable(null);
                            bodyShape3.setImageResource(0);
                            head1.setImageDrawable(null);
                            headShape1.setImageResource(0);
                            head2.setImageDrawable(null);
                            headShape2.setImageResource(0);
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//SPINNER PIEDI

        mCursorfeet=mydb.getDataFilteringByCategory("piedi");
        final List<String> categoriesfeet = new ArrayList<String>();
        mCursorfeet.moveToFirst();
        categoriesfeet.add("Niente");
        do {
            categoriesfeet.add(mCursorfeet.getString(mCursorfeet.getColumnIndex("nome_immagine")));
        } while(mCursorfeet.moveToNext());

        spinnerfeet.findViewById(R.id.spinnerFeet);
        spinnerfeet.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterfeet = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesfeet);
        // Drop down layout style - list view with radio button
        dataAdapterfeet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerfeet.setAdapter(dataAdapterfeet);
        spinnerfeet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sfeet = parent.getItemAtPosition(position).toString();
                String sfeetshape;
                if(sfeet.equals("Niente"))
                    feet.setImageDrawable(null);
                else {
                    feet.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + sfeet + ".png"));
                    mCursorfeet.moveToFirst();
                    mCursorfeet.move(position-1);
                    sfeetshape=mCursorfeet.getString(mCursorfeet.getColumnIndex("tipo"));
                    if(sfeetshape.equalsIgnoreCase("scarpe"))
                        feetShape.setImageResource(R.drawable.contorno_scarpe);
                    //if(sfeetshape.equalsIgnoreCase("calzini"))
                     //   feetShape.setImageResource(R.drawable.contorno_calzini);

                    buttonSpoglia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            feet.setImageDrawable(null);
                            feetShape.setImageResource(0);
                            leg.setImageDrawable(null);
                            legShape.setImageResource(0);
                            body1.setImageDrawable(null);
                            bodyShape1.setImageResource(0);
                            body2.setImageDrawable(null);
                            bodyShape2.setImageResource(0);
                            body3.setImageDrawable(null);
                            bodyShape3.setImageResource(0);
                            head1.setImageDrawable(null);
                            headShape1.setImageResource(0);
                            head2.setImageDrawable(null);
                            headShape2.setImageResource(0);
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
       // ImageView pantaloni = (ImageView)findViewById(R.id.pantsView);
       // ImageView pantshape = (ImageView)findViewById(R.id.pantsshapeView);
        // pantshape.setImageResource(R.drawable.pantsshape);


        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
       // if(item=="Jeans"){

         //  final int newColor =  ContextCompat.getColor(getApplicationContext(), R.color.colorPants);


            ;
       /*     pantaloni.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
             pantshape.setImageResource(R.drawable.contorno_pantaloni);
            pantaloni.setImageResource(R.drawable.pantaloni);*/
      //  }
      //  if(item=="Tuta"){
         //   pantaloni.setColorFilter(null);

         //   pantaloni.setImageResource(R.drawable.tuta);
       // }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

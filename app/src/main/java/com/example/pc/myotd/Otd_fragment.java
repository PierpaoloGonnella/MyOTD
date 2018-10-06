package com.example.pc.myotd;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pc.myotd.DatabaseClasses.CustomCursorLoader;
import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;
import com.example.pc.myotd.Model.Busto;
import com.example.pc.myotd.Model.Capo;
import com.example.pc.myotd.Model.Categoria;
import com.example.pc.myotd.Model.Gambe;
import com.example.pc.myotd.Model.OTD;
import com.example.pc.myotd.Model.Piedi;
import com.example.pc.myotd.Model.Testa;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pierpawel on 03/12/2015.
 */
public class Otd_fragment extends Fragment {
    Bitmap head1bitmap, bodybitmap, bodybitmap2, bodybitmap3, legbitmap,feetbitmap;
    Cursor mCursor;
    CustomCursorLoader ccl;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public List<Capo> outfitOfTheDay=null;
    private  String output="";
    List<Capo> otd=new ArrayList<>();
    public static String head, body1, body2, body3, leg, feet, headShape, bodyShape1, bodyShape2, bodyShape3, legShape, feetShape ;

    public static Otd_fragment newInstance(int sectionNumber) {
        Otd_fragment fragment = new Otd_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Otd_fragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.otd_layout_fragment, container, false);
        Spinner spinnerEvento = (Spinner)rootView.findViewById(R.id.spinnerEvento);
        final Button attributiButton = (Button)rootView.findViewById(R.id.attributi);
        final FloatingActionButton refreshButton = (FloatingActionButton)rootView.findViewById(R.id.buttonRefresh);
        final ImageView manView = (ImageView)rootView.findViewById(R.id.manView);
        final ImageView headView1 = (ImageView)rootView.findViewById(R.id.headView1);
        final ImageView headView2 = (ImageView)rootView.findViewById(R.id.headView2);
        final ImageView headShapeView1 = (ImageView)rootView.findViewById(R.id.headShapeView1);
        final ImageView headShapeView2 = (ImageView)rootView.findViewById(R.id.headShapeView2);
        final ImageView bodyView1 = (ImageView)rootView.findViewById(R.id.bodyView1);
        final ImageView bodyView2 = (ImageView)rootView.findViewById(R.id.bodyView2);
        final ImageView bodyView3 = (ImageView)rootView.findViewById(R.id.bodyView3);
        final ImageView bodyShapeView1 = (ImageView)rootView.findViewById(R.id.bodyShapeView1);
        final ImageView bodyShapeView2 = (ImageView)rootView.findViewById(R.id.bodyShapeView2);
        final ImageView bodyShapeView3 = (ImageView)rootView.findViewById(R.id.bodyShapeView3);
        final ImageView legView = (ImageView)rootView.findViewById(R.id.legView);
        final ImageView legShapeView = (ImageView)rootView.findViewById(R.id.legShapeView);
        final ImageView feetView = (ImageView)rootView.findViewById(R.id.feetView);
        final ImageView feetShapeView = (ImageView)rootView.findViewById(R.id.feetShapeView);
        manView.setImageResource(R.drawable.avatar);


        spinnerEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* attributiButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater factory = LayoutInflater.from(getContext());
                        final View view = factory.inflate(R.layout.attributi_dialog_layout, null);
                        AlertDialog.Builder attributiDialog = new AlertDialog.Builder(getContext());
                        attributiDialog.setView(view);
                        attributiDialog.show();
                    }
                });*/

                if(head1bitmap!=null)
                    head1bitmap.recycle();
                if( bodybitmap!=null )
                    bodybitmap.recycle();
                if(bodybitmap2!=null)
                    bodybitmap2.recycle();
                if( bodybitmap3!=null)
                    bodybitmap3.recycle();
                if(legbitmap!=null)
                    legbitmap.recycle();
                if(feetbitmap!=null)
                    feetbitmap.recycle();
                head=null;
                body1=null;
                body2=null;
                body3=null;
                leg=null;
                feet=null;
                headShape=null;
                bodyShape1=null;
                bodyShape2=null;
                bodyShape3=null;
                legShape=null;
                feetShape=null;

                ccl=new CustomCursorLoader(getContext());
                mCursor=ccl.loadInBackground();

                if(getListFromCursor(mCursor).size()>=10){
                    OTD tutti = new OTD(getListFromCursor(mCursor));

                    otd = tutti.creaAbbinamentoMeteo();
                    if(otd!=null && !otd.isEmpty()) {
                        head = otd.get(0).getMarca();
                        body3 = otd.get(1).getMarca(); //in get(1) c'è il livello più esterno (TIPO CAPPOTTO)
                        body2 = otd.get(3).getMarca(); //in get(3) c'è il livello centrale (TIPO CARDIGAN)
                        body1 = otd.get(2).getMarca(); //in get(2) c'è il livello più interno (TIPO T-SHIRT)
                        leg = otd.get(4).getMarca();
                        feet = otd.get(5).getMarca();

                        headShape = otd.get(0).getTipo();
                        bodyShape3 = otd.get(1).getTipo(); //in get(1) c'è il livello più esterno (TIPO CAPPOTTO)
                        bodyShape2 = otd.get(3).getTipo(); //in get(3) c'è il livello centrale (TIPO CARDIGAN)
                        bodyShape1 = otd.get(2).getTipo(); //in get(2) c'è il livello più interno (TIPO T-SHIRT)
                        legShape = otd.get(4).getTipo();
                        feetShape = otd.get(5).getTipo();

                        manView.setImageResource(R.drawable.avatar);
                        head1bitmap= BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + head + ".png");
                        headView1.setImageBitmap(head1bitmap);
                        //headView1.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + head + ".png"));
                        bodybitmap=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + body1 + ".png") ;
                        bodyView1.setImageBitmap(bodybitmap);
                        bodybitmap2=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + body2 + ".png");
                        bodyView2.setImageBitmap(bodybitmap2);
                        bodybitmap3=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + body3 + ".png");
                        bodyView3.setImageBitmap(bodybitmap3);
                        // bodyView3.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + body3 + ".png"));
                        //bodyView2.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + body2 + ".png"));
                        //bodyView1.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + body1 + ".png"));
                        legbitmap=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + leg + ".png") ;
                        legView.setImageBitmap(legbitmap);
                        //legView.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + leg + ".png"));
                        feetbitmap=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + feet + ".png");
                        feetView.setImageBitmap(feetbitmap);
                        // feetView.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + feet + ".png"));

                        if(headShape.equalsIgnoreCase("sciarpa"))
                            headShapeView1.setImageResource(R.drawable.contorno_sciarpa);
                        if(headShape.equalsIgnoreCase("cravatta")){}
                        //   headShapeView1.setImageResource(R.drawable.contorno_cravatta);
                        if(headShape.equalsIgnoreCase("cappello"))
                            headShapeView1.setImageResource(R.drawable.contorno_cappello);
                        if(bodyShape1.equalsIgnoreCase("cappotto"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_cappotto);
                        if(bodyShape1.equalsIgnoreCase("t-shirt"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_tshirt);
                        if(bodyShape1.equalsIgnoreCase("maglia"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_maglia);
                        if(bodyShape1.equalsIgnoreCase("maglione"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_maglione);
                        if(bodyShape1.equalsIgnoreCase("felpa"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_felpa);
                        if(bodyShape1.equalsIgnoreCase("cardigan"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_cardigan);
                        if(bodyShape1.equalsIgnoreCase("polo"))
                            bodyShapeView1.setImageResource(R.drawable.contorno_polo);
                        if(bodyShape2.equalsIgnoreCase("cappotto"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_cappotto);
                        if(bodyShape2.equalsIgnoreCase("t-shirt"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_tshirt);
                        if(bodyShape2.equalsIgnoreCase("maglia"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_maglia);
                        if(bodyShape2.equalsIgnoreCase("maglione"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_maglione);
                        if(bodyShape2.equalsIgnoreCase("felpa"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_felpa);
                        if(bodyShape2.equalsIgnoreCase("cardigan"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_cardigan);
                        if(bodyShape2.equalsIgnoreCase("polo"))
                            bodyShapeView2.setImageResource(R.drawable.contorno_polo);
                        if(bodyShape3.equalsIgnoreCase("cappotto"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_cappotto);
                        if(bodyShape3.equalsIgnoreCase("t-shirt"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_tshirt);
                        if(bodyShape3.equalsIgnoreCase("maglia"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_maglia);
                        if(bodyShape3.equalsIgnoreCase("maglione"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_maglione);
                        if(bodyShape3.equalsIgnoreCase("felpa"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_felpa);
                        if(bodyShape3.equalsIgnoreCase("cardigan"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_cardigan);
                        if(bodyShape3.equalsIgnoreCase("polo"))
                            bodyShapeView3.setImageResource(R.drawable.contorno_polo);
                        if(legShape.equalsIgnoreCase("pantaloni"))
                            legShapeView.setImageResource(R.drawable.contorno_pantaloni);
                        if(legShape.equalsIgnoreCase("jeans"))
                            legShapeView.setImageResource(R.drawable.contorno_jeans);
                        if(legShape.equalsIgnoreCase("tuta"))
                            legShapeView.setImageResource(R.drawable.contorno_tuta);
                        if(legShape.equalsIgnoreCase("pantaloncini")){}
                        //   legShapeView.setImageResource(R.drawable.contorno_pantaloncini);
                        if(feetShape.equalsIgnoreCase("scarpe"))
                            feetShapeView.setImageResource(R.drawable.contorno_scarpe);
                        //if(feetShape.equalsIgnoreCase("calzini"))
                        //   feetShapeView.setImageResource(R.drawable.contorno_calzini);
                    }}}
        });


        attributiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //LayoutInflater factory = LayoutInflater.from(getContext());
                final Dialog view = new Dialog(getContext());
                view.setContentView(R.layout.attributi_dialog_layout);
                view.setTitle("Ecco il tuo OTD!");
                //final View view = factory.inflate(R.layout.attributi_dialog_layout, null);
                ImageView testaattrview= (ImageView) view.findViewById(R.id.headImageView);
                testaattrview.setImageBitmap(head1bitmap);
                ImageView body1attrview= (ImageView) view.findViewById(R.id.bodyImageView);
                body1attrview.setImageBitmap(bodybitmap);
                ImageView body2attrview= (ImageView) view.findViewById(R.id.body2ImageView);
                body2attrview.setImageBitmap(bodybitmap2);
                ImageView body3attrview= (ImageView) view.findViewById(R.id.body3ImageView);
                body3attrview.setImageBitmap(bodybitmap3);
                ImageView legattrview=(ImageView) view.findViewById(R.id.legImageView);
                legattrview.setImageBitmap(legbitmap);
                ImageView feetattrview=(ImageView) view.findViewById(R.id.feetImageView);
                feetattrview.setImageBitmap(feetbitmap);

                TextView testatagview=(TextView) view.findViewById(R.id.tagHeadView);
                testatagview.setText(head);
                TextView body1tagview=(TextView) view.findViewById(R.id.bodyTagView);
                body1tagview.setText(body1);
                TextView body2tagview=(TextView) view.findViewById(R.id.body2TagView);
                body2tagview.setText(body2);
                TextView body3tagview=(TextView) view.findViewById(R.id.body3TagView);
                body3tagview.setText(body3);
                TextView legtagview=(TextView) view.findViewById(R.id.legTagView);
                legtagview.setText(leg);
                TextView feettagview=(TextView) view.findViewById(R.id.feetTagView);
                feettagview.setText(feet);
                TextView testatipoview=(TextView) view.findViewById(R.id.tipoHeadView);
                testatipoview.setText(headShape);
                TextView body1tipoview=(TextView) view.findViewById(R.id.bodyTipoView);
                body1tipoview.setText(bodyShape1);
                TextView body2tipoview=(TextView) view.findViewById(R.id.body2TipoView);
                body2tipoview.setText(bodyShape2);
                TextView body3tipoview=(TextView) view.findViewById(R.id.body3TipoView);
                body3tipoview.setText(bodyShape3);
                TextView legtipoview=(TextView) view.findViewById(R.id.legTipoView);
                legtipoview.setText(legShape);
                TextView feettipoview=(TextView) view.findViewById(R.id.feetTipoView);
                feettipoview.setText(feetShape);
                view.show();
            }
        });



        ccl=new CustomCursorLoader(getContext());
        mCursor=ccl.loadInBackground();

        if(getListFromCursor(mCursor).size()>=10){
            OTD tutti = new OTD(getListFromCursor(mCursor));

            otd = tutti.creaAbbinamentoMeteo();
            if(otd!=null && !otd.isEmpty()) {
                head = otd.get(0).getMarca();
                body3 = otd.get(1).getMarca(); //in get(1) c'è il livello più esterno (TIPO CAPPOTTO)
                body2 = otd.get(3).getMarca(); //in get(3) c'è il livello centrale (TIPO CARDIGAN)
                body1 = otd.get(2).getMarca(); //in get(2) c'è il livello più interno (TIPO T-SHIRT)
                leg = otd.get(4).getMarca();
                feet = otd.get(5).getMarca();

                headShape = otd.get(0).getTipo();
                bodyShape3 = otd.get(1).getTipo(); //in get(1) c'è il livello più esterno (TIPO CAPPOTTO)
                bodyShape2 = otd.get(3).getTipo(); //in get(3) c'è il livello centrale (TIPO CARDIGAN)
                bodyShape1 = otd.get(2).getTipo(); //in get(2) c'è il livello più interno (TIPO T-SHIRT)
                legShape = otd.get(4).getTipo();
                feetShape = otd.get(5).getTipo();

                head1bitmap= BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + head + ".png");
                headView1.setImageBitmap(head1bitmap);
                bodybitmap=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + body1 + ".png") ;
                bodyView1.setImageBitmap(bodybitmap);
                bodybitmap2=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + body2 + ".png");
                bodyView2.setImageBitmap(bodybitmap2);
                bodybitmap3=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + body3 + ".png");
                bodyView3.setImageBitmap(bodybitmap3);
                legbitmap=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + leg + ".png") ;
                legView.setImageBitmap(legbitmap);
                feetbitmap=BitmapFactory.decodeFile("sdcard/otd_saved_images/Image-" + feet + ".png");
                feetView.setImageBitmap(feetbitmap);
                //headView1.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + head + ".png"));
                //bodyView3.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + body3 + ".png"));
                //bodyView2.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + body2 + ".png"));
                //bodyView1.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + body1 + ".png"));
                //legView.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + leg + ".png"));
                //feetView.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-" + feet + ".png"));

                if (headShape.equalsIgnoreCase("sciarpa"))
                    headShapeView1.setImageResource(R.drawable.contorno_sciarpa);
                if (headShape.equalsIgnoreCase("cravatta")) {
                }
                //   headShapeView1.setImageResource(R.drawable.contorno_cravatta);
                if (headShape.equalsIgnoreCase("cappello"))
                    headShapeView1.setImageResource(R.drawable.contorno_cappello);
                if (bodyShape1.equalsIgnoreCase("cappotto"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_cappotto);
                if (bodyShape1.equalsIgnoreCase("t-shirt"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_tshirt);
                if (bodyShape1.equalsIgnoreCase("maglia"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_maglia);
                if (bodyShape1.equalsIgnoreCase("maglione"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_maglione);
                if (bodyShape1.equalsIgnoreCase("felpa"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_felpa);
                if (bodyShape1.equalsIgnoreCase("cardigan"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_cardigan);
                if (bodyShape1.equalsIgnoreCase("polo"))
                    bodyShapeView1.setImageResource(R.drawable.contorno_polo);
                if (bodyShape2.equalsIgnoreCase("cappotto"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_cappotto);
                if (bodyShape2.equalsIgnoreCase("t-shirt"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_tshirt);
                if (bodyShape2.equalsIgnoreCase("maglia"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_maglia);
                if (bodyShape2.equalsIgnoreCase("maglione"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_maglione);
                if (bodyShape2.equalsIgnoreCase("felpa"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_felpa);
                if (bodyShape2.equalsIgnoreCase("cardigan"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_cardigan);
                if (bodyShape2.equalsIgnoreCase("polo"))
                    bodyShapeView2.setImageResource(R.drawable.contorno_polo);
                if (bodyShape3.equalsIgnoreCase("cappotto"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_cappotto);
                if (bodyShape3.equalsIgnoreCase("t-shirt"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_tshirt);
                if (bodyShape3.equalsIgnoreCase("maglia"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_maglia);
                if (bodyShape3.equalsIgnoreCase("maglione"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_maglione);
                if (bodyShape3.equalsIgnoreCase("felpa"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_felpa);
                if (bodyShape3.equalsIgnoreCase("cardigan"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_cardigan);
                if (bodyShape3.equalsIgnoreCase("polo"))
                    bodyShapeView3.setImageResource(R.drawable.contorno_polo);
                if (legShape.equalsIgnoreCase("pantaloni"))
                    legShapeView.setImageResource(R.drawable.contorno_pantaloni);
                if (legShape.equalsIgnoreCase("jeans"))
                    legShapeView.setImageResource(R.drawable.contorno_jeans);
                if (legShape.equalsIgnoreCase("tuta"))
                    legShapeView.setImageResource(R.drawable.contorno_tuta);
                if (legShape.equalsIgnoreCase("pantaloncini")) {
                }
                //   legShapeView.setImageResource(R.drawable.contorno_pantaloncini);
                if (feetShape.equalsIgnoreCase("scarpe"))
                    feetShapeView.setImageResource(R.drawable.contorno_scarpe);
                //if(feetShape.equalsIgnoreCase("calzini"))
                //   feetShapeView.setImageResource(R.drawable.contorno_calzini);

                //capp o sciarpa
                //get 1 = cappotto
                //get2 tshirt
                //get3 cardigan
                //get4 pants
                //get5 scarpe
            }
        }
        return rootView;
    }
    public List<Capo> getListFromCursor(Cursor cursor) {
        cursor.moveToFirst();
        List<Capo> lista=new ArrayList<>();
        while(cursor.moveToNext()){
            Capo c=new Capo(0, null, null, null, null, null, null, null, null);


            String s = cursor.getString(cursor.getColumnIndex("categoria"));

            if(s.equalsIgnoreCase("testa"))
                c = new Testa(0,Categoria.TESTA, null, null, null, null, null, null, null);
            if(s.equalsIgnoreCase("busto"))
                c = new Busto(0,Categoria.BUSTO, null, null, null, null, null, null, null);
            if(s.equalsIgnoreCase("gambe"))
                c = new Gambe(0,Categoria.GAMBE, null, null, null, null, null, null, null);
            if(s.equalsIgnoreCase("piedi"))
                c = new Piedi(0,Categoria.PEDI, null, null, null, null, null, null, null);
            c.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            c.setColor1AsString(cursor.getString(cursor.getColumnIndex("pcolor")));
            c.setColor2AsString(cursor.getString(cursor.getColumnIndex("scolor")));
            c.setStagioni(Armadio_fragment.stagioni_e_meteo_fromint(Integer.parseInt(cursor.getString(cursor.getColumnIndex("stagioni4")))));
            c.setCondizioni(Armadio_fragment.stagioni_e_meteo_fromint(Integer.parseInt(cursor.getString(cursor.getColumnIndex("meteo6")))));
            c.setOccasioni(Armadio_fragment.occasioni_from_int(Integer.parseInt(cursor.getString(cursor.getColumnIndex("occasioni3")))));
            c.setMarca(cursor.getString(cursor.getColumnIndex("nome_immagine")));
            c.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
            lista.add(c);
        }
        return lista;
    }
    @Override
    public void onStop() {
        if(head1bitmap!=null)
            head1bitmap.recycle();
        if( bodybitmap!=null )
            bodybitmap.recycle();
        if(bodybitmap2!=null)
            bodybitmap2.recycle();
        if( bodybitmap3!=null)
            bodybitmap3.recycle();
        if(legbitmap!=null)
            legbitmap.recycle();
        if(feetbitmap!=null)
            feetbitmap.recycle();
        head=null;
        body1=null;
        body2=null;
        body3=null;
        leg=null;
        feet=null;
        headShape=null;
        bodyShape1=null;
        bodyShape2=null;
        bodyShape3=null;
        legShape=null;
        feetShape=null;

        super.onStop();
    }

}

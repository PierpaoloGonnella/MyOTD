package com.example.pc.myotd.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.myotd.R;


/**
 * Created by giuseppe on 28/01/2016.
 */
public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tag = (TextView) view.findViewById(R.id.tag);
        TextView tipo = (TextView) view.findViewById(R.id.tipo);
        TextView colore_primario = (TextView) view.findViewById(R.id.ittencolor);
        TextView colore_secondario = (TextView) view.findViewById(R.id.ittencolor2);
        ImageView texture= (ImageView) view.findViewById(R.id.thumbnail);
        ImageView textureshape = (ImageView) view.findViewById(R.id.thumbnailshape);
        String tiposhape = cursor.getString(cursor.getColumnIndex("tipo"));

        tag.setText(cursor.getString(cursor.getColumnIndex("nome_immagine")));
        tipo.setText(cursor.getString(cursor.getColumnIndex("tipo")));

        if (tiposhape.equals("Cravatta")) {
            //        textureshape.setImageResource(R.drawable.contorno_cravatta);
        }
        if (tiposhape.equals("Cappello")) {
            textureshape.setImageResource(R.drawable.contorno_cappello);
        }
        if (tiposhape.equals("Sciarpa")) {
            textureshape.setImageResource(R.drawable.contorno_sciarpa);
        }
        if (tiposhape.equals("Cappotto")) {
            textureshape.setImageResource(R.drawable.contorno_cappotto);
        }
        if (tiposhape.equals("T-shirt")) {
            textureshape.setImageResource(R.drawable.contorno_tshirt);
        }
        if (tiposhape.equals("Maglia")) {
            textureshape.setImageResource(R.drawable.contorno_maglia);
        }
        if (tiposhape.equals("Maglione")) {
            textureshape.setImageResource(R.drawable.contorno_maglione);
        }
        if (tiposhape.equals("Felpa")) {
            textureshape.setImageResource(R.drawable.contorno_felpa);
        }
        if (tiposhape.equals("Cardigan")) {
            textureshape.setImageResource(R.drawable.contorno_cardigan);
        }
        if (tiposhape.equals("Polo")) {
            textureshape.setImageResource(R.drawable.contorno_polo);
        }
        if (tiposhape.equals("Jeans")) {
            textureshape.setImageResource(R.drawable.contorno_jeans);
        }
        if (tiposhape.equals("Pantaloni")) {
            textureshape.setImageResource(R.drawable.contorno_pantaloni);
        }
        if (tiposhape.equals("Tuta")) {
            textureshape.setImageResource(R.drawable.contorno_tuta);
        }
        if (tiposhape.equals("Pantaloncini")) {
            //       textureshape.setImageResource(R.drawable.contorno_pantaloncini);
        }
        if (tiposhape.equals("Scarpe")) {
            textureshape.setImageResource(R.drawable.contorno_scarpe);
        }
        if (tiposhape.equals("Calzini")) {
            //       textureshape.setImageResource(R.drawable.contorno_calzini);
        }



        colore_primario.setText(cursor.getString(cursor.getColumnIndex("pcolor")));
        colore_secondario.setText(cursor.getString(cursor.getColumnIndex("scolor")));
        texture.setImageDrawable(Drawable.createFromPath("sdcard/otd_saved_images/Image-"+cursor.getString(cursor.getColumnIndex("nome_immagine"))+".png"));




    }
}

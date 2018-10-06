package com.example.pc.myotd.DatabaseClasses;

/**
 * Created by giuseppe on 24/01/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wardrobedatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Lo statement SQL di creazione del database
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS clothes (_id integer primary key autoincrement, categoria text , tipo text , pcolor text , scolor text ,indice_riscaldamento integer , stagioni4 integer  , meteo6 integer , occasioni3 integer,  nome_immagine text   );";
    //stagioni=Inverno, Autunno, Primavera, Estate ; Occasioni=Sportivo, Casual , Elegante
    // Costruttore
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Questo metodo viene chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Questo metodo viene chiamato durante l'upgrade del database, ad esempio quando viene incrementato il numero di versione
    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {

        database.execSQL("DROP TABLE IF EXISTS clothes");
        onCreate(database);

    }
    //inserisce una riga nella tabella, l'id si incrementa da solo
    public boolean insertData(String categoria , String tipo, String colore_primario,String colore_secondario , int warm_index, int stagioni4 , int meteo6 , int occasioni3, String nomeimmagine ) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("categoria", categoria);
        contentValues.put("tipo", tipo);
        contentValues.put("pcolor", colore_primario);
        contentValues.put("scolor", colore_secondario);
        contentValues.put("indice_riscaldamento", warm_index);
        contentValues.put("stagioni4", stagioni4);
        contentValues.put("meteo6", meteo6);
        contentValues.put("occasioni3", occasioni3);
        contentValues.put("nome_immagine", nomeimmagine);
        long result=db.insert("clothes", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    //restituisce tutti i dati della tabella
    public Cursor getAllData() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from clothes", null);
        return res;
    }
    //aggiorna una riga basandosi sull'id
    public boolean updateData(String id, String categoria , String tipo,String colore_primario,String colore_secondario , int warm_index, int stagioni4 , int meteo6 , int occasioni3, String nomeimmagine ) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //contentValues.put("_id", id);
        contentValues.put("categoria", categoria);
        contentValues.put("tipo", tipo);
        contentValues.put("pcolor", colore_primario);
        contentValues.put("scolor", colore_secondario);
        contentValues.put("indice_riscaldamento", warm_index);
        contentValues.put("stagioni4", stagioni4);
        contentValues.put("meteo6", meteo6);
        contentValues.put("occasioni3", occasioni3);
        contentValues.put("nome_immagine", nomeimmagine);
        db.update("clothes", contentValues, "_id=?", new String[] {id});
        return true;
    }
    //cancella una precisa riga perchè si basa sull'id
    public Integer deleteData(String id) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("clothes", "_id=?", new String[]{id});


    }
    //filtra i dati in base a un intero che rappresenta la codifica decimale delle combinazioni di sportivo , casual ed elegante
    public Cursor getDataFilteringbyOccasions(String occasioni) {
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.rawQuery("select * from clothes where occasioni3 LIKE ?", new String[]{occasioni});
    }
    //restituisce un cursore contenente i dati filtrati per categoria (busto , testa, gambe ecc.)
    public Cursor getDataFilteringByCategory(String categoria) {
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.rawQuery("select * from clothes where categoria LIKE ?",new String[]{categoria});

    }
}
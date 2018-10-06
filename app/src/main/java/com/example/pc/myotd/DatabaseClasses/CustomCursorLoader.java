package com.example.pc.myotd.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * Created by giuseppe on 28/01/2016.
 */
public class CustomCursorLoader extends CursorLoader {
    DatabaseHelper mydb;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

    public CustomCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        mydb=new DatabaseHelper(getContext());
        Cursor cursor =mydb.getAllData(); // get your cursor from wherever you like

        if (cursor != null) {
            // Ensure the cursor window is filled
            cursor.getCount();
            cursor.registerContentObserver(mObserver);
        }

        return cursor;
    }
};

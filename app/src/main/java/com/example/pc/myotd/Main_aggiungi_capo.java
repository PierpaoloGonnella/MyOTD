package com.example.pc.myotd;
/*CONTIENE I FRAGMENT DELL'AGGIUNGI CAPO E DELLA CAMERA*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main_aggiungi_capo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_aggiungi_capo_layout);
        if (savedInstanceState==null)  {
            getSupportFragmentManager().beginTransaction().add(R.id.main_aggiungi_capo_id, new Aggiungi_capo_fragment()).commit();
        }
    }
}

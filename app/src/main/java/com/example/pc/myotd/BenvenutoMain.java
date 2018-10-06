package com.example.pc.myotd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;


public class BenvenutoMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benvenuto_main_layout);
        if (savedInstanceState==null)  {
            getSupportFragmentManager().beginTransaction().add(R.id.benvenuto_main_layout_id, new BenvenutoFragment()).commit();
        }
    }


}
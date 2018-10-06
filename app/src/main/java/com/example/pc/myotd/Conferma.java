package com.example.pc.myotd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Conferma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma);

        String nome=Nome2.getNome();
        int eta=Eta3.getEta();
        boolean uomo=Genere4.getUomo();
        boolean donna=Genere4.getDonna();
        String sesso="";
        if(donna==true)
         sesso="a donna";
        else
        if(uomo==true)
            sesso=" uomo";

        TextView textView=( TextView)findViewById(R.id.domanda);
        textView.setText("Bene! Ti chiami " + nome + " e sei un" + sesso + " di " + eta + " anni"
                + getString(R.string.domanda));

        FloatingActionButton fabsx = (FloatingActionButton) findViewById(R.id.fabsx);
        fabsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finishConferma(view);
            }
        });

        Button button=(Button)findViewById(R.id.conferma);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openArmadio = new Intent(Conferma.this, MainActivity.class);
                startActivity(openArmadio);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void finishConferma(View v) {
        Conferma.this.finish();

    }

}

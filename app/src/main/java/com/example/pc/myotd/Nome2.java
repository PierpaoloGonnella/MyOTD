package com.example.pc.myotd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Nome2 extends AppCompatActivity {

    private static String nome="NESSUNO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nome2);



        FloatingActionButton fabdx = (FloatingActionButton) findViewById(R.id.fabdx);
        fabdx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openEta3 = new Intent(Nome2.this, Eta3.class);
                startActivity(openEta3);
                EditText editText= (EditText)findViewById(R.id.editTextNome);
                nome =editText.getText().toString();

            }
        });

        FloatingActionButton fabsx = (FloatingActionButton) findViewById(R.id.fabsx);
        fabsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishNome( view);

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

    public void finishNome(View v) {
        Nome2.this.finish();

    }

    public static String getNome(){
        return nome;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_benvenuto1, menu);
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

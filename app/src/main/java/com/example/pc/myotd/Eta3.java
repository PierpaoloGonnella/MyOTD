package com.example.pc.myotd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

public class Eta3 extends AppCompatActivity {

    private static int eta=0;

    public void main(){

    }

    public void foo(){
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eta3);

        final NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);

        np.setMinValue(3);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


            }
        });

        Log.d("NumberPicker", "NumberPicker");




        FloatingActionButton fabdx = (FloatingActionButton) findViewById(R.id.fabdx);
        fabdx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openConferma = new Intent(Eta3.this, Genere4.class);
                startActivity(openConferma);
                eta = np.getValue();



            }
        });
        FloatingActionButton fabsx = (FloatingActionButton) findViewById(R.id.fabsx);
        fabsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishEta(view);

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

    public void finishEta(View v) {
        Eta3.this.finish();

    }
    public static int getEta(){
        return eta;
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
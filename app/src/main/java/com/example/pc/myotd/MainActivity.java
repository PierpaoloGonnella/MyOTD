package com.example.pc.myotd;


import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.myotd.DatabaseClasses.CustomCursorAdapter;
import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    int verifica=0;
    Cursor newCursor;

    private static String TAG = MainActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;



    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

@Override
protected void onCreate(Bundle savedInstanceState) {
    mydb=new DatabaseHelper(this);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);

    ImageView avatarProfilo = (ImageView)findViewById(R.id.avatarProfilo);
    avatarProfilo.setImageResource(R.drawable.avatarprofilo);

    TextView textnome = (TextView)findViewById(R.id.userName);
    FileReader fr = null;
    String verifica = null;
    try {
        String filePath = getFilesDir() + "/DATI_UTENTE.txt";
        fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        verifica = br.readLine();
        br.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    textnome.setText(verifica);




    //DA QUI IN POI HO IMPLEMENTATO IL MENU A COMPARSA
    mNavItems.add(new NavItem("Meteo", "Visualizza le previsioni per la giornata", R.drawable.iconameteo));
    mNavItems.add(new NavItem("Manichino", "Prova i tuoi abbinamenti", R.drawable.iconaotd));
    // DrawerLayout
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
    // Populate the Navigtion Drawer with options
    mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
    mDrawerList = (ListView) findViewById(R.id.navList);
    DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);

    mDrawerList.setAdapter(adapter);
    // Drawer Item click listeners
    mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    });
}

//MENU A COMPARSA LATERALE
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }
    public void selectItemFromDrawer(int position) {
        Fragment fragment = new PreferencesFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position==0){
            this.getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new WeatherActivity()).addToBackStack(null).commit();}

        if (position==1){
            Intent openManichino = new Intent(MainActivity.this, Manichino_activity.class);
            startActivity(openManichino);
        }

        mDrawerList.setItemChecked(position, true);

        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:

                return true;
            case R.id.delete:
                verifica=mydb.deleteData(""+Armadio_fragment.getID());
                if(verifica==1)
                    Toast.makeText(this,"eliminato", Toast.LENGTH_LONG).show();
                 newCursor=mydb.getAllData();
                CustomCursorAdapter nuovo_cca=new CustomCursorAdapter(this, newCursor,2);
                ListView ehi=Armadio_fragment.getlistview();
                ehi.setAdapter(nuovo_cca);
                nuovo_cca.notifyDataSetChanged();

                return true;




            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onStop() {
        if(newCursor==null){

        }
            //piche
        else{
            newCursor.close();

        }
        super.onStop();
    }


}

package com.developer.arsltech.tasbeehcounter;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button reset;
    public TextView showDisplay;
    public int count=0;
    private AdView mAdView;
    HelperClass myDB;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3;
    boolean isMute=false;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-8863088720647115~4679683710");

        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new HelperClass(this);
        showDisplay=(TextView) findViewById(R.id.display);


        LoadCounter();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.clicksound);


        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                addZikarForm();
                materialDesignFAM.close(true);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                addZikarForm2();
                materialDesignFAM.close(true);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isMute) {
                    mediaPlayer.setVolume(0,0);
                    isMute=true;
                    floatingActionButton2.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_off_black_24dp));
                }
                else
                {
                    mediaPlayer.setVolume(1,1);
                    isMute=false;
                    floatingActionButton2.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_up_black_24dp));

                }


            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count",count);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count=savedInstanceState.getInt("count");

        showDisplay.setText(""+count);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void LoadCounter(){

        SharedPreferences loadCounter= getSharedPreferences("SavedCounter",MODE_PRIVATE);
        count=loadCounter.getInt("CounterValue",MODE_PRIVATE);
        String counter=Integer.toString(count);
        showDisplay.setText(counter);


    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences savedPreferences = getSharedPreferences("SavedCounter", MODE_PRIVATE);
        SharedPreferences.Editor editor = savedPreferences.edit();
        editor.putInt("CounterValue",count);
        editor.apply();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Reset the Counter?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    count=0;
                    showDisplay.setText("0");
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

         //creating alert dialog
            AlertDialog alertDialog = builder.create();
           alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setCounter) {


            final AlertDialog.Builder alert  = new AlertDialog.Builder(MainActivity.this);
            View mview= getLayoutInflater().inflate(R.layout.dialog_counter,null);
            final EditText counterText = (EditText)mview.findViewById(R.id.txt_counter);
            Button btnCancel = (Button) mview.findViewById(R.id.btn_cancel);
            Button btn_add = (Button)mview.findViewById(R.id.btn_add);
            alert.setView(mview);
            final AlertDialog alertDialog =alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alertDialog.dismiss();
                }
            });

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(counterText.getText().toString().equals("")){

                    counterText.setError("Please Enter Counter");
                    }
                    else{

                        int value = Integer.parseInt(counterText.getText().toString());
                        count = value;
                        showDisplay.setText(String.valueOf(count));
                        alertDialog.dismiss();
                    }
                }
            });

            alertDialog.show();


        } else if (id == R.id.nav_addZikar) {

            addZikarForm();


        } else if (id == R.id.nav_zikarList) {

            startActivity(new Intent(getApplicationContext(),zikar_list.class));

        }  else if (id == R.id.nav_share) {

            try{

                Uri uri = Uri.parse("market://details?id="+getPackageName());
                Intent gotoMarket = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(gotoMarket);
            }
            catch (ActivityNotFoundException e) {

                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName());
                Intent gotoMarket = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(gotoMarket);
            }



        }

        else if (id == R.id.nav_send) {

            try{
                Uri uri = Uri.parse("market://search?q=ArslTech");
                Intent gotoMarket = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(gotoMarket);
            }
            catch (ActivityNotFoundException e) {

                Uri uri = Uri.parse("http://play.google.com/store/search?q=ArslTech");
                Intent gotoMarket = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(gotoMarket);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void btnAction(View view) {
        if(count==10000000){
            count=0;
        }
        count++;
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        String countValue= Integer.toString(count);
        showDisplay.setText(countValue);
    }


    private void addZikarForm()
    {
        final AlertDialog.Builder alert  = new AlertDialog.Builder(MainActivity.this);
        final View mview= getLayoutInflater().inflate(R.layout.dialog_add_zikr,null);
        final EditText counterText = (EditText)mview.findViewById(R.id.txt_counter);
        final EditText zikrName = (EditText)mview.findViewById(R.id.txt_zikrName);

        Button btnCancel = (Button) mview.findViewById(R.id.btn_cancel);
        Button btn_add = (Button)mview.findViewById(R.id.btn_add);
        alert.setView(mview);
        final AlertDialog alertDialog =alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zikrName.getText().toString().isEmpty() && counterText.getText().toString().isEmpty()){

                    zikrName.setError("Add Enter Zikr Name");
                    counterText.setError("Enter Counter");
                }
                if(counterText.getText().toString().equals("")){

                    counterText.setError("Please Enter Counter");
                }
                else if(zikrName.getText().toString().equals("")){

                    zikrName.setError("Please Enter Zikr Name");
                }
                else{

                    ArrayList<String> myNames = new ArrayList<>();
                    Cursor cur = myDB.getAllData();
                    while (cur.moveToNext()) {

                        myNames.add(cur.getString(1));

                    }

                    if(myNames.contains(zikrName.getText().toString().trim())){

                        zikrName.setError("Zikr Name Already Exist Please Enter Another Zikr Name");
                    }
                    else {
                        myDB.insertData(zikrName.getText().toString(),Integer.parseInt(counterText.getText().toString()),0);
                         Toast.makeText(MainActivity.this, "Zikr Added", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),zikar_list.class));
                             alertDialog.dismiss();
                    }
                }
            }
        });

        alertDialog.show();

    }

    private void addZikarForm2()
    {
        final AlertDialog.Builder alert  = new AlertDialog.Builder(MainActivity.this);
        View mview= getLayoutInflater().inflate(R.layout.dialog_add_zikr,null);
        final EditText counterText = (EditText)mview.findViewById(R.id.txt_counter);
        final EditText zikrName = (EditText)mview.findViewById(R.id.txt_zikrName);

        final TextView txt_dialogTile = (TextView) mview.findViewById(R.id.txt_titleDialog);
        txt_dialogTile.setText("Save Current Zikr");


        Button btnCancel = (Button) mview.findViewById(R.id.btn_cancel);
        Button btn_add = (Button)mview.findViewById(R.id.btn_add);
        alert.setView(mview);
        final AlertDialog alertDialog =alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zikrName.getText().toString().isEmpty() && counterText.getText().toString().isEmpty()){

                    zikrName.setError("Add Enter Zikr Name");
                    counterText.setError("Enter Counter");
                }
                if(counterText.getText().toString().equals("")){

                    counterText.setError("Please Enter Counter");
                }
                else if(zikrName.getText().toString().equals("")){

                    zikrName.setError("Please Enter Zikr Name");
                }
                else{

                    if(Integer.parseInt(counterText.getText().toString())<Integer.parseInt(showDisplay.getText().toString())){

                        counterText.setError("Enter Counter is Less then Current Counter ");

                    }
                    else{


                        ArrayList<String> myNames = new ArrayList<>();
                        Cursor cur = myDB.getAllData();
                        while (cur.moveToNext()) {

                            myNames.add(cur.getString(1));

                        }

                        if(myNames.contains(zikrName.getText().toString().trim())){

                            zikrName.setError("Zikr Name Already Exist Please Enter Another Zikr Name");
                        }
                        else {
                            myDB.insertData(zikrName.getText().toString(),Integer.parseInt(counterText.getText().toString()),0);
                            showDisplay.setText("0");
                            startActivity(new Intent(getApplicationContext(),zikar_list.class));
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Zikr Saved", Toast.LENGTH_SHORT).show();

                        }




                    }



                }
            }
        });

        alertDialog.show();

    }



    public void addCounter(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Set Counter");
        alert.setMessage("Enter Number for Set Counter For Tasbeeh");
        // Set an EditText view to get user input
        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setGravity(Gravity.CENTER);
        input.setText("");
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(7) });
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                if(input.getText().toString().equals("")){

                    Toast.makeText(MainActivity.this, "Please Enter Counter", Toast.LENGTH_SHORT).show();
                }
                else{

                    int value = Integer.parseInt(input.getText().toString());
                    count = value;
                    showDisplay.setText(String.valueOf(count));

                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

    }


}

package com.developer.arsltech.tasbeehcounter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class zikar_list extends AppCompatActivity{

    List<zikar> zikarList;


    //the recyclerview
    RecyclerView recyclerView;

    HelperClass myDB;
    TextView txt_EmptyText;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zikar_list);

        txt_EmptyText =(TextView)findViewById(R.id.txt_empty);
        txt_EmptyText.setText("");
        myDB = new HelperClass(this);

        getSupportActionBar().setTitle("Zikr List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8863088720647115/9273534847");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        zikarList = new ArrayList<>();


      //  zikarList.add(new zikar(1,"Darood Pak",2334));
        showAll();
        zikar_adapter adapter = new zikar_adapter(this, zikarList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        if(zikarList.isEmpty()){
            txt_EmptyText.setText("List is Empty");
        }
    }
    int i=1;
    public void showAll() {
        Cursor cur = myDB.getAllData();
        while (cur.moveToNext()) {

            zikarList.add(new zikar(i,cur.getString(1),cur.getInt(2),cur.getInt(3)));
                i++;
        }
        // Toast.makeText(this, list.toString(), Toast.LENGTH_SHORT).show();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }else{
            super.onBackPressed();
        }}

}

package com.developer.arsltech.tasbeehcounter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

public class zika_counter extends AppCompatActivity {

    TextView showDisplay,textTotalCounter;
    int count=0;
    HelperClass myDB;
    String zikrNamee;
    int totalCOunter=1;
   private AdView mAdView;
    Vibrator vibe;
    MediaPlayer mediaPlayer;
    boolean isMute;
    FloatingActionButton floatingActionButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zika_counter);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        myDB = new HelperClass(this);
        showDisplay = (TextView) findViewById(R.id.display);

        MobileAds.initialize(this, "ca-app-pub-8863088720647115~4679683710");
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.clicksound);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        Intent i = getIntent();
        String counterRemaining = i.getStringExtra("counterPrevious");
        String tempTotalCounter =i.getStringExtra("counterTotal");
        count = Integer.parseInt(counterRemaining);
        totalCOunter = Integer.parseInt(tempTotalCounter);
        textTotalCounter = (TextView) findViewById(R.id.textTotal);
        textTotalCounter.setText("Total: "+totalCOunter);
        zikrNamee = i.getStringExtra("zikrName");
        showDisplay.setText(""+count);
        getSupportActionBar().setTitle(zikrNamee.toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);

                floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isMute) {
                    mediaPlayer.setVolume(0,0);
                    isMute=true;
                    floatingActionButton1.setLabelText("UnMute");
                    floatingActionButton1.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_off_black_24dp));
                }
                else
                {
                    mediaPlayer.setVolume(1,1);
                    isMute=false;
                    floatingActionButton1.setLabelText("Mute");
                    floatingActionButton1.setImageDrawable(getResources().getDrawable(R.drawable.ic_volume_up_black_24dp));

                }


            }
        });
    }



    public void btnAction(View view) {

        if(count<=totalCOunter){
            //String countValue= Integer.toString(count);
            mediaPlayer.start();
        showDisplay.setText(""+count);
        myDB.updateData(zikrNamee,count);
        count++;

        }
        else{
                vibe.vibrate(100);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
           startActivity(new Intent(getApplicationContext(),zikar_list.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

package ca.sfu.cmpt295a3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import ca.sfu.cmpt295a3.UI.MainMenu;

/**
 * Initial Driver
 * Displays Title Screen w/ Animation
 * Will swap to Main Menu on click or after all animations + 4 seconds
 */
public class MainActivity extends AppCompatActivity {
    private Animation splashMonkey, splashBloon;
    private ImageView dartMonkey, redBloon;
    private static MediaPlayer myPlayer;
    private Intent i;
    private Handler animSkip;

    public static MediaPlayer getPlayer(){
        return myPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.title_screen);

        i = MainMenu.makeLaunchIntent(MainActivity.this);

        skipIntroButton();
        setUpAnimations();
        startMusic();
    }

    private void startMusic(){
        myPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bloon_music);
        if(!myPlayer.isPlaying()){
            myPlayer.setVolume(1.0f, 1.0f);
            myPlayer.start();
            myPlayer.setLooping(true);
        }
    }

    private void setUpAnimations(){
        splashMonkey = AnimationUtils.loadAnimation(this, R.anim.splash_dart_monkey);
        splashBloon = AnimationUtils.loadAnimation(this, R.anim.splash_bloon);
        redBloon = findViewById(R.id.titleBloon);
        dartMonkey = findViewById(R.id.titleMonkey);

        redBloon.setAnimation(splashBloon);
        dartMonkey.setAnimation(splashMonkey);

        animSkip = new Handler();
        animSkip.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 12000);
    }

    private void skipIntroButton() {
        Button skipIntro = findViewById(R.id.titleScreenButton);
        skipIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animSkip.removeCallbacksAndMessages(null);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // Sourced from https://stackoverflow.com/questions/9148615/android-stop-background-music
    @Override
    protected void onPause() {
        if(isFinishing()){
            myPlayer.stop();
            myPlayer.release();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                if(myPlayer != null){
                    myPlayer.pause();
                }
            }
        }
        super.onPause();
    }
}


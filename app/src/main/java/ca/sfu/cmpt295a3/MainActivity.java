package ca.sfu.cmpt295a3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import ca.sfu.cmpt295a3.UI.MainMenu;

/**
 * Initial Driver
 * Displays Title Screen w/ Animation
 * Will swap to Main Menu on click or after all animations + 4 seconds
 */
public class MainActivity extends AppCompatActivity {
    private Animation splashMonkey, splashBloon;
    private ImageView dartMonkey, redBloon;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.title_screen);

        i = MainMenu.makeLaunchIntent(MainActivity.this);
        
        skipIntroButton();
        setUpAnimations();
    }

    private void setUpAnimations(){
        splashMonkey = AnimationUtils.loadAnimation(this, R.anim.splash_dart_monkey);
        splashBloon = AnimationUtils.loadAnimation(this, R.anim.splash_bloon);
        redBloon = findViewById(R.id.titleBloon);
        dartMonkey = findViewById(R.id.titleMonkey);

        redBloon.setAnimation(splashBloon);
        dartMonkey.setAnimation(splashMonkey);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 12000);
    }

    private void skipIntroButton() {
        Button skipIntro = findViewById(R.id.titleScreenButton);
        skipIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}


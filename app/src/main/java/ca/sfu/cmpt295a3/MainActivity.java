package ca.sfu.cmpt295a3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.sfu.cmpt295a3.UI.MainMenu;

/**
 * Initial Driver
 * Displays Title Screen w/ Animation
 * Will swap to Main Menu on click or after all animations + 4 seconds
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);
        skipIntroButton();
    }

    private void skipIntroButton(){
        Button skipIntro = findViewById(R.id.titleScreenButton);
        skipIntro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = MainMenu.makeLaunchIntent(MainActivity.this);
                startActivity(i);
            }
        });
    }

}

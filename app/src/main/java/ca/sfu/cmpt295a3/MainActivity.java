package ca.sfu.cmpt295a3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}
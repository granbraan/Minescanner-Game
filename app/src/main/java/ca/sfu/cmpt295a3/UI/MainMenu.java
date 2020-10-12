package ca.sfu.cmpt295a3.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ca.sfu.cmpt295a3.R;

public class MainMenu extends AppCompatActivity{
    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,MainMenu.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        setUpButtons();
    }

    private void setUpButtons(){
        findViewById(R.id.menuGameStartButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Game Screen
                Log.i("Main Menu - Start Game", "Start Game Button Clicked");
            }
        });
        findViewById(R.id.menuOptionsButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Options Screen
                Log.i("Main Menu - Options", "Options Button Clicked");
            }
        });
        findViewById(R.id.menuHelpButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Help Screen
                Log.i("Main Menu - Help", "Help Button Clicked");
            }
        });
    }

    @Override
    public void onBackPressed(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

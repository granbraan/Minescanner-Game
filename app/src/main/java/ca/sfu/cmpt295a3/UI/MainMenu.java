package ca.sfu.cmpt295a3.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ca.sfu.cmpt295a3.R;
import ca.sfu.cmpt295a3.model.Data;

public class MainMenu extends AppCompatActivity{
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;
    private Data savedData = Data.getInstance();

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,MainMenu.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        sharedPref = getSharedPreferences("SaveFile", MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        //used when player doesnt change options first
        savedData.add(0,4); // default rows
        savedData.add(1,6); // default cols
        savedData.add(2,6); // default mines

        setUpButtons();
    }

    private void setUpButtons(){
        findViewById(R.id.menuGameStartButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Game Screen
                Log.i("Main Menu - Start Game", "Start Game Button Clicked");

                SharedPreferences sharedPref = getSharedPreferences("SaveFile", MODE_PRIVATE);
                SharedPreferences.Editor sharedEditor = sharedPref.edit();
                int curr = sharedPref.getInt("gamesPlayed", 0);
                curr += 1;
                sharedEditor.putInt("gamesPlayed", curr);
                savedData.add(3, curr);
                sharedEditor.commit();

                Intent i = Game.makeLaunchIntent(MainMenu.this);
                startActivityForResult(i, 1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        findViewById(R.id.menuOptionsButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Options Screen
                Log.i("Main Menu - Options", "Options Button Clicked");
                Intent i = Options.makeLaunchIntent(MainMenu.this);
                startActivityForResult(i, 1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        findViewById(R.id.menuHelpButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Help Screen
                Log.i("Main Menu - Help", "Help Button Clicked");
                Intent i = HelpScreen.makeLaunchIntent(MainMenu.this);
                startActivityForResult(i, 1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        Log.e("Main Menu - Back", "This should not print");
    }
}

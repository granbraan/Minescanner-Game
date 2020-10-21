package ca.sfu.cmpt295a3.UI;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ca.sfu.cmpt295a3.MainActivity;
import ca.sfu.cmpt295a3.R;
import ca.sfu.cmpt295a3.model.Data;

/**
 * Main Menu allowing user to go to either
 * Game, Options, or Help
 */
public class MainMenu extends AppCompatActivity{
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;
    private Data savedData = Data.getInstance();

    private MediaPlayer myPlayer;

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,MainMenu.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        sharedPref = getSharedPreferences("SaveFile", MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        myPlayer = MainActivity.getPlayer();

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
                sharedEditor.apply();

                //Board Size
                switch(sharedPref.getInt("boardSize",0)){
                    case 1:
                        savedData.add(0,5); // rows
                        savedData.add(1,10); // cols
                        break;
                    case 2:
                        savedData.add(0,6); // rows
                        savedData.add(1,15); // cols
                        break;
                    default:
                        savedData.add(0,4); // rows
                        savedData.add(1,6); // cols
                        break;
                }
                // Num of Bloons
                switch(sharedPref.getInt("numOfBloons",0)){
                    case 1:
                        savedData.add(2,10);
                        break;
                    case 2:
                        savedData.add(2,15);
                        break;
                    case 3:
                        savedData.add(2,20);
                        break;
                    default:
                        savedData.add(2,6);
                        break;
                }
                savedData.add(3, curr);
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

    @Override
    protected void onPause() {
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

    @Override
    protected void onResume() {
        myPlayer.start();
        super.onResume();
    }
}

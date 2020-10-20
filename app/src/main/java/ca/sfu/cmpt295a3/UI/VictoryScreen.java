package ca.sfu.cmpt295a3.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.sfu.cmpt295a3.MainActivity;
import ca.sfu.cmpt295a3.R;
import ca.sfu.cmpt295a3.model.Data;
import ca.sfu.cmpt295a3.model.GameLogic;

/**
 * Victory Screen congratulating the user on winning as well as displaying their score
 */
public class VictoryScreen extends AppCompatActivity {
    private MediaPlayer myPlayer;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    private static Data savedData = Data.getInstance();
    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,VictoryScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory_screen);
        myPlayer = MainActivity.getPlayer();

        setUpText();
        setUpButton();
        setHighscore();
    }

    private void setUpText(){
        TextView myText = findViewById(R.id.victorySeekCount);
        String text = "Finished in " + savedData.get(4);
        text += " Scans!";

        myText.setText(text);
    }

    private void setUpButton(){
        Button myButton = findViewById(R.id.victoryButton);
        myButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = MainMenu.makeLaunchIntent(VictoryScreen.this);
                startActivityForResult(i, 1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void setHighscore(){
        sharedPref = getSharedPreferences("SaveFile", MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        int currScore = savedData.get(4);
        int currHigh = sharedPref.getInt("highScore", 0);
        if(currHigh <= 0){
            sharedEditor.putInt("highScore", currScore);
        }
        else if(currScore < currHigh){
            sharedEditor.putInt("highScore", currScore);
        }
        sharedEditor.apply();
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
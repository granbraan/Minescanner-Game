package ca.sfu.cmpt295a3.UI;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ca.sfu.cmpt295a3.MainActivity;
import ca.sfu.cmpt295a3.R;

public class Options extends AppCompatActivity{
    private MediaPlayer myPlayer;
    private SeekBar boardSizeSeek;
    private SeekBar numberOfBloons;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, Options.class);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu);
        myPlayer = MainActivity.getPlayer();

        sharedPref = getSharedPreferences("SaveFile", MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        setUpButtons();
        setUpSeekBar();
    }

    private void setUpButtons(){
        findViewById(R.id.optionsClearStatsButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Swap to Game Screen
                Log.i("Options - Reset Button", "Reset Stats Button Clicked");
                sharedEditor.remove("gamesPlayed");
                sharedEditor.remove("highScore");
                sharedEditor.apply();

                String totalGames = "Games Played: " + 0;
                String playerHigh = "High Score: " + 0;
                ((TextView)findViewById(R.id.optionsGamesPlayedText)).setText(totalGames);
                ((TextView)findViewById(R.id.optionsHighScoreText)).setText(playerHigh);
            }
        });
    }

    private void setUpSeekBar(){
        int boardSize = sharedPref.getInt("boardSize", 0);
        int numOfBloons = sharedPref.getInt("numOfBloons", 0);
        int gamesPlayed = sharedPref.getInt("gamesPlayed", 0);
        int highScore = sharedPref.getInt("highScore", 0);

        boardSizeSeek = findViewById(R.id.optionsBoardSizeSeek);
        numberOfBloons = findViewById(R.id.optionsNumberOfBloonsSeek);

        /*
            Board Size
                0 = 4x6
                1 = 5x10
                2 = 6x15
            Number of Bloons
                0 =  6
                1 = 10
                2 = 15
                3 = 20
        */
        boardSizeSeek.setProgress(boardSize);
        numberOfBloons.setProgress(numOfBloons);

        setBoardText();
        setBloonText();
        String totalGames = "Games Played: " + gamesPlayed;
        String playerHigh = "High Score: " + highScore;
        ((TextView)findViewById(R.id.optionsGamesPlayedText)).setText(totalGames);
        ((TextView)findViewById(R.id.optionsHighScoreText)).setText(playerHigh);

        boardSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                setBoardText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
            }
        });

        numberOfBloons.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                setBloonText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
            }
        });
    }

    private void setBoardText(){
        int curr = boardSizeSeek.getProgress();
        TextView boardText = findViewById(R.id.optionsBoardSizeText);
        String message = "Board Size: ";
        switch(curr){
            case 0:
                message += "4x6";
                boardText.setText(message);
                break;
            case 1:
                message += "5x10";
                boardText.setText(message);
                break;
            case 2:
                message += "6x15";
                boardText.setText(message);
                break;
            default:
                boardSizeSeek.setProgress(0);
                message += "4x6";
                boardText.setText(message);
                break;
        }

        sharedEditor.putInt("boardSize", boardSizeSeek.getProgress());
        sharedEditor.apply();
    }

    private void setBloonText(){
        int curr = numberOfBloons.getProgress();
        TextView boardText = findViewById(R.id.optionsNumberOfBloonsText);
        String message = "Number of Bloons: ";
        switch(curr){
            case 0:
                message += "6";
                boardText.setText(message);
                break;
            case 1:
                message += "10";
                boardText.setText(message);
                break;
            case 2:
                message += "15";
                boardText.setText(message);
                break;
            case 3:
                message += "20";
                boardText.setText(message);
                break;
            default:
                numberOfBloons.setProgress(0);
                message += "6";
                boardText.setText(message);
                break;
        }
        sharedEditor.putInt("numOfBloons", 0);
        sharedEditor.apply();
    }

    @Override
    public void onBackPressed(){
        finish();
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


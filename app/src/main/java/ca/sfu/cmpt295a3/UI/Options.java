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
import ca.sfu.cmpt295a3.model.Data;

/**
 * Options Menu
 * Allows user to clear highscore, games played, and set game properties such as number of bloons and size of the game board
 */
public class Options extends AppCompatActivity{
    private MediaPlayer myPlayer;
    private SeekBar boardSizeSeek;
    private SeekBar numberOfBloons;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;
    private Data savedData = Data.getInstance();

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
                sharedEditor.remove("highScore0");
                sharedEditor.remove("highScore1");
                sharedEditor.remove("highScore2");
                sharedEditor.remove("highScore3");
                sharedEditor.remove("highScore4");
                sharedEditor.remove("highScore5");
                sharedEditor.remove("highScore6");
                sharedEditor.remove("highScore7");
                sharedEditor.remove("highScore8");
                sharedEditor.remove("highScore9");
                sharedEditor.remove("highScore10");
                sharedEditor.remove("highScore11");
                sharedEditor.apply();
                String totalGames = "Games Played: " + 0;
                ((TextView)findViewById(R.id.optionsGamesPlayedText)).setText(totalGames);
                setHighscore();
            }
        });
    }

    private void setUpSeekBar(){
        int boardSize = sharedPref.getInt("boardSize", 0);
        int numOfBloons = sharedPref.getInt("numOfBloons", 0);
        int gamesPlayed = sharedPref.getInt("gamesPlayed", 0);

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
        for(int i = 0; i < savedData.size(); i++) {
            Log.i("TagData", String.valueOf(savedData.get(i)));
        }

        setBoardText();
        setBloonText();
        String totalGames = "Games Played: " + gamesPlayed;
        ((TextView)findViewById(R.id.optionsGamesPlayedText)).setText(totalGames);

        setHighscore();

        boardSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                setBoardText();
                setHighscore();
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
                setHighscore();
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
                savedData.add(0,5); // rows
                savedData.add(1,10); // cols
                boardText.setText(message);
                break;
            case 1:
                message += "5x10";
                savedData.add(0,6); // rows
                savedData.add(1,15); // cols
                boardText.setText(message);
                break;
            case 2:
                message += "6x15";
                savedData.add(0,4); // rows
                savedData.add(1,6); // cols
                boardText.setText(message);
                break;
            default:
                savedData.add(0,5); // rows
                savedData.add(1,10); // cols
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
                savedData.add(2,10);
                message += "6";
                boardText.setText(message);
                break;
            case 1:
                savedData.add(2,15);
                message += "10";
                boardText.setText(message);
                break;
            case 2:
                message += "15";
                boardText.setText(message);
                break;
            case 3:
                savedData.add(2,20);
                message += "20";
                boardText.setText(message);
                break;
            default:
                savedData.add(2,6);
                numberOfBloons.setProgress(0);
                message += "6";
                boardText.setText(message);
                break;
        }
        sharedEditor.putInt("numOfBloons", curr);
        sharedEditor.apply();
    }

    private void setHighscore(){
        int highScore = 0;
        switch(boardSizeSeek.getProgress()){
            case 0:
                switch(numberOfBloons.getProgress()){
                    case 0:
                        highScore = sharedPref.getInt("highScore0", 0);
                        break;
                    case 1:
                        highScore = sharedPref.getInt("highScore1", 0);
                        break;
                    case 2:
                        highScore = sharedPref.getInt("highScore2", 0);
                        break;
                    case 3:
                        highScore = sharedPref.getInt("highScore3", 0);
                        break;
                }
                break;
            case 1:
                switch(numberOfBloons.getProgress()) {
                    case 0:
                        highScore = sharedPref.getInt("highScore4", 0);
                        break;
                    case 1:
                        highScore = sharedPref.getInt("highScore5", 0);
                        break;
                    case 2:
                        highScore = sharedPref.getInt("highScore6", 0);
                        break;
                    case 3:
                        highScore = sharedPref.getInt("highScore7", 0);
                        break;
                }
                break;
            case 2:
                switch(numberOfBloons.getProgress()) {
                    case 0:
                        highScore = sharedPref.getInt("highScore8", 0);
                        break;
                    case 1:
                        highScore = sharedPref.getInt("highScore9", 0);
                        break;
                    case 2:
                        highScore = sharedPref.getInt("highScore10", 0);
                        break;
                    case 3:
                        highScore = sharedPref.getInt("highScore11", 0);
                        break;
                }
                break;
        }
        String playerHigh = "High Score: " + highScore;
        ((TextView)findViewById(R.id.optionsHighScoreText)).setText(playerHigh);
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


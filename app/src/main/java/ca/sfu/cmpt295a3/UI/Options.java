package ca.sfu.cmpt295a3.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.sfu.cmpt295a3.R;

public class Options extends AppCompatActivity{
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
                sharedEditor.commit();
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
            Number of Mines
                0 =  6
                1 = 10
                2 = 15
                3 = 20
        */
        boardSizeSeek.setProgress(boardSize);
        numberOfBloons.setProgress(numOfBloons);

        setBoardText();
        setMineText();
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
                setMineText();
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
        sharedEditor.commit();
    }

    private void setMineText(){
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
        sharedEditor.putInt("numOfBloons", numberOfBloons.getProgress());
        sharedEditor.commit();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}


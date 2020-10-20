package ca.sfu.cmpt295a3.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.sfu.cmpt295a3.MainActivity;
import ca.sfu.cmpt295a3.R;
import ca.sfu.cmpt295a3.model.Data;
import ca.sfu.cmpt295a3.model.GameLogic;
import ca.sfu.cmpt295a3.model.Grid;

/**
 * Game Screen where the user is playing the game
 * Has grid of buttons to press
 */
public class Game extends AppCompatActivity {
    private MediaPlayer myPlayer;
    private static Data savedData = Data.getInstance();
    private static Grid grid = Grid.getInstance();
    private final int NUM_ROWS = savedData.get(0);
    private final int NUM_COLS = savedData.get(1);
    private int scans;
    private int bloonsFound;
    private MediaPlayer popSound, scanSound;
    Button buttons[][] =  new Button[NUM_ROWS][NUM_COLS];


    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,Game.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        myPlayer = MainActivity.getPlayer();

        popSound = MediaPlayer.create(getApplicationContext(), R.raw.bloon_pop);
        scanSound = MediaPlayer.create(getApplicationContext(), R.raw.bloon_scan);
        populateButtons();
        grid.restartGrid();
        showTextCount();

        GameLogic.createGame(NUM_ROWS,NUM_COLS, savedData.get(2));
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);
        for(int row = 0; row < NUM_ROWS; row++) {

            TableRow tableRow = new TableRow(this);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));

            table.addView(tableRow);

            for(int col = 0; col < NUM_COLS; col++) {
                Button button = new Button(this);
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setText("");

                //Make text not clip on small buttons
                button.setPadding(0,0,0,0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row) {
        Button button = buttons[row][col];
        int curButton = row * NUM_COLS + col;
        //Lock button size
        lockButtonSizes();

        //Scale image to button
        //Only works in jellybean
        if(grid.getCell(curButton).isMine()) {
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.regrow_red);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource,scaledBitmap));

            //board refresher
            updateScanCount();

            //updates bloon count
            if(!grid.getCell(curButton).isReveal()) {
                popSound.start();
                bloonsFound++;
                grid.getCell(curButton).setReveal(true);
            }
            //Clicks on shown mine to scan
            else if(grid.getCell(curButton).isReveal() && !grid.getCell(curButton).isScanned()) {
                scanSound.start();
                GameLogic.scan(grid.getCell(curButton));
                button.setText("" + grid.getCell(curButton).getScanCounter());
                grid.getCell(curButton).setScanned(true);
                scans++;
            }
        }
        // When click on cell that is not a Bloon
        else if(!grid.getCell(curButton).isMine() && !grid.getCell(curButton).isScanned()) {
            scanSound.start();
            grid.getCell(curButton).setScanned(true);
            GameLogic.scan(grid.getCell(curButton));
            button.setText("" + grid.getCell(curButton).getScanCounter());
            scans++;
        }
        //board refresher
        updateScanCount();
        //textview in cells
        showTextCount();

        if(bloonsFound == savedData.get(2)) {
            savedData.add(4,scans);
            FragmentManager manager = getSupportFragmentManager();
            MessageFragment dialog = new MessageFragment();
            dialog.show(manager, "MessageDialog");
        }
    }

    private void lockButtonSizes() {
        for(int row = 0; row < NUM_ROWS; row++) {
            for(int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void updateScanCount() {
        int index = 0;
        for(Button[] buttonArr: buttons) {
            for(Button b: buttonArr) {
                GameLogic.updateScan();
                if(grid.getCell(index).isScanned()) {
                    b.setText("" + grid.getCell(index).getScanCounter());
                }
                index++;
            }
        }
    }
    private void showTextCount()
    {
        TextView bloons = findViewById(R.id.bloonCount);
        bloons.setText("Bloons found: " + bloonsFound + "/" + savedData.get(2));

        TextView scans2 = findViewById(R.id.scanCount);
        scans2.setText("# of Scans used: " + scans);

        TextView gamesPlayed = findViewById(R.id.gamesPlayed);
        gamesPlayed.setText("Games Played: " + savedData.get(3));


    }

    public int getScans() {
        return scans;
    }

    public void setScans(int scans) {
        this.scans = scans;
    }

    public int getBloonsFound() {
        return bloonsFound;
    }

    public void setBloonsFound(int bloonsFound) {
        this.bloonsFound = bloonsFound;
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
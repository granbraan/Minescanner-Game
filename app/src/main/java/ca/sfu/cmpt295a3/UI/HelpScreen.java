package ca.sfu.cmpt295a3.UI;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ca.sfu.cmpt295a3.MainActivity;
import ca.sfu.cmpt295a3.R;

public class HelpScreen extends AppCompatActivity {
    private MediaPlayer myPlayer;
    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,HelpScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_screen);
        myPlayer = MainActivity.getPlayer();

        setUpHyperlinks();
    }

    private void setUpHyperlinks(){
        TextView textView =(TextView)findViewById(R.id.helpCreditText);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "Course Website – <a href='https://opencoursehub.cs.sfu.ca/jackt/grav-cms/cmpt276-2/home'>Open Course Hub</a>\tImage & Music Credits – <a href='https://ninjakiwi.com/'>Ninja Kiwi</a>\n";
        textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
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

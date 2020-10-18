package ca.sfu.cmpt295a3.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.sfu.cmpt295a3.R;

public class HelpScreen extends AppCompatActivity {
    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,HelpScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_screen);

        setUpHyperlinks();
    }

    private void setUpHyperlinks(){
        TextView textView =(TextView)findViewById(R.id.helpCreditText);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "Course Website – <a href='https://opencoursehub.cs.sfu.ca/jackt/grav-cms/cmpt276-2/home'>Open Course Hub</a>\tImage Credits – <a href='https://ninjakiwi.com/'>Ninja Kiwi</a>\n";
        textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
    }
}

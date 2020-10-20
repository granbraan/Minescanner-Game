package ca.sfu.cmpt295a3.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import ca.sfu.cmpt295a3.R;

/**
 * Message Fragment used to display victory screen for the user
 */
public class MessageFragment extends AppCompatDialogFragment {
    public static Intent makeLaunchIntent(Context c){
        return new Intent(c,MessageFragment.class);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //create view to show
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.victory_screen, null);

        //create button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = VictoryScreen.makeLaunchIntent(getActivity());
                startActivityForResult(intent, 1);
                Log.i("TAG", "Victory Screen");
            }
        };
        //create alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations! You Won")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}

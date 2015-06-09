package edu.ncc.cis18b.project.Borrow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SettingsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_settings);

        DISPLAY_MANDATORY_ECCLESIARCHAL_MESSAGE();
    }

    private void DISPLAY_MANDATORY_ECCLESIARCHAL_MESSAGE()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();

        alertDialog.setTitle("The Emperors eyes are upon us - and we can not fail in his sight.");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "FOR THE EMPEROR!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        /*
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "SKULLS FOR THE SKULL THRONE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        toast("Heresy!");
                        throw new RuntimeException("Exterminatus Extremis");
                    }
                });
        */

        alertDialog.show();
    }

    private void toast(String msg)
    {
        Log.d("Sagev", msg);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}

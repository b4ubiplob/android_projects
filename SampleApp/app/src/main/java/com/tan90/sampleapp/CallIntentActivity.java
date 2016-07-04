package com.tan90.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by I320626 on 11/13/2015.
 */
public class CallIntentActivity extends Activity {

    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_callintent);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.intents, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onClick(View view) {
        int position = spinner.getSelectedItemPosition();
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vogella.com"));
                break;
            case 1:
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+91)7411492353"));;
                break;
            case 2:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+91)7411492353"));
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:50.123,7.1434?z=19"));
                break;
            case 4:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=query"));
                break;
            case 5:
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                break;
            case 6:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                break;
            case 7:
                intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));
                break;

        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void onActivity(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            String result = data.toURI();
            Toast.makeText(this, result, Toast.LENGTH_LONG);
        }
    }
}

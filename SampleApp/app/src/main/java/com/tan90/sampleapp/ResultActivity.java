package com.tan90.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by I320626 on 11/13/2015.
 */
public class ResultActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        String inputString = extras.getString("key");
        TextView textView = (TextView) findViewById(R.id.displayintentextra);
        textView.setText(inputString);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        EditText editText = (EditText) findViewById(R.id.returnvalue);
        String string  = editText.getText().toString();
        intent.putExtra("returnkey", string);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}

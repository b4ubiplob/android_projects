package com.tan90.lifecycle;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by I320626 on 11/20/2015.
 */
public class SecondActivity extends TracerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}

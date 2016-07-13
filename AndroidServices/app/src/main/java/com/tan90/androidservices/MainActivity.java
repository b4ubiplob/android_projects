package com.tan90.androidservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tan90.androidservices.model.Flower;
import com.tan90.androidservices.model.RequestPackage;
import com.tan90.androidservices.parsers.FlowerJsonParser;
import com.tan90.androidservices.parsers.FlowerXMLParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private ProgressBar progressBar;
    private List<MyTask> tasks;
    private List<Flower> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_do_task) {
            if (isOnline()) {
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            }
            else {
                Toast.makeText(this,"Network connection is not available", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    protected void updateDisplay() {
        FlowerAdapter flowerAdapter = new FlowerAdapter(this, R.layout.item_flower, flowers);
        listView.setAdapter(flowerAdapter);
    }

    protected boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this,"Wifi connection is not available", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else {
            return false;
        }

    }

    private void requestData(String uri) {
        MyTask myTask = new MyTask();
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setUri(uri);
        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestPackage);
    }

    private class MyTask extends AsyncTask<RequestPackage, String, List<Flower>> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected List<Flower> doInBackground(RequestPackage... packages) {

            String content = HttpManager.getData(packages[0], "feeduser", "feedpassword");
            flowers = FlowerJsonParser.parseFeed(content);

//            for (Flower flower : flowers) {
//                try {
//                    String imageUrl = PHOTOS_BASE_URL + flower.getPhoto();
//                    InputStream in = (InputStream) new URL(imageUrl).getContent();
//                    Bitmap bitmap = BitmapFactory.decodeStream(in);
//                    flower.setBitmap(bitmap);
//                    in.close();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            return flowers;
        }

        @Override
        protected void onPostExecute(List<Flower> result) {
            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            if (result == null) {
                Toast.makeText(MainActivity.this, "Cannot connect to web service", Toast.LENGTH_LONG).show();
                return;
            }

            updateDisplay();
        }


    }
}

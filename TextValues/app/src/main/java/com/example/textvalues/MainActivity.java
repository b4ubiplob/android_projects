package com.example.textvalues;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.textvalues.catalog.DataProvider;
import com.example.textvalues.catalog.Product;
import com.example.textvalues.catalog.ProductAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private static final int MENU_ITEM_LOGOUT = 1001;
    private static final String webURI = "http://www.androidcentral.com/root";
    private static final String emailAddress = "info@hplussupport.com";

    private static final int DETAIL_REQUEST = 1111;
    public static final String RETURN_MESSAGE = "RETURN_MESSAGE";

    private List<Product> products = DataProvider.productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] address = {emailAddress};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, address);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Information Request");
                intent.putExtra(Intent.EXTRA_TEXT, "Please send some imformation!");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

//        String[] items = getResources().getStringArray(R.array.clothing);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, items);
        ProductAdapter adapter = new ProductAdapter(this, R.layout.list_item, products);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Product product = products.get(position);
                intent.putExtra("PRODUCT_ID",product.getProductId());
                startActivityForResult(intent, DETAIL_REQUEST);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, MENU_ITEM_LOGOUT, 102, R.string.LOGOUT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                //Toast.makeText(MainActivity.this, "You selected the settings menu", Toast.LENGTH_LONG).show();
                Snackbar.make(coordinatorLayout, "You selected settings" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;

            case R.id.action_about:
//                Snackbar.make(coordinatorLayout, "You selected about" , Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case MENU_ITEM_LOGOUT:
                Snackbar.make(coordinatorLayout, "You selected Logout" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_web:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webURI));
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                }
                return true;
            case R.id.action_cart:
                Snackbar.make(coordinatorLayout, "You selected shopping cart" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void buttonClickHandler(View view) {
//        EditText text = (EditText) findViewById(R.id.nameText);
//        String entry = text.getText().toString();
//        Snackbar.make(coordinatorLayout, "You entered : " + entry , Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL_REQUEST) {
            if (resultCode == RESULT_OK) {
                String message = data.getExtras().getString(RETURN_MESSAGE);
                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).setAction("Go to cart",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Going to cart", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        }
    }
}

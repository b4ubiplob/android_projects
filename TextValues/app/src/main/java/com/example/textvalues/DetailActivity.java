package com.example.textvalues;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.textvalues.catalog.DataProvider;
import com.example.textvalues.catalog.Product;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String productId = (String) getIntent().getExtras().get("PRODUCT_ID");
        final Product product = DataProvider.productMap.get(productId);

        TextView textView = (TextView) findViewById(R.id.nameText);
        textView.setText(product.getName());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String price = formatter.format(product.getPrice());
        TextView priceText = (TextView) findViewById(R.id.priceText);
        priceText.setText(price);

        textView = (TextView) findViewById(R.id.descriptionText);
        textView.setText(product.getDescription());

        Bitmap bitmap = getBitMapFromAsset(product.getProductId());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(MainActivity.RETURN_MESSAGE, product.getName() + " added to shopping cart");
                setResult(RESULT_OK, data);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Bitmap getBitMapFromAsset(String productId) {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(productId + ".png");
            return BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();;
        }
        return null;
    }

}

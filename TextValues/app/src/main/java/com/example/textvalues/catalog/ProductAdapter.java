package com.example.textvalues.catalog;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.textvalues.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by i320626 on 6/22/2016.
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> products;

    public ProductAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        products = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Product product = products.get(position);
        TextView nameText = (TextView) convertView.findViewById(R.id.nameText);
        nameText.setText(product.getName());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String price = formatter.format(product.getPrice());
        TextView priceText = (TextView) convertView.findViewById(R.id.priceText);
        priceText.setText(price);

        Bitmap bitmap = getBitMapFromAsset(product.getProductId());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        return convertView;
    }

    private Bitmap getBitMapFromAsset(String productId) {
        AssetManager assetManager = getContext().getAssets();
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

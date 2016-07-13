package com.tan90.androidservices;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tan90.androidservices.model.Flower;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by I320626 on 7/12/2016.
 */
public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowers;
    private LruCache<Integer, Bitmap> cache;
    private static final String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";


    public FlowerAdapter(Context context, int resource, List<Flower> flowers) {
        super(context, resource, flowers);
        this.context = context;
        this.flowers = flowers;
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/8;
        this.cache = new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower, parent, false);

        Flower flower = flowers.get(position);
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        textView.setText(flower.getName());

        Bitmap bitmap = cache.get(flower.getProductId());
        if (bitmap != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            imageView.setImageBitmap(flower.getBitmap());
        }
        else {
            FlowerAndView container = new FlowerAndView();
            container.flower = flower;
            container.view = view;
            ImageLoader imageLoader = new ImageLoader();
            imageLoader.execute(container);
        }

        return view;
    }

    class FlowerAndView {
        public Flower flower;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<FlowerAndView, Void, FlowerAndView> {

        @Override
        protected FlowerAndView doInBackground(FlowerAndView... params) {
            FlowerAndView container = params[0];
            Flower flower = container.flower;
            try {
                String imageUrl = PHOTOS_BASE_URL + flower.getPhoto();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                flower.setBitmap(bitmap);
                in.close();
                container.bitmap = bitmap;
                return  container;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(FlowerAndView flowerAndView) {
            ImageView imageView = (ImageView) flowerAndView.view.findViewById(R.id.imageView1);
            imageView.setImageBitmap(flowerAndView.bitmap);
            //flowerAndView.flower.setBitmap(flowerAndView.bitmap);
            cache.put(flowerAndView.flower.getProductId(), flowerAndView.bitmap);
        }
    }
}

package com.app.golfapp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.golfapp.R;

import java.util.ArrayList;

/**
 * Created by nnarayan on 6/29/2018.
 */

public class SlidingImageAdapter extends PagerAdapter {
    private ArrayList<Bitmap> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImageAdapter(Context context, ArrayList<Bitmap> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


        imageView.setImageBitmap(IMAGES.get(position));

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    

}

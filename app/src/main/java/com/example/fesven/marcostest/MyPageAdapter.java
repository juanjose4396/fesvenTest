package com.example.fesven.marcostest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPageAdapter extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public MyPageAdapter(Context context,ArrayList<Integer> IMAGES) {this.IMAGES=IMAGES;
        this.context = context;

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
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.item_view_pager, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.imageViewMarco);

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = imageView.getMeasuredHeight();
                int finalWidth = imageView.getMeasuredWidth();
                Picasso.get().load(IMAGES.get(position)).resize(finalWidth,finalHeight).into(imageView);
                return true;
            }
        });

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}

package com.example.fesven.marcostest;

import android.graphics.Bitmap;

class FotoSingleton {
    private static final FotoSingleton ourInstance = new FotoSingleton();
    private Bitmap bitmap;
    private int imageResource;

    static FotoSingleton getInstance() {
        return ourInstance;
    }

    private FotoSingleton() {
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }
}

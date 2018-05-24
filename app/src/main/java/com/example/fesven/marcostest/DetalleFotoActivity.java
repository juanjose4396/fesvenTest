package com.example.fesven.marcostest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import java.io.ByteArrayOutputStream;

public class DetalleFotoActivity extends AppCompatActivity implements View.OnClickListener, CompartirFotoFragment.OnFragmentInteractionListener{

    private CompartirFotoFragment compartirFotoFragment;
    private Button btnCompartir;
    private Bitmap bitmap;
    private FragmentTransaction ft;
    private int imageResource;
    private ConstraintLayout LAYOUT_IMAGEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_foto);

        bindUI();
    }

    private void bindUI(){
        btnCompartir = findViewById(R.id.btnCompartir);
        btnCompartir.setOnClickListener(this);

        init();

    }

    public void init(){
        ft = getSupportFragmentManager().beginTransaction();
        compartirFotoFragment = CompartirFotoFragment.newInstance();
        ft.replace(R.id.fragmentCompartir, compartirFotoFragment);
        ft.commit();

        loadData();
    }

    public void loadData(){
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                compartirFotoFragment.getImageViewMarco().getViewTreeObserver().removeOnPreDrawListener(this);
//                int finalHeight = compartirFotoFragment.getImageViewMarco().getMeasuredHeight();
//                int finalWidth = compartirFotoFragment.getImageViewMarco().getMeasuredWidth();
//                Picasso.get().load(imageResource).resize(finalHeight,finalWidth).into(compartirFotoFragment.getImageViewMarco());
//                return true;
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        bitmap = FotoSingleton.getInstance().getBitmap();
        imageResource = FotoSingleton.getInstance().getImageResource();
        compartirFotoFragment.getImageViewFoto().setImageBitmap(bitmap);
        ViewTreeObserver vto = compartirFotoFragment.getImageViewMarco().getViewTreeObserver();
    }

    @Override
    public void onClick(View view) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        Uri imageUri = convertBitmapUri(getViewBitmap(LAYOUT_IMAGEN),"compartir");
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    /**
     * Crea una imagen a partir de un contenedor
     * @param v View del cual se va a crear la imagen
     * @return bitmap con la imagen creada
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("getViewBitmap", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;

    }

    public Uri convertBitmapUri(Bitmap bm,String title){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                bm, title, null);
        Uri imageUri =  Uri.parse(path);
        return imageUri;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

package com.example.fesven.marcostest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DetalleFotoActivity extends AppCompatActivity implements View.OnClickListener, CompartirFotoFragment.OnFragmentInteractionListener{

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
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
        ft.add(R.id.fragmentCompartir, compartirFotoFragment);
        ft.commit();
    }

    public void pedirPermiso(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }else {
            compartir();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bitmap = FotoSingleton.getInstance().getBitmap();
        imageResource = FotoSingleton.getInstance().getImageResource();
        compartirFotoFragment.getImageViewFoto().setImageBitmap(bitmap);
        compartirFotoFragment.getImageViewMarco().setImageResource(R.drawable.mir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    compartir();
                } else {
                    Toast.makeText(this, "Debe aceptar los permisos",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View view) {
        pedirPermiso();
    }

    public void compartir(){
        Log.d("dsds","dsds");
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        FrameLayout fl = findViewById(R.id.fragmentCompartir);

        Uri imageUri = convertBitmapUri(getViewBitmap(fl),"compartir");
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

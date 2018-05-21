package com.example.fesven.marcostest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DetalleFotoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageViewFoto;
    private ImageView imageViewMarco;
    private Button btnCompartir;
    private Bitmap bitmap;
    private int imageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_foto);

        bindUI();
    }

    private void bindUI(){
        imageViewFoto = findViewById(R.id.imageViewFoto);
        imageViewMarco = findViewById(R.id.imageViewMarco);
        btnCompartir = findViewById(R.id.btnCompartir);
        btnCompartir.setOnClickListener(this);
        loadData();
    }

    public void loadData(){
        bitmap = FotoSingleton.getInstance().getBitmap();
        imageResource = FotoSingleton.getInstance().getImageResource();
        imageViewFoto.setImageBitmap(bitmap);
        imageViewMarco.setImageResource(imageResource);
        Toast.makeText(this,imageResource+"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        ConstraintLayout v = findViewById(R.id.layout_foto_marco);
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();

        Bitmap bm = view.getDrawingCache();

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                bm, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

}

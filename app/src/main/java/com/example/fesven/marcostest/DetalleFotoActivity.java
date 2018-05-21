package com.example.fesven.marcostest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
        //Uri foto = convertBitmapUri(bitmap,"foto");
        //imageViewMarco.setImageResource(imageResource);
        imageViewFoto.setImageBitmap(bitmap);
        imageViewFoto.buildDrawingCache();
        Bitmap bmap = imageViewFoto.getDrawingCache();
        Log.d("aaaaa",bmap.getWidth()+"");
        Picasso.get().load(imageResource).resize(bmap.getWidth(),bmap.getHeight()).into(imageViewFoto);


    }

    @Override
    public void onClick(View view) {

        ConstraintLayout v = findViewById(R.id.layout_foto_marco);
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();

        Bitmap bm = view.getDrawingCache();

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        Uri imageUri = convertBitmapUri(bm,"compartir");
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    public Uri convertBitmapUri(Bitmap bm,String title){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                bm, title, null);
        Uri imageUri =  Uri.parse(path);
        return imageUri;
    }

}

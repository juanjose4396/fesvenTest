package com.example.fesven.marcostest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements AccionesCamaraFragment.OnFragmentInteractionListener,CamaraFragment.OnFragmentInteractionListener{

    private static final int FACING_BACK = CameraKit.Constants.FACING_BACK;
    private static final int FACING_FRONT = CameraKit.Constants.FACING_FRONT;
    private static final int FLASH_ON = CameraKit.Constants.FLASH_ON;
    private static final int FLASH_OFF = CameraKit.Constants.FLASH_OFF;

    private FragmentTransaction ft;
    private AccionesCamaraFragment accionesCamaraFragment;
    private CamaraFragment camaraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();
    }

    public void bindUI(){
        ft = getSupportFragmentManager().beginTransaction();

        init();

        ft.replace(R.id.fragmentAccionesCamara, accionesCamaraFragment);
        ft.replace(R.id.fragmentCamara, camaraFragment);
        ft.commit();

    }

    public void init(){
        accionesCamaraFragment = AccionesCamaraFragment.newInstance();
        camaraFragment = new CamaraFragment();
    }

    @Override
    public void onClickBtnGirarCamara() {
        int facing_change = camaraFragment.getCameraView().getFacing() == FACING_BACK
                ? FACING_FRONT : FACING_BACK;
        camaraFragment.getCameraView().setFacing(facing_change);
    }

    @Override
    public void onClickBtnCapturarImagen() {
        camaraFragment.getCameraView().captureImage(new CameraKitEventCallback<CameraKitImage>() {
            @Override
            public void callback(CameraKitImage cameraKitImage) {
                goDetalleFoto(cameraKitImage.getBitmap());
            }
        });
    }

    @Override
    public void onClickBtnFlash() {
        int flash_state = camaraFragment.getCameraView().getFlash() == FLASH_OFF
                ? FLASH_ON : FLASH_OFF;
        camaraFragment.getCameraView().setFlash(flash_state);
        Toast.makeText(this, flash_state+"", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void goDetalleFoto(Bitmap bitmap){
        int currentItemViewPage = camaraFragment.getCurrenteItemViewPager();
        FotoSingleton.getInstance().setBitmap(bitmap);
        FotoSingleton.getInstance().setImageResource(currentItemViewPage);
        Intent i = new Intent(this, DetalleFotoActivity.class);
        startActivity(i);
    }
}

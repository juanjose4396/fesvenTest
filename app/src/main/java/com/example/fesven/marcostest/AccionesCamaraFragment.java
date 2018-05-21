package com.example.fesven.marcostest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccionesCamaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccionesCamaraFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private ImageView btnGirarCamara;
    private ImageView btnCapturarImagen;
    private ImageView btnFlash;

    public AccionesCamaraFragment() {
        // Required empty public constructor
    }

    public static AccionesCamaraFragment newInstance() {
        AccionesCamaraFragment fragment = new AccionesCamaraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acciones_camara, container, false);

        bindUI(view);
        return view;
    }

    public void bindUI(View view){
        btnGirarCamara = view.findViewById(R.id.btnGirarCamara);
        btnGirarCamara.setOnClickListener(this);
        btnCapturarImagen = view.findViewById(R.id.btnCapturar);
        btnCapturarImagen.setOnClickListener(this);
        btnFlash = view.findViewById(R.id.btnFlash);
        btnFlash.setOnClickListener(this);
    }

    public void onButtonGirarCamaraPressed() {
        if (mListener != null) {
            mListener.onClickBtnGirarCamara();
        }
    }

    public void onButtonCapturarImagen() {
        if (mListener != null) {
            mListener.onClickBtnCapturarImagen();
        }
    }

    public void onButtonFlash(){
        if (mListener != null) {
            mListener.onClickBtnFlash();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGirarCamara:
                onButtonGirarCamaraPressed();
                break;
            case R.id.btnCapturar:
                onButtonCapturarImagen();
                break;
            case R.id.btnFlash:
                onButtonFlash();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onClickBtnGirarCamara();
        void onClickBtnCapturarImagen();
        void onClickBtnFlash();
    }
}

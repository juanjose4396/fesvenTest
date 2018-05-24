package com.example.fesven.marcostest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompartirFotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompartirFotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompartirFotoFragment extends Fragment {

    private ImageView imageViewMarco;
    private ImageView imageViewFoto;

    private OnFragmentInteractionListener mListener;

    public CompartirFotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompartirFotoFragment.
     */
    public static CompartirFotoFragment newInstance() {
        CompartirFotoFragment fragment = new CompartirFotoFragment();
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
        View v = inflater.inflate(R.layout.fragment_compartir_foto, container, false);

        bindUI(v);

        return v;
    }

    public void bindUI(View v){
        imageViewFoto = v.findViewById(R.id.imageViewFoto);
        imageViewMarco = v.findViewById(R.id.imageViewMarco);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    public ImageView getImageViewMarco() {
        return imageViewMarco;
    }

    public ImageView getImageViewFoto() {
        return imageViewFoto;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

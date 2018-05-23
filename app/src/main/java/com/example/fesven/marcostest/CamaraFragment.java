package com.example.fesven.marcostest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.wonderkiln.camerakit.CameraView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CamaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamaraFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.marco1,R.drawable.marco2,R.drawable.marco3,R.drawable.marco4};
    private ArrayList<Integer> ImagesArray = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private CameraView cameraView;
    InkPageIndicator pageIndicator;

    public CamaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CamaraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CamaraFragment newInstance(String param1, String param2) {
        CamaraFragment fragment = new CamaraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camara, container, false);
        bindUI(view);
        return view;
    }

    public void bindUI(View view){
        cameraView = view.findViewById(R.id.camera);
        initViewpager(view);
    }

    private void initViewpager(View view) {
        for(int i=0;i<IMAGES.length;i++){
            ImagesArray.add(IMAGES[i]);
        }
        mPager = view.findViewById(R.id.viewPagerMarcos);
        mPager.setAdapter(new MyPageAdapter(getActivity().getApplicationContext(),ImagesArray));
        pageIndicator = view.findViewById(R.id.pageIndicator);
        pageIndicator.setViewPager(mPager);
        NUM_PAGES =IMAGES.length;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonGirarCamaraPressed(Uri uri) {
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
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public CameraView getCameraView() {
        return cameraView;
    }

    public int getCurrenteItemViewPager(){
        return ImagesArray.get(mPager.getCurrentItem());
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
        void onFragmentInteraction(Uri uri);
    }
}

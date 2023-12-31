package com.example.madapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarbonFtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarbonFtFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarbonFtFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DestHome.
     */
    // TODO: Rename and change types and number of parameters
    public static CarbonFtFragment newInstance(String param1, String param2) {
        CarbonFtFragment fragment = new CarbonFtFragment();
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
        return inflater.inflate(R.layout.fragment_carbonft_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button BtnTrackCF = view.findViewById(R.id.BtnTrackCF);
        View.OnClickListener OCLTrackCF = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.firstQuestionCalculateCF); //using Fragment ID
            }
        };
        BtnTrackCF.setOnClickListener(OCLTrackCF);

        Button BtnTipsReduceCF = view.findViewById(R.id.BtnTipsReduceCF);
        View.OnClickListener OCLTipsReduceCF = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.NextToTipsReduceCF);  //if using the action link home to destDog, so the id set to that action, not like BtnCat
            }
        };
        BtnTipsReduceCF.setOnClickListener(OCLTipsReduceCF);
    }
}
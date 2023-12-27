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
 * Use the {@link fragment_thridQuestionCalculateCF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_thridQuestionCalculateCF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_thridQuestionCalculateCF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thridQuestionCalculateCF.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_thridQuestionCalculateCF newInstance(String param1, String param2) {
        fragment_thridQuestionCalculateCF fragment = new fragment_thridQuestionCalculateCF();
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
        return inflater.inflate(R.layout.fragment_thrid_question_calculate_cf, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        Button BtnPreviousQuestion = view.findViewById(R.id.BtnPreviousQuestion3);

        View.OnClickListener OCLPreviousQuestion = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.secondQuestionCalculateCF);
            }
        };
        BtnPreviousQuestion.setOnClickListener(OCLPreviousQuestion);

        Button BtnCompleteCF = view.findViewById(R.id.BtnCompleteCF);

        View.OnClickListener OCLCompleteCF = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestCertificate);
            }
        };
        BtnCompleteCF.setOnClickListener(OCLCompleteCF);
    }
}
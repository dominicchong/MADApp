package com.example.madapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_secondQuestionCalculateCF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_secondQuestionCalculateCF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CFViewModel viewModel;

    public fragment_secondQuestionCalculateCF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_secondQuestionCalculateCF.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_secondQuestionCalculateCF newInstance(String param1, String param2) {
        fragment_secondQuestionCalculateCF fragment = new fragment_secondQuestionCalculateCF();
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
        viewModel = new ViewModelProvider(requireActivity()).get(CFViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cf_second_question, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        EditText ETAnsQ2 = view.findViewById(R.id.ETAnsQ2);
        Button BtnPreviousQuestion2 = view.findViewById(R.id.BtnPreviousQuestion2);

        View.OnClickListener OCLPreviousQuestion2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.firstQuestionCalculateCF);

            }
        };
        BtnPreviousQuestion2.setOnClickListener(OCLPreviousQuestion2);

        Button BtnNextQuestionQ2 = view.findViewById(R.id.BtnNextQuestionQ2);

        View.OnClickListener OCLNextQuestion2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double AnsQ2 = Double.parseDouble(ETAnsQ2.getText().toString());
                viewModel.setAnsQ2(AnsQ2);
                Navigation.findNavController(view).navigate(R.id.thirdQuestionCalculateCF);
            }
        };
        BtnNextQuestionQ2.setOnClickListener(OCLNextQuestion2);
    }
}
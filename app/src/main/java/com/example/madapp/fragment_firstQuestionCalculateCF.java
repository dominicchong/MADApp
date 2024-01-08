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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_firstQuestionCalculateCF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_firstQuestionCalculateCF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CFViewModel viewModel;

    public fragment_firstQuestionCalculateCF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarbonFootprintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_firstQuestionCalculateCF newInstance(String param1, String param2) {
        fragment_firstQuestionCalculateCF fragment = new fragment_firstQuestionCalculateCF();
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
        return inflater.inflate(R.layout.fragment_carbonfootprint, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        EditText ETAnsQ1 = view.findViewById(R.id.ETAnsQ1);

        Button BtnNextQuestionQ1 = view.findViewById(R.id.BtnNextQuestionQ1);

        View.OnClickListener OCLNextQuestion = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AnsQ1Text = ETAnsQ1.getText().toString().trim();
                if(AnsQ1Text.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
                } else if (AnsQ1Text.equals("0")) {
                    Toast.makeText(requireContext(), "Value cant be zero", Toast.LENGTH_SHORT).show();
                } else{
                    Double AnsQ1 = Double.parseDouble(ETAnsQ1.getText().toString());
                    viewModel.setAnsQ1(AnsQ1);
                    Navigation.findNavController(view).navigate(R.id.secondQuestionCalculateCF);
                }
            }
        };
        BtnNextQuestionQ1.setOnClickListener(OCLNextQuestion);
    }
}
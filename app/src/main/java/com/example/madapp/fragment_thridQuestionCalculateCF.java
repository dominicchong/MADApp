package com.example.madapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    CFViewModel viewModel;

    private FirebaseAuth firebaseAuth;

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
        viewModel = new ViewModelProvider(requireActivity()).get(CFViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thrid_question_calculate_cf, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        EditText ETAnsQ3 = view.findViewById(R.id.ETAnsQ3);
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
                String AnsQ3Text = ETAnsQ3.getText().toString().trim();
                if(AnsQ3Text.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
                }
                else{
                    Double AnsQ3 = Double.parseDouble(ETAnsQ3.getText().toString());
                    viewModel.setAnsQ3(AnsQ3);

                    completeCalculation();
                }
            }
        };
        BtnCompleteCF.setOnClickListener(OCLCompleteCF);


    }

    private void completeCalculation() {
        String total_CF = viewModel.getTotal();
        // Inflate the custom layout
        View customDialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_layout, null);

        // Find views in the custom layout
        ImageView CustomIV = customDialogView.findViewById(R.id.CustomIV);
        TextView customDialogTitle = customDialogView.findViewById(R.id.customDialogTitle);
        TextView customDialogMessage = customDialogView.findViewById(R.id.customDialogMessage);


        String avgLevel = "";
        if(Double.parseDouble(total_CF) >= 4) {
            avgLevel = "Your carbon footprint level is above the average level";
            CustomIV.setImageResource(R.drawable.try_hard_to_reduce_cf);
            new AlertDialog.Builder(getContext())
                    .setView(customDialogView)
                    .setPositiveButton("Back to Home Carbon Footprint", (dialogInterface, i) -> backToCFHome())
                    .setCancelable(false)
                    .show();
        } else {
            avgLevel = "Congrats! Your carbon footprint level is lower than average level and you earn a certificate";
            CustomIV.setImageResource(R.drawable.congrate_gain_cert);
            new AlertDialog.Builder(getContext())
                    .setView(customDialogView)
                    .setPositiveButton("Download certificate", (dialogInterface, i) -> downloadCertificate())
                    .setNegativeButton("Back to Home Carbon Footprint", (dialogInterface, i) -> backToCFHome())
                    .setCancelable(false)
                    .show();
        }

        // Set custom content
        customDialogTitle.setText(avgLevel);
        customDialogMessage.setText("Your carbon footprint is " + total_CF+" tons");

        FirebaseUser user = firebaseAuth.getCurrentUser();

        // You can now store additional user information in the Firebase Realtime Database
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            userRef.child("total cf").setValue(total_CF);
        }

    }

    private void downloadCertificate() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.DestCertificate);
    }

    private void backToCFHome() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.DestCarbonFootprint);
    }
}
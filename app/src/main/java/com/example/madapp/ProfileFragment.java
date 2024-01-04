package com.example.madapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    DatabaseReference userRef; // Reference to the database
    TextView usernameTV, birthDateTV, phoneNumberTV, emailTV;
    ImageView profileView;
    private int pageVisited = 0;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PAGE_VISITED_KEY = "page_visited";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String userID = user.getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageButton BtnEditProfile = view.findViewById(R.id.BtnEditProfile);
        View.OnClickListener OCLEditProfile = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestEditProfile);
            }
        };
        BtnEditProfile.setOnClickListener(OCLEditProfile);

        ImageView IVEnhancedSurvey = view.findViewById(R.id.IVEnhancedSurvey);
        View.OnClickListener OCLEnhancedSurvey = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestProfileEnhancedSurvey);
            }
        };
        IVEnhancedSurvey.setOnClickListener(OCLEnhancedSurvey);


        profileView = view.findViewById(R.id.profileView);
        usernameTV = view.findViewById(R.id.usernameTV);
        birthDateTV = view.findViewById(R.id.birthDateTV);
        phoneNumberTV = view.findViewById(R.id.phoneNumberTV);
        emailTV = view.findViewById(R.id.emailTV);

        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        if (!isAdded()) {
            // Fragment is not attached to the activity, exit the method
            return;
        }

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!isAdded()) {
                    // Fragment is not attached to the activity, exit the method
                    return;
                }

                if (dataSnapshot.exists()) {
                    // Read data from the dataSnapshot
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String birthDate = dataSnapshot.child("birthDate").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String profilePicUri = dataSnapshot.child("profilePic").getValue(String.class);

                    // Update UI elements with the retrieved data
                    usernameTV.setText(username);
                    birthDateTV.setText(birthDate);
                    phoneNumberTV.setText(phoneNumber);
                    emailTV.setText(email);

                    // Load and display the profile image using Glide
                    if (profilePicUri != null && !profilePicUri.isEmpty() && isAdded()) {
                        Glide.with(requireContext())
                                .load(Uri.parse(profilePicUri))
                                .into(profileView);
                    }

                } else {
                    // Data does not exist
                    if (isAdded()) {
                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isAdded()) {
                    // Handle database errors
                    Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
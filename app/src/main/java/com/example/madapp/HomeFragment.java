package com.example.madapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ConstraintLayout upperPart;
    private ConstraintLayout lowerPart;
    private NavController navController;
    DatabaseReference userRef;
    TextView usernameText;
    ImageView circleImageView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth firebaseAuth;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        firebaseAuth = FirebaseAuth.getInstance();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.lowerpart, new NewsFragment())
                .commit();

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        usernameText = view.findViewById(R.id.textView5);
        circleImageView = view.findViewById(R.id.imageView7);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded()) {
                    // Fragment is not attached to the activity, exit the method
                    return;
                }

                if (snapshot.exists()) {
                    // Read data from snapshot
                    String username = String.valueOf(snapshot.child("username").getValue());
                    String profilePicUri = snapshot.child("profilePic").getValue(String.class);
                    usernameText.setText(username);

                    // Load and display the profile image using Glide
                    if (profilePicUri != null && !profilePicUri.isEmpty() && isAdded()) {
                        Glide.with(requireContext())
                                .load(Uri.parse(profilePicUri))
                                .into(circleImageView);
                    }
                    else{
                        circleImageView.setImageResource(R.drawable.default_profile_img);
                    }

                } else {
                    // Data does not exist
                    if (isAdded()) {
                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Find button IDs
        ImageButton newsButton = view.findViewById(R.id.news);
        ImageButton programmesButton = view.findViewById(R.id.programmes);
        ImageButton organizationButton = view.findViewById(R.id.organization);

        // Initialize upper and lower parts
        upperPart = view.findViewById(R.id.upperpart);
        lowerPart = view.findViewById(R.id.lowerpart);

        // Set OnClickListener for each button
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the lower part with the NewsFragment
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.lowerpart, new NewsFragment())
                        .commit();
            }
        });

        programmesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the lower part with the ProgrammesFragment
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.lowerpart, new ProgrammesFragment())
                        .commit();
            }
        });

        organizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the lower part with the OrganizationsFragment
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.lowerpart, new OrganizationFragment())
                        .commit();
            }
        });
    }
}


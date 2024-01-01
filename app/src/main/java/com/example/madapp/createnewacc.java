package com.example.madapp;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createnewacc extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_createnewacc, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Log statement to check if onViewCreated is called
        Log.d("SignUpFragment", "onViewCreated");

        // Sign Up Button
        Button BTNSU = view.findViewById(R.id.SignupBtn);

        // Log statement to check if the click listener is being set up
        Log.d("SignUpFragment", "Setting up click listener");

        BTNSU.setOnClickListener(v -> signUp(view));
    }

    private void signUp(View view) {
        EditText emailEditText = view.findViewById(R.id.Emailadd);
        EditText passwordEditText = view.findViewById(R.id.Password);
        EditText usernameEditText = view.findViewById(R.id.Username);
        EditText phoneNumberEditText = view.findViewById(R.id.Phonenum);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        // Validate email, password, username, and phone number if needed

        // Sign up with Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign up success
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        // You can now store additional user information in the Firebase Realtime Database
                        if (user != null) {
                            String userId = user.getUid();
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                            userRef.child("email").setValue(email);
                            userRef.child("username").setValue(username);
                            userRef.child("phoneNumber").setValue(phoneNumber);
                            userRef.child("password").setValue(password);
                            // Add more user details as needed
                        }

                        Toast.makeText(view.getContext(), "Successfully Signed Up!", Toast.LENGTH_SHORT).show();

                        // Navigate to the home screen or login screen if needed
                        Navigation.findNavController(view).navigate(R.id.DestLogin);
                    } else {
                        // Handle sign-up failure
                        // Log any exceptions for debugging
                        Log.e("SignUpFragment", "Sign up failed", task.getException());
                    }
                });
    }
}

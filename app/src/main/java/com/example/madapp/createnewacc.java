package com.example.madapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        Button BtnBTL = view.findViewById(R.id.BtnBTL);
        View.OnClickListener OCLBTL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestLogin);
            }
        };
        BtnBTL.setOnClickListener(OCLBTL);

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

        // Validate email, password, username, and phone number according to format
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(requireContext(), "Text field cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(requireContext(), "Password must be 6 characters or longer", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email or username is already in use
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email is already in use
                    Toast.makeText(requireContext(), "Email is already in use", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if username is in use
                    usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Username is already in use
                                Toast.makeText(requireContext(), "Username is already in use", Toast.LENGTH_SHORT).show();
                            } else {
                                // Sign up with Firebase
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(requireActivity(), task -> {
                                            if (task.isSuccessful()) {
                                                // Sign up success
                                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                                // You can now store additional user information in the Firebase Realtime Database
                                                if (user != null) {
                                                    String userId = user.getUid();
                                                    DatabaseReference userRef = usersRef.child(userId);
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

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle the error
                            Log.e("SignUpFragment", "Database error: " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("SignUpFragment", "Database error: " + databaseError.getMessage());
            }
        });
    }

    // Function to validate email address using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}

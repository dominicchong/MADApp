package com.example.madapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class EditProfileFragment extends Fragment {
    // UI components
    EditText usernameEditText, birthDateEditText, phoneNumberEditText;
    TextView emailTV;
    ImageButton editButton;
    ImageView profileView;

    // Activity result launchers for image operations
    ActivityResultLauncher<Intent> resultLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    // Selected image URI
    Uri selectedImageUri;

    // Firebase user and database references
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    String profilePicUri;
    boolean isImageRemoved = false;

    // Initialize user ID
    {
        assert user != null;
        userID = user.getUid();
    }

    // Database references
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
    DatabaseReference usersRef1 = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Retrieve references to UI components
        usernameEditText = view.findViewById(R.id.usernameTV);
        birthDateEditText = view.findViewById(R.id.birthDateTV);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberTV);
        emailTV = view.findViewById(R.id.emailTV);
        profileView = view.findViewById(R.id.profileView);
        editButton = view.findViewById(R.id.editButton);

        // Retrieve user data from Firebase
        retrieveUserData();

        // Register result launcher for picking images
        registerImagePickerResult();

        // Set click listener for editButton
        editButton.setOnClickListener(v -> showImageSourceOptions());

        // Set click listener for update button
        Button BtnUpdateProfile = view.findViewById(R.id.BtnUpdateProfile);
        BtnUpdateProfile.setOnClickListener(v -> {
            updateProfile(view);
        });
    }

    // Retrieve user data from Firebase
    private void retrieveUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Handle successful data retrieval
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String birthDate = dataSnapshot.child("birthDate").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    profilePicUri = dataSnapshot.child("profilePic").getValue(String.class);

                    // Set hints in EditText fields
                    usernameEditText.setHint(username);
                    emailTV.setText(email);
                    phoneNumberEditText.setHint(phoneNumber);
                    birthDateEditText.setHint(birthDate);
                    // Load and display the profile image using Glide
                    if (profilePicUri != null && !profilePicUri.isEmpty()) {
                        Glide.with(requireContext())
                                .load(Uri.parse(profilePicUri))
                                .into(profileView);
                    }
                }
                Toast.makeText(requireContext(), "Successfully obtained information", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the failure to retrieve data
                Toast.makeText(requireContext(), "Failed to obtain information. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateProfile(View view) {
        // Get updated information from EditText
        String newUsername = usernameEditText.getText().toString().trim();
        String newPhoneNumber = phoneNumberEditText.getText().toString().trim();
        String newBirthDate = birthDateEditText.getText().toString().trim();

        // Check which fields are modified
        boolean isUsernameModified = !newUsername.isEmpty();
        boolean isPhoneNumberModified = !newPhoneNumber.isEmpty();
        boolean isBirthDateModified = !newBirthDate.isEmpty();
        boolean isImageModified = selectedImageUri != null;

        // Update Realtime Database based on modified fields
        if (isUsernameModified) {
            usersRef1.orderByChild("username").equalTo(newUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Duplicates found, display a toast
                        Toast.makeText(requireContext(), "Duplicate username found!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), "Username is not updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        userRef.child("username").setValue(newUsername);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                    Toast.makeText(requireContext(), "Failed to check duplicate username. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (isPhoneNumberModified) {
            usersRef1.orderByChild("phoneNumber").equalTo(newPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Duplicate phone number found, display a toast
                        Toast.makeText(requireContext(), "Duplicate phone number found!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), "Phone Number is not updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        userRef.child("phoneNumber").setValue(newPhoneNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                    Toast.makeText(requireContext(), "Failed to check duplicate phone number. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (isBirthDateModified) {
            userRef.child("birthDate").setValue(newBirthDate);
        }

        // Update profilePic field with the image URI if it's modified
        if (isImageModified) {
            if (selectedImageUri != null) {
                String imageUriString = selectedImageUri.toString();
                Log.d("UpdateProfile", "Updating profilePic with: " + imageUriString);
                userRef.child("profilePic").setValue(imageUriString);
                Toast.makeText(requireContext(), "Updated image success", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(isImageRemoved) {
                // If the photo is removed, update profilePic to an empty string or null
                userRef.child("profilePic").setValue(null);
                Toast.makeText(requireContext(), "Removed image success", Toast.LENGTH_SHORT).show();
                isImageRemoved = false;
            }
        }

        // Navigate to Profile page when update button is clicked
        Navigation.findNavController(view).navigate(R.id.DestProfile);
    }

    // Display options for choosing image source
    private void showImageSourceOptions() {
        // AlertDialog to choose image source
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Image Source");
        String[] options = {"Pick from Gallery", "Take Photo", "Remove Photo"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    pickImage();
                    break;
                case 1:
                    if (checkCameraPermission()) {
                        takePhoto();
                    } else {
                        requestCameraPermission();
                    }
                case 2:
                    removePhoto();
                    break;
            }
        });
        builder.show();
    }

    // Check if camera permission is granted
    private boolean checkCameraPermission() {
        // Check camera permission
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Request camera permission
    private void requestCameraPermission() {
        // Request camera permission
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE
        );
    }

    // Handle camera permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Handle permission request result
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Pick an image from the gallery
    private void pickImage(){
        // Start image picker activity
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    // Take a photo using the device's camera
    private void takePhoto() {
        // Start camera activity
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    // Register result handlers for image picker and camera
    private void registerImagePickerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    try {
                        Uri imageUri = result.getData().getData();
                        profileView.setImageURI(imageUri);
                        selectedImageUri = imageUri;
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    try {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        // Convert Bitmap to Uri
                        Uri imageUri = getImageUri(requireContext(), photo);

                        // Set the Uri to selectedImageUri
                        selectedImageUri = imageUri;
                        profileView.setImageBitmap(photo);

                        // Save the image to a file if needed
                        // saveImageToFile(photo);
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), "Error taking photo", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    // Function to convert Bitmap to Uri
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null);
        return Uri.parse(path);
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            profileView.setImageURI(imageUri);
                        }catch (Exception e){
                            Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    // remove photo of user
    private void removePhoto() {
        // Check if there is a selected image
        if (profilePicUri != null) {
            // Create a confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to remove your photo?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Remove the image from the ImageView
                    profileView.setImageDrawable(null);

                    // Remove the image from the Firebase Realtime Database
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
                    databaseRef.child("profileImage").removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(requireContext(), "Photo removed success", Toast.LENGTH_SHORT).show();

                                    // Set the default image in the ImageView
                                    profileView.setImageResource(R.drawable.default_profile_img);

                                    // Reset the selectedImageUri variable
                                    selectedImageUri = null;

                                    isImageRemoved = true;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(requireContext(), "Failed to remove photo", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        } else {
            Toast.makeText(requireContext(), "There is no photo to remove", Toast.LENGTH_SHORT).show();
        }
    }


}

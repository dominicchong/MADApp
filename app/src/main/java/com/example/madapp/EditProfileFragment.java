package com.example.madapp;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    EditText usernameEditText, birthDateEditText, phoneNumberEditText, emailEditText;
    ImageButton editButton;
    ImageView profileView;
    ActivityResultLauncher<Intent> resultLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;
    Uri selectedImageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;


    {
        assert user != null;
        userID = user.getUid();
    }
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

        // Retrieve references to EditText fields
        usernameEditText = view.findViewById(R.id.usernameTV);
        birthDateEditText = view.findViewById(R.id.birthDateTV);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberTV);
        emailEditText = view.findViewById(R.id.emailTV);
        profileView = view.findViewById(R.id.profileView);
        editButton = view.findViewById(R.id.editButton);

        retrieveUserData();

        // Register result launcher for picking images
        registerImagePickerResult();

        // Set click listener for editButton
        editButton.setOnClickListener(v -> showImageSourceOptions());

        Button BtnUpdateProfile = view.findViewById(R.id.BtnUpdateProfile);
        BtnUpdateProfile.setOnClickListener(v -> {
//            String newUsername = usernameEditText.getText().toString().trim();
//            String newEmail = emailEditText.getText().toString().trim();
//            String newPhoneNumber = phoneNumberEditText.getText().toString().trim();
//
//            // Check for duplicate user
//            checkForDuplicateUser(newUsername);
//            checkForDuplicateEmail(newEmail);
//            checkForDuplicatePhoneNumber(newPhoneNumber);
            updateProfile(view);

//            if (!isDuplicateUsername && !isDuplicateEmail && !isDuplicatePhoneNumber) {
//                updateProfile(requireView());
//            }
//            else {
//                // Show a message that duplicates are found
//                Toast.makeText(requireContext(), "Duplicates found. Update aborted.", Toast.LENGTH_SHORT).show();
//            }

            // Add other logic here if needed
            // For example, update the profile if there are no duplicates
            // updateProfile(view);
        });
//        BtnUpdateProfile.setOnClickListener(v -> updateProfile(view));

//        Button BtnUpdateProfile = view.findViewById(R.id.BtnUpdateProfile);
//        View.OnClickListener OCLUpdateProfile = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.DestProfile);
//            }
//        };
//        BtnUpdateProfile.setOnClickListener(OCLUpdateProfile);

    }

    private void retrieveUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve data from the dataSnapshot
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String birthDate = dataSnapshot.child("birthDate").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
//                    String profilePicUri = dataSnapshot.child("profilePic").getValue(String.class);

                    // Set hints in EditText fields
                    usernameEditText.setHint(username);
                    emailEditText.setHint(email);
                    phoneNumberEditText.setHint(phoneNumber);
                    birthDateEditText.setHint(birthDate);
                    // Load and display the profile image using Glide
//                    if (profilePicUri != null && !profilePicUri.isEmpty()) {
//                        Glide.with(requireContext())
//                                .load(Uri.parse(profilePicUri))
//                                .into(profileView);
//                    }
                }
                Toast.makeText(requireContext(), "Information Obtain Success.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the failure to retrieve data
                Toast.makeText(requireContext(), "Information Obtain failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // This method is an example of where you might perform the query
    private void checkForDuplicateUser(String userInput) {

    }

    private void checkForDuplicateEmail(String newEmail) {

    }

    private void checkForDuplicatePhoneNumber(String newPhoneNumber) {

    }

    private void updateProfile(View view) {
        // Get updated information from EditText
        String newUsername = usernameEditText.getText().toString().trim();
        String newEmail = emailEditText.getText().toString().trim();
        String newPhoneNumber = phoneNumberEditText.getText().toString().trim();
        String newBirthDate = birthDateEditText.getText().toString().trim();
//        Integer newPhoneNumber = Integer.parseInt(phoneNumberEditText.getText().toString().trim());
//        Integer newBirthDate = Integer.parseInt(birthDateEditText.getText().toString().trim());

        // Check which fields are modified
        boolean isUsernameModified = !newUsername.isEmpty();
        boolean isEmailModified = !newEmail.isEmpty();
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
                }
            });
        }
        if (isEmailModified ) {
            usersRef1.orderByChild("email").equalTo(newEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Duplicate email found, display a toast
                        Toast.makeText(requireContext(), "Duplicate email found!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), "Email is not updated", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        userRef.child("email").setValue(newEmail);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
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
                }
            });
        }
        if (isBirthDateModified) {
            userRef.child("birthDate").setValue(newBirthDate);
        }
        // Update profilePic field with the image URI if it's modified
        if (isImageModified ) {
            String imageUriString = selectedImageUri.toString();
            Log.d("UpdateProfile", "Updating profilePic with: " + imageUriString);
            userRef.child("profilePic").setValue(imageUriString);
        }
//        else {
//            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
//        }

//        if(!isDuplicateUsername && !isDuplicateEmail && !isDuplicatePhoneNumber){
//            Toast.makeText(requireContext(), "Update completed successfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.DestProfile);
//        }
    }

    private void showImageSourceOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Image Source");
        String[] options = {"Pick from Gallery", "Take Photo"};
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
                    break;
            }
        });
        builder.show();
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

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
}

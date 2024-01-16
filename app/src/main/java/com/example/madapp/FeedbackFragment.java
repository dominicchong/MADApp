package com.example.madapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import android.Manifest;

public class FeedbackFragment extends Fragment {

    // Firebase user and database references
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    {
        assert user != null;
        userID = user.getUid();
    }
    DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

    // Activity result launchers and image URI
    ActivityResultLauncher<Intent> resultLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;
    Uri selectedImageUri;
    ImageView imageView;

    // Camera permission request code
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Connect with UI widgets
        RatingBar RateBarFeedback = view.findViewById(R.id.RatingBarFeedback);
        TextView TVRating = view.findViewById(R.id.TVRating);
        EditText ETFeedback = view.findViewById(R.id.ETFeedback);
        Button BtnSubmitFeedback = view.findViewById(R.id.BtnSubmitFeedback);
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageView = view.findViewById(R.id.imageView);

        // Rating bar listener to update the rating text
        RateBarFeedback.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TVRating.setText("Rating: " + rating);
            }
        });

        // Register activity result launchers
        registerImagePickerResult();

        // Set click listener for imageButton
        imageButton.setOnClickListener(v -> showImageSourceOptions());

        // Click listener for submit feedback button
        View.OnClickListener OCLSubmitFeedback = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the rating and feedback text
                float rating = RateBarFeedback.getRating();
                String feedbackText = ETFeedback.getText().toString();


                // Create a feedback object or map with the rating and feedback
                Map<String, Object> feedbackMap = new HashMap<>();
                feedbackMap.put("rating", rating);
                feedbackMap.put("feedback", feedbackText);

                // Convert Bitmap to Uri and add to feedbackMap
                if (selectedImageUri != null) {
                    // Convert the selected image to a Bitmap
                    Bitmap imageBitmap = getBitmapFromUri(selectedImageUri);
                    Uri imageUri = getImageUri(requireContext(), imageBitmap);
                    feedbackMap.put("imageUri", imageUri.toString());
                }
                // Validate the rating
                if(rating == 0){
                    Toast.makeText(getContext(), "Please provide a rating", Toast.LENGTH_SHORT).show();
                }else {
                    // Add the feedback to Realtime Database
                    feedbackRef.push().setValue(feedbackMap)
                            .addOnSuccessListener(aVoid -> {
                                // Handle successful feedback submission
                                String message = "Thank you for your feedback!";
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                                // Clear the rating and text input after click submit button
                                RateBarFeedback.setRating(0);
                                ETFeedback.setText("");
                                Navigation.findNavController(view).navigate(R.id.DestHome);
                            })
                            .addOnFailureListener(e -> {
                                // Handle failed feedback submission
                                Toast.makeText(getContext(), "Feedback submission failed. Please try again.", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        };
        BtnSubmitFeedback.setOnClickListener(OCLSubmitFeedback);
    }

    // Function to display options for selecting image source
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
                    takePhoto();
                    break;
            }
        });
        builder.show();
    }

    // Function to launch image picker
    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            resultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error picking image", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to launch camera for taking a photo
    private void takePhoto() {
        if (checkCameraPermission()) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
        } else {
            requestCameraPermission();
        }
    }

    // Function to check camera permission
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Function to request camera permission
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE
        );
    }

    // Handle camera permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch the camera
                takePhoto();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Register activity result launchers for image picking and camera
    private void registerImagePickerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri imageUri = data.getData();
                                imageView.setImageURI(imageUri);
                                selectedImageUri = imageUri;
                                // You can set the image to an ImageView or do other operations
                                // For example: imageView.setImageURI(imageUri);
                                Toast.makeText(requireContext(), "Image selected successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Image selection canceled", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error picking image", Toast.LENGTH_SHORT).show();
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

                        imageView.setImageBitmap(photo);

                        Toast.makeText(requireContext(), "Photo taken successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), "Error taking photo", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    // Function to convert Bitmap to Uri
    private Uri getImageUri(Context context, Bitmap bitmap) {
        if (bitmap == null) {
            // Handle the case where the Bitmap is null
            return null;
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null);
        return Uri.parse(path);
    }

    // Function to get Bitmap from Uri
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
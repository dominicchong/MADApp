package com.example.madapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class FeedbackFragment extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    {
        assert user != null;
        userID = user.getUid();
    }
    DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

    ActivityResultLauncher<Intent> resultLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;
    Uri selectedImageUri;

    ImageButton imageButton;

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
        // Connect with UI Widget RateBarFeedback, TVRating, ETFeedback, BtnFeedback
        RatingBar RateBarFeedback = view.findViewById(R.id.RatingBarFeedback);
        TextView TVRating = view.findViewById(R.id.TVRating);
        EditText ETFeedback = view.findViewById(R.id.ETFeedback);    // needed if you want to collect the feedback later
        Button BtnSubmitFeedback = view.findViewById(R.id.BtnSubmitFeedback);
        ImageButton imageButton = view.findViewById(R.id.imageButton);

        // The rating bar OnRatingBarChangeListener to change the rating whenever it is used by user
        RateBarFeedback.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TVRating.setText("Rating: " + rating);
            }
        });

        registerImagePickerResult();

        // Set click listener for imageButton
        imageButton.setOnClickListener(v -> showImageSourceOptions());

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
                if(rating==0 && feedbackText.isEmpty()){
                    Toast.makeText(getContext(), "Can't Submit as it is empty", Toast.LENGTH_SHORT).show();
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
//            @Override
//            public void onClick(View v) {
//                String message = "Thank you for your feedback! ";
//                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//
//                // Clear the rating and text input after click submit button
//                RateBarFeedback.setRating(0);
//                ETFeedback.setText("");
//            }
        };
        BtnSubmitFeedback.setOnClickListener(OCLSubmitFeedback);

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
                    takePhoto();
                    break;
            }
        });
        builder.show();
    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            resultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error picking image", Toast.LENGTH_SHORT).show();
        }
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
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri imageUri = data.getData();
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

                        // Save the image to a file if needed
                        // saveImageToFile(photo);
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
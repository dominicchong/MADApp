package com.example.madapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestCertificate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestCertificate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CFViewModel viewModel;

    private FirebaseAuth firebaseAuth;

    public DestCertificate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DestCertificate.
     */
    // TODO: Rename and change types and number of parameters
    public static DestCertificate newInstance(String param1, String param2) {
        DestCertificate fragment = new DestCertificate();
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
        return inflater.inflate(R.layout.fragment_dest_certificate, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView TVName = view.findViewById(R.id.TVName);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        // You can now store additional user information in the Firebase Realtime Database
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String username = String.valueOf(snapshot.child("username").getValue());
                    TVName.setText(username);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        Button BtnDownload = view.findViewById(R.id.BtnDownload);
        BtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadCertificate(view);
            }
        });

        Button BtnBack = view.findViewById(R.id.BtnBack);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestCarbonFootprint);
            }
        });
    }
    private void downloadCertificate(View view) {
        // Get the ImageView and TextView from the fragment's view
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView nameText = view.findViewById(R.id.TVName);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        // You can now store additional user information in the Firebase Realtime Database
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String username = String.valueOf(snapshot.child("username").getValue());
                    nameText.setText(username);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        nameText.setTextColor(Color.BLACK);

        // Create a Bitmap from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap originalBitmap = drawable.getBitmap();

        // Create a mutable copy of the Bitmap
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Create a Canvas to draw on the mutable Bitmap
        Canvas canvas = new Canvas(bitmap);

        // Set the background color of the canvas to ensure transparency
        canvas.drawColor(Color.BLACK);

        // Draw the image from the ImageView
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        // Calculate the position to center the text in the middle of the canvas
        float textX = (canvas.getWidth() - nameText.getWidth()) / 2f;
        float textY = (canvas.getHeight() + nameText.getHeight()) / 2f + 150;

        // Draw the text from the TextView onto the Bitmap at the calculated position
        canvas.drawText(nameText.getText().toString(), textX, textY, nameText.getPaint());

        // Use getExternalFilesDir for Android 10 and above
        File certificateFile;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            certificateFile = new File(requireContext().getExternalFilesDir(null), "certificate.png");
        } else {
            certificateFile = new File(requireContext().getExternalFilesDir(null), "certificate.png");
        }

        // Perform the download
        // For simplicity, we'll use a basic example with File I/O
        try {
            // Write the image to the file
            FileOutputStream fos = new FileOutputStream(certificateFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            // Display the correct path to the user
            String certificatePath = certificateFile.getAbsolutePath();
            Toast.makeText(requireContext(), "Image downloaded successfully. Path: " + certificatePath, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to download image.", Toast.LENGTH_SHORT).show();
        }
    }
}
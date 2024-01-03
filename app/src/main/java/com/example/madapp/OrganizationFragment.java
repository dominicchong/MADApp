package com.example.madapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizationFragment extends Fragment {

    private ConstraintLayout lowerPart;
    private ImageButton organizationImageButton1;
    private ImageButton organizationImageButton2;
    private ImageButton organizationImageButton3;
    private ImageButton organizationImageButton4;
    private ImageButton organizationImageButton5;
    private ImageButton organizationImageButton6;
    private TextView haveMoreOrganization;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrganizationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrganizationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizationFragment newInstance(String param1, String param2) {
        OrganizationFragment fragment = new OrganizationFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizations, container, false);

        // Initialize upper and lower parts

        lowerPart = view.findViewById(R.id.lowerpart);

        // Initialize ImageButton1 and set onClickListener
        organizationImageButton1 = view.findViewById(R.id.organizationButton1);
        organizationImageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for organizationImageButton1
                // Example: Open a website associated with the first organization
                openWebsiteForOrganization("https://www.gec.org.my/");
            }
        });

        // Initialize ImageButton2 and set onClickListener
        organizationImageButton2 = view.findViewById(R.id.organizationButton2);
        organizationImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for organizationImageButton2
                // Example: Open a website associated with the first organization
                openWebsiteForOrganization("http://ecoknights.org.my/");
            }
        });

        // Initialize ImageButton3 and set onClickListener
        organizationImageButton3 = view.findViewById(R.id.organizationButton1);
        organizationImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for organizationImageButton3
                // Example: Open a website associated with the first organization
                openWebsiteForOrganization("https://www.umlawsociety.com/ecolawgy");
            }
        });

        // Initialize ImageButton4 and set onClickListener
        organizationImageButton4 = view.findViewById(R.id.organizationButton1);
        organizationImageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for organizationImageButton4
                // Example: Open a website associated with the first organization
                openWebsiteForOrganization("https://www.greenpeace.org/");
            }
        });

        // Initialize ImageButton5 and set onClickListener
        organizationImageButton5 = view.findViewById(R.id.organizationButton1);
        organizationImageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for organizationImageButton5
                // Example: Open a website associated with the first organization
                openWebsiteForOrganization("http://www.mns.my/");
            }
        });

        // Initialize ImageButton6 and set onClickListener
        organizationImageButton6 = view.findViewById(R.id.organizationButton1);
        organizationImageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click for organizationImageButton6
                // Example: Open a website associated with the first organization
                openWebsiteForOrganization("http://www.trees.org.my/");
            }
        });

        haveMoreOrganization = view.findViewById(R.id.textViewMoreOrganizations);
        haveMoreOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleOrganizationsList("https://www.ensearch.org/global-gateway/environmental-ngos-in-malaysia/");
            }
        });


        return view;
    }

    private void openWebsiteForOrganization(String url) {
        // Create an Intent to open a web browser
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // Check if there is an app to handle the Intent
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Start the activity if there is an app to handle the Intent
            startActivity(intent);
        } else {
            // Handle the case where there is no app to handle the Intent
            // You can show a message or use a WebView to display the website within your app
            Toast.makeText(requireContext(), "No app to handle this action", Toast.LENGTH_SHORT).show();
        }
    }

    public void openGoogleOrganizationsList(String url) {

        // Create an Intent to open a web browser
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // Check if there is an app to handle the Intent
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Start the activity if there is an app to handle the Intent
            startActivity(intent);
        } else {
            // Handle the case where there is no app to handle the Intent
            // You can show a message or use a WebView to display the website within your app
            Toast.makeText(requireContext(), "No app to handle this action", Toast.LENGTH_SHORT).show();
        }
    }
}

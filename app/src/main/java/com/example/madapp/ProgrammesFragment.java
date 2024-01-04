package com.example.madapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgrammesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgrammesFragment extends Fragment {

    public static int ProgrammesPage = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgrammesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgrammesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgrammesFragment newInstance(String param1, String param2) {
        ProgrammesFragment fragment = new ProgrammesFragment();
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
        View view = inflater.inflate(R.layout.fragment_programmes, container, false);

        CardView CVProgramme1 = view.findViewById(R.id.CVProgramme1);
        CardView CVProgramme2 = view.findViewById(R.id.CVProgramme2);
        CardView CVProgramme3 = view.findViewById(R.id.CVProgramme3);
        CardView CVProgramme4 = view.findViewById(R.id.CVProgramme4);
        CardView CVProgramme5 = view.findViewById(R.id.CVProgramme5);

        CVProgramme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgrammesPage = 1;
                Navigation.findNavController(view).navigate(R.id.DestProgrammesDetails);
            }
        });

        CVProgramme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgrammesPage = 2;
                Navigation.findNavController(view).navigate(R.id.DestProgrammesDetails);
            }
        });

        CVProgramme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgrammesPage = 3;
                Navigation.findNavController(view).navigate(R.id.DestProgrammesDetails);
            }
        });

        CVProgramme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgrammesPage = 4;
                Navigation.findNavController(view).navigate(R.id.DestProgrammesDetails);
            }
        });

        CVProgramme5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgrammesPage = 5;
                Navigation.findNavController(view).navigate(R.id.DestProgrammesDetails);
            }
        });

        return view;
    }
}

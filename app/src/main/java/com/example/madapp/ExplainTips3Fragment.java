package com.example.madapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplainTips3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplainTips3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExplainTips3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExplainTips3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplainTips3Fragment newInstance(String param1, String param2) {
        ExplainTips3Fragment fragment = new ExplainTips3Fragment();
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
        return inflater.inflate(R.layout.fragment_explain_tips3, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView TVArticle3Link = view.findViewById(R.id.TVArticle3Link);

        TVArticle3Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomTabs("https://www.themomentum.com/articles/how-eating-local-foods-can-reduce-your-carbon-footprint");
            }
        });
    }
    private void openCustomTabs(String url) {
        // Create a CustomTabsIntent
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();

        // Launch the URL using Custom Tabs
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url));
    }
}
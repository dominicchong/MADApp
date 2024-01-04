package com.example.madapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgrammesDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgrammesDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgrammesDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgrammesDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgrammesDetails newInstance(String param1, String param2) {
        ProgrammesDetails fragment = new ProgrammesDetails();
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
        return inflater.inflate(R.layout.fragment_programmes_details, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView IVProgrammeImage = view.findViewById(R.id.IVProgrammeImage);
        TextView TVSummary = view.findViewById(R.id.TVSummary);
        TextView TVProgrammesDesc = view.findViewById(R.id.TVProgrammesDesc);
        TextView TVHowItWorks = view.findViewById(R.id.TVHowItWorks);
        TextView TVExplanation = view.findViewById(R.id.TVExplanation);
        TextView TVArticleWebLink = view.findViewById(R.id.TVArticleWebLink);

        switch (ProgrammesFragment.ProgrammesPage) {
            // Programme 1 is selected
            case 1:
                IVProgrammeImage.setImageResource(R.drawable.programmes1);
                TVSummary.setText("World Water Week 2024\n" + "25-29 August  Global online event");
                TVProgrammesDesc.setText("World Water Week 2024 is centred on water cooperation, for peace and security in its broadest sense. The theme, Bridging Borders: Water for a Peaceful and Sustainable Future, asks us to recognize the regional and global interconnectivity of communities and nations and underscores the collaborative effort needed to achieve a peaceful and sustainable future.");
                TVHowItWorks.setText("10 Ways to Celebrate World Water Week");
                TVExplanation.setText("1. Save water in obvious ways.\n" +
                        "Think: shorter showers and fewer baths and turning off the tap when you’re brushing your teeth, doing the dinner dishes, or cooking.\n" +
                        "\n" +
                        "2. Keep pollutants out of your water.\n" +
                        "Don’t pour obvious pollutants down the drain or into your toilet, such as oils, medicines and chemicals.\n" +
                        "\n" +
                        "3. Eat local.\n" +
                        "Shop locally for in-season ingredients and products, since these are usually made with less water.\n" +
                        "\n" +
                        "4. Protect nature.\n" +
                        "Use natural solutions that help reduce flooding and store water for future use. Some examples: planting a tree, installing a rain garden, or using rain barrels.\n" +
                        "\n" +
                        "5. Clean up the planet.\n" +
                        "Engage in local clean-ups of your community’s rivers, lakes, wetlands and beaches—or organize one yourself!\n" +
                        "6. Crack a book.\n" +
                        "Check out The Lazy Person’s Guide to Saving Water. This guide, put together by the UN Department of Social and Economic Affairs, offers simple solutions you can take with minimal effort, and a few for those who want to dive deeper. Get further informed by reading a book from the suggested SDG Book Club list.\n" +
                        "\n" +
                        "7. Shop for sustainable (not fast) fashion.\n" +
                        "Did you know it takes 10,000 liters of water to produce just one pair of jeans? This equates to how much the average person drinks in 10 years. Check out these sustainably minded fashion companies.\n" +
                        "\n" +
                        "8. Don’t waste food.\n" +
                        "When it comes to what’s left on your dinner plate, what you do with it can make a big difference. Approximately 1/3 of all food produced on the planet is either lost or wasted. Learn how to cut down on food waste with these 15 tips, from storing food the right way to picking “ugly” fruits and veggies. You’ll reduce the demand on agriculture, one of our biggest water consumers. And speaking of food …\n" +
                        "\n" +
                        "9. Eat plant-based meals a few times a week.\n" +
                        "According to the UN, “It typically takes between 790 and 1,320 liters of water to produce 2.2 pounds of rice, 528 liters for 2.2 pounds of soya, 237 liters for 2.2 pounds of wheat and 132 liters for 2.2 pounds of potatoes.” Compare that to an estimated 1,847 gallons of water to produce 1 pound of beef.\n" +
                        "\n" +
                        "10. Write to your legislators.\n" +
                        "Sit down and write a letter (bonus for a handwritten one!) to your legislators to ask for their support in providing clean water for all. Stress how off track we are as a planet in our attempt to meet Sustainable Development Goal 6: water and sanitation for all by 2030.");
                TVArticleWebLink.setText("https://www.worldwaterweek.org/");
                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://www.worldwaterweek.org/");
                    }
                });

                break;


            // programme 2 is selected
            case 2:
                IVProgrammeImage.setImageResource(R.drawable.programmes2);
                TVSummary.setText("Plastic Free July 2023\n" + "July 2023  Global event");
                TVProgrammesDesc.setText("Plastic Free July is an international campaign to raise awareness about the use of plastic and its effect on the environment.");
                TVExplanation.setText("The goal of this campaign is to encourage people to" +
                        " reduce their use of single-use plastics for the whole" +
                        " month. This includes plastic straws, cutlery, water" +
                        " bottles and shopping bags.\n" +
                        "\nIf you find it too difficult to go completely plastic-free" +
                        " for a whole month, try a smaller challenge: Avoid" +
                        " single-use plastic packaging only, or use reusable" +
                        " takeaway items (bags, bottles, straws and coffee" +
                        " cups), or come with your own challange.\n" +
                        "\nThe campaign was started in 2011 by Rebecca" +
                        " Prince-Ruiz and a small team in local government in" +
                        " Wes Australia, and is now one of the most" +
                        " influential environmental campaigns in the world." +
                        " Millions of people across the globe take part every" +
                        " year, with many committing to reducing plastic" +
                        " pollution far beyond the month of July.\n"+
                        "\nDate: July 2023\n" +
                        "Location: Global event\n" +
                        "Organized by: Plastic Free Foundation\n");
                TVArticleWebLink.setText("https://www.plasticfreejuly.org/");
                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://www.plasticfreejuly.org/");
                    }
                });

                break;

            case 3:
                IVProgrammeImage.setImageResource(R.drawable.programmes3);
                TVSummary.setText("Earth Day 2023\n" + "April 22  Global event");
                TVProgrammesDesc.setText("Earth Day is an annual event celebrated on April\n" +
                        "22nd to demonstrate support for environmental\n" +
                        "protection. It was first observed in 1970 and has\n" +
                        "since become a global event with over 1 billion\n" +
                        "participants in more than 193 countries. Earth Day\n" +
                        "aims to raise awareness of environmental issues\n" +
                        "and promote actions to protect our planet.");
                TVExplanation.setText("The history of Earth Day dates back to a time when\n" +
                        "environmental conce ns were not a priority for most" +
                        "people. The movement began in the United States" +
                        "when Senator Gaylord Nelson witnessed the\n" +
                        "devastation caused by an oil spill in Santa Barbara,\n" +
                        "California in 1969. He was inspired to create a\n" +
                        "national day of environmental education and\n" +
                        "activism, which became the first Earth Day in 1970.\n" +
                        "\nSince then, Earth Day has been celebrated annually\n" +
                        "with various events such as tree planting, recycling\n" +
                        "campaigns, and community cleanups. It has also\n" +
                        "served as a platform for political action, with\n" +
                        "environmental activists using the day to call for\n" +
                        "policy changes to address climate change, pollution," +
                        "and other environmental issues.\n" +
                        "\nIn conclusion, Earth Day is an important reminder of" +
                        "the need to protect our planet and take action to\n" +
                        "address environmental issues. It is a global event\n" +
                        "that has brought people together to work towards a" +
                        "common goal of sustainability and preserving our\n" +
                        "natural resources for future generations.\n" +
                        "\nDate: April 22\n" +
                        "Location: Global event");
                TVArticleWebLink.setText("https://education.nationalgeographic.org/resource/earth-day/");
                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://education.nationalgeographic.org/resource/earth-day/");
                    }
                });

                break;

            case 4:
                IVProgrammeImage.setImageResource(R.drawable.programmes4);
                TVSummary.setText("Veganuary - Try Vegan January\n" + "January 2024  Global event");
                TVProgrammesDesc.setText("Veganuary is a combination of the words \"vegan\"\n" +
                        " and \"January\" and it is an event that challenges\n" +
                        " people to try veganism for the month of January. The" +
                        " goal is to show people that veganism is easy and" +
                        " accessible, as well as share information about" +
                        " cruelty-free living.");
                TVExplanation.setText("If you find it too difficult to go vegan for 31 days\n" +
                        " straight, try a smaller challenge: Maybe just commit" +
                        " to going vegan for a few days, or change one meal a" +
                        " day to vegan meal, or go vegetarian and reduce meat" +
                        " only." +
                        " Veganism is a lifestyle that tries to exclude all forms" +
                        " of animal exploitation and cruelty. Vegans do not eat" +
                        " or use any animal products such as meat, eggs, dairy" +
                        " (milk, cheese, butter), honey, leather, cosmetics, wool" +
                        " and silk." +
                        " The benefits to eating vegan" +
                        " Vegan diet has a significant impact on our" +
                        " environment as well as animals in general. The " +
                        " production of animal food requires an intensive use" +
                        " of natural resources such as water and land while" +
                        " producing large amounts of greenhouse gas" +
                        " emissions.\n" +
                        "\nThe animals benefit by not being subjected to the" +
                        " cruel process of factory farming (overcrowded living" +
                        " conditions and stressful environments) or being" +
                        " hunted for their fur or skin." +
                        " Studies show that adopting a vegan diet can lessen" +
                        " the risk of heart disease, lower blood pressure and" +
                        " improve cholesterol. It might even reduce the risk of" +
                        " cancer and other chronic conditions.\n" +
                        "\nAbout Veganuary\n" +
                        "Veganuary was created in 2014 by founders Jane" +
                        " Land and Matthew Glover as part of their charity" +
                        " Veganuary. Since 2014, millions of participants have" +
                        " taken part in the Veganuary pledge." +
                        " To officially sign up, visit veganuary.com. Registrants" +
                        " will get access to the official veganuary starter kit," +
                        " daily recipes, nutrition trackers, meal plans, tips on" +
                        " dining out and a 31-day email series.\n" +
                        "\nDate: January 2023\n" +
                        "Location: Global event\n" +
                        "Organized by: Veganuary.");
                TVArticleWebLink.setText("https://veganuary.com/");
                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://veganuary.com/");
                    }
                });

                break;

            case 5:
                IVProgrammeImage.setImageResource(R.drawable.programmes5);
                TVSummary.setText("World Bicycles Day\n" + "3 June  Global event");
                TVProgrammesDesc.setText("World Bicycle Day draws attention to the benefits of using the bicycle — a simple, affordable, clean and environmentally fit sustainable means of transportation.\n " +
                        "The bicycle contributes to cleaner air and less congestion and makes education, health care and other social services more accessible to the most vulnerable populations.\n" +
                        "A sustainable transport system that promotes economic growth, reduces inequalities while bolstering the fight against climate change is critical to achieving the Sustainable Development Goals.");
                TVHowItWorks.setText("Why celebrate the bicycle?");
                TVExplanation.setText("Regular physical activity of moderate intensity – such as walking, cycling, or doing sports – has significant benefits for health. At all ages, the benefits of being physically active outweigh potential harm, for example through accidents. Some physical activity is better than none. By becoming more active throughout the day in relatively simple ways, people can quite easily achieve the recommended activity levels.\n" +
                        "\n" +
                        "According to the World Health Organization (WHO), safe infrastructure for walking and cycling is also a pathway for achieving greater health equity. For the poorest urban sector, who often cannot afford private vehicles, walking and cycling can provide a form of transport while reducing the risk of heart disease, stroke, certain cancers, diabetes, and even death. Accordingly, improved active transport is not only healthy; it is also equitable and cost-effective.\n" +
                        "\n" +
                        "Meeting the needs of people who walk and cycle continues to be a critical part of the mobility solution for helping cities de-couple population growth from increased emissions, and to improve air quality and road safety. The COVID-19 pandemic has also led many cities to rethink their transport systems.");
                TVArticleWebLink.setText("https://www.un.org/en/observances/bicycle-day");
                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://www.un.org/en/observances/bicycle-day");
                    }
                });

                break;
        }
    }

    private void openCustomTabs(String url) {
        // Create a CustomTabsIntent
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();

        // Launch the URL using Custom Tabs
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url));
    }


}

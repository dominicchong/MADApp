package com.example.madapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleDetailsFragment newInstance(String param1, String param2) {
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
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
        return inflater.inflate(R.layout.fragment_article_details, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView TVArticleTitle = view.findViewById(R.id.TVArticleTitle);
        TextView TVArticleAuthor = view.findViewById(R.id.TVArticleAuthor);
        ImageView IVArticleDescImage = view.findViewById(R.id.IVArticleDescImage);
        TextView TVArticleDesc = view.findViewById(R.id.TVArticleDesc);
        TextView TVArticleWebLink = view.findViewById(R.id.TVArticleWebLink);

        switch (ArticleReforestFragment.articlePage) {
            // article 1 is selected
            case 1:
                TVArticleTitle.setText("Tree Planting Is Booming. Here’s How That Could Help, or Harm, the Planet");
                TVArticleAuthor.setText("By Catrin Einhorn");
                IVArticleDescImage.setImageResource(R.drawable.article1_img);
                TVArticleDesc.setText("A tree planted for every T-shirt purchased. For every bottle of wine. For every swipe of a credit card. Trees planted by countries to meet global pledges and by companies to bolster their sustainability records.\n" +
                        "\n" +
                        "As the climate crisis deepens, businesses and consumers are joining nonprofit groups and governments in a global tree planting boom. Last year saw billions of trees planted in scores of countries around the world. These efforts can be a triple win, providing livelihoods, absorbing and locking away planet-warming carbon dioxide, and improving the health of ecosystems.\n" +
                        "\n" +
                        "But when done poorly, the projects can worsen the very problems they were meant to solve. Planting the wrong trees in the wrong place can actually reduce biodiversity, speeding extinctions and making ecosystems far less resilient.\n" +
                        "\n" +
                        "Addressing biodiversity loss, already a global crisis akin to climate change, is becoming more and more urgent. Extinction rates are surging. An estimated million species are at risk of disappearing, many within decades. And ecosystem collapse doesn’t just threaten animals and plants; it imperils the food and water supplies that humans rely on.\n" +
                        "\n" +
                        "Amid that worsening crisis, companies and countries are increasingly investing in tree planting that carpets large areas with commercial, nonnative species in the name of fighting climate change. These trees sock away carbon but provide little support to the webs of life that once thrived in those areas.\n" +
                        "\n" +
                        "“You’re creating basically a sterile landscape,” said Paul Smith, who runs Botanic Gardens Conservation International, an umbrella group that works to prevent plant extinctions. “If people want to plant trees, let’s also make it a positive for biodiversity.”\n" +
                        "\n" +
                        "There’s a rule of thumb in the tree planting world: One should plant “the right tree in the right place.” Some add, “for the right reason.”\n" +
                        "\n" +
                        "But, according to interviews with a range of players — scientists, policy experts, forestry companies and tree planting organizations — people often disagree on what “right” means. For some, it’s big tree farms for carbon storage and timber. For others, it’s providing fruit trees to small-scale farmers. For others still, it’s allowing native species to regenerate.\n" +
                        "\n" +
                        "The best efforts try to address a range of needs, according to restoration experts, but it can be hard to reconcile competing interests.\n\n" +
                        "“It’s kind of the Wild West,” said Forrest Fleischman, a professor of environmental policy at the University of Minnesota." +
                        "\n");

                TVArticleWebLink.setText("https://www.nytimes.com/2022/03/14/climate/tree-planting-reforestation-climate.html");

                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://www.nytimes.com/2022/03/14/climate/tree-planting-reforestation-climate.html");
                    }
                });

                break;


            // article 2 is selected
            case 2:
                TVArticleTitle.setText("Reforestation means more than just planting trees");
                TVArticleAuthor.setText("By Elizabeth Pennisi");
                IVArticleDescImage.setImageResource(R.drawable.article2_img);
                TVArticleDesc.setText("The world is set to get a lot greener over the next 10 years. The United Nations has designated 2021–30 the Decade on Ecosystem Restoration, and many countries, with help from donors, have launched ambitious programs to restore forests in places where they were chopped down or degraded. At the U.N. Climate Change Conference in Egypt last week, the European Union and 26 nations pledged $16 billion in support of forests, banking on trees’ ability to slow climate change by storing carbon. A significant chunk will be spent on reforestation.\n" +
                        "\n" +
                        "“It’s a really exciting time,” says Susan Cook-Patton, a restoration researcher at the Nature Conservancy. “We’ve got an opportunity to really restore forests at scale, and that’s really encouraging.” But little is known about how best to achieve that.\n" +
                        "\n" +
                        "Between 2000 and 2020, the amount of forest increased by 1.3 million square kilometers, an area larger than Peru, according to the World Resources Institute, with China and India leading the way. The institute estimates 9% of these new forests are plantations—dense aggregations dominated by a single species that are less beneficial for biodiversity and long-term carbon storage than natural forests—but other specialists think the share is higher, particularly in tropical regions." +
                        "\n" +
                        "Many reforestation projects focus on the number of trees planted, with less attention to how well they survive, how diverse the resulting forests are, or how much carbon they store. “We still know relatively little about what is working well or not, where, and why,” says Laura Duncanson of the University of Maryland, College Park, who studies carbon storage in forests.\n" +
                        "\n" +
                        "A theme issue of the Philosophical Transactions of the Royal Society published last week offers guidance, in the form of 20 articles—both original research and reviews. One in-depth look at reforestation projects in South and Southeast Asia details the challenge. Co-editor Lindsay Banin, a forest ecologist at the UK Centre for Ecology & Hydrology, and her colleagues examined data on how well newly planted trees survived at 176 reforested sites that differed in soil and environmental conditions as well as in what was planted. In some places, fewer than one in five saplings survived, and on average only 44% lasted more than 5 years.\n" +
                        "\n" +
                        "The study did offer one encouraging hint: When seedlings were planted near mature trees, an average of 64% survived, possibly because those spots were not as degraded. Other research has shown that measures such as fencing out cattle and improving soil conditions can boost saplings’ chances of survival as well, but they can be costly." +
                        "\n");

                TVArticleWebLink.setText("https://www.science.org/content/article/reforestation-means-just-planting-trees");

                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://www.science.org/content/article/reforestation-means-just-planting-trees");
                    }
                });

                break;


            // article 3 is selected
            case 3:
                TVArticleTitle.setText("Tree planting 'has mind-blowing potential' to tackle climate crisis");
                TVArticleAuthor.setText("By Marina Hyde");
                IVArticleDescImage.setImageResource(R.drawable.article3_img);
                TVArticleDesc.setText("Planting billions of trees across the world is one of the biggest and cheapest ways of taking CO2 out of the atmosphere to tackle the climate crisis, according to scientists, who have made the first calculation of how many more trees could be planted without encroaching on crop land or urban areas.\n" +
                        "\n" +
                        "As trees grow, they absorb and store the carbon dioxide emissions that are driving global heating. New research estimates that a worldwide planting programme could remove just under one-third of all the emissions from human activities that remain in the atmosphere today, a figure the scientists describe as “mind-blowing”.\n" +
                        "\n" +
                        "The analysis found there are 1.7bn hectares of treeless land on which 1.2tn native tree saplings would naturally grow. That area is about 11% of all land and equivalent to the size of the US and China combined. Tropical areas could have 100% tree cover, while others would be more sparsely covered, meaning that on average about half the area would be under tree canopy.\n" +
                        "\n" +
                        "The scientists specifically excluded all fields used to grow crops and urban areas from their analysis. But they did include grazing land, on which the researchers say a few trees can also benefit sheep and cattle.\n" +
                        "\n" +
                        "“This new quantitative evaluation shows [forest] restoration isn’t just one of our climate change solutions, it is overwhelmingly the top one,” said Prof Tom Crowther at the Swiss university ETH Zürich, who led the research. “What blows my mind is the scale. I thought restoration would be in the top 10, but it is overwhelmingly more powerful than all of the other climate change solutions proposed.”\n" +
                        "\n" +
                        "Crowther emphasised that it remains vital to reverse the current trends of rising greenhouse gas emissions from fossil fuel burning and forest destruction, and bring them down to zero. He said this is needed to stop the climate crisis becoming even worse and because the forest restoration envisaged would take 50-100 years to have its full effect of removing 200bn tonnes of carbon." +
                        "But tree planting is “a climate change solution that doesn’t require President Trump to immediately start believing in climate change, or scientists to come up with technological solutions to draw carbon dioxide out of the atmosphere”, Crowther said. “It is available now, it is the cheapest one possible and every one of us can get involved.” Individuals could make a tangible impact by growing trees themselves, donating to forest restoration organisations and avoiding irresponsible companies, he added.\n" +
                        "\n" +
                        "Other scientists agree that carbon will need to be removed from the atmosphere to avoid catastrophic climate impacts and have warned that technological solutions will not work on the vast scale needed.\n" +
                        "\n" +
                        "Jean-François Bastin, also at ETH Zürich, said action was urgently required: “Governments must now factor [tree restoration] into their national strategies.”" +
                        "\n");


                TVArticleWebLink.setText("https://www.theguardian.com/environment/2019/jul/04/planting-billions-trees-best-tackle-climate-crisis-scientists-canopy-emissions");

                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://www.theguardian.com/environment/2019/jul/04/planting-billions-trees-best-tackle-climate-crisis-scientists-canopy-emissions");
                    }
                });

                break;


            // article 4 is selected
            case 4:
                TVArticleTitle.setText("Reforestation: Pros, Cons And Effects On Planet");
                TVArticleAuthor.setText("By Kateryna Sergieieva");
                IVArticleDescImage.setImageResource(R.drawable.article4_img);
                TVArticleDesc.setText("Reforestation in a sustainable paradigm is done primarily to restore environmental balance rather than to serve industrial purposes. Because trees take in carbon dioxide and release oxygen, this practice can actually counteract global warming. Not everything is sunshine and rainbows, though; the benefits of reforestation to the environment won’t completely materialize for decades. We owe it to future generations to leave them a world with forests, so we must not put off our efforts but instead immediately begin reforestation planning, implementation, and monitoring." +
                        "\n\n" +
                        "◆ What Is Reforestation And Its Causes\n" +
                        "Reforestation is the act of restoring trees in an area where their population has been reduced due to either natural causes or human intervention. Reforestation after a wildfire by intentionally planting new trees or encouraging the natural recovery of degraded forests are just some examples of reforestation.\n" +
                        "\n" +
                        "The necessity for reforestation can unfortunately arise for a variety of reasons. We can classify them into two broad categories:\n" +
                        "• Human intervention: tree cutting, mining, clearing land for agriculture and construction purposes.\n" +
                        "• Natural disasters: droughts, wildfires, floods, storms, pest and disease infestations. The frequency of extreme weather events is on the rise as a result of climate change, which is driving up average temperatures and increasing the variability of precipitation." +
                        "\n\n" +
                        "Reforestation is especially important in areas subject to commercial logging. There are several purposes for reforestation on these lands:" +
                        "\n" +
                        "▪ restoration after harvesting merchantable timber;\n" +
                        "▪ compensation after the land expansion due to human activities;\n" +
                        "▪ refreshing after forest aging;\n" +
                        "▪ regeneration after natural calamities;\n" +
                        "▪ maintaining ecosystem balance and biodiversity;\n" +
                        "▪ providing habitats for eco-communities, etc.\n\n" +
                        "The best reforestation practices recommend immediate planting once a forest is cut down or destroyed. Thus, tree-felling companies are to restore the balance by planting new trees after logging, according to governmental regulations in many countries.\n" +
                        "\n" +
                        "The UN New York Declaration of 2014 obliged countries to reduce deforestation twice by 2020 and halt it by 2030. However, its annual rate almost doubled instead." +
                        "\n\n" +
                        "◆ Benefits Of Reforestation\n" +
                        "Ecosystems can better withstand future stresses from things like climate change and wildfires when they have been reforested. The most significant benefits that can be gained via reforestation are as follows.\n" +
                        "\n" +
                        "◆ Reforestation And Climate Change Mitigation\n" +
                        "Excessive carbon release is a major driver for global warming, and this is where we can use reforestation to combat climate change. How does reforestation affect the carbon cycle? In the process of photosynthesis, trees absorb carbon and convert it into nutrients required for their development. The younger the forest, the more carbon it can store. Basically, kiln-dried wood is about 50% carbon, which means that trees’ ability to accumulate carbon is impressive." +
                        "Furthermore, much carbon is sequestered in forest soils. Thus, reforestation helps to reduce the effects of climate change all across the world in the long run." +
                        "\n");


                TVArticleWebLink.setText("https://eos.com/blog/reforestation/");

                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://eos.com/blog/reforestation/");
                    }
                });

                break;


            // article 5 is selected
            case 5:
                TVArticleTitle.setText("Responsible reforestation: how to grow trees to solve climate change");
                TVArticleAuthor.setText("By Elliot Coad");
                IVArticleDescImage.setImageResource(R.drawable.article5_img);
                TVArticleDesc.setText("We know that planting trees is an excellent way to restore the planet. But, when applying tree planting as a climate solution, organisations have to take steps to ensure that the work is being done properly.\n" +
                        "\n" +
                        "We want the trees that Ecologi has funded for planting through our platform to be the most well-loved trees that ever did grow – and that means reforesting responsibly.\n" +
                        "\n" +
                        "For example, this means planting ecologically appropriate trees in the right places, coordinating with local communities, and ensuring that trees are protected from outside threats (both natural and man made) so that they can survive and thrive, and help to support biodiversity and carbon sequestration." +
                        " Forest restoration and reforestation are well-known climate solutions because of trees’ ability to absorb carbon from the atmosphere and store it in their biomass.\n\n" +
                        "This carbon absorption ability is entirely natural, and far exceeds the efficiency of any man made carbon removal tech that currently exists.\n\n" +
                        "As well as carbon sequestration though, planting trees is a wonderful thing to do for a number of other reasons:\n" +
                        "▪ Trees provide habitat, shade, shelter, and food for local wildlife. Planting trees therefore supports biodiversity and helps to prevent species loss.\n" +
                        "▪ Trees can provide products that local people can use and sell – like fruits and nuts – without harming the trees themselves.\n" +
                        "▪ Degraded soil increases the risk of landslides and desertification, and the roots of trees help to stabilise the soil and minimise this risk for local communities.\n" +
                        "▪ Trees can help to purify air and water sources, providing better health outcomes for local communities.\n" +
                        "▪ Coastal tree species like mangroves provide excellent natural barriers which protect the shoreline from storms.\n" +
                        "▪ Trees make us feel better: research suggests they have a positive impact on our mental health." +
                        "\n");


                TVArticleWebLink.setText("https://ecologi.com/articles/in-depth/responsible-reforestation-faqs");

                TVArticleWebLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCustomTabs("https://ecologi.com/articles/in-depth/responsible-reforestation-faqs");
                    }
                });

                break;


            // when no article is selected
            default:
                return;

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
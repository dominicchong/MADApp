package com.example.madapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsApiTask extends AsyncTask<Void, Void, String> {

    private static final String API_URL = "https://climate-news-feed.p.rapidapi.com/page/1?limit=15";
    private static final String API_KEY = "10774329cdmsh26219e20677757ap199f7djsn97fa2cd569dc";

    private NewsRecyclerAdapter newsAdapter;
    private List<Article> articleList;

    public NewsApiTask(NewsRecyclerAdapter newsAdapter, List<Article> articleList) {
        this.newsAdapter = newsAdapter;
        this.articleList = articleList;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // Define the URL for the request
            URL url = new URL(API_URL);

            // Open a connection to the URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Set up the request method
            urlConnection.setRequestMethod("GET");

            // Set the RapidAPI key in the request headers
            urlConnection.setRequestProperty("X-RapidAPI-Key", API_KEY);

            // Read the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);

                }

                return response.toString();

            }
        } catch (Exception e) {
            Log.e("NewsApiTask", "Error in HTTP request", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // Parse the result and update newsData
            List<Article> parsedData = parseResult(result);

            // Update the RecyclerView data
            articleList.clear();
            articleList.addAll(parsedData);
            newsAdapter.notifyDataSetChanged();

            Log.d("NewsApiTask", "Response: " + result);
        }
    }

    // Example method to parse the API response
    private List<Article> parseResult(String response) {
        List<Article> parsedData = new ArrayList<>();

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONArray articlesArray = responseObject.getJSONArray("articles");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject articleObject = articlesArray.getJSONObject(i);

                String title = articleObject.getString("title");
                String url = articleObject.getString("url");
                String source = articleObject.getString("source");
                String thumbnail = articleObject.getString("thumbnail");

                Article newsArticle = new Article(title, url, source,thumbnail); //imageUrl;
                parsedData.add(newsArticle);
            }
        } catch (JSONException e) {
            Log.e("NewsApiTask", "Error parsing JSON", e);
        }

        return parsedData;
    }

}

package com.example.lintangapp.github;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {
    private final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    private final static String PARAM_QUERY = "q";
    private final static String PARAM_SORT = "sort";
    private final static String SORT_BY = "stars";

    private static URL buildURL(String gitHubSearchQuery){
        Uri buildUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, gitHubSearchQuery)
                .appendQueryParameter(PARAM_SORT, SORT_BY)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    private static String getRepositoryFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in =urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

    private static List<GithubView> jsonFromatter(String jsonResponse){
        List<GithubView> repositoryList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.getJSONArray("items");

            int dataLen = 50;
            if (items.length()<dataLen){
                dataLen = items.length();
            }
            for (int i = 0;i < dataLen;i++){
                JSONObject currentRepo = items.getJSONObject(i);
                String repoName = currentRepo.getString("name");
                String repoStars = currentRepo.getString("stargazers_count");

                Log.v("Data","Number" + 1);

                GithubView repository = new GithubView(repoName,repoStars);
                repositoryList.add(repository);
            }
        } catch (JSONException e){
            Log.v("Network", "cant read JSON");
        }
        return repositoryList;
    }

    public static List<GithubView> getDataFromApi (String query) throws IOException {
        URL apiURL = buildURL(query);
        String jsonResponse = getRepositoryFromHttpUrl(apiURL);
        List<GithubView> repositoryList = jsonFromatter(jsonResponse);
        return repositoryList;
    }
}

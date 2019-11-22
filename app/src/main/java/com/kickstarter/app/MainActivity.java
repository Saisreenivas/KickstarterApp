package com.kickstarter.app;

import Adapter.ProjectListAdapter;
import Model.Project;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final String url= "http://starlord.hackerearth.com/kickstarter";
    private ArrayList<Project> fullData;
    private ProjectListAdapter projectListAdapter;
    private int limit = 0;
    private ArrayList<Project> initialData;
    private SearchView searchView;
    private ImageButton sortBtn, filterBtn;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        searchView = findViewById(R.id.search_view);
        sortBtn = findViewById(R.id.sort_btn);
        filterBtn = findViewById(R.id.filter_btn);
        /*TODO search the data by name- Implementation*/
        /*TODO sort the data by time or alphabetically- Implementation*/
        /*TODO filter the data by "No of Backers"- Implementation*/


        /*TODO can add Portfolio activity comprising awesoome work you have done in android*/

        new AddDataForContent(getParent(), recyclerView, progressBar).execute(url);
//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//            }
//        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                limit += 20;
//                initialData.addAll(getList(limit, 20));
//                projectListAdapter.notifyDataSetChanged();
//            }
//        });
        /*TODO onscroll listener in recyclerview*/
    }


    private class AddDataForContent extends AsyncTask<String, Boolean, ArrayList<Project>>{
        public AddDataForContent(Activity parent, RecyclerView recyclerView, ProgressBar progressBar) {

        }


        @Override
        protected ArrayList<Project> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    fullData = convertStreamToString(conn.getInputStream());
                    return fullData;
                }

            } catch (MalformedURLException e) {
                Log.v("MainActivity", "URL Error: "+ e.getMessage());
            } catch (IOException e) {
                Log.v("MainActivity", "HttpURLConnection Error(I/O): " + e.getMessage());
            }

            return fullData;
        }

        @Override
        protected void onPostExecute(ArrayList<Project> arrayList) {
            super.onPostExecute(arrayList);

            setUpData(arrayList, 20);
        }
    }

    private void setUpData(ArrayList arrayList, int i) {
        initialData = getList(limit,20);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        projectListAdapter = new ProjectListAdapter(getApplicationContext(), initialData, 20);
        recyclerView.setAdapter(projectListAdapter);
        projectListAdapter.notifyDataSetChanged();
    }

    private ArrayList<Project> getList(int totalTillNow, int extra) {
        ArrayList<Project> ar = new ArrayList<>();
        for(int i=totalTillNow;i<extra;i++){
            ar.add((Project) fullData.get(i));
        }

        return ar;
    }

    private ArrayList<Project> convertStreamToString(InputStream inputStream) {
        ArrayList<Project> projectArrayList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line= null;
        StringBuilder sb = new StringBuilder();

        try {
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Log.v("MainActivity", "I/O(readLine()) error: " + e.getMessage());

        }

        line = sb.toString();
        try {
            JSONArray jsonArray = new JSONArray(line);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jb = jsonArray.getJSONObject(i);
                Project project = new Project(jb.getInt("s.no")
                        , jb.getInt("amt.pledged")
                        , jb.getString("blurb")
                        , jb.getString("by")
                        , jb.getString("country")
                        , jb.getString("currency")
                        , jb.getString("end.time")
                        , jb.getString("location")
                        , jb.getInt("percentage.funded")
                        , jb.getInt("num.backers")
                        , jb.getString("state")
                        , jb.getString("title")
                        , jb.getString("type")
                        , jb.getString("url")
                        );
                projectArrayList.add(project);
            }
        } catch (JSONException e) {
            Log.v("MainActivity", "JSON error: " + e.getMessage());
        }

        return projectArrayList;
    }
}

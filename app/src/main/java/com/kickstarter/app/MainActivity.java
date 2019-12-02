package com.kickstarter.app;

import Adapter.ProjectListAdapter;
import Model.Project;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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
import java.util.Collections;

import static Adapter.ProjectListAdapter.FEW_BACKERS;
import static Adapter.ProjectListAdapter.FILTER_VIEW;
import static Adapter.ProjectListAdapter.HUGE_BACKERS;
import static Adapter.ProjectListAdapter.LOW_BACKERS;
import static Adapter.ProjectListAdapter.MEDIUM_BACKERS;
import static Adapter.ProjectListAdapter.SEARCH_VIEW;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final String url= "http://starlord.hackerearth.com/kickstarter";
    private ArrayList<Project> fullData;
    private ProjectListAdapter projectListAdapter;
    private int limit = 0;
    private ArrayList<Project> initialData;
    private SearchView searchView;
//    private

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        sortBtn
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
            .getActionView();
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setMaxWidth(?);
//        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search by name");
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
//        searchView.setLayoutParams(params);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                if(fullData.contains(s)){
////                    projectListAdapter
//                }
                Log.v(TAG, "onQueryTextSubmit: " + s );
                projectListAdapter.getFilter(SEARCH_VIEW).filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.v(TAG, "onQueryTextChange: "+s );
                projectListAdapter.getFilter(SEARCH_VIEW).filter(s);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }else if(id == R.id.action_filter_few){
            Log.v(TAG, "item click: " + item.getTitle());
            projectListAdapter.getFilter(FILTER_VIEW).filter(FEW_BACKERS);
        }else if(id == R.id.action_filter_low){
            projectListAdapter.getFilter(FILTER_VIEW).filter(LOW_BACKERS);
        }else if(id == R.id.action_filter_medium){
            projectListAdapter.getFilter(FILTER_VIEW).filter(MEDIUM_BACKERS);
        }else if(id == R.id.action_filter_high){
            projectListAdapter.getFilter(FILTER_VIEW).filter(HUGE_BACKERS);
        }else if(id == R.id.action_sort_az) {
            Log.v(TAG, "item click: " + item.getTitle());
            projectListAdapter.getSorted("az");

        }else if(id == R.id.action_sort_za) {
            Log.v(TAG, "item click: " + item.getTitle());
            projectListAdapter.getSorted("za");
        }else if(id == R.id.action_sort_time){
            Log.v(TAG, "item click: " + item.getTitle());
            projectListAdapter.getSorted("time");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("IPO's");
        toolbar.setBackgroundColor(Color.parseColor("#f6f8fa"));
        setTitleColor(Color.parseColor("#f6f8fa"));
//        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
//        getWindow().setStatusBarColor();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.parseColor("#f6f8fa"));
//                    darkenColor(
//                            ContextCompat.getColor(MainActivity.this, color)));
//        }

        /*TODO mock and Unit testing, mokito.org*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        whiteNotificationBar(recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

//        searchView = findViewById(R.id.search_view);
//        sortBtn = findViewById(R.id.sort_btn);
//        filterBtn = findViewById(R.id.filter_btn);



        /*TODO can add Portfolio activity comprising awesoome work you have done in android*/

        startBackgroundWork();
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

    private void startBackgroundWork() {
        new AddDataForContent(getParent(), recyclerView, progressBar).execute(url);
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
            if(arrayList == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("No Data Found!");
                builder.setMessage("Unable to resolve host. Retry?");
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startBackgroundWork();
                    }
                });
                builder.create();
                builder.show();
            }else{
                Log.v("MainAcitivity", "onPostExecute: " + arrayList.toString());
                setUpData(arrayList, 20);
            }
        }
    }

    private void setUpData(ArrayList arrayList, int i) {
//        initialData = getList(limit,20);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        projectListAdapter = new ProjectListAdapter(MainActivity.this, arrayList, 20);
        recyclerView.setAdapter(projectListAdapter);
        progressBar.setVisibility(View.GONE);
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
                int backers;
                try{
                    backers = Integer.parseInt(jb.getString("num.backers"));
                }catch (Exception e){
                    backers = 0;
                }

                Project project = new Project(jb.getInt("s.no")
                        , jb.getInt("amt.pledged")
                        , jb.getString("blurb")
                        , jb.getString("by")
                        , jb.getString("country")
                        , jb.getString("currency")
                        , jb.getString("end.time")
                        , jb.getString("location")
                        , jb.getInt("percentage.funded")
                        , backers
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

package com.kickstarter.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.google.gson.Gson;

import Model.Project;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

public class DetailedItemActivity extends AppCompatActivity {

    private static final String TAG = DetailedItemActivity.class.getSimpleName();
    Project project;
    TextView title, pledge, backers, content, written_by, location, state,
    type, currency, funded_perc;
    private Slide slide;

    @Override
    protected void onStart() {
        super.onStart();
//        overridePendingTransition(
//                R.anim.slide_in_right, R.anim.slide_in_left);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_detailed));
        setTitle("");
        setTitleColor(Color.parseColor("#f6f8fa"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setElevation(10f);

        setAnimation();


        title = findViewById(R.id.item_detailed_heading);
        pledge = findViewById(R.id.item_detailed_pledge);
        backers = findViewById(R.id.item_detailed_backers);
        content = findViewById(R.id.item_detailed_content);
        written_by = findViewById(R.id.item_detailed_written_by);
        location = findViewById(R.id.item_detailed_location);
        state = findViewById(R.id.item_detailed_state);
        type = findViewById(R.id.item_detailed_type);
        currency = findViewById(R.id.item_detailed_currency);
        funded_perc = findViewById(R.id.item_detailed_percentage_funded);

        Intent i = getIntent();
        if(i !=null){
            String alpha = i.getStringExtra("project");
            project = (new Gson()).fromJson(alpha, Project.class);
            setData(project);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v(this.getClass().getSimpleName(), "Back Pressed" );
        finish();
        DetailedItemActivity.this.overridePendingTransition(
                R.anim.slide_out_left,R.anim.slide_out_right);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i(TAG, "You have forgotten to specify the parentActivityName in the AndroidManifest!");
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setData(Project project) {
        title.setText(project.getTitle());
        pledge.setText("Amount Pledged: " + project.getAmountPledged() + "");
        backers.setText("Number of Backers: " + project.getNumOfBackers() + "");
        content.setText(project.getBlurb());
        written_by.setText("Written By:" + project.getWrittenBy());
        location.setText("Location: " + project.getLocation());
        state.setText("State: " + project.getState());
        type.setText("Type: "+ project.getType());
        currency.setText("Currency: " + project.getCurrency());
        funded_perc.setText("Percentage Funded: "+ project.getPercentage());
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            slide = new Slide();
            slide.setSlideEdge(Gravity.RIGHT);
            slide.setDuration(400);
            slide.setInterpolator(new DecelerateInterpolator());
//            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);

        }
    }
}

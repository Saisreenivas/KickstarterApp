package Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kickstarter.app.DetailedItemActivity;
import com.kickstarter.app.MainActivity;
import com.kickstarter.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import Model.Project;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private static final String TAG = ProjectListAdapter.class.getSimpleName();
    Activity context;
    ArrayList<Project> projectList;
    int noOfDisplayItems;
    ArrayList<Project> finalListFiltered;
    public static final String FEW_BACKERS = "FewBackers";
    public static final String LOW_BACKERS = "LowBackers";
    public static final String MEDIUM_BACKERS = "MediumBackers";
    public static final String HUGE_BACKERS = "HugeBackers";
    public static final int FEW_BACKERS_COUNT = 20000;
    public static final int LOW_BACKERS_COUNT = 50000;
    public static final int LOW_TO_MEDIUM_BACKERS_COUNT = 90000;
    public static final String SEARCH_VIEW= "title";
    public static final String FILTER_VIEW= "backers";

    public ProjectListAdapter(Activity applicationContext, ArrayList arrayList, int i) {
        context = applicationContext;
        projectList = arrayList;
        noOfDisplayItems = i;
        this.finalListFiltered = arrayList;
    }

    @NonNull
    @Override
    public ProjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProjectListAdapter.ViewHolder holder, final int position) {
        Project project = finalListFiltered.get(position);
        int background = 0;
        int d_background =0;
        if(position % 4  == 1){
            background = R.drawable.bg_border_quarter_circle_one;
            d_background = R.drawable.bg_border_quarter_circle_two;
        }else if(position % 4 == 2){
            background = R.drawable.bg_border_quarter_circle_two;
            d_background = R.drawable.bg_border_quarter_circle_three;
        }else if(position % 4 == 3){
            background = R.drawable.bg_border_quarter_circle_three;
            d_background = R.drawable.bg_border_quarter_circle_four;
        }else if(position % 4 == 0 ){
            background = R.drawable.bg_border_quarter_circle_four;
            d_background = R.drawable.bg_border_quarter_circle_one;
        }
        holder.layout.setBackgroundResource(background);
        holder.dump_back.setBackgroundResource(d_background);
        holder.heading.setText(
                project.getTitle()
        );
        holder.pledge.setText(
                "Pleadge: " + project.getAmountPledged()
        );
        /*TODO add symbol to amountPledged based on country*/
        holder.backers.setText(
                "Backers - " + project.getNumOfBackers()
        );

//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-TZD");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = null;
//        try {
//            date = inputFormat.parse("2018-04-10T04:00:00.000Z");
//            String formattedDate = outputFormat.format(date);
//            Date date2 = new Date();
//            String presentDate = String.valueOf((inputFormat.parse(date2.toString())));
//        } catch (ParseException e) {
//            Log.v("ProjectListAdapter" , "Date inputFormatError" + e.getMessage());
//        }
//        System.out.println(formattedDate); // prints 10-04-2018
        holder.daysToGo.setText(
                "No. of Days to Go - " + project.getEndTime() /*yyyy-MM-dd'T'HH:mm:ss-TZD*/
        );
        /*TODO convert the endTime into the numberOfDaysRemaining*/

        holder.layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), DetailedItemActivity.class)
                                .putExtra("project", (new Gson()).toJson(finalListFiltered.get(position)));


                        if(Build.VERSION.SDK_INT>20) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext());
                            view.getContext().startActivity(i, options.toBundle());
//                            ((Activity) view.getContext()).overridePendingTransition(
//                                    R.anim.slide_in_right, R.anim.slide_in_left);
                        }else{
                            view.getContext().startActivity(i);
//                            ((Activity) view.getContext()).overridePendingTransition(
//                                    R.anim.slide_in_right, R.anim.slide_in_left);
                        }
//                        view.getContext().startActivity(
//                                new Intent(context.getApplicationContext(), DetailedItemActivity.class)
//                                        .putExtra("project", (new Gson()).toJson(projectList.get(position)))
//
//                        );
                    }
                }
        );


    }


    @Override
    public int getItemCount() {
        return finalListFiltered.size();
    }

    public Filter getFilter(String type) {
        if(type.equals(SEARCH_VIEW)){

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString= charSequence.toString();
                    Log.v(TAG, "performFiltering: " + charString );
                    if(charString.isEmpty()){
                        finalListFiltered = projectList;
                    }else{
                        ArrayList<Project> filteredList = new ArrayList<>();
                        for(Project row : projectList){
                            if(row.getTitle().toLowerCase().contains(charString)){
                                filteredList.add(row);
                            }
                        }

                        finalListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = finalListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    finalListFiltered = (ArrayList<Project>) filterResults.values;
                    Log.v(TAG, finalListFiltered.toString() );
                    notifyDataSetChanged();
                }
            };
        }else if(type.equals(FILTER_VIEW)){

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString= charSequence.toString();
                    Log.v(TAG, "performFiltering: " + charString );
                    if(charString.isEmpty()){
                        finalListFiltered = projectList;
                    }else{

                        ArrayList<Project> filteredList = new ArrayList<>();
                        if(charString.equals(FEW_BACKERS)){
                            for(Project row : projectList){
                                if(row.getNumOfBackers() <= FEW_BACKERS_COUNT){
                                    filteredList.add(row);
                                }
                            }
                        }else if(charString.equals(LOW_BACKERS)){
                            for(Project row : projectList){
                                if(row.getNumOfBackers() >= FEW_BACKERS_COUNT && row.getNumOfBackers() <= LOW_BACKERS_COUNT){
                                    filteredList.add(row);
                                }
                            }
                        }else if(charString.equals(MEDIUM_BACKERS)){
                            for(Project row : projectList){
                                if(row.getNumOfBackers() >= LOW_BACKERS_COUNT && row.getNumOfBackers() <= LOW_TO_MEDIUM_BACKERS_COUNT){
                                    filteredList.add(row);
                                }
                            }
                        }else if(charString.equals(HUGE_BACKERS)){
                            for(Project row : projectList){
                                if(row.getNumOfBackers() >= LOW_TO_MEDIUM_BACKERS_COUNT){
                                    filteredList.add(row);
                                }
                            }
                        }


                        finalListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = finalListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    finalListFiltered = (ArrayList<Project>) filterResults.values;
                    Log.v(TAG, finalListFiltered.toString() );
                    notifyDataSetChanged();
                }
            };
        }

        return null;
    }


    public void getSorted(String type) {
        if(type.equals("az")){
            Collections.sort(finalListFiltered, new Comparator<Project>() {
                @Override
                public int compare(Project project, Project t1) {
                    return project.getTitle().toLowerCase().compareTo(t1.getTitle().toLowerCase());
                }
            });
        }else if(type.equals("za")){
            Collections.sort(finalListFiltered, new Comparator<Project>() {
                @Override
                public int compare(Project project, Project t1) {
                    return t1.getTitle().toLowerCase().compareTo(project.getTitle().toLowerCase());
                }
            });
        }else if(type.equals("time")){
            Collections.sort(finalListFiltered, new Comparator<Project>() {
                @Override
                public int compare(Project project, Project t1) {
                    return project.getEndTime().compareTo(t1.getEndTime());
                }
            });
        }

        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView heading, pledge, backers, daysToGo;
        LinearLayout layout, dump_back;
//        CardView layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.item_heading);
            pledge = itemView.findViewById(R.id.item_pledge);
            backers = itemView.findViewById(R.id.item_backers);
            daysToGo = itemView.findViewById(R.id.no_of_days_to_go);
            layout = itemView.findViewById(R.id.item_layout);
            dump_back = itemView.findViewById(R.id.item_dump_back);
        }
    }


}

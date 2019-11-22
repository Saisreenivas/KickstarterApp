package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kickstarter.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Project;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    Context context;
    ArrayList<Project> projectList;
    int noOfDisplayItems;

    public ProjectListAdapter(Context applicationContext, ArrayList arrayList, int i) {
        context = applicationContext;
        projectList = arrayList;
        noOfDisplayItems = i;
    }

    @NonNull
    @Override
    public ProjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProjectListAdapter.ViewHolder holder, int position) {
        Project project = projectList.get(position);
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
                "No. of Days to Go - " + project.getEndTime()
        );
        /*TODO convert the endTime into the numberOfDaysRemaining*/


        /*TODO onClickListener to open the next activity of the application*/
        /*TODO to start a new activity, there should be a "slide" animation*/

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView heading, pledge, backers, daysToGo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.item_heading);
            pledge = itemView.findViewById(R.id.item_pledge);
            backers = itemView.findViewById(R.id.item_backers);
            daysToGo = itemView.findViewById(R.id.no_of_days_to_go);
        }
    }
}

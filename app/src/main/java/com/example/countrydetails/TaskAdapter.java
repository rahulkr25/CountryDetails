package com.example.countrydetails;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import androidx.recyclerview.widget.RecyclerView;

import com.example.countrydetails.database.CountryEntry;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {



    final private ItemClickListener mItemClickListener;

    private List<CountryEntry> countryEntries;
    private Context mContext;


    public TaskAdapter(Context context,ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        CountryEntry taskEntry = countryEntries.get(position);
        String name = taskEntry.getName();
        String imageUrl=taskEntry.getImageUrl();
        holder.nameView.setText(name);

        //Picasso.get().load(imageUrl).into(holder.imageView);
        GlideToVectorYou.init().with(mContext).load(Uri.parse(imageUrl), holder.imageView);


    }



    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (countryEntries == null) {
            return 0;
        }
        return countryEntries.size();
    }

    public List<CountryEntry> getTasks() {
        return countryEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<CountryEntry> taskEntries) {
        countryEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(CountryEntry countryEntry);
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        ImageView imageView;
        TextView nameView;



        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageview);
            nameView=itemView.findViewById(R.id.nameview);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CountryEntry countryEntry = countryEntries.get(getAdapterPosition());
            mItemClickListener.onItemClickListener(countryEntry);
        }
    }
}

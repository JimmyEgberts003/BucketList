package com.egberts.jimmy.bucketlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {

    public interface BucketListItemClickListener {
        void OnBucketListItemClick(int i, boolean finished);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bucketListTitle;
        TextView bucketListDescription;
        CheckBox bucketListFinished;

        ViewHolder(View itemView) {
            super(itemView);
            bucketListTitle = itemView.findViewById(R.id.bucketListItemTitle);
            bucketListDescription = itemView.findViewById(R.id.bucketListItemDescription);
            bucketListFinished = itemView.findViewById(R.id.bucketListItemFinished);
            itemView.findViewById(R.id.bucketListItemFinished).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            bucketListItemClickListener.OnBucketListItemClick(clickedPosition, ((CheckBox) view).isChecked());
        }
    }

    private List<BucketListItem> bucketListItems;
    final private BucketListItemClickListener bucketListItemClickListener;

    BucketListAdapter(List<BucketListItem> bucketListItems, BucketListItemClickListener bucketListItemClickListener) {
        this.bucketListItems = bucketListItems;
        this.bucketListItemClickListener = bucketListItemClickListener;
    }

    @NonNull
    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bucket_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BucketListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bucketListTitle.setText(bucketListItems.get(i).getTitle());
        viewHolder.bucketListDescription.setText(bucketListItems.get(i).getDescription());
        viewHolder.bucketListFinished.setChecked(bucketListItems.get(i).isFinished());
    }

    @Override
    public int getItemCount() {
        return bucketListItems.size();
    }

    void swapList(List<BucketListItem> newBucketListItems) {
        bucketListItems = newBucketListItems;
        if (newBucketListItems != null) {
            this.notifyDataSetChanged();
        }
    }
}


package com.example.groceries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceries.R;
import com.example.groceries.helper.GroupImageHelper;

import java.util.List;
import java.util.Map;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<Map<String, Object>> groupDataList; // Changed from List<String>
    private OnGroupClickListener listener;

    public interface OnGroupClickListener {
        void onGroupClick(String groupName);
    }

    public GroupAdapter(List<Map<String, Object>> groupDataList) {
        this.groupDataList = groupDataList;
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_group_list, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Map<String, Object> groupData = groupDataList.get(position);
        String groupName = (String) groupData.get("groupName");
        Integer imageResId = (Integer) groupData.get("imageResId");

        holder.bind(groupName, imageResId != null ? imageResId : GroupImageHelper.DEFAULT_IMAGE_RES_ID);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGroupClick(groupName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupDataList.size();
    }

    public void updateData(List<Map<String, Object>> newData) {
        groupDataList = newData;
        notifyDataSetChanged();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupNameTextView;
        CardView cardView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupNameTextView = itemView.findViewById(R.id.group_name);
            cardView = itemView.findViewById(R.id.group_card);
        }

        public void bind(String groupName, int imageResId) {
            groupNameTextView.setText(groupName);
            cardView.setBackgroundResource(imageResId);
        }
    }
}
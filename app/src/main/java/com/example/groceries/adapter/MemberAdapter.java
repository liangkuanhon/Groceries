package com.example.groceries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceries.R;
import com.example.groceries.User;
import com.example.groceries.databinding.ItemGroupMemberBinding;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private List<User> members;
    private OnMemberClickListener listener;

    public interface OnMemberClickListener {
        void onMemberClick(User member);
    }

    public MemberAdapter(List<User> members, OnMemberClickListener listener) {
        this.members = members;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        User member = members.get(position);
        holder.bind(member, listener);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        private final ItemGroupMemberBinding b;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            b = ItemGroupMemberBinding.bind(itemView);
        }

        public void bind(User member, OnMemberClickListener listener) {
            b.username.setText(member.getUsername());
            b.email.setText(member.getEmail());

            // Set click listeners
            itemView.setOnClickListener(v -> listener.onMemberClick(member));
        }
    }

    public void updateMembers(List<User> newMembers) {
        this.members.clear();
        this.members.addAll(newMembers);
        notifyDataSetChanged();
    }
}

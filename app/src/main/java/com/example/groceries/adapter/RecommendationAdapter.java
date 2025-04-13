package com.example.groceries.adapter;

import android.graphics.Color;
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

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<String> recommendations;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RecommendationAdapter(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recommendations.get(position));
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            nameTextView = itemView.findViewById(R.id.recommendation_name);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }

        public void bind(String recommendation) {
            nameTextView.setText(recommendation);

            // Make text more visible on images
            nameTextView.setTextColor(Color.WHITE);
            nameTextView.setShadowLayer(4f, 2f, 2f, Color.BLACK);

            int imageResId = GroupImageHelper.getGroupImage(recommendation);
            cardView.setBackgroundResource(imageResId);

        }
    }
}

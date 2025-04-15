package com.example.groceries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.groceries.R;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private final List<String> routeSteps;

    public RouteAdapter(List<String> routeSteps) {
        this.routeSteps = routeSteps;
    }

    @NonNull
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.ViewHolder holder, int position) {
        holder.stepText.setText((position + 1) + ". " + routeSteps.get(position));
    }

    @Override
    public int getItemCount() {
        return routeSteps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepText;

        ViewHolder(View itemView) {
            super(itemView);
            stepText = itemView.findViewById(R.id.step_text);
        }
    }
}

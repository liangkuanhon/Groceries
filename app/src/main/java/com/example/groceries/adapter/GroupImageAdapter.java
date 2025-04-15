package com.example.groceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.groceries.R;
import com.example.groceries.databinding.LayoutGroupImageBinding;
import com.example.groceries.helper.GroupImageHelper;

import java.util.List;
import java.util.Map;

public class GroupImageAdapter extends BaseAdapter {
    private final Context context;
    private final List<Map.Entry<String, Integer>> images; // a list of map pairs (key: image name, value: drawable resource id)
    private int selectedPosition = -1; // tracks which image is selected -1 means no selection

    public GroupImageAdapter(Context context, List<Map.Entry<String, Integer>> images) {
        this.context = context; // creategroup activity
        this.images = images; // a list of images from groupimagehelper
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position); // returns the Map.Entry (image name + resource ID) at the given position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_group_image, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.groupImage);
        View selectionIndicator = convertView.findViewById(R.id.selectionIndicator);

        Map.Entry<String, Integer> entry = images.get(position);
        imageView.setImageResource(entry.getValue());

        // Show selection indicator if this item is selected
        selectionIndicator.setVisibility(position == selectedPosition ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedImageResId() {
        if (selectedPosition != -1 && selectedPosition < images.size()) {
            return images.get(selectedPosition).getValue();
        }
        return GroupImageHelper.DEFAULT_IMAGE_RES_ID;
    }
}
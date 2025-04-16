package com.example.groceries.helper;

import com.example.groceries.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupImageHelper {

    public static final int DEFAULT_IMAGE_RES_ID = R.drawable.background_default;
    // Map of all available group images
    private static final Map<String, Integer> GROUP_IMAGES = new HashMap<String, Integer>() {{
        put("Family Group", R.drawable.background_family);
        put("Work Group", R.drawable.background_work);
        put("School Group", R.drawable.background_school);
        put("Friend Group", R.drawable.background_friends);
        put("Couple Group", R.drawable.background_couple);
        put("Roomie Group", R.drawable.background_roomie);
        put("Project Group", R.drawable.background_project);
    }};

    // Get all available image resources at once
    public static List<Map.Entry<String, Integer>> getGroupImageEntries() {
        return new ArrayList<>(GROUP_IMAGES.entrySet());
    }

    // Get all available image resources at once
    public static List<Integer> getAllGroupImages() {
        return new ArrayList<>(GROUP_IMAGES.values());
    }

    // Get image resource by group name
    public static int getGroupImage(String groupName) {
        return GROUP_IMAGES.getOrDefault(groupName, R.drawable.background_default);
    }
}
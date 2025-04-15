package com.example.groceries.fragments;

import com.example.groceries.helper.FirebaseHelper;
import com.example.groceries.ItemCategoryMapper;
import com.example.groceries.Graph;
import com.example.groceries.R;
import com.example.groceries.BFSRouter;
import com.example.groceries.SupermarketFactory;
import com.example.groceries.SupermarketGraph;
import com.example.groceries.adapter.RouteAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import android.os.Handler;
import android.util.Log;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class SupermarketMapFragment extends Fragment {

    private static final String ARG_GROUP_ID = "group_id";
    private static final String ARG_SUPERMARKET_NAME = "supermarket_name";
    private static final String ARG_GROUP_NAME = "group_name";

    private String groupId;
    private static String groupName;
    private String supermarketName;


    private List<String> currentRoute = new ArrayList<>();
    private int currentStepIndex = 0;

    //used for filtering items
    private List<String> originalShoppingList;

    //used for filtering items
    private List<String> originalItemNames;


    //to prevent showing "Your shopping list items are" multiple times
    private Set<String> categoriesWithHints = new HashSet<>();




    public static SupermarketMapFragment newInstance(String groupId, String groupName, String supermarketName) {
        SupermarketMapFragment fragment = new SupermarketMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        args.putString(ARG_SUPERMARKET_NAME, supermarketName);
        args.putString(ARG_GROUP_NAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
            supermarketName = getArguments().getString(ARG_SUPERMARKET_NAME);
            groupName = getArguments().getString(ARG_GROUP_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_supermarket_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            currentRoute = savedInstanceState.getStringArrayList("currentRoute");
            currentStepIndex = savedInstanceState.getInt("currentStepIndex", 0);
            originalItemNames = savedInstanceState.getStringArrayList("originalItemNames");
        }

        view.findViewById(R.id.back_arrow).setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        });

        // If route is already available (i.e., restored from orientation), just show it
        if (currentRoute != null && !currentRoute.isEmpty()) {
            updateStepDisplay(
                    view.findViewById(R.id.step_text),
                    view.findViewById(R.id.item_hint_text)
            );

            view.findViewById(R.id.next_button).setOnClickListener(v -> {
                if (currentStepIndex < currentRoute.size() - 1) {
                    currentStepIndex++;
                    updateStepDisplay(
                            view.findViewById(R.id.step_text),
                            view.findViewById(R.id.item_hint_text)
                    );
                }
            });

            view.findViewById(R.id.previous_button).setOnClickListener(v -> {
                if (currentStepIndex > 0) {
                    currentStepIndex--;
                    updateStepDisplay(
                            view.findViewById(R.id.step_text),
                            view.findViewById(R.id.item_hint_text)
                    );
                }
            });

        } else {

            // 1. Load the map PNG dynamically
            ImageView mapImage = view.findViewById(R.id.supermarket_map_image);
            String imageName = "map_" + supermarketName.toLowerCase().replace(" ", "_");
            int imageResId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
            if (imageResId != 0) {
                mapImage.setImageResource(imageResId);
            } else {
                mapImage.setImageResource(R.drawable.add); //default image in case none was found
                Toast.makeText(requireContext(), "Map not found for " + supermarketName, Toast.LENGTH_SHORT).show();
            }

            // 2. Get shopping list from Firebase and trigger routing
            FirebaseHelper.getGroupItems(groupId, new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> shoppingList = new ArrayList<>();
                    originalItemNames = new ArrayList<>(); // Ensure this is initialized correctly

                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String itemName = itemSnapshot.child("name").getValue(String.class);
                        Boolean checked = itemSnapshot.child("checked").getValue(Boolean.class);

                        // Only include items that are not checked
                        if (itemName != null && (checked == null || !checked)) {
                            // Add the item to the originalItemNames
                            originalItemNames.add(itemName);

                            // Map the item to its category using the helper class
                            String category = ItemCategoryMapper.getCategoryForItem(itemName);

                            if (category != null) {
                                shoppingList.add(category); // Add the category to the list
                            } else {
                                Log.e("ShoppingList", "No category found for item: " + itemName);
                            }
                        }
                    }

                    Log.d("ShoppingList", "Shopping List with Categories: " + shoppingList);

                    // 3. Create the graph for the selected supermarket
                    SupermarketGraph supermarketGraph = SupermarketFactory.getSupermarketGraph(supermarketName);

                    // Verify if categories exist in the graph
                    for (String category : shoppingList) {
                        if (!supermarketGraph.containsNode(category)) {
                            Log.e("ShoppingList", "Category not found in graph: " + category);
                        }
                    }

                    // Once the shopping list with categories is ready, run routing and display the result
                    runRoutingAndShow(shoppingList, supermarketGraph);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), "Failed to load items from Firebase.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateStepDisplay(TextView stepText, TextView hintText) {
        if (currentRoute == null || currentRoute.isEmpty()) {
            stepText.setText("No route found.");
            hintText.setVisibility(View.GONE);
            return;
        }

        String stepLocation = currentRoute.get(currentStepIndex);
        stepText.setText("Step " + (currentStepIndex + 1) + ": Go to " + stepLocation);

        // Find relevant items at this step
        List<String> allItemsAtCategory = ItemCategoryMapper.getItemsAtCategory(stepLocation);
        List<String> relevantItems = new ArrayList<>();

        for (String item : originalItemNames) {
            String itemCategory = ItemCategoryMapper.getCategoryForItem(item);
            if (itemCategory != null && itemCategory.equals(stepLocation) && allItemsAtCategory.contains(item)) {
                relevantItems.add(item);
            }
        }

        if (!relevantItems.isEmpty()) {
            categoriesWithHints.add(stepLocation);
            hintText.setText("Your shopping list items to collect here: " + String.join(", ", relevantItems));
            hintText.setVisibility(View.VISIBLE);
        } else {
            hintText.setVisibility(View.GONE);
        }

    }






    private void runRoutingAndShow(List<String> shoppingList, SupermarketGraph supermarketGraph) {
        BFSRouter router = new BFSRouter(supermarketGraph);
        ArrayList<String> shoppingListCopy = new ArrayList<>(shoppingList);
        List<String> optimalRoute = router.greedyBFSRouting(shoppingListCopy);
        originalShoppingList = new ArrayList<>(shoppingList);


        currentRoute = optimalRoute;
        currentStepIndex = 0;

        TextView stepText = requireView().findViewById(R.id.step_text);
        TextView hintText = requireView().findViewById(R.id.item_hint_text);
        Button nextButton = requireView().findViewById(R.id.next_button);
        Button previousButton = requireView().findViewById(R.id.previous_button);

        updateStepDisplay(stepText, hintText); // Show the first step

        nextButton.setOnClickListener(v -> {
            if (currentStepIndex < currentRoute.size() - 1) {
                currentStepIndex++;
                updateStepDisplay(stepText, hintText);
                if (currentStepIndex == currentRoute.size() - 1) {
                    nextButton.setText("Finish");
                }
            }
            else {
                navigateToGroupDetailsFragment(groupId, groupName);
            }
        });


        //Show hint again if backtrack logic
        previousButton.setOnClickListener(v -> {
            if (currentStepIndex > 0) {
                currentStepIndex--;
                updateStepDisplay(stepText, hintText);
                nextButton.setText("Next");

                // Re-show hint if this step had one originally
                String stepLocation = currentRoute.get(currentStepIndex);
                if (categoriesWithHints.contains(stepLocation)) {
                    List<String> allItemsAtCategory = ItemCategoryMapper.getItemsAtCategory(stepLocation);
                    List<String> relevantItems = new ArrayList<>();
                    for (String item : originalItemNames) {
                        String itemCategory = ItemCategoryMapper.getCategoryForItem(item);
                        if (itemCategory != null && itemCategory.equals(stepLocation) && allItemsAtCategory.contains(item)) {
                            relevantItems.add(item);
                        }
                    }

                    if (!relevantItems.isEmpty()) {
                        hintText.setText("Your shopping list items to collect here: " + String.join(", ", relevantItems));
                        hintText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("currentRoute", new ArrayList<>(currentRoute));
        outState.putInt("currentStepIndex", currentStepIndex);
        outState.putStringArrayList("originalItemNames", new ArrayList<>(originalItemNames));
    }

    private void navigateToGroupDetailsFragment(String groupId, String groupName) {
        SingleGroupFragment fragment = SingleGroupFragment.newInstance(groupId, groupName);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                .addToBackStack(null)
                .commit();
    }




}

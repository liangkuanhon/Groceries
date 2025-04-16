package com.example.groceries.helper;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NavigationHelper {
    private final Context context;
    private FragmentManager fragmentManager;
    private int fragmentContainerId;

    // used for activity to activity navigation
    public NavigationHelper(Context context) {
        this.context = context;
    }

    // used when fragment transactions are needed
    public NavigationHelper(FragmentActivity activity, int fragmentContainerId) {
        this.context = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.fragmentContainerId = fragmentContainerId;
    }

    // ==================== ACTIVITY NAVIGATION ====================

    // opens a new activity
    public void navigateToActivity(Class<?> targetActivity) {
        context.startActivity(new Intent(context, targetActivity));
    }

    // opens a new activity and clearing the back stack; prevents users from returning to the previous screen
    // and also pass an extra string to the next activity
    public void navigateToActivityWithExtra(Class<?> targetActivity, String extraKey, String extraValue) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtra(extraKey, extraValue);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    // prevent user from going back after transaction to the next activity
    public void navigateToActivityClearStack(Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    // ==================== FRAGMENT NAVIGATION ====================

    // no back navigation
    public void navigateToFragment(Fragment fragment) {
        navigateToFragment(fragment, true);
    }

    // add to back stack to allow back navigation
    public void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainerId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    // ==================== FRAGMENT <-> ACTIVITY ====================

    public void navigateFromFragmentToActivity(Fragment sourceFragment, Class<?> targetActivity) {
        sourceFragment.requireActivity().startActivity(new Intent(context, targetActivity));
    }

    public void navigateFromActivityToFragment(FragmentActivity activity,
                                               Fragment fragment,
                                               int containerId) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    // ==================== BACK NAVIGATION ====================

    public void navigateBack() {
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else if (context instanceof FragmentActivity) {
            ((FragmentActivity) context).finish();
        }
    }
}
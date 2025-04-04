package com.example.groceries;

public class GroceryItem {
    private String id;
    private String name;
    private int imageRes;
    private boolean checked;
    private String addedBy;

    // Empty constructor required for Firebase
    public GroceryItem() {
    }

    public GroceryItem(String name, int imageRes, String addedBy) {
        this.name = name;
        this.imageRes = imageRes;
        this.addedBy = addedBy;
        this.checked = false;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    // Setter for ID
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}

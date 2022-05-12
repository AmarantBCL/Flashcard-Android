package com.example.android.flashcard.model;

import android.content.Context;

import com.example.android.flashcard.R;
import com.example.android.flashcard.utils.ImageUtils;

import java.util.ArrayList;

public class CategoryItem {
    private String name;
    private int imageResource;

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public CategoryItem(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public static ArrayList<CategoryItem> init(Context context) {
        ArrayList<CategoryItem> categoryItems = new ArrayList<>();
        String[] categories = context.getResources().getStringArray(R.array.categories);
        for (String category : categories) {
            categoryItems.add(new CategoryItem(category,
                    ImageUtils.getImageId(context, category.toLowerCase()))
            );
        }
        return categoryItems;
    }
}

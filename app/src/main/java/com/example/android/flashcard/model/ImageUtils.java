package com.example.android.flashcard.model;

import android.content.Context;

public class ImageUtils {
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" +
                imageName, null, context.getPackageName());
    }
}

package com.example.ribeshmaharjan.ivy;

import java.util.ArrayList;
import java.util.List;

public class ImageAssets {
    private static final List<Integer> school = new ArrayList<Integer>() {{

        add(R.drawable.school_img1);
        add(R.drawable.school_img2);



    }};
    private static final List<Integer> all = new ArrayList<Integer>() {{
        addAll(school);
    }};
    // Getter methods that return lists of all food images

    public static List<Integer> getSchool() {
        return school;
    }

    public static List<Integer> getAll() {
        return all;
    }
}
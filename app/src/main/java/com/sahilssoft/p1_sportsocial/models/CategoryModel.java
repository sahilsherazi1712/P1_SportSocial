package com.sahilssoft.p1_sportsocial.models;

import java.util.List;

public class CategoryModel {
    public int id, en_uk_priority, en_us_priority;
    public String name, image, webImage, appImage, bgImage, en_uk_name, en_us_name, created_at, updated_at;

    public List<CategoryData> category_data;
}

package com.examplemod.exmod.data;

import java.util.ArrayList;
import java.util.List;

public class Tab {
    public List<Category> categories;
    private int activeCategoryIndex;

    public Tab(List<Category> categories) {
        this.categories = categories;
    }

    public Tab() {
        this.categories = new ArrayList<>();
        this.categories.add(new Category(true)); // is a fake category for the search bar
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Category activeCategory() {
        return !(categories.size() <= 1) ? categories.get(activeCategoryIndex) : null;
    }

    public void setActiveCategoryIndex(int activeCategoryIndex) {
        this.activeCategoryIndex = activeCategoryIndex;
    }

    public void setActiveCategory(Category activeCategory) {
        activeCategoryIndex = categories.indexOf(activeCategory);
    }
}

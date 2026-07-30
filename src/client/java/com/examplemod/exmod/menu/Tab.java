package com.examplemod.exmod.menu;

import java.util.ArrayList;
import java.util.List;

public class Tab {
    List<Category> categories;
    private int activeCategoryIndex;

    public Tab(List<Category> categories) {
        this.categories = categories;
    }

    public Tab() {
        this.categories = new ArrayList<>();
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Category activeCategory() {
        return categories.get(activeCategoryIndex);
    }

    public void setActiveCategoryIndex(int activeCategoryIndex) {
        this.activeCategoryIndex = activeCategoryIndex;
    }

    public void setActiveCategory(Category activeCategory) {
        activeCategoryIndex = categories.indexOf(activeCategory);
    }
}

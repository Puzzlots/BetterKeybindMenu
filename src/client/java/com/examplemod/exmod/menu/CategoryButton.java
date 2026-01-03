package com.examplemod.exmod.menu;

import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.util.Identifier;

public class CategoryButton extends LangButton {

    Identifier categoryId;

    public CategoryButton(Category category) {
        super(Lang.get(category.getId().toString()));
        this.categoryId = category.getId();
    }

    public void deselect() {

    }

    public void select() {

    }

}

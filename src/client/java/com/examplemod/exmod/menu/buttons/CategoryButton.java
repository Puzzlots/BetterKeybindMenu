package com.examplemod.exmod.menu.buttons;

import com.examplemod.exmod.menu.Category;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.lang.Lang;

public class CategoryButton extends LangButton {

    Identifier categoryId;

    public CategoryButton(Category category) {
        super(Lang.get(category.id().toString()));
        this.categoryId = category.id();
    }

    public void deselect() {

    }

    public void select() {

    }

}

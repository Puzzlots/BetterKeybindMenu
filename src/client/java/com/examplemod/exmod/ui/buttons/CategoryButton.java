package com.examplemod.exmod.ui.buttons;

import com.examplemod.exmod.data.Category;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.lang.Lang;

public class CategoryButton extends CRButton {

    Identifier categoryId;

    public CategoryButton(Category category) {
        super(Lang.get(category.id().toString()));
        this.categoryId = category.id();
    }

}

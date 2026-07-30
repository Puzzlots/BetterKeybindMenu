package com.examplemod.exmod.menu;

import java.util.ArrayList;

public record KeybindTabs(Tab keyboard, Tab controller) {
    public KeybindTabs() {
        this(new Tab(), new Tab());
    }
    public static Tab activeTab;
}

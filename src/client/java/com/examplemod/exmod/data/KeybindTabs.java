package com.examplemod.exmod.data;

public record KeybindTabs(Tab keyboard, Tab controller) {
    public KeybindTabs() {
        this(new Tab(), new Tab());
    }
    public static Tab activeTab;
}

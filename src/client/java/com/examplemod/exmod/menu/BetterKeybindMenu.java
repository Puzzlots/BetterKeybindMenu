package com.examplemod.exmod.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.examplemod.exmod.Keybind;
import com.examplemod.exmod.KeyAtlas;
import com.examplemod.exmod.KeyBindRegistry;
import com.examplemod.exmod.data.Category;
import com.examplemod.exmod.data.KeybindEntry;
import com.examplemod.exmod.data.KeybindTabs;
import com.examplemod.exmod.data.TabType;
import com.examplemod.exmod.ui.buttons.CategoryButton;
import com.examplemod.exmod.ui.widgets.KeybindWidget;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.ui.GameStyles;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.lang.Lang;
import finalforeach.cosmicreach.world.Sky;

import java.util.List;

public class BetterKeybindMenu extends GameState {

    private Camera starCamera;

    TextField searchBar;

    KeybindTabs keybindTabs = new KeybindTabs();

    /// tables ///
    Table baseTable = new Table();
    Table categoryTable = new Table();
    Table keybindTable = new Table();
//    Table keybindsContainer = new Table();

    /// buttons ///

    public static KeybindWidget activeKeybindWidget;
    CategoryButton activeCategoryButton;
    CRButton keyboardTabButton;
    CRButton controllerTabButton;

    ScrollPane keybindScroll;

    Color activeColor = Color.GOLD;
    Color inactiveColor = Color.WHITE;


    public final Category ALL = new Category("base", "all");
    public final Category MOVEMENT = new Category("base", "movement");
    public final Category INTERACTIONS = new Category("base", "interactions");
    public final Category INVENTORY = new Category("base", "inventory");
    public final Category CHAT = new Category("base", "chat");
    public final Category OTHER = new Category("base", "other");
    public final Category DEBUG = new Category("base", "debug");

    public BetterKeybindMenu(GameState previousState) {
        this.previousState = previousState;
    }

    // for mods and you if you want it :)
    public void addKeybind(Category category, Identifier LangId, Keybind keybind) {
        KeybindEntry keybindEntry = new KeybindEntry(LangId, keybind);
        category.addKeybind(keybindEntry);
        ALL.addKeybind(keybindEntry);
    }

    public void addKeybind(Category category, Keybind keybind) {
        KeybindEntry keybindEntry = new KeybindEntry(keybind.getId(), keybind);
        category.addKeybind(keybindEntry);
        ALL.addKeybind(keybindEntry);
    }

    public void initCategories() {
        keybindTabs.keyboard().addCategory(ALL);
        keybindTabs.keyboard().addCategory(MOVEMENT);
        keybindTabs.keyboard().addCategory(INTERACTIONS);
        keybindTabs.keyboard().addCategory(INVENTORY);
        keybindTabs.keyboard().addCategory(CHAT);
        keybindTabs.keyboard().addCategory(OTHER);
        keybindTabs.keyboard().addCategory(DEBUG);
    }

    //TODO FFE can you make your keybinds have base in them like a identifier
    public void initKeybinds() {
        addKeybind(MOVEMENT, KeyBindRegistry.keyForward);
        addKeybind(MOVEMENT, KeyBindRegistry.keyBackward);
        addKeybind(MOVEMENT, KeyBindRegistry.keyLeft);
        addKeybind(MOVEMENT, KeyBindRegistry.keyRight);
        addKeybind(MOVEMENT, KeyBindRegistry.keyJump);
        addKeybind(MOVEMENT, KeyBindRegistry.keyCrouch);
        addKeybind(MOVEMENT, KeyBindRegistry.keySprint);
        addKeybind(MOVEMENT, KeyBindRegistry.keyProne);

        addKeybind(INTERACTIONS, KeyBindRegistry.keyUsePlace);
        addKeybind(INTERACTIONS, KeyBindRegistry.keyAttackBreak);
        addKeybind(INTERACTIONS, KeyBindRegistry.keyPickBlock);

        addKeybind(INVENTORY, KeyBindRegistry.keyInventory);
        addKeybind(INVENTORY, KeyBindRegistry.keyDropItem);
        addKeybind(INVENTORY, KeyBindRegistry.keySwapGroupItem);

        addKeybind(CHAT, KeyBindRegistry.keyChat);
        addKeybind(CHAT, KeyBindRegistry.keyVoice);

        addKeybind(OTHER, KeyBindRegistry.keyHideUI);
        addKeybind(OTHER, KeyBindRegistry.keyScreenshot);
        addKeybind(OTHER, KeyBindRegistry.keyChangePerspective);
        addKeybind(OTHER, KeyBindRegistry.keyFullscreen);

        addKeybind(DEBUG, KeyBindRegistry.keyDebugInfo);
        addKeybind(DEBUG, KeyBindRegistry.keyDebugReloadShaders);

    }

    private void setTab(TabType tabType) {
        boolean isControllerTab = tabType == TabType.controller;
        KeybindTabs.activeTab = isControllerTab ? keybindTabs.controller() : keybindTabs.keyboard();
        KeybindTabs.activeTab.setActiveCategoryIndex(0);

        categoryTable.clear();
        for (Category category : KeybindTabs.activeTab.categories) {
            CategoryButton button = new CategoryButton(category) {
                @Override public void onClick() {
                    selectCategory(category, this);
                }
            };
            if (category == KeybindTabs.activeTab.activeCategory()) activeCategoryButton = button;

            categoryTable.add(button).width(250).height(50).padBottom(5).row();
        }

        keyboardTabButton.setTextColor(isControllerTab ? inactiveColor : activeColor);
        controllerTabButton.setTextColor(isControllerTab ? activeColor : inactiveColor);

        selectCategory(KeybindTabs.activeTab.activeCategory(), activeCategoryButton);
    }

    private void selectCategory(Category category, CategoryButton newButton) {
        keybindTable.clear();
        if (category == null) return;

        if (activeCategoryButton != null) activeCategoryButton.setTextColor(inactiveColor);
        newButton.setTextColor(activeColor);
        activeCategoryButton = newButton;

        KeybindTabs.activeTab.setActiveCategory(category);
        List<KeybindEntry> filteredKeybinds = KeybindTabs.activeTab.activeCategory().keybinds().stream().filter(keybindEntry -> {
            String sanitisedText = searchBar.getText().toLowerCase().strip();
            if (searchBar.getText().contains(":key")) {
                String key = sanitisedText.replace(" ", "").replace(":key", "");
                String setKey = keybindEntry.keybind().getKeyName().replace(" ", "").toLowerCase().strip();
                if (setKey.equals(key)){
                    return true;
                }
            }

            return (Lang.get(keybindEntry.langId().toString()).toLowerCase().contains(sanitisedText) || keybindEntry.langId().toString().toLowerCase().contains(sanitisedText));
        }).toList();
        for (KeybindEntry entry : filteredKeybinds) {
            KeybindWidget widget = new KeybindWidget(entry);
            keybindTable.add(widget).growX().height(70).padBottom(5).row();
        }
    }

    @Override
    public void create() {
        super.create();
        this.stage.clear();
        this.starCamera = new PerspectiveCamera(GraphicsSettings.fieldOfView.getValue(), (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
        this.starCamera.near = 0.1F;
        this.starCamera.far = 2500.0F;

        initCategories();
        initKeybinds();

        baseTable.clear();
        baseTable.setFillParent(true);
        this.stage.addActor(baseTable);

        // header //////////

        Table header = new Table();
        this.searchBar = new TextField("", GameStyles.textstyle);
        this.searchBar.setMessageText(Lang.get("keybindSelectionSearch"));

        this.searchBar.setTextFieldListener((_, _) ->
                selectCategory(KeybindTabs.activeTab.activeCategory(), activeCategoryButton));

        keyboardTabButton = new CRButton(Lang.get("keyboard_tab")) {
            public void onClick() {
                setTab(TabType.keyboard);
            }
        };

        controllerTabButton = new CRButton(Lang.get("controller_tab")) {
            public void onClick() {
                setTab(TabType.controller);
            }
        };

        boolean controllerTabActive = KeybindTabs.activeTab == keybindTabs.controller();
        keyboardTabButton.setTextColor(controllerTabActive ? inactiveColor : activeColor);
        controllerTabButton.setTextColor(controllerTabActive ? activeColor : inactiveColor);

        header.add(searchBar).minWidth(200).prefWidth(500).height(50).growX();
        header.add(keyboardTabButton).padLeft(5).minWidth(180).prefWidth(250).maxWidth(250).height(50);
        header.add(controllerTabButton).padLeft(5).minWidth(180).prefWidth(250).maxWidth(250).height(50);
        header.setClip(true);

        baseTable.add(header).growX().top().pad(10);
        baseTable.row();


        // INIT //

        setTab(Controls.controllers.size > 0 ? TabType.controller : TabType.keyboard);

        // categories //

        Table content = new Table();
        baseTable.add(content).grow();

        Table rightSideContent = new Table();

        categoryTable.top();

        ScrollPane categoryScroll = new ScrollPane(categoryTable);

        categoryTable.defaults().growX().top();

        categoryScroll.setFadeScrollBars(false);
        categoryScroll.setScrollingDisabled(true, false);

        Stack categoryStack = new Stack();
        categoryStack.add(categoryScroll);

        content.add(categoryStack)
                .growY()
                .top()
                .left()
                .width(250)
                .pad(10);


        // ///////////// keybinds /////////////////////

        keybindScroll = new ScrollPane(keybindTable);
        keybindScroll.setFadeScrollBars(false);
        keybindScroll.setScrollingDisabled(true, false);

        Stack keybindStack = new Stack();
        keybindStack.add(keybindScroll);


        rightSideContent.defaults().growX();
        rightSideContent.top();
        rightSideContent.add(keybindStack).growX().top().padTop(10).padRight(10).padBottom(10);

        // bottom buttons /////
        Table bottomButtons = new Table();

        CRButton doneButton = new CRButton(Lang.get("doneButton")) {
            public void onClick() {
                GameState.switchToGameState(previousState);
            }
        };

        bottomButtons.defaults().growX();

        CRLabel keybindsTip = new CRLabel(Lang.get("keybindsTip"));
        bottomButtons.add(keybindsTip)
                .right()
                .padLeft(15);

        bottomButtons.add(doneButton)
                .right()
                .padLeft(5)
                .width(250)
                .height(50);

        rightSideContent.row();
        rightSideContent.add().expandY();
        rightSideContent.row();

        rightSideContent.add(bottomButtons)
                .bottom()
                .right()
                .expandX()
                .padRight(10)
                .padBottom(10);

        content.add(rightSideContent).grow();

        this.stage.addListener(event -> {
            if (event instanceof InputEvent inputEvent) {
                if (activeKeybindWidget != null) {
                    KeybindEntry keybindEntry = activeKeybindWidget.getKeybindEntry();
                    activeKeybindWidget.setColor(activeColor);
                    boolean isUpdated = false;

                    if (inputEvent.getType() == InputEvent.Type.keyDown) {
                        int keycode = inputEvent.getKeyCode();
                        if (keycode != 111) {
                            keybindEntry.keybind().setValue(keycode);
                            isUpdated = true;
                        } else {
                            keybindEntry.keybind().getKeyUnset().setValue(true);
                            isUpdated = true;
                        }
                    } else if (inputEvent.getType() == InputEvent.Type.touchDown && keybindEntry.keybind().mouseAllowed()) {
                        int savedButtonCode = -2 - inputEvent.getButton();
                        keybindEntry.keybind().setValue(savedButtonCode);
                        isUpdated = true;
                    }

                    if (isUpdated) {
                        activeKeybindWidget.updateIcon();
                        activeKeybindWidget.setColor(inactiveColor);
                        activeKeybindWidget = null;
                        return true;

                    }
                }
            }

            return false;
        });

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void switchAwayTo(GameState gameState) {
        super.switchAwayTo(gameState);
        Gdx.input.setInputProcessor(null);
        KeyAtlas.dispose();
    }

    public void onSwitchTo() {
        super.onSwitchTo();
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render() {
        super.render();
        this.stage.act();
        ScreenUtils.clear(0, 0, 0F, 1.0F, true);

        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDisable(2884);
        Sky.SPACE_DAY.drawSky(this.starCamera);
        this.starCamera.rotate(Vector3.Z, Gdx.graphics.getDeltaTime() * 0.25F);
        this.stage.draw();
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(2884);
    }
}

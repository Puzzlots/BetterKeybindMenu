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
import com.examplemod.exmod.BetterKeybindMenuInit;
import com.examplemod.exmod.ExampleOfNewKeybind;
import com.examplemod.exmod.KeyAtlas;
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

public class BetterKeybindMenu extends GameState {

    private Camera starCamera;

    public final static boolean DEBUG = false;

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


    public Category MOVEMENT = new Category("base", "movement");
    public Category INTERACTIONS = new Category("base", "interactions");
    public Category INVENTORY = new Category("base", "inventory");
    public Category CHAT = new Category("base", "chat");
    public Category OTHER = new Category("base", "other");
    public Category C_DEBUG = new Category("base", "debug"); // can be renamed to just debug

    public BetterKeybindMenu(GameState previousState) {
        this.previousState = previousState;
    }

    // for mods and you if you want it :)
    public void addKeybind(Category category, Identifier LangId, ExampleOfNewKeybind keybind) {
        category.addKeybind(new KeybindEntry(LangId, keybind));
    }

    public void addKeybind(Category category, ExampleOfNewKeybind keybind) {
        category.addKeybind(new KeybindEntry(keybind.getId(), keybind));
    }

    public void initCategories() {
        keybindTabs.keyboard().addCategory(MOVEMENT);
        keybindTabs.keyboard().addCategory(INTERACTIONS);
        keybindTabs.keyboard().addCategory(INVENTORY);
        keybindTabs.keyboard().addCategory(CHAT);
        keybindTabs.keyboard().addCategory(OTHER);
        keybindTabs.keyboard().addCategory(C_DEBUG);
    }

    //TODO FFE can you make your keybinds have base in them like a identifier
    public void initKeybinds() {
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyForward);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyBackward);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyLeft);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyRight);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyJump);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyCrouch);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keySprint);
        addKeybind(MOVEMENT, BetterKeybindMenuInit.keyProne);

        addKeybind(INTERACTIONS, BetterKeybindMenuInit.keyUsePlace);
        addKeybind(INTERACTIONS, BetterKeybindMenuInit.keyAttackBreak);
        addKeybind(INTERACTIONS, BetterKeybindMenuInit.keyPickBlock);

        addKeybind(INVENTORY, BetterKeybindMenuInit.keyInventory);
        addKeybind(INVENTORY, BetterKeybindMenuInit.keyDropItem);
        addKeybind(INVENTORY, BetterKeybindMenuInit.keySwapGroupItem);

        addKeybind(CHAT, BetterKeybindMenuInit.keyChat);
        addKeybind(CHAT, BetterKeybindMenuInit.keyVoice);

        addKeybind(OTHER, BetterKeybindMenuInit.keyHideUI);
        addKeybind(OTHER, BetterKeybindMenuInit.keyScreenshot);
        addKeybind(OTHER, BetterKeybindMenuInit.keyChangePerspective);
        addKeybind(OTHER, BetterKeybindMenuInit.keyFullscreen);

        addKeybind(C_DEBUG, BetterKeybindMenuInit.keyDebugInfo);
        addKeybind(C_DEBUG, BetterKeybindMenuInit.keyDebugReloadShaders);


        // this is for testing!!! not recommended for human consumption
//        for (int i = 0; i <= Input.Keys.MAX_KEYCODE; i++) {
//            addKeybind(test, Identifier.of("BetterKeybindMenu", Input.Keys.toString(i)), Keybind.fromDefaultKey(String.valueOf(i), i));
//        }
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
        for (KeybindEntry entry : KeybindTabs.activeTab.activeCategory().keybinds()) {
            KeybindWidget widget = new KeybindWidget(entry);
            keybindTable.add(widget).growX().height(70).padBottom(5).row();
        }

        // need to do this so can set sub widget to debug
        if (DEBUG) baseTable.setDebug(DEBUG, true);
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
        this.searchBar = new TextField(Lang.get("keybindSelectionSearch"), GameStyles.textstyle);

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
        // do better controls checking

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
        // need to do this so can set sub widget to debug
        if (DEBUG) baseTable.setDebug(DEBUG, true);
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
        //noinspection DuplicatedCode
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

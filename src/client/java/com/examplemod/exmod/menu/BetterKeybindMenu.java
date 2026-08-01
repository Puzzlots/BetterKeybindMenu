package com.examplemod.exmod.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.examplemod.exmod.KeyAtlas;
import com.examplemod.exmod.menu.buttons.CategoryButton;
import com.examplemod.exmod.menu.buttons.LangButton;
import com.examplemod.exmod.ui.widgets.KeybindWidget;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.settings.Keybind;
import finalforeach.cosmicreach.ui.GameStyles;
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

    LangButton keyboardTabButton;
    LangButton controllerTabButton;

    ScrollPane keybindScroll;

    Color activeColor = Color.GOLD;
    Color inactiveColor = Color.WHITE;


    public static Category CAT_MOVEMENT = new Category("base", "movement");
    public static Category CAT_INTERACTIONS = new Category("base", "interactions");
    public static Category CAT_INVENTORY = new Category("base", "inventory");
    public static Category CAT_DEBUG = new Category("base", "debug");
    public static Category CAT_OTHER = new Category("base", "other");

    public static Category test = new Category("BetterKeybindMenu", "other");

    public BetterKeybindMenu(GameState previousState) {
        this.previousState = previousState;
    }

    public void addKeybind(Category category, Identifier id, Keybind keybind) {
        category.addKeybind(new KeybindEntry(id, keybind));
    }

    public void initCategories() {
        keybindTabs.keyboard().addCategory(CAT_MOVEMENT);
        keybindTabs.keyboard().addCategory(CAT_INTERACTIONS);
        keybindTabs.keyboard().addCategory(CAT_INVENTORY);
        keybindTabs.keyboard().addCategory(CAT_OTHER);
        keybindTabs.keyboard().addCategory(test);

        keybindTabs.controller().addCategory(test);
        keybindTabs.controller().addCategory(CAT_DEBUG);
    }

    public void initKeybinds() {
//        addKeybind(CAT_MOVEMENT, Identifier.of("base", "Forward"), ControlSettings.keyForward);
//
//        addKeybind(test, Identifier.of("BetterKeybindMenu", "keyForward"), ControlSettings.keyForward);
//        addKeybind(test, Identifier.of("BetterKeybindMenu", "keyChat"), ControlSettings.keyChat);
//        addKeybind(test, Identifier.of("BetterKeybindMenu", "keyAttackBreak"), ControlSettings.keyAttackBreak);
//        addKeybind(test, Identifier.of("BetterKeybindMenu", "keyCrouch"), ControlSettings.keyCrouch);
//        addKeybind(test, Identifier.of("BetterKeybindMenu", "keyChangePerspective"), ControlSettings.keyChangePerspective);

        // this is for testing!!! not recommended for human consumption
        for (int i = 0; i <= Input.Keys.MAX_KEYCODE; i++) {
            addKeybind(test, Identifier.of("BetterKeybindMenu", Input.Keys.toString(i)), Keybind.fromDefaultKey(String.valueOf(i), i));
        }
    }

    private void setTab(TabType tabType) {
        boolean isControllerTab = tabType == TabType.controller;
        KeybindTabs.activeTab = isControllerTab ? keybindTabs.controller() : keybindTabs.keyboard();
        KeybindTabs.activeTab.setActiveCategoryIndex(0);

        categoryTable.clear();
        for (Category category : KeybindTabs.activeTab.categories) {
            CategoryButton button = new CategoryButton(category) {
                @Override public void onClick() {
                    selectCategory(category);
                }
            };

            categoryTable.add(button).width(250).height(50).padBottom(5).row();
        }

        keyboardTabButton.setTextColor(isControllerTab ? inactiveColor : activeColor);
        controllerTabButton.setTextColor(isControllerTab ? activeColor : inactiveColor);

        selectCategory(KeybindTabs.activeTab.activeCategory());
    }

    private void selectCategory(Category category) {
        keybindTable.clear();
        KeybindTabs.activeTab.setActiveCategory(category);
        for (KeybindEntry entry : KeybindTabs.activeTab.activeCategory().keybinds()) {
            KeybindWidget button = new KeybindWidget(entry);
            keybindTable.add(button).growX().height(70).padBottom(5).row();
        }

        baseTable.setDebug(DEBUG, true);
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

        keyboardTabButton = new LangButton(Lang.get("keyboard_tab")) {
            public void onClick() {
                setTab(TabType.keyboard);
            }
        };

        controllerTabButton = new LangButton(Lang.get("controller_tab")) {
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

        LangButton doneButton = new LangButton(Lang.get("done")) {
            public void onClick() {
                GameState.switchToGameState(previousState);
            }
        };

        bottomButtons.defaults().growX();
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

        Gdx.input.setInputProcessor(this.stage);
        baseTable.setDebug(DEBUG, true);
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

package com.examplemod.exmod.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.examplemod.exmod.ui.widgets.KeybindWidget;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.settings.ControlSettings;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.settings.Keybind;
import finalforeach.cosmicreach.ui.GameStyles;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.world.Sky;

import static com.examplemod.exmod.menu.CategoryType.*;

public class BetterKeybindMenu extends GameState {

    boolean isControllerTab;
    private Camera starCamera;

    public final static boolean DEBUG = true;

    TextField searchBar;
    CategoryButton activeCategoryButton;

    OrderedMap<Identifier, Category> categories = new OrderedMap<>();

    /// tables ///
    Table baseTable = new Table();
    Table keyboardCategoryTable = new Table();
    Table controllerCategoryTable = new Table();

    Table keyboardKeybindsContainer = new Table();
    Table controllerKeybindsContainer = new Table();

    /// buttons ///

    LangButton keyboardTabButton;
    LangButton controllerTabButton;

    ScrollPane keyboardKeybindScroll;
    ScrollPane controllerKeybindScroll;

    Color activeColor = Color.GOLD;
    Color inactiveColor = Color.WHITE;


    public static Identifier CAT_MOVEMENT = Identifier.of("base", "movement");
    public static Identifier CAT_INTERACTIONS = Identifier.of("base", "interactions");
    public static Identifier CAT_INVENTORY = Identifier.of("base", "inventory");
    public static Identifier CAT_DEBUG = Identifier.of("base", "debug");
    public static Identifier CAT_OTHER = Identifier.of("base", "other");

    public static Identifier test = Identifier.of("BetterKeybindMenu", "other");

    public void addCategory(CategoryType type, Identifier id) {
        categories.put(id, new Category(type, id));
    }

    public void addKeybind(Identifier categoriesId, Identifier id, Keybind keybind) {
        categories.get(categoriesId).addKeybind(new KeybindEntry(id, keybind));
    }

    public void initCategory() {
        addCategory(keyboard, CAT_MOVEMENT);
        addCategory(keyboard, CAT_INTERACTIONS);
        addCategory(keyboard, CAT_INVENTORY);
        addCategory(controller, CAT_DEBUG);
        addCategory(keyboard, CAT_OTHER);
        addCategory(both, test);
    }

    public void initKeybinds() {
        addKeybind(CAT_MOVEMENT, Identifier.of("base", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyChat);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyAttackBreak);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyCrouch);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyChangePerspective);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
        addKeybind(test, Identifier.of("BetterKeybindMenu", "Forward"), ControlSettings.keyForward);
    }

    private void setControllerTab(boolean controller) {
        isControllerTab = controller;

        keyboardTabButton.setTextColor(controller ? inactiveColor : activeColor);
        controllerTabButton.setTextColor(controller ? activeColor : inactiveColor);

        keyboardCategoryTable.getParent().setVisible(!controller);
        controllerCategoryTable.getParent().setVisible(controller);

        hideInactiveKeybinds();

        if (activeCategoryButton != null) {
            activeCategoryButton.setTextColor(inactiveColor);
            activeCategoryButton.deselect();
            activeCategoryButton = null;
        }

        ((Stack) keyboardCategoryTable.getParent().getParent()).layout();
        baseTable.setDebug(DEBUG, true);
    }

    private void hideInactiveKeybinds() {
        keyboardKeybindsContainer.setVisible(!isControllerTab);
        controllerKeybindsContainer.setVisible(isControllerTab);

        if (isControllerTab) {
            keyboardKeybindsContainer.getParent().setTouchable(Touchable.disabled);
            controllerKeybindsContainer.getParent().setTouchable(Touchable.enabled);
        } else {
            keyboardKeybindsContainer.getParent().setTouchable(Touchable.enabled);
            controllerKeybindsContainer.getParent().setTouchable(Touchable.disabled);
        }
        baseTable.setDebug(DEBUG, true);
    }

    private void selectCategory(CategoryButton button, Table keybindTable) {
        if (activeCategoryButton != null) {
            activeCategoryButton.deselect();
            activeCategoryButton.setTextColor(inactiveColor);
        }

        activeCategoryButton = button;
        activeCategoryButton.setTextColor(activeColor);
        button.select();

        if (isControllerTab) {
            controllerKeybindsContainer.clearChildren();
            controllerKeybindsContainer.add(keybindTable).growX().row();
            controllerKeybindsContainer.add().growY();

            controllerKeybindsContainer.invalidateHierarchy();
            controllerKeybindScroll.layout();
            controllerKeybindScroll.setScrollY(0);
        } else {
            keyboardKeybindsContainer.clearChildren();
            keyboardKeybindsContainer.add(keybindTable).growX().row();
            keyboardKeybindsContainer.add().growY();

            keyboardKeybindsContainer.invalidateHierarchy();
            keyboardKeybindScroll.layout();
            keyboardKeybindScroll.setScrollY(0);
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

        keyboardKeybindsContainer.setName("keyboardKeybindsContainer");
        controllerKeybindsContainer.setName("controllerKeybindsContainer");

        initCategory();
        initKeybinds();

        baseTable.clear();
        baseTable.setFillParent(true);
        this.stage.addActor(baseTable);

        // header //////////

        Table header = new Table();
        this.searchBar = new TextField(Lang.get("keybindSelectionSearch"), GameStyles.textstyle);

        keyboardTabButton = new LangButton(Lang.get("keyboard_tab")) {
            public void onClick() {
                setControllerTab(false);
            }
        };

        controllerTabButton = new LangButton(Lang.get("controller_tab")) {
            public void onClick() {
                setControllerTab(true);
            }
        };

        keyboardTabButton.setTextColor(isControllerTab ? inactiveColor : activeColor);
        controllerTabButton.setTextColor(isControllerTab ? activeColor : inactiveColor);

        header.add(searchBar).minWidth(200).prefWidth(500).height(50).growX();
        header.add(keyboardTabButton).padLeft(5).minWidth(180).prefWidth(250).maxWidth(250).height(50);
        header.add(controllerTabButton).padLeft(5).minWidth(180).prefWidth(250).maxWidth(250).height(50);
        header.setClip(true);

        baseTable.add(header).growX().top().pad(10);
        baseTable.row();

        // categories //

        Table content = new Table();
        baseTable.add(content).grow();

        Table rightSideContent = new Table();

        keyboardCategoryTable.top();
        controllerCategoryTable.top();

        ScrollPane keyboardCategoryScroll = new ScrollPane(keyboardCategoryTable);
        ScrollPane controllerCategoryScroll = new ScrollPane(controllerCategoryTable);

        keyboardCategoryTable.defaults().growX().top();
        controllerCategoryTable.defaults().growX().top();


        keyboardCategoryScroll.setFadeScrollBars(false);
        controllerCategoryScroll.setFadeScrollBars(false);
        keyboardCategoryScroll.setScrollingDisabled(true, false);
        controllerCategoryScroll.setScrollingDisabled(true, false);


        Stack categoryStack = new Stack();
        categoryStack.add(keyboardCategoryScroll);
        categoryStack.add(controllerCategoryScroll);

        content.add(categoryStack)
                .growY()
                .top()
                .left()
                .width(250)
                .pad(10);


        // ///////////// keybinds /////////////////////

        keyboardKeybindScroll = new ScrollPane(keyboardKeybindsContainer);
        controllerKeybindScroll = new ScrollPane(controllerKeybindsContainer);

        keyboardKeybindScroll.setFadeScrollBars(false);
        controllerKeybindScroll.setFadeScrollBars(false);
        keyboardKeybindScroll.setScrollingDisabled(true, false);
        controllerKeybindScroll.setScrollingDisabled(true, false);

        Stack keybindStack = new Stack();
        keybindStack.add(keyboardKeybindScroll);
        keybindStack.add(controllerKeybindScroll);

        rightSideContent.defaults().growX();
        rightSideContent.top();
        rightSideContent.add(keybindStack).growX().top().padTop(10).padRight(10).padBottom(10);

        activeCategoryButton = null;

        for (Category category : categories.values()) {
            Table keyboardKeybinds = new Table();
            Table controllerKeybinds = new Table();

            for (KeybindEntry entry : category.keybinds) {
//                String name = Lang.get(entry.id().toString());
//                TextField keyboardField = new TextField(name, GameStyles.textstyle);
//                TextField controllerField = new TextField(name, GameStyles.textstyle);

                KeybindWidget keyboardButton = new KeybindWidget(entry);
                KeybindWidget controllerButton = new KeybindWidget(entry);

                //TODO make Typed
                keyboardKeybinds.add(keyboardButton).growX().height(70).padBottom(5).row();
                controllerKeybinds.add(controllerButton).growX().height(70).padBottom(5).row();
            }

            CategoryButton keyboardButton = new CategoryButton(category) {
                @Override public void onClick() {
                    selectCategory(this, keyboardKeybinds);
                }
            };

            CategoryButton controllerButton = new CategoryButton(category) {
                @Override public void onClick() {
                    selectCategory(this, controllerKeybinds);
                }
            };

            switch (category.getType()) {
                case keyboard -> keyboardCategoryTable.add(keyboardButton).width(250).height(50).padBottom(5).row();
                case controller -> controllerCategoryTable.add(controllerButton).width(250).height(50).padBottom(5).row();
                case both -> {
                    keyboardCategoryTable.add(keyboardButton).width(250).height(50).padBottom(5).row();
                    controllerCategoryTable.add(controllerButton).width(250).height(50).padBottom(5).row();
                }
            }
        }

        // bottm buttons /////
        Table bottomButtons = new Table();

        LangButton doneButton = new LangButton(Lang.get("done")) {
            public void onClick() {
                GameState.switchToGameState(new OptionsMenu(currentGameState));
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
        setControllerTab(Controls.controllers.size > 0);
        baseTable.setDebug(DEBUG, true);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void switchAwayTo(GameState gameState) {
        super.switchAwayTo(gameState);
        Gdx.input.setInputProcessor(null);
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

package com.examplemod.exmod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;

public class GetKeyNumMenu extends GameState {

    CRLabel keyboardField;

    CRLabel keyCodeField;

    Cell<Image> keyIconImageCell;
    Image keyIconImage;
    CRButton button;
    CRButton button2;

    int keyCode = 0;
    Table table;

    @Override
    public void create() {
        super.create();

        table = new Table();
        table.setFillParent(true);

        keyboardField = new CRLabel("test");

        keyCodeField = new CRLabel(Input.Keys.toString(keyCode) + ": " + keyCode + ": " + Integer.toHexString(keyCode));

        table.add(keyboardField).center().padRight(30);
        table.add(keyCodeField).center().padRight(30);

        button = new CRButton("up keycode") {
            @Override
            public void onClick() {
                super.onClick();
                if (!(keyCode >= Input.Keys.MAX_KEYCODE)){
                    ++keyCode;
                } else {
                    keyCode = 0;
                }
                String keyString = Input.Keys.toString(keyCode);
                String text = keyString + ": " + keyCode + ": " + Integer.toHexString(keyCode);
                keyCodeField.setText(text);
                System.out.println(text);
                updateIcon();
            }
        };

        button2 = new CRButton("down keycode") {
            @Override
            public void onClick() {
                super.onClick();
                if (keyCode > 0){
                    --keyCode;
                } else {
                    keyCode = 0;
                }
                String keyString = Input.Keys.toString(keyCode);
                String text = keyString + ": " + keyCode + ": " + Integer.toHexString(keyCode);
                keyCodeField.setText(text);
                System.out.println(text);
                updateIcon();
            }
        };
        table.add(button).size(200, 50).padRight(30);
        table.add(button2).size(200, 50).padRight(30);

        keyIconImage = KeyAtlas.getImageOfKey(keyCode);

        this.keyIconImageCell = table.add(this.keyIconImage)
                .size(this.keyIconImage.getWidth() * 4, this.keyIconImage.getHeight() * 4)
                .padRight(30);

        this.stage.addActor(table);

        this.stage.addListener(event -> {
            if (event instanceof InputEvent inputevent) {

                if (inputevent.getType() == InputEvent.Type.keyDown) {
                    int keyNum = inputevent.getKeyCode();
                    String keyString = Input.Keys.toString(keyNum);
                    String text = keyString + ": " + keyNum + ": " + Integer.toHexString(keyNum);
                    keyboardField.setText(text);
                    System.out.println(text);
                }

                if (inputevent.getType() == InputEvent.Type.touchDown) {
                    int button = inputevent.getButton();
                    String buttonName = switch (button) {
                        case Input.Buttons.LEFT -> "LEFT";
                        case Input.Buttons.RIGHT -> "RIGHT";
                        case Input.Buttons.MIDDLE -> "MIDDLE";
                        case Input.Buttons.BACK -> "BACK";
                        case Input.Buttons.FORWARD -> "FORWARD";
                        default -> "UNKNOWN";
                    };
                    String text = buttonName + ": " + button + ": " + Integer.toHexString(button);
                    keyboardField.setText(text);
                    System.out.println(text);
                }
            }

            return false;
        });
    }

    public void updateIcon() {
        Image newKeyIconImage = KeyAtlas.getImageOfKey(keyCode);
        this.keyIconImage.setDrawable(newKeyIconImage.getDrawable());

        float newWidth = newKeyIconImage.getWidth() * 4;
        float newHeight = newKeyIconImage.getHeight() * 4;

        this.keyIconImage.setSize(newWidth, newHeight);
        this.keyIconImageCell.size(newWidth, newHeight);

        table.invalidate();
    }

    @Override
    public void switchAwayTo(GameState gameState) {
        super.switchAwayTo(gameState);
        Gdx.input.setInputProcessor(null);
    }

    @Override
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
        this.stage.draw();
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(2884);

    }

}

package com.examplemod.exmod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.widgets.CRLabel;

public class GetKeyNumMenu extends GameState {

    CRLabel keyboardField;

    @Override
    public void create() {
        super.create();

        Table table = new Table();
        table.setFillParent(true);

        keyboardField = new CRLabel("test");

        table.add(keyboardField).center();
        this.stage.addActor(table);

        this.stage.addListener(event -> {
            if (event instanceof InputEvent inputevent) {

                if (inputevent.getType() == InputEvent.Type.keyDown) {
                    int keyNum = inputevent.getKeyCode();
                    String keyString = Input.Keys.toString(keyNum);
                    String text = keyString + ": " + keyNum;
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
                    String text = buttonName + ": " + button;
                    keyboardField.setText(text);
                    System.out.println(text);
                }
            }

            return false;
        });
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

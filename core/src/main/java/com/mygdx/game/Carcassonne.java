package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.screen.MainMenuScreen;


public class Carcassonne extends Game {

    public interface NativeInterface {
        void getPhoto(PhotoCallback cb);
    }

    public interface PhotoCallback {
        void onPhotoReady(byte[] bytes);
    }


    public static Skin skin;
    private static NativeInterface nativeInterface;

    public Carcassonne(NativeInterface ni) {
        nativeInterface = ni;
    }

    public static void setSkin() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void create() {
        setSkin();
        NetworkHelper.setPlayer(new Player(GameBoard.Color.RED, "Player"));
        this.setScreen(new MainMenuScreen(this));
    }

    public static NativeInterface getNativeInterface() {
        return nativeInterface;
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}

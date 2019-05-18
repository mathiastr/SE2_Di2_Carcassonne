package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Player;

public class GameOverScreen implements Screen {

    private Stage stage;
    private Player winner;
    private Game game;

    public GameOverScreen(Game aGame, Player aWinner) {
        winner = aWinner;
        game = aGame;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        //// TODO change.....
        //winner = new Player(GameBoard.Color.black, "Herbert");
        //winner.setPhoto(new Texture(Gdx.files.internal("profilePhoto.png")));


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/Amble-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 150;
        parameter.borderWidth = 1;
        parameter.color = Color.ORANGE;
        parameter.shadowOffsetX = 10;
        parameter.shadowOffsetY = 10;
        parameter.shadowColor = new Color(0, 0, 0f, 1);
        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;

        Label winTextLabel = new Label("winner with " + winner.getScore() + " points is ...", labelStyle);
        winTextLabel.setAlignment(Align.center);
        winTextLabel.setY(Gdx.graphics.getHeight()/1.2f);
        winTextLabel.setWidth(Gdx.graphics.getWidth());
        winTextLabel.setFontScale(0.5f);
        stage.addActor(winTextLabel);


        Label winnerLabel = new Label(winner.getName(), labelStyle);
        winnerLabel.setAlignment(Align.center);
        winnerLabel.setY(Gdx.app.getGraphics().getHeight()/1.6f);
        winnerLabel.setWidth(Gdx.graphics.getWidth());
        stage.addActor(winnerLabel);


        Image winnerImage = new Image(winner.getPhoto());
        winnerImage.setSize(Gdx.app.getGraphics().getHeight()/2.5f, Gdx.app.getGraphics().getHeight()/2.5f);
        winnerImage.setPosition(Gdx.app.getGraphics().getWidth()/2f-Gdx.app.getGraphics().getHeight()/2.5f/2f, Gdx.app.getGraphics().getHeight()/7f);
        stage.addActor(winnerImage);

        Image crown = new Image(new Texture(Gdx.files.internal("crown.png")));
        crown.setSize(Gdx.app.getGraphics().getHeight()/10f, Gdx.app.getGraphics().getHeight()/10f);
        crown.setPosition(Gdx.app.getGraphics().getWidth()/2f-Gdx.app.getGraphics().getHeight()/10f/2f,
                Gdx.app.getGraphics().getHeight()/2f);
        stage.addActor(crown);

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        Gdx.input.setInputProcessor(stage);

        Music sound = Gdx.audio.newMusic(Gdx.files.internal("trumpet.mp3"));
        sound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

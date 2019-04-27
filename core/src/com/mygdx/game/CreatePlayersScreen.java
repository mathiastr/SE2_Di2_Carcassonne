package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class CreatePlayersScreen implements Screen {

    private Game game;
    private Stage stage;
    private Texture background;
    private List<Player> players = new ArrayList<Player>();
    private final BitmapFont font;
    private Table playerListTable;

    public void renderPlayersList() {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        font.getData().setScale(4);
        textStyle.font = font;
        textStyle.fontColor = Color.BLACK;

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;

        if (playerListTable != null) {
            playerListTable.remove();
        }
        playerListTable = new Table();

        playerListTable.add(new Label("", textStyle)).width(100);
        Label nameLabel = new Label("Name", textStyle);
        nameLabel.setAlignment(Align.center);
        playerListTable.add(nameLabel).width(500);
        playerListTable.row();

        for (Player player : players) {
            TextField nameField = new TextField(player.getName(), textFieldStyle);

            //TODO profile photo
            Texture imageTexture = new Texture(Gdx.files.internal("profilePhoto.png"));
            Image profilePhoto = new Image();
            profilePhoto.setDrawable(new TextureRegionDrawable(new TextureRegion(imageTexture)));

            playerListTable.add(profilePhoto).width(100).height(100);
            nameField.setAlignment(Align.center);

            nameField.setTextFieldListener(new TextField.TextFieldListener() {
                @Override
                public void keyTyped(TextField textField, char c) {
                    player.setName(nameField.getText());
                }
            });
            playerListTable.add(nameField).width(500);
            playerListTable.add(new Label("" + player.getColor().name(), textStyle));
            playerListTable.row();

        }

        playerListTable.setFillParent(true);
        stage.addActor(playerListTable);
    }

    public CreatePlayersScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        players.add(new Player(GameBoard.Color.red, "Player 1"));
        players.add(new Player(GameBoard.Color.blue, "Player 2"));

        // TODO: do I need this?
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        background = new Texture("background.png");
        Image backgroundImage = new Image(background);
        backgroundImage.setWidth(Gdx.graphics.getWidth());
        backgroundImage.setHeight(Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        int marginTop = 50;

        // TODO another styleName
        TextButton startGameButton = new TextButton("Start Game", Carcassonne.skin, "menu");
        startGameButton.setWidth(Gdx.graphics.getWidth() / 4);
        startGameButton.setPosition(Gdx.graphics.getWidth() / 2 - startGameButton.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9 - marginTop);
        startGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("touch", "start button is touched");
                game.setScreen(new GameScreen(game, players));
            }
        });

        TextButton addPlayer = new TextButton("Add player", Carcassonne.skin, "menu");
        addPlayer.setWidth(Gdx.graphics.getWidth() / 4);
        addPlayer.setPosition(Gdx.graphics.getWidth() / 2 - startGameButton.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9 + startGameButton.getHeight() * 3 / 2 - marginTop);
        addPlayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (players.size() < GameBoard.MAX_NUM_OF_PLAYERS) {
                    List<GameBoard.Color> colors = new ArrayList<>();
                    for (Player p : players) {
                        colors.add(p.getColor());
                    }
                    players.add(new Player(GameBoard.Color.getRandomColorExcept(colors),
                            "Player " + (players.size() + 1)));
                    renderPlayersList();
                }
                Gdx.app.debug("touch", "add player button is touched");
            }
        });

        stage.addActor(addPlayer);
        stage.addActor(startGameButton);
        // TODO what is that?

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Label text;
        Label.LabelStyle headerStyle = new Label.LabelStyle();
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getData().setScale(5);
        headerStyle.font = font;
        headerStyle.fontColor = Color.BLACK;
        text = new Label("Players", headerStyle);
        text.setAlignment(Align.top);

        stage.addActor(text);

        renderPlayersList();

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
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

package com.mygdx.game.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.network.NetworkHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePlayersScreen extends BaseScreen {

    private Stage stage;
    private Texture background;
    private List<Player> players = new ArrayList<>();
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

        populatePlayerListTable(textStyle, textFieldStyle);
    }

    public CreatePlayersScreen(final Game game) {
        stage = new Stage(new ScreenViewport());

        initilizeBasicPlayers();

        NetworkHelper.setPlayer(players.get(0));

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        setBackground();

        int marginTop = 10;

        TextButton startGameButton = createStartGameButton(game, marginTop);
        TextButton addPlayer = createAddPlayerButton(marginTop, startGameButton);

        stage.addActor(addPlayer);
        stage.addActor(startGameButton);

        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getData().setScale(5);

        renderPlayersList();
        Gdx.input.setInputProcessor(stage);

    }

    private void setBackground() {
        background = new Texture("background.png");
        Image backgroundImage = new Image(background);
        backgroundImage.setWidth(Gdx.graphics.getWidth());
        backgroundImage.setHeight(Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
    }

    private void populatePlayerListTable(Label.LabelStyle textStyle, TextField.TextFieldStyle textFieldStyle) {
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

            profilePhoto.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Carcassonne.getNativeInterface().getPhoto((byte[] bytes) -> {
                        Pixmap p = new Pixmap(bytes, 0, bytes.length);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                Texture tex = new Texture(p);
                                Sprite sprite = new Sprite(tex);
                                sprite.setRotation(180f);
                                profilePhoto.setDrawable(new SpriteDrawable(sprite));
                                player.setPhoto(tex);
                            }
                        });
                    });
                }
            });

            playerListTable.add(profilePhoto).width(100).height(100);
            nameField.setAlignment(Align.center);

            nameField.setTextFieldListener((textField, c) -> player.setName(nameField.getText()));
            playerListTable.add(nameField).width(500);
            playerListTable.add(new Label("" + player.getColor().name(), textStyle));

            TextButton deleteButton = new TextButton("delete", Carcassonne.skin, "menu");
            playerListTable.add(deleteButton);
            deleteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.debug("touch", "delete button is touched");
                    players.remove(player);
                    renderPlayersList();
                }
            });
            playerListTable.row();

        }

        playerListTable.setFillParent(true);
        playerListTable.setY(-125);
        stage.addActor(playerListTable);
    }

    private void initilizeBasicPlayers() {
        players.add(new Player(GameBoard.Color.RED, "Player 1"));
        players.add(new Player(GameBoard.Color.BLUE, "Player 2"));
    }

    private TextButton createStartGameButton(Game game, int marginTop) {
        TextButton startGameButton = new TextButton("Start Game", Carcassonne.skin, "menu");
        startGameButton.setWidth((float) Gdx.graphics.getWidth() / 4);
        startGameButton.setPosition((float) Gdx.graphics.getWidth() / 2 - startGameButton.getWidth() / 2, (float) Gdx.graphics.getHeight() * 6 / 9 - marginTop);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("touch", "start button is touched");
                // in this case "me" doesn't matter
                game.setScreen(new GameScreen(game, players, true, players.get(0), null));
            }
        });
        return startGameButton;
    }

    private TextButton createAddPlayerButton(int marginTop, TextButton startGameButton) {
        TextButton addPlayer = new TextButton("Add player", Carcassonne.skin, "menu");
        addPlayer.setWidth((float) Gdx.graphics.getWidth() / 4);
        addPlayer.setPosition((float) Gdx.graphics.getWidth() / 2 - startGameButton.getWidth() / 2, (float) Gdx.graphics.getHeight() * 6 / 9 + startGameButton.getHeight() * 3 / 2 - marginTop);
        addPlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (players.size() < GameBoard.MAX_NUM_OF_PLAYERS) {
                    List<GameBoard.Color> colors = new ArrayList<>();
                    List<Integer> numbers = new ArrayList<>();
                    for (Player player : players) {
                        numbers.add(Integer.valueOf(player.getName().split(" ")[1]));
                        colors.add(player.getColor());
                    }
                    Integer[] arr = {1, 2, 3, 4, 5, 6};
                    List<Integer> rest = (new ArrayList<>(Arrays.asList(arr)));
                    rest.removeAll(numbers);
                    players.add(new Player(GameBoard.Color.getRandomColorExcept(colors),
                            "Player " + rest.get(0)));
                    renderPlayersList();
                }
                Gdx.app.debug("touch", "add player button is touched");
            }
        });
        return addPlayer;
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}

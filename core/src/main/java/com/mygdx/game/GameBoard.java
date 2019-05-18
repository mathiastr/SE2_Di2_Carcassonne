package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.actor.AddTileActor;
import com.mygdx.game.actor.PlayerStatusActor;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.network.GameClient;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.network.response.CurrentTileMessage;
import com.mygdx.game.network.response.TilePlacementMessage;
import com.mygdx.game.network.response.TurnEndMessage;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GameBoard {
    public enum Color {
        yellow, red, green, blue, black, grey;

        public static Color getRandomColorExcept(List<Color> colors) {
            Color randomColor = getRandom();
            if (colors.size() >= values().length) {
                return randomColor;
            }
            while (colors.contains(randomColor)) {
                randomColor = getRandom();
            }
            return randomColor;
        }

        public static Color getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private Stage stageOfBoard;
    private Stage stageOfUI;
    public final static int MAX_NUM_OF_PLAYERS = 6;
    private final TextButton finishTurnButton;
    private boolean tileIsPlaced = false;
    private boolean turnIsFinished = false;
    private boolean isLocal;
    private Player me;
    private int lastTileNumber;
    private Position lastTilePosition;
    private int currentTileNumber;
    private GameClient gameClient;

    private int numberOfPlayers;
    private Player currentPlayer;
    private List<Player> players;
    private ArrayList<TileActor> hints = new ArrayList<>();
    private ArrayList<TileActor> usedTiles = new ArrayList<>();

    /* is the deck of tiles */
    Board board;

    private TileActor currentTile;

    public TileActor getCurrentTile() {
        return currentTile;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public TileActor getRandomElement(List<TileActor> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public void showCurrentTile() {
        currentTile.setSize(300);
        currentTile.setPosition(Gdx.graphics.getWidth() - currentTile.getWidth() - 100, 100);
        currentTile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameClient != null && isMyTurn()) {
                    super.clicked(event, x, y);
                    clickForRotation();
                    event.handle();
                } else if (gameClient == null) {
                    super.clicked(event, x, y);
                    clickForRotation();
                    event.handle();
                }
            }
        });
        usedTiles.add(currentTile);
        board.getAvailableTiles().remove(board.getAvailableTiles().size()-1); // TODO
        stageOfUI.addActor(currentTile);
    }

    public void nextTurn() {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        turnIsFinished = false;
        tileIsPlaced = false;
        update();
    }

    public void beginMyTurn() {
        TileActor nextTile = getRandomElement(board.getAvailableTiles());
        CurrentTileMessage cm = new CurrentTileMessage();
        cm.tileNumber = board.getAvailableTiles().indexOf(nextTile);

        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(cm);
        }
        onTurnBegin(cm);
        showHintsForTile(currentTile);
    }

    public void onTurnBegin(CurrentTileMessage cm) {
        currentTile = board.getAvailableTiles().get(cm.tileNumber);
        showCurrentTile();
    }

    public void placeMyTile(Position position) {
        TilePlacementMessage tilePlacementMessage = new TilePlacementMessage(position, currentTile.getRotationValue());
        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(tilePlacementMessage);
        }

        onTilePlaced(tilePlacementMessage);
    }

    public void onTilePlaced(TilePlacementMessage tilePlacementMessage) {
        currentTile.setRotation(tilePlacementMessage.rotation);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                placeCurrentTileAt(tilePlacementMessage.position);
            }
        });
    }

    public void endMyTurn() {
        TurnEndMessage turnEndMessage = new TurnEndMessage();
        turnEndMessage.setMeeples(currentTile.getMeeples());
        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(turnEndMessage);
        }
        onTurnEnd(turnEndMessage);
    }

    public void onTurnEnd(TurnEndMessage turnEndMessage) {
        if (gameClient != null) {
            GameBoard that = this;
            Position pos = currentTile.getPosition();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    for (Meeple m : turnEndMessage.getMeeples()) {
                        new MeeplePlacement(that).drawMeeple(m.getSide(), pos);
                    }
                }
            });
        }
        nextTurn();
        if (isMyTurn()) {
            beginMyTurn();
        }
    }

    public void update() {

        if (gameClient != null) {

            if (!isMyTurn()) {
                finishTurnButton.setText("Wait");
            } else {
                finishTurnButton.setText("Finish turn");
            }


            if (isMyTurn()) {
                showHintsForTile(currentTile);
            }
        } else {
            if (!tileIsPlaced) {
                showHintsForTile(currentTile);
            }

        }
    }

    public boolean isMyTurn() {

        // TODO check not for name but for an ID (add id to a Player class)
        return (currentPlayer.getName().equals(me.getName()) || gameClient == null);
    }

    public GameBoard(Stage stageGame, Stage stageUI, List<Player> players, boolean isLocal, Player me, GameClient gameClient) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stageOfBoard = stageGame;
        stageOfUI = stageUI;

        numberOfPlayers = players.size();
        this.players = players;
        this.isLocal = isLocal;
        currentPlayer = players.get(0);
        this.me = me;
        this.gameClient = gameClient;


        this.board = new Board();
        board.createDeckTilesAndStartTile();
        TileActor startTile = board.getAvailableTiles().remove(0);
        board.getPlacedTiles().put(new Position(0, 0), startTile);
        stageOfBoard.addActor(startTile);


        if (gameClient != null) {

            gameClient.getClient().addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof TilePlacementMessage) {
                        onTilePlaced((TilePlacementMessage) object);
                    }

                    if (object instanceof CurrentTileMessage) {
                        onTurnBegin((CurrentTileMessage) object);
                    }
                    if (object instanceof TurnEndMessage) {
                        onTurnEnd((TurnEndMessage)object);
                    }
                }
            });
        }

        /*
        TODO: probably best to generate a seed at the "server-player", then send out the seed and do the shuffle locally
         */

        long seed = 123456789;


        if (isMyTurn()) {
            beginMyTurn();
        }

        finishTurnButton = new TextButton("Finish turn", Carcassonne.skin, "default");

        finishTurnButton.setWidth(300);
        finishTurnButton.getLabel().setFontScale(0.8f);
        finishTurnButton.setPosition(Gdx.graphics.getWidth() - 300 - 100, 0);
        finishTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameClient != null && isMyTurn()) {
                    if (tileIsPlaced) {
                        turnIsFinished = true;
                        endMyTurn();
                    }
                } else if (gameClient == null) {
                    turnIsFinished = true;
                    endMyTurn();
                }

            }
        });

        stageUI.addActor(finishTurnButton);

        for (Player p : players) {
            PlayerStatusActor playerStatusActor = new PlayerStatusActor(p);
            playerStatusActor.setPosition(players.indexOf(p) * PlayerStatusActor.WIDTH, Gdx.graphics.getHeight(), Align.topLeft);
            stageUI.addActor(playerStatusActor);
        }
    }

    public int tilesLeft() {
        return board.getAvailableTiles().size();
    }

    public void placeTileAt(TileActor tileToPlace, Position position) {
        if (!tileIsPlaced) {
            stageOfBoard.addActor(tileToPlace);
            tileToPlace.setPosition(position);
            board.getPlacedTiles().put(position, tileToPlace);
            tileIsPlaced = true;

            DelayedRemovalArray<EventListener> listeners = tileToPlace.getListeners();

            if (listeners != null && listeners.size != 0) {
                tileToPlace.removeListener(listeners.peek());
            }
        }
    }

    public void placeCurrentTileAt(Position position) {
        if (!tileIsPlaced) {
            DelayedRemovalArray<EventListener> listeners = currentTile.getListeners();

            currentTile.setSize(128);
            TileActor tileToPlace = currentTile;
            tileToPlace.remove(); // remove tile from ui view, so we can place it on the board

            placeTileAt(currentTile, position);


            /*-----------------------------*/
            // Testing the road/city scoring
            // for every placed tile
            /*
            for (Feature f : tileToPlace.getFeatures()) {
                if (f instanceof Road)
                    Gdx.app.log("scoring [road]", f.toString() + " " + Integer.toString(scoreRoadOrCity(tileToPlace, (Road) f)));
                if (f instanceof City)
                    Gdx.app.log("scoring [city]", f.toString() + " " + Integer.toString(scoreRoadOrCity(tileToPlace, (City) f)));
            }
            for (Position pos : tileToPlace.getPosition().getSurroundingPositions()) {
                if (tiles.containsKey(pos)) {
                    TileActor t = tiles.get(pos);
                    if (t.isMonastery()) {
                        Gdx.app.log("scoring [monastery]", Integer.toString(scoreMonastery(t)));
                    }
                }
            } */
            /*-----------------------------*/

            removeOldHints();

        }

    }

    public void clickForRotation() {
        currentTile.rotate();
        removeOldHints();
        //if (!tileIsPlaced) {
        showHintsForTile(currentTile);
        //}
    }

    /*
        method for dynamic checking owners of the city/road/field
        if two or more players own the same property, score is splitted between
     */
    public ArrayList<Player> getFeatureOwners(Feature feature, Side side, TileActor tile) {

        // count number of meeples of each player

        return new ArrayList<Player>();
    }

    public void showHintsForTile(TileActor tile) {
        removeOldHints();
        for (Position validPos : board.getValidPositionsForTile(tile)) {
            AddTileActor newHint = new AddTileActor(validPos, this);
            stageOfBoard.addActor(newHint);
            hints.add(newHint);
        }
    }

    public Stage getStageOfBoard() {
        return stageOfBoard;
    }

    public Stage getStageOfUI() {
        return stageOfUI;
    }

    public void removeOldHints() {
        for (TileActor a : hints) a.remove();
        hints.clear();
    }
    public ArrayList<TileActor> getUsedTiles() {
        return usedTiles;
    }
      public TileActor getPreviousTile(){
        int lastElement = usedTiles.size()-1;
        return usedTiles.get(lastElement);
    }

    public Player getWinningPlayer() {
        return Collections.max(players, Comparator.comparing(Player::getScore));
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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
import com.mygdx.game.network.response.CheatOnScoreMessage;
import com.mygdx.game.network.response.CurrentTileMessage;
import com.mygdx.game.network.response.ErrorMessage;
import com.mygdx.game.network.response.ErrorNumber;
import com.mygdx.game.network.response.TilePlacementMessage;
import com.mygdx.game.network.response.TurnEndMessage;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameBoard {

   private ArrayList<PlayerStatusActor> playerActorList;

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
    private TextButton finishTurnButton;
    private boolean tileIsPlaced = false;
    private Player me;
    private GameClient gameClient;
    private boolean meepleIsPlaced;
    private int numberOfPlayers;
    private static HashMap<Position, TileActor> usedTileHash = new HashMap<>();
    private Player currentPlayer;
    private List<Player> players;
    private ArrayList<TileActor> hints = new ArrayList<>();
    private ArrayList<TileActor> usedTiles = new ArrayList<>();
    private ArrayList<PlayerStatusActor> statuses = new ArrayList<>();
    private Board board;

    private TileActor currentTile;

    public TileActor getCurrentTile() {
        return currentTile;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() { return board; }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TileActor getRandomElement(List<TileActor> list) throws Exception {
        Random rand = new Random();
        if (list.size() == 0) {
            throw new Exception("No more tiles");
        }
        int randNumber = rand.nextInt(list.size());
        return list.get(randNumber);
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
        board.getAvailableTiles().remove(currentTile);
        stageOfUI.addActor(currentTile);
    }

    public void nextTurn() {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        tileIsPlaced = false;
        update();
    }

    public void beginMyTurn() {
        TileActor nextTile = null;
        try {
            nextTile = getRandomElement(board.getAvailableTiles());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Gdx.app.debug("DEBUG", " " + currentTile.getName() + " " + currentTile.toString());
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
        Gdx.app.debug("DEBUG", " " + tilePlacementMessage.rotation + " " + currentTile.toString());
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
        int score = board.getScore(currentTile);
        turnEndMessage.setPlayerScore(currentPlayer.getScore() + score);

        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(turnEndMessage);
        }

        onTurnEnd(turnEndMessage);
    }

    public void onTurnEnd(TurnEndMessage turnEndMessage) {
        this.currentPlayer.setScore(turnEndMessage.getPlayerScore());
        updatePlayersInfo();
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
        reduceCheatTime();
        nextTurn();
        if (isMyTurn()) {
            beginMyTurn();
        }
    }

    private void reduceCheatTime() {
        for (Player p :
                players) {
            p.reduceCheatTimeByOne();
        }
    }

    public void updatePlayersInfo() {
        for (PlayerStatusActor status : statuses) {
            status.updateInfo();
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
        return (currentPlayer.getId() == NetworkHelper.getPlayer().getId() || gameClient == null);
    }


    public GameBoard(Stage stageGame, Stage stageUI, List<Player> players, boolean isLocal, Player me, GameClient gameClient) {
        stageOfBoard = stageGame;
        stageOfUI = stageUI;

        numberOfPlayers = players.size();
        this.players = players;
        currentPlayer = players.get(0);
        this.me = me;
        this.gameClient = gameClient;
        this.board = new Board();
    }

    public void init() {
        board.createDeckTilesAndStartTile();
        TileActor startTile = board.getAvailableTiles().remove(0);
        board.getPlacedTiles().put(new Position(0, 0), startTile);
        stageOfBoard.addActor(startTile);

        if (gameClient != null) {

            gameClient.getClient().addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof CurrentTileMessage) {
                        onTurnBegin((CurrentTileMessage) object);
                    }
                    if (object instanceof TilePlacementMessage) {
                        onTilePlaced((TilePlacementMessage) object);
                    }
                    if (object instanceof TurnEndMessage) {
                        onTurnEnd((TurnEndMessage)object);
                    }
                    if (object instanceof CheatOnScoreMessage) {
                        onCheatOnScore((CheatOnScoreMessage)object);
                    }

                    if (object instanceof ErrorMessage) {
                        errorHandling((ErrorMessage)object, connection);
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

        if(NetworkHelper.getLastMessage() != null) {
            if(NetworkHelper.getLastMessage() instanceof CurrentTileMessage){
                onTurnBegin((CurrentTileMessage)NetworkHelper.getLastMessage());

                Gdx.app.debug("DEBUG", "Restore Game init error: " + NetworkHelper.getLastMessage().toString());
                NetworkHelper.setLastMessage(null);
            }
        }

        finishTurnButton = new TextButton("Finish turn", Carcassonne.skin, "default");

        finishTurnButton.setWidth(300);
        finishTurnButton.getLabel().setFontScale(0.8f);
        finishTurnButton.setPosition((float)Gdx.graphics.getWidth() - 300 - 100, 0);
        finishTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameClient != null && isMyTurn()) {
                    if (tileIsPlaced) {
                        endMyTurn();
                    }
                } else if (gameClient == null) {
                    endMyTurn();
                }

            }
        });

        stageOfUI.addActor(finishTurnButton);
        playerActorList = new ArrayList<>();

        for (Player p : players) {
            PlayerStatusActor playerStatusActor = new PlayerStatusActor(p);
            statuses.add(playerStatusActor);
            playerStatusActor.setPosition((float) players.indexOf(p) * PlayerStatusActor.WIDTH , Gdx.graphics.getHeight(), Align.topLeft);
            if(p == NetworkHelper.getPlayer()){
                playerStatusActor.addListener(new ActorGestureListener(20,0.4f,5f,0.15f){
                    @Override
                    public boolean longPress(Actor actor, float x, float  y) {
                        Gdx.app.debug("DEBUG","Long Press");
                        CheatOnScore();
                        return false;
                    }

                    @Override
                    public void touchDown(InputEvent event, float x, float y, int pointer, int button) {

                        Gdx.app.debug("DEBUG","Touch Down");
                    }
                });
            }
            stageOfUI.addActor(playerStatusActor);
            playerActorList.add(playerStatusActor);
        }
    }

    private void errorHandling(ErrorMessage error, Connection connection) {
        if(error.errorNumber == ErrorNumber.GAMENOTSTARTED){

            Gdx.app.debug("DEBUG","Game was not initialized by " + connection.getID());
        }
    }

    private void onCheatOnScore(CheatOnScoreMessage message) {
        for (Player p :
                players) {
            if(p.equals(message.getPlayer())){
                p.addScore(100);
                p.addTimeToDetectUsedCheats(message.getCheatTime());
                updatePlayersInfo();
            }
        }

    }

    private void CheatOnScore() {
        for (Player p : players
        ) {
            if(p.equals(NetworkHelper.getPlayer())){
                p.addScore(100);
                p.setTimeToDetectUsedCheats(3);
                if(NetworkHelper.getGameManager() != null){
                    NetworkHelper.getGameManager().sendToServer(new CheatOnScoreMessage(3,NetworkHelper.getPlayer()));
                }
                updatePlayersInfo();
            }
        }
    }

    public void performCheatAction(Player p) {
        if (p.equals(currentPlayer)) {
            p.cheatMeeple();
        } else if (p.isCheater()) {
            p.detectCheat();
        } else {
            currentPlayer.detectCheat();
        }
        updatePlayersInfo();
    }

    public int tilesLeft() {
        return board.getAvailableTiles().size();
    }

    public void placeTileAt(TileActor tileToPlace, Position position) {
        if (!tileIsPlaced) {
            addTileOnBoard(tileToPlace, position);
            usedTileHash.put(position, tileToPlace);
            tileIsPlaced = true;

            DelayedRemovalArray<EventListener> listeners = tileToPlace.getListeners();

            if (listeners != null && listeners.size != 0) {
                tileToPlace.removeListener(listeners.peek());
            }
        }
    }

    public void addTileOnBoard(TileActor tileToPlace, Position position) {
        tileToPlace.setPosition(position);
        board.getPlacedTiles().put(position, tileToPlace);
        stageOfBoard.addActor(tileToPlace);
    }

    public void placeCurrentTileAt(Position position) {
        if (!tileIsPlaced) {
            DelayedRemovalArray<EventListener> listeners = currentTile.getListeners();

            currentTile.setSize(128);
            TileActor tileToPlace = currentTile;
            tileToPlace.remove(); // remove tile from ui view, so we can place it on the board

            placeTileAt(currentTile, position);
            GameScreen.placeMeeple.setVisible(true);




            /*-----------------------------/
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

    public boolean meepleIsPlaced(){
        return meepleIsPlaced;
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

    public void setMeepleIsPlaced(boolean meepleIsPlaced) {
        this.meepleIsPlaced = meepleIsPlaced;
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
    public TileActor getNewestTile() {
        int lastElement = usedTiles.size()-1;
        return usedTiles.get(lastElement);
    }

    public List<TileActor> getNewestTileList() {
        return usedTiles;
    }


    public static Map<Position, TileActor> getUsedTileHash() {
        return usedTileHash;
    }

    public List<PlayerStatusActor> getPlayerActorList() {
        return playerActorList;
    }

    public int getScoreFromPlayer(Player p){
        int score = 0;
        for (Player player: players
        ) {
            if(p.equals(player)){
                score = player.getScore();
            }
        }
        return score;
    }

    public int getCheatTimeFromPlayer(Player p){
        int time = 0;
        for (Player player: players
        ) {
            if(p.equals(player)){
                time = player.getTimeToDetectUsedCheats();
            }
        }
        return time;
    }

    public Player getWinningPlayer() {
        return Collections.max(players, Comparator.comparing(Player::getScore));
    }

    public void setCurrentTile (TileActor tile){
        currentTile = tile;
    }
}
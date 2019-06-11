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
import com.mygdx.game.emotes.Emote;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.meeple.MeepleTextureFactory;
import com.mygdx.game.network.GameClient;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.network.response.CheatMessage;
import com.mygdx.game.network.response.CheatType;
import com.mygdx.game.network.response.CurrentTileMessage;
import com.mygdx.game.network.response.EmoteMessage;
import com.mygdx.game.network.response.ErrorMessage;
import com.mygdx.game.network.response.ErrorNumber;
import com.mygdx.game.network.response.TilePlacementMessage;
import com.mygdx.game.network.response.TurnEndMessage;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameBoard {

    private ArrayList<PlayerStatusActor> playerActorList;

    public enum Color {
        YELLOW, RED, GREEN, BLUE, BLACK, GREY;

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
    private final GameScreen gameScreen;
    private boolean meepleIsPlaced;
    private int numberOfPlayers;
    private Player currentPlayer;
    private List<Player> players;
    private static HashMap<Position, TileActor> usedTileHash = new HashMap<>();
    private ArrayList<TileActor> hints = new ArrayList<>();

    public ArrayList<TileActor> getUsedTiles() {
        return usedTiles;
    }

    private ArrayList<TileActor> usedTiles = new ArrayList<>();
    private Random rand;

    public void addMeepleOnCurrentTile(Meeple meeple) {
        currentTile.addMeeple(meeple);
    }

    public ArrayList<PlayerStatusActor> getStatuses() {
        return statuses;
    }

    public Meeple getUnusedCurrentPlayerMeeple() {
        return currentPlayer.getUnusedMeeple();
    }

    private ArrayList<PlayerStatusActor> statuses = new ArrayList<>();
    private com.mygdx.game.Board board;

    private TileActor currentTile;

    public TileActor getCurrentTile() {
        return currentTile;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public com.mygdx.game.Board getBoard() {
        return board;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TileActor getRandomElement(List<TileActor> list) throws Exception {
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
        cm.setTileNumber(board.getAvailableTiles().indexOf(nextTile));

        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(cm);
        }
        onTurnBegin(cm);
        showHintsForTile(currentTile);
    }

    public void onTurnBegin(CurrentTileMessage cm) {
        currentTile = board.getAvailableTiles().get(cm.getTileNumber());
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
        Gdx.app.debug("DEBUG", " " + tilePlacementMessage.getRotation() + " " + currentTile.toString());
        currentTile.setRotation(tilePlacementMessage.getRotation());

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                placeCurrentTileAt(tilePlacementMessage.getPosition());
            }
        });
    }

    public void endMyTurn() {
        TurnEndMessage turnEndMessage = new TurnEndMessage();
        turnEndMessage.setMeeples(currentTile.getMeeples());

        for (Feature feature : currentTile.getFeatures()) {
            int score = board.getScore(currentTile, feature);
            List<Player> owners = getFeatureOwners(currentTile, feature);

            for (Player player : owners) {
                if (turnEndMessage.getScoreChanges().containsKey(player)) {
                    turnEndMessage.getScoreChanges().put(player, turnEndMessage.getScoreChanges().get(player) + score);
                } else {
                    turnEndMessage.getScoreChanges().put(player, score);
                }
            }
        }

        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(turnEndMessage);
        }

        onTurnEnd(turnEndMessage);
    }

    public void onTurnEnd(TurnEndMessage turnEndMessage) {
        for (Player player : turnEndMessage.getScoreChanges().keySet()) {
            getPlayer(player.getColor()).addScore(turnEndMessage.getScoreChanges().get(player));
        }
        updatePlayersInfo();
        if (gameClient != null) {
            GameBoard that = this;
            Position pos = currentTile.getPosition();

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    for (Meeple m : turnEndMessage.getMeeples()) {
                        new MeeplePlacement(that, gameScreen, new MeepleTextureFactory()).drawMeeple(m.getSide(), pos);
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
        for (Player player :
                players) {
            player.reduceCheatTimeByOne();
        }
    }

    public void updatePlayersInfo() {
        for (PlayerStatusActor status : statuses) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    status.updateInfo();
                }
            });
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


    public GameBoard(GameScreen screen, Stage stageGame, Stage stageUI, List<Player> players, boolean isLocal, Player me, GameClient gameClient, GameScreen gameScreen) {
        stageOfBoard = stageGame;
        stageOfUI = stageUI;
        gameScreen = screen;

        numberOfPlayers = players.size();
        this.players = players;
        currentPlayer = players.get(0);
        boolean isLocal1 = isLocal;
        this.me = me;
        this.gameClient = gameClient;
        this.gameScreen = gameScreen;
        this.board = new Board();
        this.rand = new Random();

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
                        onTurnEnd((TurnEndMessage) object);
                    }
                    if (object instanceof CheatMessage) {
                        onCheat((CheatMessage) object);
                    }
                    if (object instanceof ErrorMessage) {
                        errorHandling((ErrorMessage) object, connection);
                    }
                    if (object instanceof EmoteMessage) {
                        onEmote((EmoteMessage) object);
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

        if (NetworkHelper.getLastMessage() != null) {
            if (NetworkHelper.getLastMessage() instanceof CurrentTileMessage) {
                onTurnBegin((CurrentTileMessage) NetworkHelper.getLastMessage());

                Gdx.app.debug("DEBUG", "Restore Game init error: " + NetworkHelper.getLastMessage().toString());
                NetworkHelper.setLastMessage(null);
            }
        }

        createFinishTurnButton();

        stageOfUI.addActor(finishTurnButton);
        playerActorList = new ArrayList<>();

        createPlayerStatuses();
    }

    private void createPlayerStatuses() {
        for (Player player : players) {
            PlayerStatusActor playerStatusActor = new PlayerStatusActor(player);
            statuses.add(playerStatusActor);
            playerStatusActor.setPosition((float) players.indexOf(player) * PlayerStatusActor.WIDTH, Gdx.graphics.getHeight(), Align.topLeft);
            playerStatusActor.addListener(new ActorGestureListener(20, 0.4f, 5f, 0.15f) {
                @Override
                public boolean longPress(Actor actor, float x, float  y) {
                    Gdx.app.debug("DEBUG","Long Press");
                    cheat(CheatType.SCORE, player);
                    return false;
                }

                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    Gdx.app.debug("DEBUG","double tap");
                    cheat(CheatType.MEEPLE, player);
                }

                @Override
                public void touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    Gdx.app.debug("DEBUG", "Touch Down on yourself");
                }
            });
            stageOfUI.addActor(playerStatusActor);
            playerActorList.add(playerStatusActor);
        }
    }

    private void createFinishTurnButton() {
        finishTurnButton = new TextButton("Finish turn", Carcassonne.skin, "default");

        finishTurnButton.setWidth(300);
        finishTurnButton.getLabel().setFontScale(0.8f);
        finishTurnButton.setPosition((float) Gdx.graphics.getWidth() - 300f - 100f, 0);
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
    }

    public List<Player> getFeatureOwners(TileActor tile, Feature feature) {
        HashMap<Player, Integer> owners = new HashMap<>();
        for (Player player : players) {
            owners.put(player, 0);
        }

        collectOwners(tile, feature, owners, new HashSet<>(), null);

        List<Player> players = new ArrayList<>();
        // get players with max value of meeples
        int maxScore = Collections.max(owners.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getValue();
        for (Player player : owners.keySet()) {
            if (owners.get(player) == maxScore) {
                players.add(player);
            }
        }
        return players;
    }


    public void collectOwners(TileActor tile, Feature feature, HashMap<Player, Integer> owners, HashSet<TileActor> visited, TileActor parent) {
        if (!visited.add(tile)) return;
        for (Side side : feature.getSides()) {
            side = tile.getSideAfterRotation(side);

            for (com.mygdx.game.meeple.Meeple meeple : tile.getMeeples()) {
                if (meeple.getFeature().getClass() == feature.getClass() && meeple.getSide() == side) {
                    Player player = getPlayer(meeple.getColor());
                    int meeplesNumber = owners.get(player);
                    owners.put(player, meeplesNumber + 1);
                }
            }

            TileActor nextTile = board.getTileInDirectionOfSide(tile, side);
            if (nextTile == null) return;
            if (nextTile == parent) continue;

            Feature nextFeature = nextTile.getFeatureAtSide(board.getFacingSideOfSurroundingTile(side));
            collectOwners(nextTile, nextFeature, owners, visited, tile);
        }
    }

    private void errorHandling(ErrorMessage error, Connection connection) {
        if (error.errorNumber == ErrorNumber.GAMENOTSTARTED) {

            Gdx.app.debug("DEBUG", "Game was not initialized by " + connection.getID());
        }
    }

    private void onEmote(EmoteMessage emoteMessage) {
        //Cant test emotes with api 21 :(
        players.stream().filter(p -> p.getId() == (emoteMessage.getPlayer().getId())).findAny().ifPresent(player -> gameScreen.showEmote(emoteMessage));
    }

    public void showEmote(Emote emote) {
        if (gameClient != null && NetworkHelper.getGameManager() != null) {
            NetworkHelper.getGameManager().sendToServer(new EmoteMessage(emote, me));
        }
    }

    private Player findPlayerById(int id) {
        for (Player player : players) {
            if (player.getId() == id) return player;
        }
        return null;
    }

    private void onCheat(CheatMessage message) {
        performCheatAction(findPlayerById(message.getCaller()), findPlayerById(message.getCallee()), message.getType());
    }

    public void cheat(CheatType type, Player player) {

        Player currentPlayer = findPlayerById(NetworkHelper.getPlayer().getId());
        performCheatAction(currentPlayer, player, type);

        if(currentPlayer != null && NetworkHelper.getGameManager() != null){
            NetworkHelper.getGameManager().sendToServer(new CheatMessage(currentPlayer.getId(), player.getId(), type));
        }


    }

    public void performCheatAction(Player caller, Player callee, CheatType type) {
        if (callee.getId() == caller.getId()) {
            callee.cheat(type);
        } else if (callee.isCheater()) {
            callee.punish();
        } else {
            caller.punish();
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
            gameScreen.placeMeeple.setVisible(true);




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

    public boolean meepleIsPlaced() {
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

    public Player getPlayer(GameBoard.Color color) {
        for (Player player : players) {
            if (player.getColor() == color) {
                return player;
            }
        }
        return null;
    }

    public void setMeepleIsPlaced(boolean meepleIsPlaced) {
        this.meepleIsPlaced = meepleIsPlaced;
    }

    public Stage getStageOfBoard() {
        return stageOfBoard;
    }

    public void addActorToBoardStage(Actor actor) {
        stageOfBoard.addActor(actor);
    }

    public Stage getStageOfUI() {
        return stageOfUI;
    }

    public void removeOldHints() {
        for (TileActor a : hints) a.remove();
        hints.clear();
    }

    public TileActor getNewestTile() {
        int lastElement = usedTiles.size() - 1;
        return usedTiles.get(lastElement);
    }

    public static Map<Position, TileActor> getUsedTileHash() {
        return usedTileHash;
    }

    public List<PlayerStatusActor> getPlayerActorList() {
        return playerActorList;
    }

    public PlayerStatusActor getPlayerActor(int i) {
        return playerActorList.get(i);
    }

    public int getScoreFromPlayer(Player p) {
        int score = 0;
        for (Player player : players
        ) {
            if (p.equals(player)) {
                score = player.getScore();
            }
        }
        return score;
    }

    public int getCheatTimeFromPlayer(Player p) {
        int time = 0;
        for (Player player : players
        ) {
            if (p.equals(player)) {
                time = player.getTimeToDetectUsedCheats();
            }
        }
        return time;
    }

    public Player getWinningPlayer() {
        Player winningPlayer = players.get(0);
        for (Player player :
                players) {
            if (player.getScore() > winningPlayer.getScore()) {
                winningPlayer = player;
            }
        }
        return winningPlayer;
        //return Collections.max(players, Comparator.comparing(Player::getScore));
    }

    void setCurrentTile(TileActor tile) {
        currentTile = tile;
    }
}
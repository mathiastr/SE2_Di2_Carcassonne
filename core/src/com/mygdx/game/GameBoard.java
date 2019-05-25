package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GameBoard {

    ArrayList<PlayerStatusActor> playerActorList;

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

    private HashMap<Position, TileActor> tiles = new HashMap<>();
    private Stage stageOfBoard;
    private Stage stageOfUI;
    public final static int MAX_NUM_OF_PLAYERS = 6;
    private TextButton finishTurnButton;
    private boolean tileIsPlaced = false;
    private Player me;
    private GameClient gameClient;
    private boolean meepleIsPlaced;
    private int numberOfPlayers;
    private Player currentPlayer;
    private List<Player> players;
    private ArrayList<TileActor> hints = new ArrayList<>();
    private ArrayList<TileActor> usedTiles = new ArrayList<>();
    private ArrayList<PlayerStatusActor> statuses = new ArrayList<>();

    /* is the deck of tiles */
    private ArrayList<TileActor> availableTiles = new ArrayList<>();

    private TileActor currentTile;

    public TileActor getCurrentTile() {
        return currentTile;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void createDeckTilesAndStartTile() {
        int straightRoadUnderCityCount = 4; // 3 + (1 start-tile)
        int diagCityCount = 5;
        int straightRoadCount = 8;
        int cityLeftRightCount = 3;
        int cityTopCount = 5;
        int cityTopRightCount = 2;
        int cityTopWithLeftRoadCount = 3;
        int cityTopWithRightRoadCount = 3;
        int cityWithTripleRoadCount = 3;
        int diagCityWithRoadCount = 5;
        int fullCityCount = 1;
        int fullCityLeftRightCount = 3;
        int fullCityLeftTopRightCount = 4;
        int fullCityLeftTopRightWithRoadCount = 3;
        int monasteryCount = 4;
        int monasteryWithRoadCount = 2;
        int tripleRoadCount = 4;
        int turningRoadCount = 9;
        int quadRoadCount = 1;

        // TODO: only create all Textures once, then reference them...

        /* road under city */
        for (int i = 0; i < straightRoadUnderCityCount; ++i) {
            TileActor straightRoadUnderCity = new TileActor(this);
            straightRoadUnderCity.setTexture(new Texture(Gdx.files.internal("road_under_city_128.jpg")));
            straightRoadUnderCity.addFeature(new City(Side.top));
            straightRoadUnderCity.addFeature(new Road(Arrays.asList(Side.left, Side.right)));
            if (i != 0) availableTiles.add(straightRoadUnderCity);
            else {
                addTileOnBoard(straightRoadUnderCity, new Position(0, 0));
            }
        }

        // diagonal city
        for (int i = 0; i < diagCityCount; ++i) {
            TileActor diagCity = new TileActor(this);
            diagCity.setTexture(new Texture(Gdx.files.internal("diagonal_city_128.jpg")));
            diagCity.addFeature(new City(Arrays.asList(Side.top, Side.right)));
            availableTiles.add(diagCity);
        }

        // straight road
        for (int i = 0; i < straightRoadCount; ++i) {
            TileActor straightRoad = new TileActor(this);
            straightRoad.setTexture(new Texture(Gdx.files.internal("straight_road_128.jpg")));
            straightRoad.addFeature(new Road(Arrays.asList(Side.top, Side.bottom)));
            availableTiles.add(straightRoad);
        }

        // city left right
        for (int i = 0; i < cityLeftRightCount; ++i) {
            TileActor cityLeftRight = new TileActor(this);
            cityLeftRight.setTexture(new Texture(Gdx.files.internal("city_left_right_128.jpg")));
            cityLeftRight.addFeature(new City(Side.left));
            cityLeftRight.addFeature(new City(Side.right));
            availableTiles.add(cityLeftRight);
        }

        // city top
        for (int i = 0; i < cityTopCount; ++i) {
            TileActor cityTop = new TileActor(this);
            cityTop.setTexture(new Texture(Gdx.files.internal("city_top_128.jpg")));
            cityTop.addFeature(new City(Side.top));
            availableTiles.add(cityTop);
        }

        /* city top right
        for (int i = 0; i < cityTopRightCount; ++i) {
            TileActor cityTopRight = new TileActor(this);
            cityTopRight.setTexture(new Texture(Gdx.files.internal("city_top_right_128.jpg")));
            cityTopRight.addFeature(new City(Side.top));
            cityTopRight.addFeature(new City(Side.right));
            availableTiles.add(cityTopRight);
        }
*/
        /* city top with left road
        for (int i = 0; i < cityTopWithLeftRoadCount; ++i) {
            TileActor cityTopWithLeftRoad = new TileActor(this);
            cityTopWithLeftRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_left_road_128.jpg")));
            cityTopWithLeftRoad.addFeature(new City(Side.top));
            cityTopWithLeftRoad.addFeature(new Road(Arrays.asList(Side.left, Side.bottom)));
            availableTiles.add(cityTopWithLeftRoad);
        }
        */

        // city top with right road
        for (int i = 0; i < cityTopWithRightRoadCount; ++i) {
            TileActor cityTopWithRightRoad = new TileActor(this);
            cityTopWithRightRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_right_road_128.jpg")));
            cityTopWithRightRoad.addFeature(new City(Side.top));
            cityTopWithRightRoad.addFeature(new Road(Arrays.asList(Side.right, Side.bottom)));
            availableTiles.add(cityTopWithRightRoad);
        }

        // city with triple road
        for (int i = 0; i < cityWithTripleRoadCount; ++i) {
            TileActor cityWithTripleRoad = new TileActor(this);
            cityWithTripleRoad.setTexture(new Texture(Gdx.files.internal("city_with_triple_road_128.jpg")));
            cityWithTripleRoad.addFeature(new City(Side.top));
            cityWithTripleRoad.addFeature(new Road(Side.left));
            cityWithTripleRoad.addFeature(new Road(Side.right));
            cityWithTripleRoad.addFeature(new Road(Side.bottom));
            availableTiles.add(cityWithTripleRoad);
        }

        /* diagonal city with road
        for (int i = 0; i < diagCityWithRoadCount; ++i) {
            TileActor diagCityWithRoad = new TileActor(this);
            diagCityWithRoad.setTexture(new Texture(Gdx.files.internal("diagonal_city_with_road_128.jpg")));
            diagCityWithRoad.addFeature(new City(Arrays.asList(Side.top, Side.left)));
            diagCityWithRoad.addFeature(new Road(Arrays.asList(Side.bottom, Side.right)));
            availableTiles.add(diagCityWithRoad);
        }
    */
        /* full city
        for (int i = 0; i < fullCityCount; ++i) {
            TileActor fullCity = new TileActor(this);
            fullCity.setTexture(new Texture(Gdx.files.internal("full_city_128.jpg")));
            fullCity.addFeature(new City(Arrays.asList(Side.left, Side.top, Side.right, Side.bottom)));
            availableTiles.add(fullCity);
        }
    */
        /* full city left/right
        for (int i = 0; i < fullCityLeftRightCount; ++i) {
            TileActor fullCityLeftRight = new TileActor(this);
            fullCityLeftRight.setTexture(new Texture(Gdx.files.internal("full_city_left_right_128.jpg")));
            fullCityLeftRight.addFeature(new City(Arrays.asList(Side.left, Side.right)));
            availableTiles.add(fullCityLeftRight);
        }
*/
        /* full city left/top/right
        for (int i = 0; i < fullCityLeftTopRightCount; ++i) {
            TileActor fullCityLeftTopRight = new TileActor(this);
            fullCityLeftTopRight.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_128.jpg")));
            fullCityLeftTopRight.addFeature(new City(Arrays.asList(Side.left, Side.top, Side.right)));
            availableTiles.add(fullCityLeftTopRight);
        }
*/
        /* full city left/top/right with road
        for (int i = 0; i < fullCityLeftTopRightWithRoadCount; ++i) {
            TileActor fullCityLeftTopRightWithRoad = new TileActor(this);
            fullCityLeftTopRightWithRoad.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_with_road_128.jpg")));
            fullCityLeftTopRightWithRoad.addFeature(new City(Arrays.asList(Side.left, Side.top, Side.right)));
            fullCityLeftTopRightWithRoad.addFeature(new Road(Side.bottom));
            availableTiles.add(fullCityLeftTopRightWithRoad);
        }
*/
        // monastery
        for (int i = 0; i < monasteryCount; ++i) {
            TileActor monastery = new TileActor(this);
            monastery.setTexture(new Texture(Gdx.files.internal("monastery_128.jpg")));
            monastery.setMonastery();
            availableTiles.add(monastery);
        }

        // monastery with road
        for (int i = 0; i < monasteryWithRoadCount; ++i) {
            TileActor monasteryWithRoad = new TileActor(this);
            monasteryWithRoad.setTexture(new Texture(Gdx.files.internal("monastery_with_road_128.jpg")));
            monasteryWithRoad.setMonastery();
            monasteryWithRoad.addFeature(new Road(Side.bottom));
            availableTiles.add(monasteryWithRoad);
        }

        /* triple road */
        for (int i = 0; i < tripleRoadCount; ++i) {
            TileActor tripleRoad = new TileActor(this);
            tripleRoad.setTexture(new Texture(Gdx.files.internal("triple_road_128.jpg")));
            tripleRoad.addFeature(new Road(Side.left));
            tripleRoad.addFeature(new Road(Side.bottom));
            tripleRoad.addFeature(new Road(Side.right));
            availableTiles.add(tripleRoad);
        }

        /* turning road
        for (int i = 0; i < turningRoadCount; ++i) {
            TileActor turningRoad = new TileActor(this);
            turningRoad.setTexture(new Texture(Gdx.files.internal("turning_road_128.jpg")));
            turningRoad.addFeature(new Road(Arrays.asList(Side.left, Side.bottom)));
            availableTiles.add(turningRoad);
        }*/

        /* triple road
        for (int i = 0; i < quadRoadCount; ++i) {
            TileActor quadRoad = new TileActor(this);
            quadRoad.setTexture(new Texture(Gdx.files.internal("quad_road_128.jpg")));
            quadRoad.addFeature(new Road(Side.left));
            quadRoad.addFeature(new Road(Side.bottom));
            quadRoad.addFeature(new Road(Side.right));
            quadRoad.addFeature(new Road(Side.top));
            availableTiles.add(quadRoad);
        }
        */
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
        availableTiles.remove(availableTiles.indexOf(currentTile));
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
            nextTile = getRandomElement(availableTiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CurrentTileMessage cm = new CurrentTileMessage();
        cm.tileNumber = availableTiles.indexOf(nextTile);

        if (gameClient != null) {
            NetworkHelper.getGameManager().sendToServer(cm);
        }
        onTurnBegin(cm);
        showHintsForTile(currentTile);
    }

    public void onTurnBegin(CurrentTileMessage cm) {
        currentTile = availableTiles.get(cm.tileNumber);
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
        int score = getScore(currentTile);
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
    }

    public void init() {
        createDeckTilesAndStartTile();

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
                NetworkHelper.setLastMessage(null);
            }
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
                        endMyTurn();
                    }
                } else if (gameClient == null) {
                    endMyTurn();
                }

            }
        });

        stageOfUI.addActor(finishTurnButton);

        for (Player p : players) {
            PlayerStatusActor playerStatusActor = new PlayerStatusActor(p);
            statuses.add(playerStatusActor);
            playerStatusActor.setPosition(players.indexOf(p) * PlayerStatusActor.WIDTH, Gdx.graphics.getHeight(), Align.topLeft);
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
            stageUI.addActor(playerStatusActor);
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
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        p.addScore(100);
                        p.addTimeToDetectUsedCheats(message.getCheatTime());
                    }
                });
            }
        }

    }

    private void CheatOnScore() {
        for (Player p : players
                ) {
            if(p.equals(NetworkHelper.getPlayer())){
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        p.addScore(100);
                        p.setTimeToDetectUsedCheats(3);
                        if(NetworkHelper.getGameManager() != null){
                            NetworkHelper.getGameManager().sendToServer(new CheatOnScoreMessage(3,NetworkHelper.getPlayer()));
                        }
                    }
                });

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
        return availableTiles.size();
    }

    public void gameEnds() {
        // TODO
    }

    public void placeTileAt(TileActor tileToPlace, Position position) {
        if (!tileIsPlaced) {
            addTileOnBoard(tileToPlace, position);
            tileIsPlaced = true;

            DelayedRemovalArray<EventListener> listeners = tileToPlace.getListeners();

            if (listeners != null && listeners.size != 0) {
                tileToPlace.removeListener(listeners.peek());
            }
        }
    }

    public void addTileOnBoard(TileActor tileToPlace, Position position) {
        tileToPlace.setPosition(position);
        tiles.put(position, tileToPlace);
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

            if (availableTiles.isEmpty()) {
                Gdx.app.log("hmmmm", "No Tiles left, game ends");
                gameEnds();
            } else {
                // nextCurrentTile();
            }

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

    /* -------------------------------------------------------------------- */
    /* -------------------------------------------------------------------- */

    private TileActor getTileInDirectionOfSide(TileActor tile, Side side) {
        switch (side) {
            case top:
                return tiles.get(tile.getPosition().add(new Position(0, 1)));
            case right:
                return tiles.get(tile.getPosition().add(new Position(1, 0)));
            case bottom:
                return tiles.get(tile.getPosition().add(new Position(0, -1)));
            case left:
                return tiles.get(tile.getPosition().add(new Position(-1, 0)));
            default:
                return null;
        }
    }


    public int getScore(TileActor tile) {
        int score = 0;
        for (Feature feature: tile.getFeatures()) {
            if(feature instanceof City || feature instanceof Road) {
                score += scoreRoadOrCity(tile, feature);
            }
            else if (feature instanceof Monastery) {
                score += scoreMonastery(tile);
            }
            // TODO check for monastery around
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                TileActor tileAround = tiles.get(tile.getPosition().add(new Position(i, j)));
                if ( tileAround != null) {
                    if (tileAround.isMonastery()) {
                        score += scoreMonastery(tileAround);
                    }
                }
            }
        }
        return score;
    }

    // TODO maybe adjust scoring methods for the end of game partial scoring...
    /* mid game scoring */
    /* ---------------- */
    public int scoreMonastery(TileActor tile) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (!tiles.containsKey(tile.getPosition().add(new Position(dx, dy)))) {
                    // there is no tile at this surrounding position => monastery not completed
                    return 0;
                }
            }
        }
        return tile.isMonastery() ? 9 : 0;
    }

    /* returns 0 if road/city is not completed, score of component otherwise */
    /* city score is 2 per tile, road score is 1 per tile */
    public int scoreRoadOrCity(TileActor tile, Feature feature) {
        if (!(feature instanceof Road) && !(feature instanceof City))
            throw new IllegalArgumentException("only road or city allowed!");

        if (!tile.getFeatures().contains(feature))
            throw new IllegalArgumentException("not a feature of the tile!");

        HashSet<TileActor> visited = new HashSet<>();
        int score = scoreRoadOrCityRec(tile, feature, null, visited);
        return score == -1 ? 0 : score;
    }

    public int scoreRoadOrCityRec(TileActor tile, Feature feature, TileActor parent, HashSet<TileActor> visited) {
        int score = feature instanceof City ? 2 : 1; // tile itself

        if (!visited.add(tile)) return 0; // we found a loop => road closed

        for (Side side : feature.getSides()) {
            side = tile.getSideAfterRotation(side);
            TileActor nextTile = getTileInDirectionOfSide(tile, side);
            if (nextTile == null) return -1;
            if (nextTile == parent) continue;

            Feature nextRoad = nextTile.getFeatureAtSide(getFacingSideOfSurroundingTile(side));
            int currScore = scoreRoadOrCityRec(nextTile, nextRoad, tile, visited);
            if (currScore == -1) return -1;

            score += currScore;
        }
        return score;
    }

    // e.g. for top we want bottom, for right we want left ...
    private Side getFacingSideOfSurroundingTile(Side side) {
        return Side.values()[side.ordinal() ^ 2];
    }

    private boolean isValidAtPosition(TileActor tile, Position pos) {
        if (tiles.containsKey(pos)) return false;

        boolean connected = false;
        Position[] surroundPositions = pos.getSurroundingPositions();
        for (Side side : Side.values()) {
            if (tiles.containsKey(surroundPositions[side.ordinal()])) {
                connected = true;
                TileActor surroundTile = tiles.get(surroundPositions[side.ordinal()]);
                Feature feature1 = tile.getFeatureAtSide(side);
                Feature feature2 = surroundTile.getFeatureAtSide(getFacingSideOfSurroundingTile(side));

                /* check if features line up */
                if (feature1 == null && feature2 == null) continue;
                if (feature1 == null || feature2 == null) return false;
                if (!feature1.getClass().equals(feature2.getClass())) return false;
            }
        }
        return connected; // if the position was not connected to the current board -> return false
    }

    public HashSet<Position> getValidPositionsForTile(TileActor placedTile) {
        HashSet<Position> validPositions = new HashSet<>();
        for (TileActor tile : tiles.values()) {
            for (Position pos : tile.getPosition().getSurroundingPositions()) {
                if (!validPositions.contains(pos) && isValidAtPosition(placedTile, pos)) {
                    validPositions.add(pos);
                }
            }
        }
        return validPositions;
    }

    public void showHintsForTile(TileActor tile) {
        removeOldHints();
        for (Position validPos : getValidPositionsForTile(tile)) {
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
    public ArrayList<TileActor> getUsedTiles() {
        return usedTiles;
    }
    public TileActor getPreviousTile(){
        int lastElement = usedTiles.size()-1;
        return usedTiles.get(lastElement);
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
}
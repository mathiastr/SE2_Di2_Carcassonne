package com.mygdx.game.emotes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.actor.PlayerStatusActor;
import com.mygdx.game.actor.TileActor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.swing.GroupLayout;

public class EmoteManager {
    GameBoard gameBoard;
    private class PlayerEmote {
        Player player;
        Emote emote;
        public PlayerEmote(Player p, Emote e) {
            player = p;
            emote = e;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerEmote that = (PlayerEmote) o;
            return player.getId() == that.player.getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(player.getId(), emote);
        }
    }

    private Map<PlayerEmote, Image> emoteImages = new HashMap<>();

    public EmoteManager(GameBoard gb) {
        gameBoard = gb;
    }

    public void init(List<PlayerStatusActor> statuses) {
        generateTinyClickEmotes();
        generateEmotesForPlayers(statuses);
    }

    private void generateEmotesForPlayers(List<PlayerStatusActor> statuses) {
        Map<Emote, Texture> emoteTextures = new HashMap<>();
        emoteTextures.put(Emote.cool, new Texture(Gdx.files.internal("emotes/glasses.png")));
        emoteTextures.put(Emote.happy, new Texture(Gdx.files.internal("emotes/happy.png")));
        emoteTextures.put(Emote.angry, new Texture(Gdx.files.internal("emotes/angry.png")));
        for (PlayerStatusActor psa : statuses) {
            for (Map.Entry<Emote, Texture> entry2 : emoteTextures.entrySet()) {
                Emote e = entry2.getKey();
                Texture t = entry2.getValue();
                Image emoteImage = new Image(t);
                emoteImage.setName(e.toString()+t.toString());
                emoteImage.setPosition(psa.getX() + psa.getWidth() / 2f, psa.getY()-50);
                emoteImage.setScale(.4f);
                emoteImages.put(new PlayerEmote(psa.getPlayer(), e), emoteImage);
                //emoteImage.setVisible(false);

                gameBoard.getStageOfUI().addActor(emoteImage);
                emoteImage.setTouchable(Touchable.disabled);
                //emoteImage.addAction(Actions.alpha(0));
                SequenceAction sq = new SequenceAction();
                sq.addAction(Actions.alpha(0));
                emoteImage.addAction(sq);
            }
        }
    }

    private void generateTinyClickEmotes() {
        Map<Emote, Image> emotes = new HashMap<>();
        emotes.put(Emote.cool, new Image(new Texture(Gdx.files.internal("emotes/glasses_tiny_b.png"))));
        emotes.put(Emote.happy, new Image(new Texture(Gdx.files.internal("emotes/happy_tiny_b.png"))));
        emotes.put(Emote.angry, new Image(new Texture(Gdx.files.internal("emotes/angry_tiny_b.png"))));

        Table emoteTable = new Table();
        float size = Gdx.app.getGraphics().getHeight()/10f;
        int padding = 20;

        emoteTable.setPosition(size/2+5, (padding+size)*emotes.size()/2 + Gdx.graphics.getHeight()/8f+10+size); // TODO
        //emoteTable.setDebug(true);
        for (Map.Entry<Emote, Image> entry : emotes.entrySet()) {
            Emote emote = entry.getKey();
            Image image = entry.getValue();
            image.setVisible(!image.isVisible());
            emoteTable.add(image).width(size).height(size).padTop(padding);
            emoteTable.row();
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameBoard.showEmote(emote);
                    event.handle();
                }
            });
        }

        Image expand = new Image(new Texture(Gdx.files.internal("emotes/up_arrow.png")));
        emoteTable.add(expand).width(size).height(size).padTop(padding);
        emoteTable.row();
        expand.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Image emote : emotes.values())
                    emote.setVisible(!emote.isVisible());
                expand.setOrigin(Align.center);
                expand.rotateBy(180);
                event.handle();
            }
        });

        gameBoard.getStageOfUI().addActor(emoteTable);
    }

    public void showEmoteFromPlayer(Emote emote, Player player) {
        Image image = emoteImages.get(new PlayerEmote(player, emote));
        SequenceAction sequence = new SequenceAction();
        //image.setVisible(true);
        //sequence.addAction(Actions.show());
        sequence.addAction(Actions.fadeIn(1));
        sequence.addAction(Actions.delay(1));
        sequence.addAction(Actions.fadeOut(1));
        //sequence.addAction(Actions.hide());
        // postaction setvisible false
        TileActor a = gameBoard.getCurrentTile();
        gameBoard.getCurrentTile().remove();
        image.addAction(sequence);
        gameBoard.getStageOfUI().addActor(a);
    }
}

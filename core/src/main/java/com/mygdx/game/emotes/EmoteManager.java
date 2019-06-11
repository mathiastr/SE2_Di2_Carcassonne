package com.mygdx.game.emotes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.actor.PlayerStatusActor;
import com.mygdx.game.network.response.EmoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmoteManager {
    private Stage emoteStage;
    private GameBoard gameBoard;

    private class PlayerEmote {
        Player player;
        Emote emote;

        PlayerEmote(Player p, Emote e) {
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

    public EmoteManager(GameBoard gb, Stage stage) {
        gameBoard = gb;
        this.emoteStage = stage;
        generateTinyClickEmotes();
        generateEmotesForPlayers();
    }

    private void generateEmotesForPlayers() {
        Map<Emote, Texture> emoteTextures = new HashMap<>();
        emoteTextures.put(Emote.cool, new Texture(Gdx.files.internal("emotes/glasses.png")));
        emoteTextures.put(Emote.happy, new Texture(Gdx.files.internal("emotes/happy.png")));
        emoteTextures.put(Emote.angry, new Texture(Gdx.files.internal("emotes/angry.png")));
        for (PlayerStatusActor psa : gameBoard.getStatuses()) {
            for (Map.Entry<Emote, Texture> entry2 : emoteTextures.entrySet()) {
                Emote emote = entry2.getKey();
                Texture texture = entry2.getValue();

                Image emoteImage = new Image(texture);
                emoteImage.setName(emote.toString() + texture.toString());
                emoteImage.setPosition(psa.getX() + psa.getWidth() / 4f, psa.getY() + psa.getHeight() / 4f);
                emoteImage.setScale(.4f);
                emoteImages.put(new PlayerEmote(psa.getPlayer(), emote), emoteImage);

                emoteStage.addActor(emoteImage);
                emoteImage.setTouchable(Touchable.disabled);
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
        float size = Gdx.app.getGraphics().getHeight() / 10f;
        int padding = 20;

        emoteTable.setPosition(size / 2 + 5, (padding + size) * emotes.size() / 2 + Gdx.graphics.getHeight() / 8f + 10 + size);
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

        emoteStage.addActor(emoteTable);
    }

    public void showEmoteFromPlayer(EmoteMessage emoteMessage) {
        Image image = emoteImages.get(new PlayerEmote(emoteMessage.getPlayer(), emoteMessage.getEmote()));
        SequenceAction sequence = new SequenceAction();
        sequence.addAction(Actions.fadeIn(1));
        sequence.addAction(Actions.delay(1));
        sequence.addAction(Actions.fadeOut(1));
        image.addAction(sequence);
    }
}

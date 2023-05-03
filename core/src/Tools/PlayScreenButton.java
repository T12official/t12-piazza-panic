package Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team13.piazzapanic.PlayScreen;

public class PlayScreenButton {
    TextButton button;

    public PlayScreenButton(String displayText, final Functionality f, final PlayScreen playScreen) {
        initialiseButton(displayText);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (f) {
                    case SHOP:
                        playScreen.toggleShop();
                    case CHOP:
                        playScreen.buyChoppingBoard();
                    case PAN:
                        playScreen.buyPan();
                    case SAVEGAME:
                        gameSaveTool.saveMyGame(playScreen);
                        break;
                }

            }
        });
    }

    private void initialiseButton(String displayText) {
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        button = new TextButton(displayText, skin);
    }

    public TextButton getButton() {
        return button;
    }

    public enum Functionality {
        SHOP, CHOP, PAN, SAVEGAME, LOAD,
        EASY, NORMAL, HARD, SCENARIO, ENDLESS

    }


}

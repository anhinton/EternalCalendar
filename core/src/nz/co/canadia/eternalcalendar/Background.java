package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class Background extends Group {
    public Background(TextureAtlas atlas, Skin skin, int gameWidth, int gameHeight) {

        float datePadding = (float) Constants.DATE_PADDING / Constants.GAME_WIDTH * gameWidth;
        float dateColWidth = (float) Constants.DATE_COLUMN_WIDTH / Constants.GAME_WIDTH * gameWidth;
        float firstColumnY = gameHeight - datePadding - dateColWidth;

        // Parse date rows and columns from CSV file
        FileHandle file = Gdx.files.internal("data/dates.csv");
        String[] textArray = file.readString("UTF-8").split("\\r?\\n");
        String[][] dateArray = new String[textArray.length][];
        for (int i = 0; i < textArray.length; i++) {
            dateArray[i] = textArray[i].split(",");
        }

        Image backgroundImage = new Image(atlas.findRegion("background"));
        backgroundImage.setSize(gameWidth, gameHeight);
        backgroundImage.setPosition(0, 0);
        super.addActor(backgroundImage);

        for (int i = 0; i < dateArray.length; i++) {
            for (int j = 0; j < dateArray[i].length; j++) {
                Label l;
                if (i == 4 && j < 2) {
                    l = new Label(dateArray[i][j], skin.get("smallDate", Label.LabelStyle.class));
                } else {
                    l = new Label(dateArray[i][j], skin.get("date", Label.LabelStyle.class));
                }
                l.setPosition(datePadding + j * dateColWidth, firstColumnY - i * dateColWidth, Align.center);
                super.addActor(l);
            }
        }
    }
}

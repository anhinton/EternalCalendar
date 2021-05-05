package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class Slider extends Group {
    private final float gameWidth;
    private final float sliderWidth;

    public Slider(EternalCalendar game, TextureAtlas atlas, float gameWidth, float gameHeight) {
        this.gameWidth = gameWidth;

        sliderWidth = (float) Constants.SLIDER_WIDTH / Constants.GAME_WIDTH * gameWidth;
        final float sliderHeight = (float) Constants.SLIDER_HEIGHT / Constants.GAME_HEIGHT * gameHeight;
        float padding = (float) Constants.DATE_PADDING / Constants.GAME_WIDTH * gameWidth;
        float colWidth = (float) Constants.DATE_COLUMN_WIDTH / Constants.GAME_WIDTH * gameWidth;

        // Slider panel
        final Image sliderImage = new Image(atlas.findRegion("slider"));
        sliderImage.setSize(sliderWidth, sliderHeight);
        sliderImage.setPosition(0f / Constants.GAME_WIDTH * gameWidth, gameHeight - sliderHeight);
        super.addActor(sliderImage);

        // Weekday labels
        String[] weekdayArray = game.bundle.get("weekdays").split(",");
        for (int i = 0; i < weekdayArray.length; i++) {
            Label dayLabel = new Label(weekdayArray[i], game.skin.get("weekday", Label.LabelStyle.class));
            dayLabel.setPosition(padding + i * colWidth, gameHeight - padding, Align.center);
            super.addActor(dayLabel);
        }
    }

    @Override
    public void setX(float x) {
        x = MathUtils.clamp(x, 0, gameWidth - sliderWidth);
        super.setX(x);
    }

    public void snap(float x) {

    }
}

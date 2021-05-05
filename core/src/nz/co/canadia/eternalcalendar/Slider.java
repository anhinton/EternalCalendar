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
    private final float colWidth;
    private int column;

    public Slider(EternalCalendar game, TextureAtlas atlas, float gameWidth, float gameHeight, int column) {
        this.gameWidth = gameWidth;
        this.column = column;

        sliderWidth = (float) Constants.SLIDER_WIDTH / Constants.GAME_WIDTH * gameWidth;
        float sliderHeight = (float) Constants.SLIDER_HEIGHT / Constants.GAME_HEIGHT * gameHeight;
        float padding = (float) Constants.DATE_PADDING / Constants.GAME_WIDTH * gameWidth;
        colWidth = (float) Constants.DATE_COLUMN_WIDTH / Constants.GAME_WIDTH * gameWidth;

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

        setX(column * colWidth);
    }

    @Override
    public void setX(float x) {
        x = MathUtils.clamp(x, 0, gameWidth - sliderWidth);
        super.setX(x);
    }

    public void snap(float x) {
        column = MathUtils.round (x / colWidth);
        setX(column * colWidth);
    }

    public void moveLeft() {
        column--;
        move();
    }

    public void moveRight() {
        column++;
        move();
    }
    private void move() {
        column = MathUtils.clamp(column, Constants.SLIDER_COLUMN_MIN, Constants.SLIDER_COLIMN_MAX);
        setX(column * colWidth);
    }
}

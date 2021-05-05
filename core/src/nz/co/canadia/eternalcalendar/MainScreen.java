package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements InputProcessor, Screen {
    private final EternalCalendar game;
    private final Stage stage;
    private int curMonth;
    private final TextButton monthButton;

    public MainScreen(EternalCalendar game) {
        this.game = game;

        Texture backgroundTexture = game.manager.get("textures/background.jpg", Texture.class);
        Texture sliderTexture = game.manager.get("textures/slider.png", Texture.class);
        Texture infoButtonTexture = game.manager.get("textures/info_icon.png", Texture.class);
        infoButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Parse date rows and columns from CSV file
        FileHandle file = Gdx.files.internal("data/dates.csv");
        String[] textArray = file.readString("UTF-8").split("\\r?\\n");
        String[][] dateArray = new String[textArray.length][];
        for (int i = 0; i < textArray.length; i++) {
            dateArray[i] = textArray[i].split(",");
        }

        int uiWidth = MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.GAME_ASPECT_RATIO);
        OrthographicCamera uiCamera = new OrthographicCamera(uiWidth, Gdx.graphics.getBackBufferHeight());
        Viewport uiViewport = new FitViewport(uiWidth, Gdx.graphics.getBackBufferHeight(), uiCamera);
        stage = new Stage(uiViewport);

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.setPosition(0, 0);
        stage.addActor(backgroundImage);

        float datePadding = (float) Constants.DATE_PADDING / Constants.GAME_WIDTH * uiWidth;
        float dateColWidth = (float) Constants.DATE_COLUMN_WIDTH / Constants.GAME_WIDTH * uiWidth;
        float firstColumnY = uiViewport.getScreenHeight() - datePadding - dateColWidth;

        for (int i = 0; i < dateArray.length; i++) {
            for (int j = 0; j < dateArray[i].length; j++) {
                Label l;
                if (i == 4 && j < 2) {
                    l = new Label(dateArray[i][j], game.skin.get("smallDate", Label.LabelStyle.class));
                } else {
                    l = new Label(dateArray[i][j], game.skin.get("date", Label.LabelStyle.class));
                }
                l.setPosition(datePadding + j * dateColWidth, firstColumnY - i * dateColWidth, Align.center);
                stage.addActor(l);
            }
        }

        float sliderWidth = (float) Constants.SLIDER_WIDTH / Constants.GAME_WIDTH * stage.getWidth();
        float sliderHeight = (float) Constants.SLIDER_HEIGHT / Constants.GAME_HEIGHT * stage.getHeight();
        Image sliderImage = new Image(sliderTexture);
        sliderImage.setSize(sliderWidth, sliderHeight);
        sliderImage.setPosition(0f / Constants.GAME_WIDTH * uiWidth, stage.getHeight() - sliderHeight);
        stage.addActor(sliderImage);

        String[] weekdayArray = game.bundle.get("weekdays").split(",");
        for (int i = 0; i < weekdayArray.length; i++) {
            Label dayLabel = new Label(weekdayArray[i], game.skin.get("weekday", Label.LabelStyle.class));
            dayLabel.setPosition(datePadding + i * dateColWidth, uiViewport.getScreenHeight() - datePadding, Align.center);
            stage.addActor(dayLabel);
        }

        final String[] months = game.bundle.get("months").split(",");
        curMonth = 0;
        float buttonPadding = (float) Constants.BUTTON_PADDING / Constants.GAME_HEIGHT * stage.getHeight();
        float monthButtonWidth = (float) Constants.MONTH_BUTTON_WIDTH / Constants.GAME_HEIGHT * stage.getWidth();
        float buttonSize = (float) Constants.BUTTON_SIZE / Constants.GAME_HEIGHT * stage.getHeight();
        float infoIconSize = (float) Constants.INFO_ICON_SIZE / Constants.GAME_HEIGHT * stage.getHeight();

        ImageButton.ImageButtonStyle infoButtonStyle = new ImageButton.ImageButtonStyle(game.skin.get("default", Button.ButtonStyle.class));
        TextureRegionDrawable infoIconDrawable = new TextureRegionDrawable(infoButtonTexture);
        infoIconDrawable.setMinSize(infoIconSize, infoIconSize);
        infoButtonStyle.imageUp = infoIconDrawable;
        ImageButton infoButton = new ImageButton(infoButtonStyle);
        infoButton.setSize(buttonSize, buttonSize);
        infoButton.setPosition(buttonPadding, buttonPadding);
        stage.addActor(infoButton);

        monthButton = new TextButton(months[curMonth], game.skin, "month");
        monthButton.setPosition(stage.getWidth() - buttonPadding - monthButtonWidth,
                buttonPadding);
        monthButton.setSize(monthButtonWidth, buttonSize);
        monthButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                curMonth = (curMonth + 1) % 12;
                monthButton.setText(months[curMonth]);
            }
        });
        stage.addActor(monthButton);

                InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Constants.BACKGROUND_COLOR);

        stage.act(delta);
        stage.draw();

        game.batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                Gdx.app.exit();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

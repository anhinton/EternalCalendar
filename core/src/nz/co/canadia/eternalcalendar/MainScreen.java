package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements InputProcessor, Screen {
    private final EternalCalendar game;
    private final Stage stage;
    private final Slider slider;
    private final String[] months;
    private int curColumn;
    private int curMonth;
    private final TextButton monthButton;

    public MainScreen(final EternalCalendar game) {
        this.game = game;
        curMonth = game.loadMonth();
        curColumn = game.loadColumn();

        TextureAtlas atlas = game.manager.get("textures/textures.atlas", TextureAtlas.class);

        int gameWidth = MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.GAME_ASPECT_RATIO);
        int gameHeight = Gdx.graphics.getBackBufferHeight();
        OrthographicCamera uiCamera = new OrthographicCamera(gameWidth, Gdx.graphics.getBackBufferHeight());
        Viewport uiViewport = new FitViewport(gameWidth, Gdx.graphics.getBackBufferHeight(), uiCamera);
        stage = new Stage(uiViewport);

        // Create background
        Background background = new Background(atlas, game.skin, gameWidth, gameHeight);
        stage.addActor(background);

        // Create Slider
        slider = new Slider(game, atlas, gameWidth, gameHeight, curColumn);
        slider.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                slider.snap(slider.getX());
                curColumn = slider.getColumn();
                game.saveColumn(curColumn);
            }

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                slider.setX(slider.getX() + deltaX);
                curColumn = slider.getColumn();
                game.saveColumn(curColumn);
            }
        });
        stage.addActor(slider);

        float buttonPadding = (float) Constants.BUTTON_PADDING / Constants.GAME_HEIGHT * stage.getHeight();
        float monthButtonWidth = (float) Constants.MONTH_BUTTON_WIDTH / Constants.GAME_HEIGHT * stage.getWidth();
        float buttonSize = (float) Constants.BUTTON_SIZE / Constants.GAME_HEIGHT * stage.getHeight();
        float infoIconSize = (float) Constants.INFO_ICON_SIZE / Constants.GAME_HEIGHT * stage.getHeight();

        ImageButton.ImageButtonStyle infoButtonStyle = new ImageButton.ImageButtonStyle(game.skin.get("default", Button.ButtonStyle.class));
        TextureRegionDrawable infoIconDrawable = new TextureRegionDrawable(atlas.findRegion("info_icon"));
        infoIconDrawable.setMinSize(infoIconSize, infoIconSize);
        infoButtonStyle.imageUp = infoIconDrawable;
        ImageButton infoButton = new ImageButton(infoButtonStyle);
        infoButton.setSize(buttonSize, buttonSize);
        infoButton.setPosition(buttonPadding, buttonPadding);
        stage.addActor(infoButton);

        // Create Month Button
        months = game.bundle.get("months").split(",");
        monthButton = new TextButton(months[curMonth], game.skin, "month");
        monthButton.setPosition(stage.getWidth() - buttonPadding - monthButtonWidth,
                buttonPadding);
        monthButton.setSize(monthButtonWidth, buttonSize);
        monthButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeMonth();
            }
        });
        stage.addActor(monthButton);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void changeMonth() {
        curMonth = (curMonth + 1) % 12;
        monthButton.setText(months[curMonth]);
        game.saveMonth(curMonth);
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
            // Slider Movement
            case Input.Keys.LEFT:
                slider.moveLeft();
                break;
            case Input.Keys.RIGHT:
                slider.moveRight();
                break;
            // Change month
            case Input.Keys.M:
                changeMonth();
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

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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements InputProcessor, Screen {
    private final EternalCalendar game;
    private final Stage gameStage;
    private final Stage uiStage;
    private final Slider slider;
    private final String[] months;
    private final ImageButton infoButton;
    private final Table table;
    private final float buttonPadding;
    private final InputMultiplexer multiplexer;
    private int curColumn;
    private int curMonth;
    private final TextButton monthButton;
    private enum GameState { GAME, INFO}
    private GameState currentState;

    public MainScreen(final EternalCalendar game) {
        this.game = game;
        curMonth = game.loadMonth();
        curColumn = game.loadColumn();
        currentState = GameState.GAME;

        TextureAtlas atlas = game.manager.get("textures/textures.atlas", TextureAtlas.class);

        int gameWidth = MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.GAME_ASPECT_RATIO);
        int gameHeight = Gdx.graphics.getBackBufferHeight();
        OrthographicCamera uiCamera = new OrthographicCamera(gameWidth, Gdx.graphics.getBackBufferHeight());
        Viewport uiViewport = new FitViewport(gameWidth, Gdx.graphics.getBackBufferHeight(), uiCamera);
        gameStage = new Stage(uiViewport);

        // Create background
        Background background = new Background(atlas, game.skin, gameWidth, gameHeight);
        gameStage.addActor(background);

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
        gameStage.addActor(slider);

        buttonPadding = (float) Constants.BUTTON_PADDING / Constants.GAME_HEIGHT * gameStage.getHeight();
        float monthButtonWidth = (float) Constants.MONTH_BUTTON_WIDTH / Constants.GAME_HEIGHT * gameStage.getWidth();
        float buttonSize = (float) Constants.BUTTON_SIZE / Constants.GAME_HEIGHT * gameStage.getHeight();
        float infoIconSize = (float) Constants.INFO_ICON_SIZE / Constants.GAME_HEIGHT * gameStage.getHeight();

        ImageButton.ImageButtonStyle infoButtonStyle = new ImageButton.ImageButtonStyle(game.skin.get("default", Button.ButtonStyle.class));
        TextureRegionDrawable infoIconDrawable = new TextureRegionDrawable(atlas.findRegion("info_icon"));
        infoIconDrawable.setMinSize(infoIconSize, infoIconSize);
        infoButtonStyle.imageUp = infoIconDrawable;
        infoButton = new ImageButton(infoButtonStyle);
        infoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showInfoMenu();
            }
        });
        infoButton.setSize(buttonSize, buttonSize);
        infoButton.setPosition(buttonPadding, buttonPadding);
        gameStage.addActor(infoButton);

        // Create Month Button
        months = game.bundle.get("months").split(",");
        monthButton = new TextButton(months[curMonth], game.skin, "month");
        monthButton.setPosition(gameStage.getWidth() - buttonPadding - monthButtonWidth,
                buttonPadding);
        monthButton.setSize(monthButtonWidth, buttonSize);
        monthButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeMonth();
            }
        });
        gameStage.addActor(monthButton);

        // Info UI table
        uiStage = new Stage(uiViewport);
        table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);

        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        setGameInputs();
    }

    private void goBack() {
        switch (currentState) {
            case GAME:
                Gdx.app.exit();
                break;
            case INFO:
                hideInfoMenu();
                break;
        }
    }

    private void setGameInputs() {
        multiplexer.clear();
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(this);
    }

    private void setMenuInputs() {
        multiplexer.clear();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(this);
    }

    private void showInfoMenu() {
        currentState = GameState.INFO;
        setMenuInputs();
        table.clear();

        Table infoTable = new Table();
        infoTable.pad(buttonPadding);

        Label infoTitle = new Label("Eternal Calendar", game.skin, "info");
        infoTable.add(infoTitle);
        infoTable.row();

        ScrollPane scrollPane = new ScrollPane(infoTable, game.skin, "default");
        scrollPane.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideInfoMenu();
                return true;
            }
        });
        table.add(scrollPane)
//                .prefSize(gameWidth, gameHeight)
                .pad(buttonPadding);
    }

    private void hideInfoMenu() {
        currentState = GameState.GAME;
        setGameInputs();
        table.clearChildren();
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

        gameStage.act(delta);
        gameStage.draw();
        uiStage.draw();

        game.batch.setProjectionMatrix(gameStage.getViewport().getCamera().combined);
        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
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
        uiStage.dispose();
        gameStage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        InputEvent touchDownEvent = new InputEvent();
        touchDownEvent.setType(InputEvent.Type.touchDown);

        if (currentState == GameState.GAME) {
            switch (keycode) {
                case Input.Keys.Q:
                    goBack();
                    break;
                // Slider Movement
                case Input.Keys.LEFT:
                    slider.moveLeft();
                    break;
                case Input.Keys.RIGHT:
                    slider.moveRight();
                    break;
                // Info button
                case Input.Keys.I:
                case Input.Keys.SLASH:
                    infoButton.fire(touchDownEvent);
                    break;
                // Change month
                case Input.Keys.M:
                    monthButton.fire(touchDownEvent);
                    break;
            }
        }
        switch (keycode) {
            // Go back
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                goBack();
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        InputEvent touchUpEvent = new InputEvent();
        touchUpEvent.setType(InputEvent.Type.touchUp);

        switch(keycode) {
            case Input.Keys.I:
            case Input.Keys.SLASH:
                infoButton.fire(touchUpEvent);
                break;
            case Input.Keys.M:
                monthButton.fire(touchUpEvent);
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        hideInfoMenu();
        return true;
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

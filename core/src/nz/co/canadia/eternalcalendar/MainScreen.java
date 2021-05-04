package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements InputProcessor, Screen {
    private final EternalCalendar game;
    private final Stage stage;

    public MainScreen(EternalCalendar game) {
        this.game = game;

        BitmapFont dateFont = game.fontLoader.getDateFont(game.manager);
        BitmapFont smallDateFont = game.fontLoader.getSmallDateFont(game.manager);
        Texture backgroundTexture = game.manager.get("textures/background.jpg", Texture.class);
        Texture sliderTexture = game.manager.get("textures/slider.png", Texture.class);

        // Parse date rows and columns from CSV file
        FileHandle file = Gdx.files.internal("data/dates.csv");
        String text = file.readString("UTF-8");
        String[] textArray = text.split("\\r?\\n");
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

        Label.LabelStyle dateLabelStyle = new Label.LabelStyle(dateFont, Constants.FONT_COLOR);
        Label.LabelStyle smallDateLabelStyle = new Label.LabelStyle(smallDateFont, Constants.FONT_COLOR);
        for (int i = 0; i < dateArray.length; i++) {
            for (int j = 0; j < dateArray[i].length; j++) {
                Label l;
                if (i == 4 && j < 2) {
                    l = new Label(dateArray[i][j], smallDateLabelStyle);
                } else {
                    l = new Label(dateArray[i][j], dateLabelStyle);
                }
                l.setPosition(datePadding + j * dateColWidth, firstColumnY - i * dateColWidth, Align.center);
                stage.addActor(l);
            }
        }

        float sliderWidth = (float) Constants.SLIDER_WIDTH / Constants.GAME_WIDTH * stage.getWidth();
        float sliderHeight = (float) Constants.SLIDER_HEIGHT / Constants.GAME_HEIGHT * stage.getHeight();
        Image sliderImage = new Image(sliderTexture);
        sliderImage.setSize(sliderWidth, sliderHeight);
        sliderImage.setPosition(270f / Constants.GAME_WIDTH * uiWidth, stage.getHeight() - sliderHeight);
        stage.addActor(sliderImage);

        Gdx.input.setInputProcessor(this);
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
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
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

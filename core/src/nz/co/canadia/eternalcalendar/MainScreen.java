package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements InputProcessor, Screen {
    private final EternalCalendar game;
    private final Viewport uiViewport;
    private final BitmapFont dateFont;
    private final String[][] dateArray;
    private final BitmapFont smallDateFont;
    private final int colWidth;

    public MainScreen(EternalCalendar game) {
        this.game = game;

        dateFont = game.fontLoader.getDateFont(game.manager);
        smallDateFont = game.fontLoader.getSmallDateFont(game.manager);

        // Parse date rows and columns from CSV file
        FileHandle file = Gdx.files.internal("data/dates.csv");
        String text = file.readString("UTF-8");
        String[] textArray = text.split("\\r?\\n");
        dateArray = new String[textArray.length][];
        for (int i = 0; i < textArray.length; i++) {
            dateArray[i] = textArray[i].split(",");
        }

        int uiWidth = MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.GAME_ASPECT_RATIO);
        OrthographicCamera uiCamera = new OrthographicCamera(uiWidth, Gdx.graphics.getBackBufferHeight());
        uiViewport = new FitViewport(uiWidth, Gdx.graphics.getBackBufferHeight(), uiCamera);

        colWidth = uiWidth / (Constants.N_COLUMNS + 1);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Constants.BACKGROUND_COLOR);

        game.batch.setProjectionMatrix(uiViewport.getCamera().combined);

        game.batch.begin();

        for (int i = 0; i < dateArray.length; i++) {
            String[] row = dateArray[i];
            for (int j = 0; j < row.length; j++) {
                if (i == 4 && (j == 0 || j == 1)) {
                    smallDateFont.draw(game.batch, row[j], colWidth + j * colWidth, 400 - i * 50, 1, Align.center, false);
                } else {
                    dateFont.draw(game.batch, row[j], colWidth + j * colWidth, 400 - i * 50, 1, Align.center, false);
                }
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
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

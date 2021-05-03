package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements InputProcessor, Screen {
    private final EternalCalendar game;
    private final Viewport viewport;
    private final BitmapFont font;
    private final String[][] dateArray;

    public MainScreen(EternalCalendar game) {
        this.game = game;

        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);

        // Parse date rows and columns from CSV file
        FileHandle file = Gdx.files.internal("dates.csv");
        String text = file.readString("UTF-8");
        String[] textArray = text.split("\\r?\\n");
        dateArray = new String[textArray.length][];
        for (int i = 0; i < textArray.length; i++) {
            dateArray[i] = textArray[i].split(",");
        }

        OrthographicCamera camera = new OrthographicCamera(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        viewport = new FillViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Constants.BACKGROUND_COLOR);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        game.batch.begin();

        for (int i = 0; i < dateArray.length; i++) {
            String[] row = dateArray[i];
            for (int j = 0; j < row.length; j++) {
                font.draw(game.batch, row[j], 100 + j * 40, 400 - i * 50);
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

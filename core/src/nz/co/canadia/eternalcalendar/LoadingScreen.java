package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen {
    private final EternalCalendar game;
    private final Stage stage;
    private final ProgressBar progressBar;
    private final int gameWidth;
    private float progress;

    public LoadingScreen(EternalCalendar game) {
        this.game = game;
        gameWidth = MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.GAME_ASPECT_RATIO);

        // Load assets
        game.manager.load("textures/textures.atlas", TextureAtlas.class);

        float buttonPadding =
                (float) Constants.BUTTON_PADDING / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight();

        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(gameWidth,
                Gdx.graphics.getBackBufferHeight(), camera) {
        };
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(buttonPadding);
        stage.addActor(table);

        // create ProgressBarStyle
        ProgressBar.ProgressBarStyle progressBarStyle =
                game.skin.get("loading",
                        ProgressBar.ProgressBarStyle.class);
        // ProgressBar
        progressBar = new ProgressBar(0, 1, .01f,
                false, progressBarStyle);
        progressBar.setValue(progress);

        Label timerLabel = new Label(game.bundle.get("loadingLabel"), game.skin,
                "date");
        table.add(timerLabel).space(buttonPadding);
        table.row();
        table.add(progressBar).prefWidth(gameWidth / 2f).space(buttonPadding);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Constants.GAME_BACKGROUND_COLOR);

        if (game.manager.update()) {
            game.setScreen(new MainScreen(game));
        }

        // Update progress
        progress = game.manager.getProgress();
        progressBar.setValue(progress);

        // Draw background rectangle
        game.shape.setProjectionMatrix(stage.getViewport().getCamera().combined);
        game.shape.begin(ShapeRenderer.ShapeType.Filled);
        game.shape.setColor(Constants.LOADING_BACKGROUND_COLOR);
        game.shape.rect(0, 0, gameWidth, Gdx.graphics.getBackBufferHeight());
        game.shape.end();

        // draw UI
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
}

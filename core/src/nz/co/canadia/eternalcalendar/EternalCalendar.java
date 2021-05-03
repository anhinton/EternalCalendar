package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class EternalCalendar extends Game {

	public AssetManager manager;
	public SpriteBatch batch;
	FontLoader fontLoader;

	public EternalCalendar(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		manager = new AssetManager();
		batch = new SpriteBatch();

		fontLoader.loadDateFont(manager);
		fontLoader.loadSmallDateFont(manager);
		manager.finishLoading();

		this.setScreen(new MainScreen(this));
	}

	public static int getDateFontSize() {
		return MathUtils.round((float) Constants.DATE_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
	}

	public static int getSmallDateFontSize() {
		return MathUtils.round((float) Constants.SMALL_DATE_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}

package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

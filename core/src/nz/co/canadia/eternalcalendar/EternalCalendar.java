package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

public class EternalCalendar extends Game {

	AssetManager manager;
	SpriteBatch batch;
	FontLoader fontLoader;
	I18NBundle bundle;

	public EternalCalendar(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		I18NBundle.setSimpleFormatter(true);
		// Catch BACK on Android devices
		Gdx.input.setCatchKey(Input.Keys.BACK, true);

		manager = new AssetManager();
		batch = new SpriteBatch();

		fontLoader.loadDateFont(manager);
		fontLoader.loadSmallDateFont(manager);
		fontLoader.loadWeekdayFont(manager);
		manager.load("textures/background.jpg", Texture.class);
		manager.load("textures/slider.png", Texture.class);
		manager.load("i18n/Bundle", I18NBundle.class);
		manager.finishLoading();

		bundle = manager.get("i18n/Bundle", I18NBundle.class);

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

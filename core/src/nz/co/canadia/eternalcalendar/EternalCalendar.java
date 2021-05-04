package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;

public class EternalCalendar extends Game {

	AssetManager manager;
	SpriteBatch batch;
	FontLoader fontLoader;
	I18NBundle bundle;
	Skin skin;

	public EternalCalendar(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		I18NBundle.setSimpleFormatter(true);
		// Catch BACK on Android devices
		Gdx.input.setCatchKey(Input.Keys.BACK, true);

		// Load assets
		manager = new AssetManager();
		fontLoader.loadDateFont(manager);
		fontLoader.loadSmallDateFont(manager);
		fontLoader.loadWeekdayFont(manager);
		// Textures etc
		// TODO: Load these on a loading screen
		manager.load("textures/background.jpg", Texture.class);
		manager.load("textures/slider.png", Texture.class);
		manager.load("i18n/Bundle", I18NBundle.class);
		manager.load("skin/uiskin.json", Skin.class);
		manager.finishLoading();

		// Prepare Skin
		skin = manager.get("skin/uiskin.json", Skin.class);
		// Label.LabelStyle
		skin.add("date", new Label.LabelStyle(fontLoader.getDateFont(manager), Constants.FONT_COLOR), Label.LabelStyle.class);
		skin.add("smallDate", new Label.LabelStyle(fontLoader.getSmallDateFont(manager), Constants.FONT_COLOR), Label.LabelStyle.class);
		skin.add("weekday", new Label.LabelStyle(fontLoader.getWeekdayFont(manager), Constants.FONT_COLOR), Label.LabelStyle.class);
		// TextButton.TextButtonStyle
		TextButton.TextButtonStyle monthTextButtonStye = new TextButton.TextButtonStyle(skin.get("default", TextButton.TextButtonStyle.class));
		monthTextButtonStye.font = fontLoader.getWeekdayFont(manager);
		skin.add("month", monthTextButtonStye);

		batch = new SpriteBatch();
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

package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;

public class EternalCalendar extends Game {

	AssetManager manager;
	SpriteBatch batch;
	FontLoader fontLoader;
	I18NBundle bundle;
	Skin skin;
	Preferences preferences;
	ShapeRenderer shape;

	public EternalCalendar(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		preferences = Gdx.app.getPreferences(Constants.PREFERENCES_PATH);
		I18NBundle.setSimpleFormatter(true);
		// Catch BACK on Android devices
		Gdx.input.setCatchKey(Input.Keys.BACK, true);

		// Load assets
		manager = new AssetManager();
		// Required in this class
		manager.load("i18n/Bundle", I18NBundle.class);
		manager.load("skin/uiskin.json", Skin.class);
		fontLoader.loadDateFont(manager);
		fontLoader.loadSmallDateFont(manager);
		fontLoader.loadWeekdayFont(manager);
		fontLoader.loadCreditsFont(manager);
		manager.finishLoading();

		// Prepare Skin
		skin = manager.get("skin/uiskin.json", Skin.class);
		// Label.LabelStyle
		skin.add("date", new Label.LabelStyle(fontLoader.getDateFont(manager), Constants.GAME_FONT_COLOR), Label.LabelStyle.class);
		skin.add("smallDate", new Label.LabelStyle(fontLoader.getSmallDateFont(manager), Constants.GAME_FONT_COLOR), Label.LabelStyle.class);
		skin.add("weekday", new Label.LabelStyle(fontLoader.getWeekdayFont(manager), Constants.GAME_FONT_COLOR), Label.LabelStyle.class);
		skin.add("info", new Label.LabelStyle(fontLoader.getWeekdayFont(manager), Constants.PANEL_FONT_COLOR), Label.LabelStyle.class);
		skin.add("credits", new Label.LabelStyle(fontLoader.getCreditsFont(manager), Constants.PANEL_FONT_COLOR), Label.LabelStyle.class);
		// TextButton.TextButtonStyle
		TextButton.TextButtonStyle monthTextButtonStyle = new TextButton.TextButtonStyle(skin.get("default", TextButton.TextButtonStyle.class));
		monthTextButtonStyle.font = fontLoader.getWeekdayFont(manager);
		monthTextButtonStyle.fontColor = Constants.GAME_FONT_COLOR;
		skin.add("month", monthTextButtonStyle);
		// ScrollPaneStyles
		ScrollPane.ScrollPaneStyle creditsScrollPaneStyle = new ScrollPane.ScrollPaneStyle(skin.get("default", ScrollPane.ScrollPaneStyle.class));
		creditsScrollPaneStyle.background = null;
		skin.add("credits", creditsScrollPaneStyle);

		batch = new SpriteBatch();
		bundle = manager.get("i18n/Bundle", I18NBundle.class);
		shape = new ShapeRenderer();

		this.setScreen(new LoadingScreen(this));
	}

	public int loadColumn() {
		return preferences.getInteger("column", Constants.DEFAULT_SLIDER_COLUMN);
	}

	public int loadMonth() {
		return preferences.getInteger("month", Constants.DEFAULT_MONTH);
	}

	public void saveColumn(int column) {
		preferences.putInteger("column", column);
		preferences.flush();
	}

	public void saveMonth(int month) {
		preferences.putInteger("month", month);
		preferences.flush();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
		shape.dispose();
	}
}

package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;

public class IOSFontLoader implements FontLoader {

    private final FileHandleResolver resolver;

    public IOSFontLoader() {
        resolver = new InternalFileHandleResolver();
    }

    private void setLoader(AssetManager manager) {
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    @Override
    public void loadDateFont(AssetManager manager) {
        setLoader(manager);

        FreetypeFontLoader.FreeTypeFontLoaderParameter dateFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        dateFont.fontFileName = "fonts/Podkova-VariableFont_wght.ttf";
        dateFont.fontParameters.characters = Constants.CHARACTERS;
        dateFont.fontParameters.size = MathUtils.round((float) Constants.DATE_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        manager.load("fonts/Podkova-VariableFont_wghtDate.ttf", BitmapFont.class, dateFont);
    }

    @Override
    public BitmapFont getDateFont(AssetManager manager) {
        return manager.get("fonts/Podkova-VariableFont_wghtDate.ttf", BitmapFont.class);
    }

    @Override
    public void loadSmallDateFont(AssetManager manager) {
        setLoader(manager);

        FreetypeFontLoader.FreeTypeFontLoaderParameter smallDateFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallDateFont.fontFileName = "fonts/Podkova-VariableFont_wght.ttf";
        smallDateFont.fontParameters.characters = Constants.CHARACTERS;
        smallDateFont.fontParameters.size = MathUtils.round((float) Constants.SMALL_DATE_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        manager.load("fonts/Podkova-VariableFont_wghtSmallDate.ttf", BitmapFont.class, smallDateFont);
    }

    @Override
    public BitmapFont getSmallDateFont(AssetManager manager) {
        return manager.get("fonts/Podkova-VariableFont_wghtSmallDate.ttf", BitmapFont.class);
    }

    @Override
    public void loadWeekdayFont(AssetManager manager) {
        setLoader(manager);

        FreetypeFontLoader.FreeTypeFontLoaderParameter weekdayFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        weekdayFont.fontFileName = "fonts/Inconsolata-VariableFont_wdth,wght.ttf";
        weekdayFont.fontParameters.characters = Constants.CHARACTERS;
        weekdayFont.fontParameters.size = MathUtils.round((float) Constants.WEEKDAY_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        manager.load("fonts/Inconsolata-VariableFont_wdth,wghtWeekday.ttf", BitmapFont.class, weekdayFont);
    }

    @Override
    public BitmapFont getWeekdayFont(AssetManager manager) {
        return manager.get("fonts/Inconsolata-VariableFont_wdth,wghtWeekday.ttf", BitmapFont.class);
    }

    @Override
    public void loadCreditsFont(AssetManager manager) {
        setLoader(manager);

        FreetypeFontLoader.FreeTypeFontLoaderParameter creditsFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        creditsFont.fontFileName = "fonts/Inconsolata-VariableFont_wdth,wght.ttf";
        creditsFont.fontParameters.characters = Constants.CHARACTERS;
        creditsFont.fontParameters.size = MathUtils.round((float) Constants.CREDITS_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        manager.load("fonts/Inconsolata-VariableFont_wdth,wghtCredits.ttf", BitmapFont.class, creditsFont);
    }

    @Override
    public BitmapFont getCreditsFont(AssetManager manager) {
        return manager.get("fonts/Inconsolata-VariableFont_wdth,wghtCredits.ttf", BitmapFont.class);
    }
}

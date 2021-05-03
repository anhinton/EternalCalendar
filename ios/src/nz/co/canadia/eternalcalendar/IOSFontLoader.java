package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

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
        dateFont.fontParameters.characters = Constants.FONT_CHARACTERS;
        dateFont.fontParameters.size = Constants.FONT_SIZE;
        dateFont.fontParameters.color = Constants.FONT_COLOR;
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
        smallDateFont.fontParameters.characters = Constants.FONT_CHARACTERS;
        smallDateFont.fontParameters.size = Constants.SMALL_FONT_SIZE;
        smallDateFont.fontParameters.color = Constants.FONT_COLOR;
        manager.load("fonts/Podkova-VariableFont_wghtSmallDate.ttf", BitmapFont.class, smallDateFont);
    }

    @Override
    public BitmapFont getSmallDateFont(AssetManager manager) {
        return manager.get("fonts/Podkova-VariableFont_wghtSmallDate.ttf", BitmapFont.class);
    }
}

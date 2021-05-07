package nz.co.canadia.eternalcalendar.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import nz.co.canadia.eternalcalendar.FontLoader;

public class DesktopFontLoader implements FontLoader {
    @Override
    public void loadDateFont(AssetManager manager) {
        manager.load("fonts/Podkova22.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getDateFont(AssetManager manager) {
        return manager.get("fonts/Podkova22.fnt", BitmapFont.class);
    }

    @Override
    public void loadSmallDateFont(AssetManager manager) {
        manager.load("fonts/Podkova11.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getSmallDateFont(AssetManager manager) {
        return manager.get("fonts/Podkova11.fnt", BitmapFont.class);
    }

    @Override
    public void loadWeekdayFont(AssetManager manager) {
        manager.load("fonts/Inconsolata22.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getWeekdayFont(AssetManager manager) {
        return manager.get("fonts/Inconsolata22.fnt", BitmapFont.class);
    }

    @Override
    public void loadCreditsFont(AssetManager manager) {
        manager.load("fonts/Inconsolata16.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getCreditsFont(AssetManager manager) {
        return manager.get("fonts/Inconsolata16.fnt", BitmapFont.class);
    }
}

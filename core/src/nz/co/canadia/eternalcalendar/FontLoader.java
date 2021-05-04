package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface FontLoader {

    void loadDateFont (AssetManager manager);

    BitmapFont getDateFont (AssetManager manager);

    void loadSmallDateFont (AssetManager manager);

    BitmapFont getSmallDateFont (AssetManager manager);

    void loadWeekdayFont (AssetManager manager);

    BitmapFont getWeekdayFont (AssetManager manager);

}

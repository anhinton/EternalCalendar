package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;

public class Constants {
    public static final String GAME_NAME = "Eternal Calendar";
    public static final Color BACKGROUND_COLOR = new Color(.9f, .9f, .9f, 1);

    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 480;

    public static final String FONT_CHARACTERS = FreeTypeFontGenerator.DEFAULT_CHARS + "\u2022";
    public static final Color FONT_COLOR = Color.BLACK;
    public static final int FONT_SIZE = 22;
    public static final int SMALL_FONT_SIZE = MathUtils.ceil(FONT_SIZE / 2f);
}

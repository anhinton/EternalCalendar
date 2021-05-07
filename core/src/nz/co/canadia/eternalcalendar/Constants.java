package nz.co.canadia.eternalcalendar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Constants {
    public static final String GAME_NAME = "Eternal Calendar";
    public static final Color BACKGROUND_COLOR = new Color(0,0,0, 1);
    public static final String PREFERENCES_PATH = "nz.co.canadia.eternalcalendar.preferences";

    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 480;
    public static final float GAME_ASPECT_RATIO = 1.333f;

    public static final int SLIDER_WIDTH = 370;
    public static final int SLIDER_HEIGHT = 320;
    public static final int DEFAULT_SLIDER_COLUMN = 0;
    public static final int SLIDER_COLUMN_MIN = 0;
    public static final int SLIDER_COLUMN_MAX = 6;

    public static final int DEFAULT_MONTH = 0;

    public static final int DATE_PADDING = 50;
    public static final int DATE_COLUMN_WIDTH = 45;
    public static final int BUTTON_PADDING = 25;

    public static final String CHARACTERS = FreeTypeFontGenerator.DEFAULT_CHARS + "\u2022";
    public static final Color GAME_FONT_COLOR = Color.BLACK;
    public static final Color PANEL_FONT_COLOR = Color.WHITE;
    public static final int DATE_FONT_SIZE = 22;
    public static final int SMALL_DATE_FONT_SIZE = 11;
    public static final int WEEKDAY_FONT_SIZE = 22;
    public static int CREDITS_FONT_SIZE = 16;

    public static final int TEXT_BUTTON_WIDTH = 120;
    public static final int BUTTON_SIZE = 40;
    public static final int INFO_ICON_SIZE = 30;
}

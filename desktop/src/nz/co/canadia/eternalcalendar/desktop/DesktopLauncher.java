package nz.co.canadia.eternalcalendar.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import nz.co.canadia.eternalcalendar.Constants;
import nz.co.canadia.eternalcalendar.EternalCalendar;

public class DesktopLauncher {
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(Constants.GAME_NAME);
		config.setWindowedMode(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		config.setResizable(false);
		config.setWindowIcon("desktopIcons/icon_128.png",
				"desktopIcons/icon_32.png",
				"desktopIcons/icon_16.png");
		config.setDecorated(true);

		new Lwjgl3Application(new EternalCalendar(new DesktopFontLoader()), config);
	}
}

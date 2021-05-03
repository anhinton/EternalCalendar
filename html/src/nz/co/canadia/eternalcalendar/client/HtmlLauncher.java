package nz.co.canadia.eternalcalendar.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import nz.co.canadia.eternalcalendar.Constants;
import nz.co.canadia.eternalcalendar.EternalCalendar;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new EternalCalendar(new HtmlFontLoader());
        }
}
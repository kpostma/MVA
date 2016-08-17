package com.kpostma.mva.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kpostma.mva.MVA;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = MVA.WIDTH;
        config.height = MVA.HEIGHT;
        config.title = MVA.Title;


		new LwjglApplication(new MVA(), config);
	}
}

package com.kpostma.mva;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.states.GameStateManager;
import com.kpostma.mva.states.MenuState;

public class MVA extends ApplicationAdapter{

    public static int WIDTH = 480;
    public static int HEIGHT = 800;
    public static final String Title = "Man Vs Astroid";


    private GameStateManager gsm;
	private SpriteBatch batch;
	Texture img;

    private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
        gsm = new GameStateManager();

        gsm.setEffectVolume(0.5f);
        gsm.setMusicVolume(0.5f);

        music = Gdx.audio.newMusic(Gdx.files.internal("App Score.mp3"));
        music.setLooping(true);
        setMusicVol(gsm.getMusicVol());
        music.play();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
	}



    public void setMusicVol(float vol){
        music.setVolume(vol);
    }


    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }
}

package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.MVA;

/**
 * Created by Postma on 8/12/2016.
 */
public class MenuState extends State  implements  InputProcessor {
    private Texture bg;
    private Texture playBtn;
    private Texture hsBtn;
    private Texture settingsBtn;


    private int test;
    private int hs;

    private TouchInfo Tinfo;

    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("spacebg.jpg");
        playBtn = new Texture("playbtn.png");
        hsBtn = new Texture("highscorebtn.png");
        settingsBtn = new Texture("settingbtn.png");
        test = 5;
        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);

        if(gsm.getHighScore() < 10000)
        hs=10000;
        else
        hs=gsm.getHighScore();


        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;

        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void handleInput() {
        if(Tinfo.touched)
        {
            System.out.println("touched" + Tinfo.touchY);
            if(Tinfo.touchY > 100  && Tinfo.touchY < 200)
            {

                System.out.println("touched play");
                gsm.set(new PlayState(gsm));
                gsm.setHighScore(hs);
                gsm.setPState(false);
            }
            else if(Tinfo.touchY < 350 && Tinfo.touchY > 250)
            {
                System.out.println("Touched Settings");
                gsm.set(new SettingsState(gsm));
                gsm.setHighScore(hs);
                gsm.setPState(false);
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        if(gsm.getPstate() == false)
        {
            Gdx.input.setInputProcessor(this);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0,0, MVA.WIDTH, MVA.HEIGHT);
        sb.draw(playBtn ,50, MVA.HEIGHT - 200 , MVA.WIDTH - 100, 100);
        sb.draw(settingsBtn ,50, MVA.HEIGHT - 350 , MVA.WIDTH - 100, 100);
        sb.draw(hsBtn ,50, MVA.HEIGHT - 500 , MVA.WIDTH - 100, 100);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        playBtn.dispose();
        hsBtn.dispose();
        settingsBtn.dispose();
        System.out.println("Menu State Dispoed");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Tinfo.touchX = screenX;
        Tinfo.touchY = screenY;
        Tinfo.touched = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;
        Tinfo.touched = false;
        return true;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }



    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

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
    private int test;
    private int hs;

    private TouchInfo Tinfo;

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("MainBG.jpg");
        playBtn = new Texture("playbtn.png");
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
            if(Tinfo.touchX >(MVA.WIDTH/2) - (playBtn.getWidth()/2) && Tinfo.touchX < (MVA.WIDTH/2) + (playBtn.getWidth()/2) && Tinfo.touchY > (MVA.HEIGHT/2) - (playBtn.getHeight()/4) && Tinfo.touchY < (MVA.HEIGHT/2) + (playBtn.getHeight()/4))
            {
                gsm.set(new PlayState(gsm));
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
        sb.draw(playBtn , (MVA.WIDTH/2) - (playBtn.getWidth()/2) , (MVA.HEIGHT/2) - (playBtn.getHeight()/4));
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        playBtn.dispose();
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
        Tinfo.touchX = 0;
        Tinfo.touchY = 0;
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

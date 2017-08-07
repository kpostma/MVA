package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.MVA;

import javax.xml.soap.Text;

/**
 * Created by Postma on 9/1/2016.
 */
public class SettingsState extends State implements InputProcessor {
    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private Texture bg;
    private Texture up;
    private Texture down;
    private Texture back;

    private int MainVol;
    private int SFXVol;
    private int MusicVol;
    private TouchInfo  Tinfo;
    BitmapFont Headings;

    private int mainValue;
    private int SFXValue;
    private int musicValue;

    public SettingsState(GameStateManager gsm){
        super(gsm);

        bg = new Texture("spacebg.jpg");
        back = new Texture("backArrow.png");
        up = new Texture("upArrow.png");
        down = new Texture("downArrow.png");
        Headings = new BitmapFont();

        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);

        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;


        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {
        if(Tinfo.touched){
            System.out.println(Tinfo.touchX + " : " + Tinfo.touchY);
            if(Tinfo.touchY < 200 && Tinfo.touchY > 100)
            {
                //touched first button
                gsm.pop(false);
                System.out.println("popped and sent false");
            }
            if(Tinfo.touchY > 250 && Tinfo.touchY < 350)
            {
                gsm.setPState(false);
                gsm.set(new MenuState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0, 0, MVA.WIDTH, MVA.HEIGHT);
        sb.draw(back,50, MVA.HEIGHT - 100 , 100 , 50);

        sb.draw(up ,MVA.WIDTH/2 , MVA.HEIGHT - 200 , 50, 50);
        sb.draw(down ,MVA.WIDTH/2 + 75 , MVA.HEIGHT - 200 , 50, 50);


        sb.draw(up ,MVA.WIDTH/2 , MVA.HEIGHT - 310 , 50, 50);
        sb.draw(down ,MVA.WIDTH/2 + 75 , MVA.HEIGHT - 310 , 50, 50);


        sb.draw(up ,MVA.WIDTH/2 , MVA.HEIGHT - 410 , 50, 50);
        sb.draw(down ,MVA.WIDTH/2 + 75 , MVA.HEIGHT - 410 , 50, 50);


        Headings.getData().setScale(2);
        Headings.draw(sb,"Master Volume \n\n\nEffects Volume \n\n\nMusic Volume",MVA.WIDTH/2 - 200 , MVA.HEIGHT - 160 );

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        back.dispose();
        up.dispose();
        down.dispose();
        back.dispose();
        Headings.dispose();

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

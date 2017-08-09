package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.MVA;

import javax.xml.soap.Text;

/**
 * Created by Postma on 9/1/2016.
 */
public class SettingsState extends State implements InputProcessor {

    private Texture bg;
    private Texture up;
    private Texture down;
    private Texture back;

    private int MainVol;
    private int SFXVol;
    private int MusicVol;
    private TouchInfo  Tinfo;
    BitmapFont Headings;


    private Sound ButtonClick;


    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    public SettingsState(GameStateManager gsm){
        super(gsm);
        System.out.println("Setting State Created.");
        bg = new Texture("spacebg.jpg");
        back = new Texture("backArrow.png");
        up = new Texture("upArrow.png");
        down = new Texture("downArrow.png");
        Headings = new BitmapFont();

        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;


        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {
        if(Tinfo.touched){

            if(Tinfo.touchY < 250 && Tinfo.touchX < 300  )
            {
                //touched first button
                System.out.println("back button");
                ButtonClick.play(gsm.getEffectVolume());
                gsm.set(new MenuState(gsm));
                gsm.setPState(false);
            }
            if(Tinfo.touchX > MVA.WIDTH/2 && Tinfo.touchX < MVA.WIDTH/2 + 50)
            {
                if(Tinfo.touchY > MVA.HEIGHT - 200  && Tinfo.touchY > MVA.HEIGHT - 150)
                {
                    System.out.println("touched Master up");
                }
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
        sb.begin();
        sb.draw(bg, 0, 0, MVA.WIDTH + 300 , MVA.HEIGHT + 300);
        sb.draw(back,50, MVA.HEIGHT - 100 , 100 , 50);


        //effects
        sb.draw(up ,MVA.WIDTH/2 , MVA.HEIGHT - 310 , 50, 50);
        sb.draw(down ,MVA.WIDTH/2 + 75 , MVA.HEIGHT - 310 , 50, 50);

        //Music
        sb.draw(up ,MVA.WIDTH/2 , MVA.HEIGHT - 410 , 50, 50);
        sb.draw(down ,MVA.WIDTH/2 + 75 , MVA.HEIGHT - 410 , 50, 50);


        Headings.getData().setScale(2);
        Headings.draw(sb,"\n\n\nEffects Volume \n\n\nMusic Volume",MVA.WIDTH/2 - 200 , MVA.HEIGHT - 160 );


        //effect volume value
        Headings.draw(sb, String.valueOf(gsm.getEffectVolume()*10) ,MVA.WIDTH/2 + 150 , MVA.HEIGHT - 270 );

        //Music volume value
        Headings.draw(sb, String.valueOf(gsm.getMusicVol()*10) ,MVA.WIDTH/2 + 150 , MVA.HEIGHT - 370 );

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
        ButtonClick.dispose();
        System.out.println("Setting State disposed.");

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
        System.out.println("touched: " + Tinfo.touchX +" , " + Tinfo.touchY );
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

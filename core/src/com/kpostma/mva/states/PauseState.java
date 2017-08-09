package com.kpostma.mva.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.MVA;

/**
 * Created by Postma on 8/22/2016.
 */
public class PauseState extends State  implements  InputProcessor
{



    private Texture bg;
    private Texture resumebtn;
    private Texture quitbtn;
    private TouchInfo Tinfo;
    BitmapFont scoreFont;

    private Sound ButtonClick;



    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    protected PauseState(GameStateManager gsm)
    {
        super(gsm);
        bg = new Texture("spacebg.jpg");
        resumebtn = new Texture("resumebtn.png");
        quitbtn = new Texture("quitbtn.png");

        scoreFont = new BitmapFont();

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

        cam.setToOrtho(false, MVA.WIDTH, MVA.HEIGHT);
        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;


        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput( ) {
        if(Tinfo.touched){
            System.out.println(Tinfo.touchX + " : " + Tinfo.touchY);
            if(Tinfo.touchY < 500 && Tinfo.touchY > 200)
            {
                //touched first button
                gsm.pop(false);
                ButtonClick.play(gsm.getEffectVolume());
                System.out.println("popped and sent false");
            }
            if(Tinfo.touchY > 600 && Tinfo.touchY < 800)
            {
                //quit
                ButtonClick.play(gsm.getEffectVolume());
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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0);
        sb.draw(resumebtn,50, MVA.HEIGHT - 200 , MVA.WIDTH-100, 100);
        sb.draw(quitbtn,50, MVA.HEIGHT - 350 , MVA.WIDTH - 100, 100);
        scoreFont.getData().setScale(2);
        scoreFont.draw(sb,gsm.getHighScoreString(),MVA.WIDTH/8 , MVA.HEIGHT - 50 );

        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        resumebtn.dispose();
        quitbtn.dispose();
        scoreFont.dispose();
        ButtonClick.dispose();
        System.out.println("Pause State Disposed");
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

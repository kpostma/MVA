package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.MVA;

/**
 * Created by Postma on 8/12/2016.
 */
public class MenuState extends State {
    private Texture bg;
    private Texture playBtn;
    private int test;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("MainBG.jpg");
        playBtn = new Texture("playbtn.png");
        test = 5;
        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);
    }

    @Override
    public void handleInput() {

        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
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


}

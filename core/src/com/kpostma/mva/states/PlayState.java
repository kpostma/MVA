package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kpostma.mva.MVA;
import com.kpostma.mva.sprites.Astroid;
import com.kpostma.mva.sprites.Ship;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.kpostma.mva.sprites.Shot;

import javax.xml.soap.Text;

/**
 * Created by Postma on 8/12/2016.
 */
public class PlayState extends State implements  ApplicationListener, InputProcessor{
    private static final int Astroid_count = 5;

    private Ship ship;
    private Texture bg;
    private boolean movementSwitch;
    //private Astroid astroid;
    private Array<Astroid> astroids;
    private Array<Shot> shots;


    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private TouchInfo Tinfo;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        movementSwitch = true;

        ship = new Ship(MVA.WIDTH/2,50);
        bg = new Texture("spacebg.jpg");
        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);


        astroids = new Array<Astroid>(Astroid_count);
        for(int i =  0; i < Astroid_count ; i++)
        {
            astroids.add(new Astroid());
        }

        shots = new Array<Shot>();


        Tinfo = new TouchInfo();
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;
        Tinfo.touched = false;

        Gdx.input.setInputProcessor(this);
    }




    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
        {
            if(Tinfo.touched){
                if(Tinfo.touchX > (MVA.WIDTH/2) && Tinfo.touchX < MVA.WIDTH) {
                    if (movementSwitch) {
                        ship.moveLeft();
                        movementSwitch = false;
                    } else {
                        ship.moveRight();
                        movementSwitch = true;
                    }
                }
                else if(Tinfo.touchX < (MVA.WIDTH/2))
                {
                    shots.add(new Shot((int)ship.getPosition().x + (ship.getTexture().getWidth()/2) , (int)ship.getPosition().y + (ship.getTexture().getHeight())) );
                }
            }


        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        ship.update(dt);

       for (Astroid astroid : astroids)
        {
            astroid.update(dt);

            if (astroid.getPosition().y < 0)
                astroid.reset();

            if(astroid.getPosition().x < 0 + 5 || astroid.getPosition().x > (MVA.WIDTH - astroid.getTexture().getWidth() - 5))
                astroid.bounce();

            if(astroid.collides(ship.getBounds())){
                gsm.set(new MenuState(gsm));
            }
        }

        for(Shot shot : shots)
        {
            shot.update(dt);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0,0 , MVA.WIDTH, MVA.HEIGHT);
        sb.draw(ship.getTexture(),ship.getPosition().x,ship.getPosition().y);

        for(Astroid astroid : astroids)
        sb.draw(astroid.getTexture(),astroid.getPosition().x,astroid.getPosition().y);

        for(Shot shot: shots)
        sb.draw(shot.getTexture(),shot.getPosition().x,shot.getPosition().y);

        sb.end();

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
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        ship.dispose();
        System.out.println("PlayState Disposed");
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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

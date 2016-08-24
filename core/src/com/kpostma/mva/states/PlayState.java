package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kpostma.mva.MVA;
import com.kpostma.mva.sprites.Astroid;
import com.kpostma.mva.sprites.Ship;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.kpostma.mva.sprites.Shot;
import com.kpostma.mva.sprites.smallAstroid;

import javax.xml.soap.Text;

/**
 * Created by Postma on 8/12/2016.
 */
public class PlayState extends State implements  ApplicationListener, InputProcessor{



    static final int GAME_RUNNING = 0;
    static final int GAME_PAUSED = 1;
    int state;

    //initail amount of astroids added
    private static final int Astroid_count = 1  ;


    private Ship ship;
    private Texture bg;
    private Texture setting;
    //player movement switch for player direction
    private boolean movementSwitch;
    private Array<Astroid> astroids;
    private Array<smallAstroid> smallastroids;
    private Array<Shot> shots;
    private boolean shotswitch;
    BitmapFont scoreFont;
    private int score;
    private int hs;


    //class to hold the info from any screen touch
    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }
    //class to say what key is touched/pressed
    class KeyDown {
        public int keycode;
        public boolean touched = false;
    }

    private TouchInfo Tinfo;
    private KeyDown Kdown;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        movementSwitch = true;
        ship = new Ship(MVA.WIDTH/2,50);
        bg = new Texture("whitebg.jpg");
        setting = new Texture("settingicon.png");
        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);
        shotswitch = true;
        scoreFont = new BitmapFont();
        score = 0;
        hs = gsm.getHighScore();

        astroids = new Array<Astroid>(Astroid_count);
        for(int i =  0; i < Astroid_count ; i++)
        {
            astroids.add(new Astroid());
        }

        shots = new Array<Shot>();
        smallastroids = new Array<smallAstroid>();


        Tinfo = new TouchInfo();
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;
        Tinfo.touched = false;

        Kdown = new KeyDown();
        Kdown.keycode = -1;
        Kdown.touched = false;


        state = GAME_RUNNING;
        Gdx.input.setInputProcessor(this);
    }




    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
        {
            if(Tinfo.touched){
                System.out.println(Tinfo.touchX + " : " + Tinfo.touchY);
                if(Tinfo.touchX > MVA.WIDTH - 50 && Tinfo.touchY < 50)
                {
                    if(score > hs)
                    {
                        hs = score;
                        gsm.setHighScore(hs);
                    }
                    gsm.push(new PauseState(gsm),true);
                    state = GAME_PAUSED;
                    System.out.println("game paused");
                }


                if(state==GAME_RUNNING)
                {
                    Gdx.input.setInputProcessor(this);
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
                        shotswitch = true;
                    }
                }
            }
        }
        if(Kdown.touched)
        {
            shots.add(new Shot((int)ship.getPosition().x + (ship.getTexture().getWidth()/2) , (int)ship.getPosition().y + (ship.getTexture().getHeight())) );
            Kdown.touched=false;
            shotswitch = true;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        if(gsm.getPstate() == false)
        {
            Gdx.input.setInputProcessor(this);
            state = GAME_RUNNING;
        }
        if(state == GAME_RUNNING)
            updateRunning(dt);
    }

    private void updateRunning(float dt)
    {
        ship.update(dt);

        if(astroids.size < 1 && smallastroids.size < 1){ astroids.add(new Astroid()); }

        for (Astroid astroid : astroids)
        {
            astroid.update(dt);

            if (astroid.getPosition().y < 0)
                astroid.reset();

            if(astroid.getPosition().x < 0 + 5 || astroid.getPosition().x > (MVA.WIDTH - astroid.getTexture().getWidth() - 5))
                astroid.bounce();

            if(astroid.collides(ship.getBounds()))
                gsm.set(new MenuState(gsm));
        }


        for (smallAstroid smallastroid : smallastroids)
        {
            smallastroid.update(dt);

            if (smallastroid.getPosition().y < 0)
                smallastroid.reset();

            if(smallastroid.getPosition().x < 0 + 5 || smallastroid.getPosition().x > (MVA.WIDTH - smallastroid.getTexture().getWidth() - 5))
                smallastroid.bounce();

            if(smallastroid.collides(ship.getBounds())){
                gsm.set(new MenuState(gsm));
            }
        }

        for(int idx = 0 ; idx < shots.size ; ++idx) {
            shots.get(idx).update(dt);

            if (shots.get(idx).getPosition().y > MVA.HEIGHT){
                shots.get(idx).dispose();
                shots.removeIndex(idx);
                break;
            }

            for (int i = 0; i < smallastroids.size; i++) {
                if (shots.get(idx).collides(smallastroids.get(i).getBounds())) {

                    if(smallastroids.get(i).astroidRespawn())
                    {
                        astroids.add(new Astroid());
                        System.out.println("Astroid Added");
                    }
                    else
                        System.out.println("Astroid Not Added");


                    smallastroids.get(i).dispose();
                    smallastroids.removeIndex(i);
                    System.out.println("smallAstroid Removed");

                    shots.get(idx).dispose();
                    shots.removeIndex(idx);
                    System.out.println("Shot Removed");
                    if(shots.size == 0 || idx == shots.size)
                    {
                        shotswitch = false;
                    }

                    score += 250;
                    System.out.println("250 points added");
                    break;
                }
            }

            if(shotswitch) {
                System.out.println("shotswitch fine");
                for (int index = 0; index < astroids.size; index++) {
                    System.out.println("inside astroids forloop");
                    if (shots.get(idx).collides(astroids.get(index).getBounds())) {
                        System.out.println("inside collides");

                        smallastroids.add(new smallAstroid((int) astroids.get(index).getPosition().x, (int) astroids.get(index).getPosition().y));
                        smallastroids.add(new smallAstroid((int) astroids.get(index).getPosition().x, (int) astroids.get(index).getPosition().y));
                        System.out.println("Two smallAstroids added");

                        astroids.get(index).dispose();
                        astroids.removeIndex(index);
                        System.out.println("Astroid Removed");

                        shots.get(idx).dispose();
                        shots.removeIndex(idx);
                        System.out.println("Shot Removed");


                        score += 500;
                        break;
                    }
                }
            }
        }
    }




    @Override
    public void render(SpriteBatch sb) {
        if(state == GAME_RUNNING)
        {
            sb.setProjectionMatrix(cam.combined);
            sb.begin();
            sb.draw(bg, 0,0 , MVA.WIDTH, MVA.HEIGHT);
            sb.draw(setting ,MVA.WIDTH-50, MVA.HEIGHT-50, setting.getWidth(), setting.getHeight());
            sb.draw(ship.getTexture(),ship.getPosition().x,ship.getPosition().y);

            for(Astroid astroid : astroids)
                sb.draw(astroid.getTexture(),astroid.getPosition().x,astroid.getPosition().y);


            for(smallAstroid astroid : smallastroids)
                sb.draw(astroid.getTexture(),astroid.getPosition().x,astroid.getPosition().y);

            for(Shot shot: shots)
                sb.draw(shot.getTexture(),shot.getPosition().x,shot.getPosition().y);

            scoreFont.draw(sb,String.valueOf(score), 10 , MVA.HEIGHT - 25 );
            sb.end();
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        ship.dispose();
        System.out.println("Play State Disposed");
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
        Kdown.touched = true;
        Kdown.keycode = keycode;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        Kdown.touched = false;
        Kdown.keycode = keycode;
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

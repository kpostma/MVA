package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.kpostma.mva.sprites.powerup;
import com.kpostma.mva.sprites.smallAstroid;

import javax.xml.soap.Text;

/**
 * Created by Postma on 8/12/2016.
 */
public class PlayState extends State implements  ApplicationListener, InputProcessor{



    static final int GAME_RUNNING = 0;
    static final int GAME_PAUSED = 1;
    int state;


    private Ship ship;
    private Texture bg;
    private Texture setting;
    //player movement switch for player direction
    private boolean movementSwitch;
    private Array<Astroid> astroids;
    private Array<smallAstroid> smallastroids;
    private Array<Shot> shots;
    private Array<powerup> powerups;
    private boolean shotswitch;
    BitmapFont scoreFont;
    private int score;
    private int hs;

    //power ups
    private boolean rapidfire;
    private boolean pointmulti;
    private boolean penshot;

    //sounds
    private Sound Afterdeath;
    private Sound NukeSound;
    private Sound PowerupSound;
    private Sound ShipExplosion;
    private Sound ClickButton;



    //class to hold the info from any screen touch
    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }
    //class to say what key is touched/pressed
    private class KeyDown {
        public int keycode;
        public boolean touched = false;
    }

    private TouchInfo Tinfo;
    private KeyDown Kdown;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        movementSwitch = true;
        ship = new Ship(MVA.WIDTH/2,50);
        bg = new Texture("spacebg.jpg");
        setting = new Texture("settinggear.png");
        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);
        shotswitch = true;
        scoreFont = new BitmapFont();
        score = 0;
        hs = gsm.getHighScore();
        astroids = new Array<Astroid>();

        Afterdeath = Gdx.audio.newSound(Gdx.files.internal("After Death.mp3"));
        NukeSound  = Gdx.audio.newSound(Gdx.files.internal("Nuke.mp3"));
        PowerupSound  = Gdx.audio.newSound(Gdx.files.internal("Power Up.mp3"));
        ShipExplosion = Gdx.audio.newSound(Gdx.files.internal("Ship Explosion.mp3"));
        ClickButton = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

        shots = new Array<Shot>();
        smallastroids = new Array<smallAstroid>();
        powerups = new Array<powerup>();

        rapidfire = false;
        penshot = false;
        pointmulti = false;

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
                if(Tinfo.touchX > MVA.WIDTH - 200 && Tinfo.touchY < 200)
                {
                    if(score > hs)
                    {
                        hs = score;
                        gsm.setHighScore(hs);
                    }
                    ClickButton.play(gsm.getEffectVolume());
                    gsm.push(new PauseState(gsm),true);
                    state = GAME_PAUSED;
                    System.out.println("game paused");
                }


                if(state==GAME_RUNNING)
                {
                    Gdx.input.setInputProcessor(this);
                if (movementSwitch) {
                    ship.moveLeft();
                    movementSwitch = false;
                } else {
                    ship.moveRight();
                    movementSwitch = true;
                }

                shots.add(new Shot((int)ship.getPosition().x + (ship.getTexture().getWidth()/2) , (int)ship.getPosition().y + (ship.getTexture().getHeight())) );
                shotswitch = true;

                }
            }
        }
        if(Kdown.touched)
        {
            System.out.print("shot fired");
            if(rapidfire)
            {
                shots.add(new Shot((int)ship.getPosition().x + (ship.getTexture().getWidth()/2) -5 , (int)ship.getPosition().y + (ship.getTexture().getHeight())) );
                shots.add(new Shot((int)ship.getPosition().x + (ship.getTexture().getWidth()/2) +5 , (int)ship.getPosition().y + (ship.getTexture().getHeight())) );
            }
            else
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
        if(powerups.size !=0)
        {
            for(int indexPU=0; indexPU< powerups.size ; ++indexPU)
            {
                powerups.get(indexPU).update(dt);
                if( powerups.get(indexPU).collides(ship.getBounds()))
                {

                    PowerupSound.play(gsm.getEffectVolume());
                    //activate powerup
                    switch (powerups.get(indexPU).getPoweruptype())
                    {
                        case 1:
                            //rapid fire
                            System.out.println("rapid fire");
                            rapidfire = true;
                            penshot = false;
                            pointmulti = false;
                            break;
                        case 2:
                            //pen shot
                            System.out.println("pen shot");
                            rapidfire = false;
                            penshot = true;
                            pointmulti = false;
                            break;
                        case 3:
                            //nuke
                            System.out.println("nuke");
                            rapidfire = false;
                            penshot = false;
                            pointmulti = false;

                            NukeSound.play(gsm.getEffectVolume());

                            if(astroids.size!=0) {
                                for(int index=0;index!=astroids.size;) {
                                    astroids.get(index).dispose();
                                    astroids.removeIndex(index);
                                }
                            }
                            if(smallastroids.size!=0) {
                                for (int i = 0; i != smallastroids.size;) {
                                    smallastroids.get(i).dispose();
                                    smallastroids.removeIndex(i);
                                }
                            }
                            break;
                        case 4:
                            //point bonus
                            System.out.println("+1500 points");
                            score += 1500;
                            break;
                        case 5:
                            //point multiplier
                            System.out.println("point multiplier");
                            rapidfire = false;
                            penshot = false;
                            pointmulti = true;
                            break;

                    }
                    powerups.get(indexPU).dispose();
                    powerups.removeIndex(indexPU);

                }
                else if(powerups.get(indexPU).getPosition().y <= 0)
                {
                    powerups.get(indexPU).dispose();
                    powerups.removeIndex(indexPU);

                }

                if(powerups.size <=0)
                {
                    break;
                }
            }
        }

        //when no astroids add 1
        if(astroids.size < 1 && smallastroids.size < 1){ astroids.add(new Astroid()); }

        //when there are astroids
        for (Astroid astroid : astroids)
        {
            astroid.update(dt);
            //reset if astroid hits bottom
            if (astroid.getPosition().y < 0)
                astroid.reset();
            //bounce if hits a side
            if(astroid.getPosition().x < 0 + 5 || astroid.getPosition().x > (MVA.WIDTH - astroid.getTexture().getWidth() - 5))
                astroid.bounce();
            //ship hit end game
            if(astroid.collides(ship.getBounds())) {
                gsm.setHighScore(hs);
                ShipExplosion.play(gsm.getEffectVolume());
                Afterdeath.play(gsm.getEffectVolume());
                gsm.set(new MenuState(gsm));
            }
        }


        for (smallAstroid smallastroid : smallastroids)
        {
            smallastroid.update(dt);
            //if hits bottom reset
            if (smallastroid.getPosition().y < 0)
                smallastroid.reset();
            //if hits wall bounce
            if(smallastroid.getPosition().x < 0 + 5 || smallastroid.getPosition().x > (MVA.WIDTH - smallastroid.getTexture().getWidth() - 5))
                smallastroid.bounce();
            //if hits ship end game
            if(smallastroid.collides(ship.getBounds())){
                gsm.setHighScore(hs);
                ShipExplosion.play(gsm.getEffectVolume());
                Afterdeath.play(gsm.getEffectVolume());
                gsm.set(new MenuState(gsm));
            }
        }

        for(int idx = 0 ; idx < shots.size ; ++idx) {
            shots.get(idx).update(dt);
            //dispose of shots that go off screen
            if (shots.get(idx).getPosition().y > MVA.HEIGHT){
                shots.get(idx).dispose();
                shots.removeIndex(idx);
                break;
            }

            for (int i = 0; i < smallastroids.size; i++) {
                if (shots.get(idx).collides(smallastroids.get(i).getBounds())) {

                    //if a new astroid spawns or a powerup
                    if(smallastroids.get(i).astroidRespawn())
                    {
                        astroids.add(new Astroid());
                        System.out.println("Astroid Added");
                    }
                    else {
                        powerups.add(new powerup(smallastroids.get(i).getPosition().x,smallastroids.get(i).getPosition().y));
                        System.out.println("Powerup Added");
                    }


                    smallastroids.get(i).dispose();
                    smallastroids.removeIndex(i);
                    System.out.println("smallAstroid Removed");
                    if(!penshot)
                    {
                        shots.get(idx).dispose();
                        shots.removeIndex(idx);
                        System.out.println("Shot Removed");
                    }
                    if(shots.size == 0 || idx == shots.size)
                    {
                        shotswitch = false;
                    }

                    if(pointmulti)
                    score += 500;
                    else
                    score += 250;

                    break;
                }
            }

            if(shotswitch) {
                for (int index = 0; index < astroids.size; index++) {
                    if (shots.get(idx).collides(astroids.get(index).getBounds())) {

                        smallastroids.add(new smallAstroid((int) astroids.get(index).getPosition().x, (int) astroids.get(index).getPosition().y));
                        smallastroids.add(new smallAstroid((int) astroids.get(index).getPosition().x, (int) astroids.get(index).getPosition().y));

                        astroids.get(index).dispose();
                        astroids.removeIndex(index);
                        if(!penshot) {
                            shots.get(idx).dispose();
                            shots.removeIndex(idx);
                        }

                        if(pointmulti)
                            score += 500;
                        else
                            score += 250;
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
            sb.draw(bg, 0,0 , MVA.WIDTH + 300, MVA.HEIGHT+300);
            sb.draw(setting ,MVA.WIDTH-50, MVA.HEIGHT-50, 50, 50);
            sb.draw(ship.getTexture(),ship.getPosition().x,ship.getPosition().y);
            for(powerup pu : powerups)
                sb.draw(pu.getTexture(),pu.getPosition().x,pu.getPosition().y);

            for(Astroid astroid : astroids)
                sb.draw(astroid.getTexture(),astroid.getPosition().x,astroid.getPosition().y);

            for(smallAstroid astroid : smallastroids)
                sb.draw(astroid.getTexture(),astroid.getPosition().x,astroid.getPosition().y);

            for(Shot shot: shots)
                sb.draw(shot.getTexture(),shot.getPosition().x,shot.getPosition().y);
            scoreFont.getData().setScale(1.5f);
            scoreFont.draw(sb,String.valueOf(score), 10 , MVA.HEIGHT - 25 );
            sb.end();
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        setting.dispose();
        ship.dispose();
        scoreFont.dispose();
        ClickButton.dispose();
        Afterdeath.dispose();
        NukeSound.dispose();
        PowerupSound.dispose();
        ShipExplosion.dispose();

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

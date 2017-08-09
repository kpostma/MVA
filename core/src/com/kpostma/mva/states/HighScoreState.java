package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kpostma.mva.MVA;

/**
 * Created by Postma on 7/13/2017.
 */
public class HighScoreState extends State implements InputProcessor {

    private Texture bg;
    private Texture back;
    private TouchInfo  Tinfo;
    private String highscore;
    BitmapFont Headings;

    private long[] highscores;
    private String[] names;


    private Sound ButtonClick;


    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    public HighScoreState(GameStateManager gsm){
        super(gsm);
        System.out.println("Highscore State Created.");
        bg = new Texture("spacebg.jpg");
        back = new Texture("backArrow.png");
        Headings = new BitmapFont();

        highscore = gsm.getHighScoreString();

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

        Save.load();
        highscores = Save.gd.getHighscores();
        names = Save.gd.getNames();


        for(int i = 0; i< highscores.length; i++)
        {
            if(names[i] == "YOU")
            {
                if(highscores[i] < gsm.getHighScore() )
                {
                    highscores[i] = gsm.getHighScore();
                }
            }
        }
        sortHighScores();

        cam.setToOrtho(false, MVA.WIDTH, MVA.HEIGHT);

        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;


        Gdx.input.setInputProcessor(this);
    }


    public void sortHighScores(){
        for(int i = 0 ; i < highscores.length; i++)
        {
            long score = highscores[i];
            String name = names[i];
            int t;
            for(t = i - 1;t >= 0 && highscores[t] < score; t++) {
                highscores[t+1] = highscores[t];
                names[t+1] = names[t];
            }
            highscores[t+1] = score;
            names[t+1] = name;
        }
    }

    @Override
    protected void handleInput() {
        if(Tinfo.touched){
            if(Tinfo.touchY < 250 && Tinfo.touchX < 300  ){
                //touched first button
                System.out.println("back button");
                ButtonClick.play(gsm.getEffectVolume());
                gsm.set(new MenuState(gsm));
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
        sb.begin();
        sb.draw(bg, 0, 0, MVA.WIDTH +300, MVA.HEIGHT + 300);
        sb.draw(back,50, MVA.HEIGHT - 100 , 100 , 50);

        Headings.getData().setScale(2);


        Headings.draw(sb,"Your "+ highscore,50, MVA.HEIGHT/4 *3);
        Headings.draw(sb,"\n\n\n\n\n\nRank\n1st\n2nd\n3rd\n4th\n5th\n6th\n7th\n8th\n9th\n10th",50, MVA.HEIGHT - 160 );
        Headings.draw(sb,"\n\n\n\n\n\nName\n"+names[0]+"\n"+names[1]+"\n"+names[2]+"\n"+names[3]+"\n"+names[4]+"\n"+names[5]+"\n"+names[6]+"\n"+names[7]+"\n"+names[8]+"\n"+names[9],150 , MVA.HEIGHT - 160 );
        Headings.draw(sb,"\n\n\n\n\n\nScore\n"+ highscores[0] +"\n"+ highscores[1] +"\n"+ highscores[2] +"\n"+ highscores[3] +"\n"+ highscores[4] +"\n"+ highscores[5] +"\n"+ highscores[6]+"\n"+       highscores[7] +"\n"+ highscores[8] +"\n"+ highscores[9],350 , MVA.HEIGHT - 160 );

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        back.dispose();
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
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;
        Tinfo.touched = false;
        System.out.println("touched: " + Tinfo.touchX +" , " + Tinfo.touchY );
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

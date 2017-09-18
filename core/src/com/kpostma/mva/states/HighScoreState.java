package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kpostma.mva.MVA;

/**
 * Created by Postma on 7/13/2017.
 */
public class HighScoreState extends State implements InputProcessor {

    private Texture bg;
    private TouchInfo  Tinfo;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton backButton;
    private Label HighscoreLabel, scorelabel, ranklabel, nameslabel;
    private Label[] Scores, Ranks, Names;
    private BitmapFont white, black;

    private long[] highscores;
    private String[] names;

    private long yourHighscore;


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
        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

        cam.setToOrtho(false, MVA.WIDTH, MVA.HEIGHT);

        //loads the array with scores
        //Save.load();
        highscores = gsm.mySave.gd.getHighscores();
        names = gsm.mySave.gd.getNames();

        for(int i = 0; i< highscores.length; i++)
        {
            if(names[i] == "YOU")
            {
                yourHighscore = highscores[i];
            }
        }

        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;

        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"),false);

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/Button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.center().top();

        TextButton.TextButtonStyle textButtonBack = new TextButton.TextButtonStyle();
        textButtonBack.up = skin.getDrawable("backArrow");
        textButtonBack.down = skin.getDrawable("downArrow");
        textButtonBack.pressedOffsetX = 1;
        textButtonBack.pressedOffsetY = 1;
        textButtonBack.font = black;

        backButton = new TextButton("", textButtonBack);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                mainMenu();
            }
        });
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);

        HighscoreLabel = new Label("Your Highscore : " + yourHighscore , headingStyle);
        HighscoreLabel.setFontScale(2);

        scorelabel =  new Label("Score" , headingStyle);
        ranklabel = new Label("Rank" , headingStyle);
        nameslabel = new Label("Name" , headingStyle);

        Scores = new Label[10];
        Ranks = new Label[10];
        Names = new Label[10];

        for(int i = 0; i< highscores.length; i++)
        {
            Scores[i] = new Label(String.valueOf(highscores[i]),headingStyle);
            Ranks[i] = new Label("#" + (i + 1) ,headingStyle);
            Names[i] = new Label(String.valueOf(names[i]),headingStyle);
        }

        table.add(backButton).left().width(300).height(125);
        table.row();
        table.add(HighscoreLabel).colspan(3).uniform().padBottom(200).padTop(200).padLeft(50).padRight(50);
        table.row();
        table.add(ranklabel).uniform();
        table.add(nameslabel).uniform();
        table.add(scorelabel).uniform();
        table.row();

        for(int i = 0; i<Scores.length; i++)
        {
            table.add(Ranks[i]);
            table.add(Names[i]);
            table.add(Scores[i]);
            table.row();
        }

        //table.debug();
        stage.addActor(table);
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

    }

    public void mainMenu(){
        //touched first button
        System.out.println("back button");
        ButtonClick.play(gsm.getEffectVolume());
        gsm.set(new MenuState(gsm));
        gsm.setPState(false);
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(gsm.getPstate() == false)
        {
            Gdx.input.setInputProcessor(stage);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
      /*  sb.begin();
        sb.draw(bg, 0, 0, MVA.WIDTH +300, MVA.HEIGHT + 300);
        sb.draw(back,50, MVA.HEIGHT - 100 , 100 , 50);

        Headings.getData().setScale(2);


        Headings.draw(sb,"Your "+ highscore,50, MVA.HEIGHT/4 *3);
        Headings.draw(sb,"\n\n\n\n\n\nRank\n1st\n2nd\n3rd\n4th\n5th\n6th\n7th\n8th\n9th\n10th",50, MVA.HEIGHT - 160 );
        Headings.draw(sb,"\n\n\n\n\n\nName\n"+names[0]+"\n"+names[1]+"\n"+names[2]+"\n"+names[3]+"\n"+names[4]+"\n"+names[5]+"\n"+names[6]+"\n"+names[7]+"\n"+names[8]+"\n"+names[9],150 , MVA.HEIGHT - 160 );
        Headings.draw(sb,"\n\n\n\n\n\nScore\n"+ highscores[0] +"\n"+ highscores[1] +"\n"+ highscores[2] +"\n"+ highscores[3] +"\n"+ highscores[4] +"\n"+ highscores[5] +"\n"+ highscores[6]+"\n"+       highscores[7] +"\n"+ highscores[8] +"\n"+ highscores[9],350 , MVA.HEIGHT - 160 );

        sb.end();

*/
        sb.begin();
        sb.draw(bg, 0, 0, MVA.WIDTH + 300 , MVA.HEIGHT + 300);
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
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

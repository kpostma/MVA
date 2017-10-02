package com.kpostma.mva.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
 * Created by Postma on 8/22/2016.
 */
public class PauseState extends State  implements  InputProcessor
{



    private Texture bg;
    
    private TouchInfo Tinfo;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton resumeButton , quitButton , helpButton;
    private Label scoreLabel;
    private BitmapFont white, black;
    
    private Sound ButtonClick;
    
    
    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    protected PauseState(GameStateManager gsm)
    {
        super(gsm);
        System.out.println("pause state created");
        bg = new Texture("spacebg.jpg");

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

        cam.setToOrtho(false, MVA.WIDTH, MVA.HEIGHT);
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
        table.center().center();

        TextButton.TextButtonStyle textButtonResume = new TextButton.TextButtonStyle();
        textButtonResume.up = skin.getDrawable("resumebtn");
        textButtonResume.down = skin.getDrawable("resumebtn");
        textButtonResume.pressedOffsetX = 1;
        textButtonResume.pressedOffsetY = 1;
        textButtonResume.font = black;

        TextButton.TextButtonStyle textButtonQuit = new TextButton.TextButtonStyle();
        textButtonQuit.up = skin.getDrawable("quitbtn");
        textButtonQuit.down = skin.getDrawable("quitbtn");
        textButtonQuit.pressedOffsetX = 1;
        textButtonQuit.pressedOffsetY = 1;
        textButtonQuit.font = black;

        TextButton.TextButtonStyle textButtonControls = new TextButton.TextButtonStyle();
        textButtonControls.up = skin.getDrawable("helpbtn");
        textButtonControls.down = skin.getDrawable("helpbtn");
        textButtonControls.pressedOffsetX = 1;
        textButtonControls.pressedOffsetY = 1;
        textButtonControls.font = black;

        helpButton = new TextButton("", textButtonControls);
        helpButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                HelpStateCall();
            }
        });

        resumeButton = new TextButton("", textButtonResume);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ResumeGame();
            }
        });

        quitButton = new TextButton("", textButtonQuit);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                QuitGame();
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        scoreLabel = new Label("Score : " + String.valueOf(gsm.getCurrentScore()) , headingStyle);
        scoreLabel.setFontScale(3);

        table.add(scoreLabel).uniform();
        table.row();
        table.add(resumeButton).uniform().padTop(100).padBottom(100);
        table.row();
        table.add(helpButton).uniform().padBottom(100);
        table.row();
        table.add(quitButton).uniform().padBottom(100);
        //table.debug();
        stage.addActor(table);

        
    }

    @Override
    protected void handleInput( ) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {
            ResumeGame();
        }
        Gdx.input.setInputProcessor(stage);
    }

    public void HelpStateCall(){
        System.out.println("controls button");
        ButtonClick.play(gsm.getEffectVolume());
        gsm.push(new HelpState(gsm),true);
        gsm.setPState(true);
    }

    public void QuitGame(){
        //quit
        ButtonClick.play(gsm.getEffectVolume());
        gsm.setPState(false);
        gsm.set(new MenuState(gsm));
    }

    public void ResumeGame(){
        //touched first button
        gsm.pop(false);
        ButtonClick.play(gsm.getEffectVolume());
        System.out.println("popped and sent false");
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

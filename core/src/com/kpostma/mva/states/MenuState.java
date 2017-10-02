package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
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
 * Created by Postma on 8/12/2016.
 */
public class MenuState extends State  implements  InputProcessor {
    
    private Texture bg;

    private Sound ButtonClick;
    private TouchInfo Tinfo;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton PlayBtn , HighscoreBtn , SettingsBtn, controls;
    private BitmapFont white, black;

    private class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }
    public MenuState(GameStateManager gsm) {
        super(gsm);
        System.out.println("Menu State Created.");
        bg = new Texture("spacebg.jpg");

        Gdx.files.local("hs.bin").delete();

        gsm.loadmySave();

        gsm.SavemySave();


        cam.setToOrtho(false,MVA.WIDTH, MVA.HEIGHT);

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));
        
        Tinfo = new TouchInfo();
        Tinfo.touched = false;
        Tinfo.touchX = -1;
        Tinfo.touchY = -1;


        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"),false);


        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        atlas = new TextureAtlas("ui/Button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.center().top();

        TextButton.TextButtonStyle textButtonPlay = new TextButton.TextButtonStyle();
        textButtonPlay.up = skin.getDrawable("playbtn");
        textButtonPlay.down = skin.getDrawable("playbtn");
        textButtonPlay.pressedOffsetX = 1;
        textButtonPlay.pressedOffsetY = 1;
        textButtonPlay.font = black;

        TextButton.TextButtonStyle textButtonSettings = new TextButton.TextButtonStyle();
        textButtonSettings.up = skin.getDrawable("settingbtn");
        textButtonSettings.down = skin.getDrawable("settingbtn");
        textButtonSettings.pressedOffsetX = 1;
        textButtonSettings.pressedOffsetY = 1;
        textButtonSettings.font = black;

        TextButton.TextButtonStyle textButtonHighscore = new TextButton.TextButtonStyle();
        textButtonHighscore.up = skin.getDrawable("highscorebtn");
        textButtonHighscore.down = skin.getDrawable("highscorebtn");
        textButtonHighscore.pressedOffsetX = 1;
        textButtonHighscore.pressedOffsetY = 1;
        textButtonHighscore.font = black;

        TextButton.TextButtonStyle textButtonControls = new TextButton.TextButtonStyle();
        textButtonControls.up = skin.getDrawable("helpbtn");
        textButtonControls.down = skin.getDrawable("helpbtn");
        textButtonControls.pressedOffsetX = 1;
        textButtonControls.pressedOffsetY = 1;
        textButtonControls.font = black;


        controls = new TextButton("", textButtonControls);
        controls.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                HelpStateCall();
            }
        });


        PlayBtn = new TextButton("",textButtonPlay);
        PlayBtn.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                PlayStateStart();
            }
        });
        SettingsBtn = new TextButton("", textButtonSettings);
        SettingsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SettingsStart();
            }
        });
        HighscoreBtn = new TextButton("", textButtonHighscore);
        HighscoreBtn.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                HighscoreStart();
            }
        });
        table.pad(100).center().center();
        table.add(PlayBtn);
        table.row();
        table.add(SettingsBtn).padTop(100);
        table.row();
        table.add(HighscoreBtn).padTop(100);
        table.row();
        table.add(controls).padTop(100);
        //table.debug();
        stage.addActor(table);
        
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }


    public void HelpStateCall(){
        System.out.println("controls button");
        ButtonClick.play(gsm.getEffectVolume());
        gsm.set(new HelpState(gsm));
        gsm.setPState(false);
    }

    public void PlayStateStart(){
        System.out.println("Play Pressed");
        ButtonClick.play(gsm.getEffectVolume());
        gsm.set(new PlayState(gsm));
        gsm.setPState(false);
    }
    public void SettingsStart(){
        ButtonClick.play(gsm.getEffectVolume());
        System.out.println("settings pressed");
        gsm.set(new SettingsState(gsm));
        gsm.setPState(false);
    }
    public void HighscoreStart(){
        ButtonClick.play(gsm.getEffectVolume());
        System.out.println("Hishcore Pressed");
        gsm.set(new HighScoreState(gsm));
        gsm.setPState(false);
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0, MVA.WIDTH + 300 , MVA.HEIGHT + 300);
        sb.end();

        stage.act();
        stage.draw();
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
    public void dispose() {
        bg.dispose();
        ButtonClick.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        System.out.println("Menu State Dispoed");
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
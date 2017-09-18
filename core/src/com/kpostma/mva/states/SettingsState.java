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

import java.awt.event.InputEvent;

/**
 * Created by Postma on 8/9/2017.
 */
public class SettingsState extends State implements InputProcessor {

    private Texture bg;

    private TouchInfo  Tinfo;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton backButton , effectvolup , effectvoldown, musicvolup, musicvoldown;
    private Label heading,musicLabel, effectLabel;
    private BitmapFont white, black;

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

        cam.setToOrtho(false, MVA.WIDTH, MVA.HEIGHT);

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));

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

        TextButton.TextButtonStyle textButtonUp = new TextButton.TextButtonStyle();
        textButtonUp.up = skin.getDrawable("upArrow");
        textButtonUp.down = skin.getDrawable("backArrow");
        textButtonUp.pressedOffsetX = 1;
        textButtonUp.pressedOffsetY = 1;
        textButtonUp.font = black;

        TextButton.TextButtonStyle textButtonDown = new TextButton.TextButtonStyle();
        textButtonDown.up = skin.getDrawable("downArrow");
        textButtonDown.down = skin.getDrawable("backArrow");
        textButtonDown.pressedOffsetX = 1;
        textButtonDown.pressedOffsetY = 1;
        textButtonDown.font = black;

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

        effectvolup = new TextButton("", textButtonUp);
        effectvolup.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                changevolume(1);
            }
        });

        effectvoldown = new TextButton("", textButtonDown);
        effectvoldown.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                changevolume(2);
            }
        });
        musicvolup = new TextButton("", textButtonUp);
        musicvolup.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                changevolume(3);
            }
        });
        musicvoldown = new TextButton("", textButtonDown);
        musicvoldown.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                changevolume(4);
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);

        heading = new Label("Volume Controls" , headingStyle);
        heading.setFontScale(3);
        musicLabel = new Label("Music Volume : " + String.valueOf((int)(gsm.getMusicVol()*10)) , headingStyle);
        musicLabel.setFontScale(1.5f);
        effectLabel = new Label("Effect Volume : " + String.valueOf((int)(gsm.getEffectVolume()*10)) , headingStyle);
        effectLabel.setFontScale(1.5f);

        table.add(backButton).left().width(300).height(125);
        table.row();
        table.add(heading).colspan(3).uniform().padBottom(200).padTop(200).padLeft(50).padRight(50);
        table.row();
        table.row();
        table.add(musicLabel).uniform();
        table.add(musicvolup).width(100).height(100).maxWidth(100);
        table.add(musicvoldown).width(100).height(100).maxWidth(100);
        table.row().padTop(50);
        table.add(effectLabel).uniform().height(200);
        table.add(effectvolup).width(100).height(100).maxWidth(100);
        table.add(effectvoldown).width(100).height(100).maxWidth(100);
        table.row().padTop(50);
        //table.debug();
        stage.addActor(table);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        if(gsm.getPstate() == false)
        {
            Gdx.input.setInputProcessor(stage);
        }

    }

    public void mainMenu(){
        System.out.println("back button");
        ButtonClick.play(gsm.getEffectVolume());
        gsm.set(new MenuState(gsm));
        gsm.setPState(false);
    }

    public void changevolume(int whatone)
    {
        ButtonClick.play(gsm.getEffectVolume());
        switch(whatone) {
            case 1:
                gsm.EffectVolumeUp();
                effectLabel.setText("Effect Volume : " + String.valueOf((int)(gsm.getEffectVolume()*10)));
                break;
            case 2:
                gsm.EffectVolumeDown();
                effectLabel.setText("Effect Volume : " + String.valueOf((int)(gsm.getEffectVolume()*10)));
                break;
            case 3:
                gsm.MusicVolumeUp();
                musicLabel.setText("Music Volume : " + String.valueOf((int)(gsm.getMusicVol()*10)));
                gsm.MusicVolumeChange();
                break;
            case 4:
                gsm.MusicVolumeDown();
                musicLabel.setText("Music Volume : " + String.valueOf((int)(gsm.getMusicVol()*10)));
                gsm.MusicVolumeChange();
                break;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
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

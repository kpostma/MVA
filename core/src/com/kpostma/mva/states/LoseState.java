package com.kpostma.mva.states;

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
 * Created by Postma on 8/11/2017.
 */
public class LoseState extends State implements InputProcessor {
    private Texture bg;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton backButton;
    private Label heading,scoreLabel;
    private BitmapFont white, black;
    private Sound ButtonClick;
    private long Score;


    public LoseState(GameStateManager gsm , long score) {
        super(gsm);
        Score = score;
        System.out.println("Lose State Created.");
        bg = new Texture("spacebg.jpg");

        cam.setToOrtho(false, MVA.WIDTH, MVA.HEIGHT);

        ButtonClick = Gdx.audio.newSound(Gdx.files.internal("Menu Click.mp3"));


        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"),false);


        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/Button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.center().center();

        TextButton.TextButtonStyle textButtonBack = new TextButton.TextButtonStyle();
        textButtonBack.up = skin.getDrawable("quitbtn");
        textButtonBack.down = skin.getDrawable("quitbtn");
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

        heading = new Label("You Lose" , headingStyle);
        heading.setFontScale(4);

        scoreLabel = new Label("Score : " + Score , headingStyle);
        scoreLabel.setFontScale(3);

        table.add(heading).uniform();
        table.row();
        table.add(scoreLabel).uniform();
        table.row();
        table.add(backButton).uniform();
        //table.debug();
        stage.addActor(table);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {
            mainMenu();
        }
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
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
 * Created by Postma on 8/17/2017.
 */
public class HelpState extends State{

    private Texture bg;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton backButton , shipPic, astroidPic , powerup1pic , powerup2pic , powerup3pic , powerup4pic , powerup5pic;
    private Label heading,controls, powerup1 , powerup2 , powerup3 , powerup4 , powerup5, pwrups;
    private BitmapFont white, black;
    private Sound ButtonClick;

    public HelpState(GameStateManager gsm)
    {
        super(gsm);
        System.out.println("Help State Created.");
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
                SettingsStateLaunch();
            }
        });

        TextButton.TextButtonStyle textButtonShip = new TextButton.TextButtonStyle();
        textButtonShip.up = skin.getDrawable("ship1");
        textButtonShip.down = skin.getDrawable("ship1");
        textButtonShip.font = black;

        TextButton.TextButtonStyle textButtonAstroid = new TextButton.TextButtonStyle();
        textButtonAstroid.up = skin.getDrawable("astroidReal");
        textButtonAstroid.down = skin.getDrawable("astroidReal");
        textButtonAstroid.font = black;

        shipPic = new TextButton("",textButtonShip);
        astroidPic = new TextButton("",textButtonAstroid);


        TextButton.TextButtonStyle textButtonpu1 = new TextButton.TextButtonStyle();
        textButtonpu1.up = skin.getDrawable("pu1");
        textButtonpu1.down = skin.getDrawable("pu1");
        textButtonpu1.font = black;

        powerup1pic = new TextButton("",textButtonpu1);


        TextButton.TextButtonStyle textButtonpu2 = new TextButton.TextButtonStyle();
        textButtonpu2.up = skin.getDrawable("pu2");
        textButtonpu2.down = skin.getDrawable("pu2");
        textButtonpu2.font = black;
        powerup2pic = new TextButton("",textButtonpu2);


        TextButton.TextButtonStyle textButtonpu3 = new TextButton.TextButtonStyle();
        textButtonpu3.up = skin.getDrawable("pu3");
        textButtonpu3.down = skin.getDrawable("pu3");
        textButtonpu3.font = black;
        powerup3pic = new TextButton("",textButtonpu3);

        TextButton.TextButtonStyle textButtonpu4 = new TextButton.TextButtonStyle();
        textButtonpu4.up = skin.getDrawable("pu4");
        textButtonpu4.down = skin.getDrawable("pu4");
        textButtonpu4.font = black;
        powerup4pic = new TextButton("",textButtonpu4);

        TextButton.TextButtonStyle textButtonpu5 = new TextButton.TextButtonStyle();
        textButtonpu5.up = skin.getDrawable("pu5");
        textButtonpu5.down = skin.getDrawable("pu5");
        textButtonpu5.font = black;
        powerup5pic = new TextButton("",textButtonpu5);



        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);

        heading = new Label("How To Play" , headingStyle);
        heading.setFontScale(3);

        pwrups = new Label("Power Ups" , headingStyle);
        pwrups.setFontScale(3);
        
        controls = new Label ("Tap on screen will both change ship direction and shots.\n \nGet points for destroying both large and small asteroids.\n\nLarge Asteroid = 200 Points\n\nSmall Asteroid = 300 Points", headingStyle);
        controls.setFontScale(1.0f);
        
        powerup1 = new Label("Each tap shoots 2 lasers instead of 1", headingStyle);
        powerup1.setFontScale(1.0f);

        powerup2 = new Label("Each shot penetrates through all asteroids", headingStyle);
        powerup2.setFontScale(1.0f);

        powerup3 = new Label("Destroys all asteroids", headingStyle);
        powerup3.setFontScale(1.0f);

        powerup4 = new Label("Doubles all points earned",headingStyle);
        powerup4.setFontScale(1.0f);

        powerup5 = new Label("1500 Point Bonus" , headingStyle);
        powerup5.setFontScale(1.0f);


        table.add(backButton).left().width(300).height(125).padLeft(100);
        table.row();
        table.add(heading).colspan(4).uniform();
        table.row();
        table.add(controls).uniform().colspan(4).pad(10);
        //table.row();
        //table.add(shipPic).uniform().colspan(2).height(100).width(100);
        //table.add(astroidPic).left().colspan(2).height(150).width(150);
        table.row();

        table.add(pwrups).colspan(4).uniform();

        table.row();

        table.add(powerup1).colspan(3).left().padLeft(100).center();
        table.add(powerup1pic).width(75).uniform().height(75).padLeft(50).maxWidth(100);
        table.row();
        table.add(powerup2).colspan(3).left().padLeft(100).center();
        table.add(powerup2pic).width(75).uniform().height(75).padLeft(50).maxWidth(100);
        table.row();
        table.add(powerup3).colspan(3).left().padLeft(100).center();
        table.add(powerup3pic).width(75).uniform().height(75).padLeft(50).maxWidth(100);
        table.row();
        table.add(powerup4).colspan(3).left().padLeft(100).center();
        table.add(powerup4pic).width(75).uniform().height(75).padLeft(50).maxWidth(100);
        table.row();
        table.add(powerup5).colspan(3).left().padLeft(100).center();
        table.add(powerup5pic).width(75).uniform().height(75).padLeft(50).maxWidth(100);
        table.row();


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

    public void SettingsStateLaunch(){
        if(gsm.getPstate())
        {
            ButtonClick.play(gsm.getEffectVolume());
            gsm.pop(true);
        }
        else {
            System.out.println("back button");
            ButtonClick.play(gsm.getEffectVolume());
            gsm.set(new MenuState(gsm));
            gsm.setPState(false);
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
}

package com.kpostma.mva.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kpostma.mva.MVA;

import org.w3c.dom.css.Rect;


/**
 * Created by Postma on 8/12/2016.
 */
public class Ship {
    private Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -15;
    private Rectangle bounds;
    private Texture ship;
    private Sound shot;
    private float SoundEffectVol;

    public Ship(int x, int y)
    {
        ship = new Texture("ship1.png");
        position = new Vector3(x - (ship.getWidth()/2),y,0);
        velocity = new Vector3(0,0,0);
        bounds = new Rectangle(x,y,ship.getWidth()-15,ship.getHeight()-15);
        shot = Gdx.audio.newSound(Gdx.files.internal("Ship Laser.mp3"));
        SoundEffectVol = 0.5f;

    }

    public void update(float dt){
        velocity.add(0,GRAVITY,0);
        velocity.scl(dt);
        position.add(velocity.x, 0, 0);

        if(position.x < 0)
            position.x = 0;
        if(position.x > MVA.WIDTH - ship.getTextureData().getWidth())
            position.x = MVA.WIDTH - ship.getTextureData().getWidth() ;
        velocity.scl(1/dt);


        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return ship;
    }

    public void moveLeft()
    {
        velocity.x = -200;
        shot.play(SoundEffectVol);
    }
    public void moveRight()
    {
        velocity.x = 200;
        shot.play(SoundEffectVol);
    }
    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setSoundVol(float vol)
    {
       SoundEffectVol = vol;
    }

    public void dispose()
    {
        ship.dispose();
        System.out.println("Ship Disposed");
        shot.dispose();
    }

}

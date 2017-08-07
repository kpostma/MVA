package com.kpostma.mva.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by Postma on 8/30/2016.
 */
public class powerup{
    private Vector3 position;
    private Vector3 velocity;
    private static final int GRAVITY = -50;
    private Texture powerup;
    private int poweruptype;
    private Rectangle bounds;
    private Random rand;


    public powerup(float x, float y)
    {
        poweruptype = 1;

        switch(poweruptype)
        {
            case 1:
                //speed shooting
                powerup = new Texture("ship3.png");
                break;
        }
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,GRAVITY,0);
        bounds = new Rectangle(position.x,position.y, powerup.getWidth(),powerup.getHeight());

    }

    public void update(float dt)
    {
        velocity.scl(dt);
        position.add(0,velocity.y,0);
        if(position.y < 0)
        {
            dispose();
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y );
    }

    public Vector3 getPosition(){return position;}
    public Texture getTexture(){return powerup;}
    public int getPoweruptype(){return poweruptype;}
    public Rectangle getBounds(){return bounds;}

    public boolean collides(Rectangle player){return  player.overlaps(bounds);}


    public void dispose() {
        powerup.dispose();
        System.out.println("Powerup Disposed");
    }

}

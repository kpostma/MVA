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
    private int ranint;

    public powerup(float x, float y)
    {
        rand = new Random();
        poweruptype = rand.nextInt((5-1)+1)+1;

        switch(poweruptype)
        {
            case 1:
                //speed shooting
                powerup = new Texture("pu1.png");
                break;
            case 2:
                //pen shot
                powerup = new Texture("pu2.png");
                break;
            case 3:
                //nuke
                powerup = new Texture("pu3.png");
                break;
            case 4:
                //bonus points
                powerup = new Texture("pu4.png");
                break;
            case 5:
                //point multiplier
                powerup = new Texture("pu5.png");
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

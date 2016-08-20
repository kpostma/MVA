package com.kpostma.mva.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kpostma.mva.MVA;

/**
 * Created by Postma on 8/17/2016.
 */
public class Shot {
    private static final int speed = 10;
    private Vector3 velocity;
    private Vector3 position;
    private Texture shot;
    private Rectangle bounds;

    public Shot(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(x,speed,0);
        shot = new Texture("shot.png");
        bounds = new Rectangle(x,y,shot.getWidth(),shot.getHeight());

    }

    public void update(float dt){

        position.add(0,velocity.y, 0);
        bounds.setPosition(position.x,position.y);
    }


    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return shot;
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public void dispose()
    {
        shot.dispose();
        System.out.println("Shot disposed");
    }

}

package com.kpostma.mva.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kpostma.mva.MVA;
import com.kpostma.mva.states.PlayState;

import java.util.Random;

/**
 * Created by Postma on 8/12/2016.
 */
public class smallAstroid {
    private static final int GRAVITY = -50;
    private Vector3 position;
    private Vector3 velocity;
    private Random rand;
    private Texture astroid;
    private boolean leftorrightswitch;
    private int switchnumber;
    private Rectangle bounds;

    public smallAstroid(float x , float y)
    {
        rand = new Random();
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,(rand.nextInt(25)* -2) - 20,0);
        switchnumber = rand.nextInt(10);
        if(switchnumber % 2 ==0)
            leftorrightswitch = true;
        else
            leftorrightswitch = false;

        astroid = new Texture("smallAstroid.png");


        if(leftorrightswitch)
            velocity.x = rand.nextInt(25)* -2;
        else
            velocity.x = rand.nextInt(50);

        bounds = new Rectangle(position.x + 5 , position.y + 5  , astroid.getWidth()-5, astroid.getHeight()-15);
    }

    public void update(float dt)
    {

        velocity.scl(dt);
        position.add(velocity.x,velocity.y,0);
        if(position.x == 0)
            velocity.x = 15;
        else if(position.x == MVA.WIDTH)
            velocity.x = -15;


        velocity.scl(1/dt);

        bounds.setPosition(position.x  , position.y );

    }
    public void bounce(){
        if(position.x < -5 )
            velocity.x = 15;
        else if(position.x >(MVA.WIDTH - astroid.getWidth() -5))
            velocity.x = -15;

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return astroid;
    }

    public void reset() {
        position.x = rand.nextInt(MVA.WIDTH);
        position.y = 5 + rand.nextInt(20) + (MVA.HEIGHT);
        velocity.y = velocity.y - 20;
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void dispose()
    {
        astroid.dispose();
        System.out.println("Astroid Disposed");
    }

    public boolean astroidRespawn()
    {
        if(rand.nextInt(100) < 76 )
        {
            return true;
        }
        else
            return false;
    }

}

package com.kpostma.mva.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Postma on 8/12/2016.
 */
public class GameStateManager {

    private  Stack<State> states;
    private boolean PriorityState;
    private int HighScore;

    public GameStateManager(){
        states = new Stack<State>();
    }
    public void push(State state){
        states.push(state);
    }
    public void push(State state, boolean pstate){
        states.push(state); PriorityState = pstate;
    }
    public  void pop(){

        states.pop().dispose();
    }
    public  void pop(boolean pstate){
        PriorityState = pstate;
        states.pop().dispose();
    }
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }
    public void update(float dt)
    {
        states.peek().update(dt);
    }
    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }
    public boolean getPstate(){return PriorityState;}
    public void setPState(boolean pState){ PriorityState = pState;}
    public int getHighScore(){return HighScore;}
    public void setHighScore(int hs){HighScore = hs;}
}

package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Postma on 8/12/2016.
 */
public class GameStateManager {

    private  Stack<State> states;
    private boolean PriorityState;
    private int CurrentScore;

    public Save mySave;


    private float EffectVolume;
    private float MusicVolume;
    private Music music;


    public GameStateManager(){
        mySave = new Save();
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


    public void StartMusic(){
        music = Gdx.audio.newMusic(Gdx.files.internal("App Score.mp3"));
        music.setLooping(true);
        music.setVolume(getMusicVol());
        music.play();
    }

    public void MusicVolumeChange(){
        music.setVolume(getMusicVol());
    }

    public void setCurrentScore(int score)
    {
        CurrentScore = score;
        mySave.gd.addHighScore((long)CurrentScore , "YOU");
    }
    public int getCurrentScore(){
        return CurrentScore;
    }

    public float getMusicVol(){
        if(MusicVolume <= 1.1f && MusicVolume >= 0.0f) {
           return MusicVolume;
        }
        else {
            MusicVolume = 0.5f;
            return MusicVolume;
        }
    }

    public void setMusicVolume(float vol){MusicVolume = vol;}

    public void MusicVolumeUp(){if(MusicVolume <= 1.1f) MusicVolume += 0.1f;}
    public void MusicVolumeDown(){if(MusicVolume >= 0.1f) MusicVolume -= 0.1f;}

    public float getEffectVolume(){
        if(EffectVolume <= 1.0f && EffectVolume >=0.0f) {
            return EffectVolume;
        }
        else {
            EffectVolume = 0.5f;
            return EffectVolume;
        }
    }

    public void loadmySave(){
        mySave.gd = Save.LoadMySaveScores(mySave.gd);
    }

    public void SavemySave(){
        Save.save(mySave.gd);
    }

    public void setEffectVolume(float vol){EffectVolume = vol;}
    public void EffectVolumeUp(){if(EffectVolume <= 1.0f) EffectVolume += 0.1f;}
    public void EffectVolumeDown(){if(EffectVolume >= 0.1f) EffectVolume -= 0.1f;}

}

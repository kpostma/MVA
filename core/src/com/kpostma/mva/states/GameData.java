package com.kpostma.mva.states;

import java.io.Serializable;

/**
 * Created by Postma on 8/3/2017.
 */
public class GameData implements Serializable{

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 10;
    private final long[] highscores;
    private String[] names;

    private long tentativeScore;

    public GameData(){
        highscores = new long[MAX_SCORES];
        names = new String[MAX_SCORES];
    }

    //sets
    public void init() {
        highscores[0] = 1000000;
        names[0] = "Kevin";
        highscores[1] = 500000;
        names[1] = "Matt";
        highscores[2] = 250000;
        names[2] = "Alex";
        highscores[3] = 100000;
        names[3] = "Kyle";
        highscores[4] = 75000;
        names[4] = "Laura";
        highscores[5] = 50000;
        names[5] = "Kim";
        highscores[6] = 25000;
        names[6] = "Kayla";
        highscores[7] = 10000;
        names[7] = "Tony";
        highscores[8] = 5000;
        names[8] = "Tim";
        highscores[9] = 0;
        names[9] = "YOU";

    }

    public long[] getHighscores(){return  highscores;}
    public String[] getNames(){return names;}

    public long getTentativeScore() {return tentativeScore;}
    public void setTentativeScore(int i) {tentativeScore = i;}

    public boolean isHighScore(long score){
        return score > highscores[MAX_SCORES - 1];
    }

    public void addHighScore(long newScore, String name){
       for(int i = 0; i< highscores.length; i++)
       {
           if(names[i] == "YOU")
           {
               if(highscores[i] < newScore)
               {
                   highscores[i] = newScore;
               }
           }
       }
        sortHighScores();
    }

    public void sortHighScores(){
        for(int i = 0 ; i < MAX_SCORES; i++)
        {
            long score = highscores[i];
            String name = names[i];
            int t;
            for(t = i - 1;t >= 0 && highscores[t] < score; t++) {
             highscores[t+1] = highscores[t];
             names[t+1] = names[t];
            }
            highscores[t+1] = score;
            names[t+1] = name;
        }
    }

}

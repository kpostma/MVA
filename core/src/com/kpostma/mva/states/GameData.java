package com.kpostma.mva.states;

import java.io.Serializable;

/**
 * Created by Postma on 8/3/2017.
 */
public class GameData implements Serializable{

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 10;
    public final long[] highscores;
    public String[] names;

    public GameData(){
        highscores = new long[MAX_SCORES];
        names = new String[MAX_SCORES];
    }

    //sets
    public void init() {
        if(names[0] == null) {
            highscores[0] = 1000000;
            names[0] = "Kevin";
            highscores[1] = 500000;
            names[1] = "Matt";
            highscores[2] = 250000;
            names[2] = "Alex";
            highscores[3] = 150000;
            names[3] = "Kyle";
            highscores[4] = 100000;
            names[4] = "Kayla";
            highscores[5] = 75000;
            names[5] = "Kiersten";
            highscores[6] = 50000;
            names[6] = "Angela";
            highscores[7] = 25000;
            names[7] = "Dan";
            highscores[8] = 10000;
            names[8] = "Mike";
            highscores[9] = 0;
            names[9] = "YOU";
        }
    }

    public long[] getHighscores(){return  highscores;}

    public String[] getNames(){return names;}

    public void addHighScore(long newScore, String name){
       for(int i = 0; i< highscores.length; i++)
       {
           if(names[i] == "YOU")
           {
               if(highscores[i] < newScore)
               {
                   highscores[i] = newScore;
                   int t = i;

                   while(highscores[t] > highscores[t-1])
                   {
                       long tempScore = highscores[t];
                       highscores[t] = highscores[t-1];
                       highscores[t-1] = tempScore;
                       names[t] = names[t-1];
                       names[t-1] = "YOU";
                       if(t > 1)
                        {t--;}
                       else
                        {break;}
                   }
               }
           }
       }
    }


}

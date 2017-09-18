package com.kpostma.mva.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kpostma.mva.states.GameData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Postma on 8/3/2017.
 */
public class Save {
    public static GameData gd;

    //if there is no save
    public Save() {
    }

    public static void save(GameData mygd){
        try{
            String[] names;
            String str = "";
            long[] highscores;
            long yourHighscore;

            highscores = mygd.getHighscores();
            names = mygd.getNames();

            for(int i = 0; i< highscores.length; i++)
            {
                if(names[i] == "YOU")
                {
                    yourHighscore = highscores[i];

                    FileHandle file = Gdx.files.local("hs.txt");
                    file.writeString( str.valueOf(yourHighscore), false);
                    System.out.print("File saved!!!");
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static GameData LoadMySaveScores(GameData mygd){
        try{
            Long temp = null;

            if(mygd == null)
            {
                mygd = new GameData();
                mygd.init();
            }
            if(Gdx.files.local("hs.txt").exists())
            {
                System.out.print("File FOUND!!!");
                FileHandle file = Gdx.files.local("hs.txt");
                String text = file.readString();
                temp = temp.valueOf(text);

                String[] names;
                long[] highscores;
                highscores = mygd.getHighscores();
                names = mygd.getNames();
                for(int i = 0; i< highscores.length; i++)
                {
                    if(names[i] == "YOU")
                    {
                        if(temp > highscores[i])
                        {
                            highscores[i] = temp;
                        }
                    }
                }

            }
            else
            {
                gd = new GameData();
                gd.init();
            }

        }
            catch (Exception e)
        {
            e.printStackTrace();
            Gdx.app.exit();
        }
        return mygd;
    }


}

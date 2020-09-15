package com.example.oddkeys.cointoss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;
import java.util.Locale;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    private static final String STREAK_KEY="STREAK";
    private static final String CURRENTSCORE_KEY="CURRENT_SCORE";
    private static final String HIGHSCORE_KEY="HIGHSCORE";
    private static final String WINLOSS_KEY="WIN";
    private static final String TEMP_HIGHSCORE_KEY="TEMP_HIGHSCORE";
    private static final String OUTP_KEY="OUTP";
    private static final String LANG="LANGUAGE";
    private String streak="";
    private int currentScore=0;
    private int highScore=0;
    private boolean win=false;
    private String msg="";
    String current= Locale.getDefault().getLanguage();
    String language= Locale.getDefault().getLanguage();

    private String outp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            streak = savedInstanceState.getString(STREAK_KEY);
            highScore = savedInstanceState.getInt(HIGHSCORE_KEY);
            currentScore = savedInstanceState.getInt(CURRENTSCORE_KEY);
            win=savedInstanceState.getBoolean(WINLOSS_KEY);
            outp=savedInstanceState.getString(OUTP_KEY);
            language=savedInstanceState.getString(LANG);
            current=Locale.getDefault().getLanguage();

            if(win==true)
                currentScore-=1;//μη ξαναμετρησουμε το ++ στην updateScreen()
            if (!current.equals(language))
            {
                if (current.equals("en"))
                    changeStreak(streak,0);
                else
                    if (current.equals("el"))
                        changeStreak(streak,1);
            }
            updateScreen();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(HIGHSCORE_KEY,highScore);
        bundle.putString(STREAK_KEY,streak);
        bundle.putInt(CURRENTSCORE_KEY,currentScore);
        bundle.putBoolean(WINLOSS_KEY,win);
        bundle.putString(OUTP_KEY,outp);
        bundle.putString(LANG,language);
       // bundle.putInt(TEMP_HIGHSCORE_KEY,tempHighScore);

    }
    public void changeStreak(String streaksa,int a)//an exeis winstreak kai allakseis glwssa tote de swzetai swsta
    {

        int i;
        String newStreak;
        if (a==1) {
            if (!streaksa.isEmpty()) {
                newStreak=streaksa.replaceAll("H", "Κ");
                newStreak=newStreak.replaceAll("T", "Γ");
                streak=newStreak;
            }
            if(outp.equals("head"))
                outp="Κορώνα";
            else
                if (outp.equals("tails"))
                    outp="Γράμματα";
        }
        else
            if (a==0) {
                if (!streaksa.isEmpty()) {

                    newStreak=streaksa.replaceAll("Κ", "H");
                    newStreak=newStreak.replaceAll("Γ", "T");
                    streak=newStreak;
                }
                if(outp.equals("Κορώνα"))
                    outp="head";
                else
                if (outp.equals("Γράμματα"))
                    outp="tails";
            }

    }
    public void cointoss(View view)
    {
        Log.v("MainActivity",(String) current);
        //Log.v("MainActivity", "arxh coin toss");
        String choice="heads";
        //current=Locale.getDefault().getLanguage();
        if (view.getId() == R.id.head)
            choice = "heads";
        if(view.getId()== R.id.tails)
            choice="tails";

        Log.v("MainActivity", "mesh coin toss");
        Random rand=new Random();
        int x=rand.nextInt(2);
        Log.v("MainActivity",Integer.toString(x));
        if (x==0) {

            if (current.equals("el"))
                outp="Κορώνα";
            else {
                if (current.equals("en"))
                    outp = "head";
            }
            if (choice.equals("heads")) {
                if (current.equals("en"))
                    streak = streak + "H";
                else {
                    if (current.equals("el"))
                        streak = streak + "Κ";
                }

                win = true;
                updateScreen();
            } else if (choice.equals("tails")) {
                win = false;
                updateScreen();
            }
        }
        else if(x==1)
        {
            if (current.equals("el"))
                outp="Γράμματα";
            else {
                if (current.equals("en"))
                    outp = "tails";
            }
            if (choice.equals("tails")) {
                if (current.equals("en"))
                    streak = streak + "T";
                else {
                    if (language.equals("el"))
                        streak = streak + "Γ";
                }

                win = true;
                updateScreen();
            } else if (choice.equals("heads")) {
                win = false;
                updateScreen();
            }
        }



    }

    private void updateScreen()
    {
        Log.v("MainActivity", "arxh updatescreen me " +msg);
        TextView viewStreak=(TextView) findViewById(R.id.streak);
        TextView viewHighScore= (TextView) findViewById((R.id.highScore));
        TextView viewCurrentScore = (TextView) findViewById(R.id.currentScore);
        TextView viewWinLoss = (TextView) findViewById(R.id.result);
        current = Locale.getDefault().getLanguage();
        if (win) {
            if (current.equals("el"))
                msg="Κέρδισες";
            else {
                if (current.equals("en"))
                    msg = "won";
            }
            currentScore++;

            if(currentScore>highScore)//το ισον το βαλαμε για να καλυπτει το highscore save Instance
            {
                highScore=currentScore;
            }
        }
        else
        {if (current.equals("el"))
            msg="Έχασες";
        else {
            if (current.equals("en"))
                msg = "lost";
        }
            currentScore=0;
            streak="";
        }
        //current = Locale.getDefault().getLanguage();
        if (current.equals("en"))
            viewWinLoss.setText("The result was " + outp + " and you "+msg);
        else {
            if (current.equals("el"))
                viewWinLoss.setText("Το αποτέλεσμα ήταν " + outp + " και " + msg);
        }
        language = Locale.getDefault().getLanguage();
        viewStreak.setText(streak);
        viewCurrentScore.setText(Integer.toString(currentScore));
        viewHighScore.setText(Integer.toString(highScore));
        Log.v("MainActivity", "telos updatescreen");

    }


}

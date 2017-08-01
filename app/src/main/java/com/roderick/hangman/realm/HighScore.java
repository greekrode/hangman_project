package com.roderick.hangman.realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.roderick.hangman.R;
import com.roderick.hangman.model.Score;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by roder on 7/31/2017.
 */

public class HighScore extends AppCompatActivity {
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);
    }

    public void viewScore(){
        RealmResults<Score> results = realm.where(Score.class).findAll();

        for (Score score : results){

        }
    }
}

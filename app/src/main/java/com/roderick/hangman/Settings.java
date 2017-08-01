package com.roderick.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

/**
 * Created by roder on 8/1/2017.
 */

public class Settings extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        changeDifficulty();
    }

    public void changeDifficulty(){
        RadioButton button_easy = (RadioButton)findViewById(R.id.easy);
        RadioButton button_medium = (RadioButton)findViewById(R.id.medium);
        RadioButton button_hard = (RadioButton)findViewById(R.id.hard);

        if (button_easy.isChecked()){
            Intent intent = new Intent(getBaseContext(), PlayGame.class);
            String difficulty = "easy";
            intent.putExtra("Difficulty",difficulty);
            startActivity(intent);
        } else if (button_medium.isChecked()){
            Intent intent = new Intent(getBaseContext(), PlayGame.class);
            String difficulty = "medium";
            intent.putExtra("Difficulty",difficulty);
            startActivity(intent);
        } else if (button_hard.isChecked()){
            Intent intent = new Intent(getBaseContext(), PlayGame.class);
            String difficulty = "hard";
            intent.putExtra("Difficulty",difficulty);
            startActivity(intent);
        }
    }
}

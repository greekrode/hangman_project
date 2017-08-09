package com.roderick.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        Button back = (Button) findViewById(R.id.button3);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        // Inflate the layout for this fragment
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if(checkedId == R.id.radioButtonEasy){
                    PlayGame.Difficulty = "Easy";
                }else if(checkedId==R.id.radioButtonNormal){
                    PlayGame.Difficulty = "Normal";
                }else if(checkedId==R.id.radioButtonHard){
                    PlayGame.Difficulty = "Hard";
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
//                getActivity().getFragmentManager().popBackStack("e",
//                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //  getActivity().getSupportFragmentManager().popBackStackImmediate("Frag1",0);
            }
        });
    }
}

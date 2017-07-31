package com.roderick.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by roder on 7/31/2017.
 */

public class EnterPlayerName extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_name);

        Button sbmt_btn = (Button) findViewById(R.id.sbmt_btn);
        sbmt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.name);
                String words = text.getText().toString();
                Intent intent = new Intent(getBaseContext(), PlayGame.class);
                intent.putExtra("Text", words);
                startActivity(intent);
            }
        });
    }
}

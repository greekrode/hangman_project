package com.roderick.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by roder on 7/27/2017.
 */

public class MultiplayerGuess extends AppCompatActivity {
    public ArrayList<Button> buttonList = new ArrayList<Button>();
    public ArrayList<String> ansWords= new ArrayList<String>();
    public String guessWord = "";
    public int chanceTry = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        getWord();
    }

    public void getWord() {
        Intent intent = getIntent();
        guessWord = intent.getStringExtra("Text");
        generateWords(guessWord);
        try {
            generateAnswers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void generateWords(String s){
        String[] arrayWords = s.split("");
        int a = 20;
        //assign id ke textview , mark as _ first
        for(String r:arrayWords) {
            Log.d(r,"hehe");
            if (r.equals("")) {
            } else {
                TextView tView = new TextView(this);
                tView.setText("_");
                tView.setId(++a);
                Log.d("Current id =  "+a,"coba");
                LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
                int dp = 10;
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
                tView.setLayoutParams(new ViewGroup.LayoutParams(
                        2 * px,
                        3 * px));
                ll.addView(tView);
            }
        }
    }
    int i = 100; // di depan biar bisa cek benar salah
    public void generateAnswers() throws InterruptedException {
        String[] arrayAns = guessWord.split("");
        for(final String s : arrayAns) {

            if (s.equals("")) {
            }
            else {
                if (ansWords.contains(s)) {
                } else {
                    String currWord = s;
                    //create button for the answer
                    final Button buttonAns = new Button(this);
                    buttonAns.setText(s);
                    //assign id
                    buttonAns.setId(i + 1);
                    //generateUniqueId();
                    final int index = i;
                    //for onclicklistener
                    buttonAns.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonAns.setEnabled(false);
                            Answer(s);
                        }
                    });
                    buttonList.add(buttonAns); // masukkan ke list
                    ansWords.add(currWord);
                }
            }
        }
        //generating wrong answer button
        int besaran = buttonList.size();
        for(int a = 0;a < 14-besaran;a++){
            boolean ulang = true;
            while(ulang) {
                ulang = true;
                Random r = new Random();
                //randoming from A-Z
                Thread.sleep(10);
                char c = (char) (r.nextInt(26) + 'A');
                final String testMe = String.valueOf(c);
                if (ansWords.contains(testMe)) {
                    ulang = true;
                    Log.d("hello", testMe);
                    } else {
                    ansWords.add(testMe);
                    //belum ada char tersebut, generate the button and add to list
                    ulang = false;
                    //create button for the answer
                    final Button buttonAns = new Button(this);
                    buttonAns.setText(testMe);
                    //assign id
                    buttonAns.setId(i+buttonList.size());//100+size dari list yang sudah dibuat (correct answers)
                    //generateUniqueId();
                    final int index = i;
                    buttonAns.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonAns.setEnabled(false);
                            //hajar ke kata yang ada
                            Answer(testMe);
                        }
                    });
                    buttonList.add(buttonAns); // masukkan ke list
                }
            }
            ulang= true;
        }
        //https://stackoverflow.com/questions/17916803/shuffle-button-with-position-android
        LinearLayout bagianAtas = (LinearLayout)findViewById(R.id.linearAtas);
        LinearLayout bagianBawah = (LinearLayout)findViewById(R.id.linearBawah);
        LinearLayout box = (LinearLayout)findViewById(R.id.shuffleBox);
        int dp = 25;
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(2*px,3*px);
        //shuffle buttons !
        Collections.shuffle(buttonList);
        for(int j = 0;j<7;j++){
            buttonList.get(j).setLayoutParams(layoutParams);
            bagianAtas.addView(buttonList.get(j));
        }
        for(int j = 7;j<14;j++){
            buttonList.get(j).setLayoutParams(layoutParams);
            bagianBawah.addView(buttonList.get(j));
        }


    }
    public void Answer(String s){
        int kena = 0;
        for(int i = 0;i<guessWord.length();i++){
            if(s.equals(String.valueOf(guessWord.charAt(i)))){
                TextView textAns = (TextView)findViewById(i+21);
                textAns.setText(s);
                kena++;
                checkWinCondition();
            }else{
            }
        }
        if(kena ==0) {
            chanceTry--;
            changeImage();
            Log.d("Chance try = " + String.valueOf(chanceTry), "bugggg");
        }
    }
    public void changeImage(){
        ImageView imgView = (ImageView) findViewById(R.id.imageView11);
        switch(chanceTry){

            case 10: imgView.setImageResource(R.drawable.pic1);
                break;
            case 9: imgView.setImageResource(R.drawable.pic2);
                break;
            case 8: imgView.setImageResource(R.drawable.pic3);
                break;
            case 7: imgView.setImageResource(R.drawable.pic4);
                break;
            case 6: imgView.setImageResource(R.drawable.pic5);
                break;
            case 5 : imgView.setImageResource(R.drawable.pic6);
                break;
            case 4: imgView.setImageResource(R.drawable.pic7);
                break;
            case 3: imgView.setImageResource(R.drawable.pic8);
                break;
            case 2: imgView.setImageResource(R.drawable.pic9);
                break;
            case 1: imgView.setImageResource(R.drawable.pic10);
                break;
            case 0:imgView.setImageResource(R.drawable.pic11);
                //show game over screen!
                gameOver();
                break;
        }
    }
    public void checkWinCondition(){
        int winValue = 0;
        for(int i = 0;i<guessWord.length();i++){
            TextView checkThis = (TextView)findViewById(i+21);
            String wordsToCheck = checkThis.getText().toString();
            if(wordsToCheck.equals(ansWords.get(i))){
                winValue++;
            }
        }
        if(winValue == guessWord.length()){
            Bundle bundle = new Bundle();
            bundle.putString("Status","Win");
            bundle.putString("Chance",String.valueOf(chanceTry));
            bundle.putString("Mode","Multiplayer");
            EndGameFragment fragment = new EndGameFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(
                    R.id.linearLayoutMain, fragment
                    ,EndGameFragment.class.getSimpleName())
                    .commit();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.linearLayoutMain, fragment,
                    EndGameFragment.class.getSimpleName()).commit();

            Log.d("Menang ! ","hello");
        }
    }

    public void gameOver(){
        Bundle bundle = new Bundle();
        bundle.putString("Status","Lose");
        bundle.putString("Chance",String.valueOf(chanceTry));
        bundle.putString("Mode","Multiplayer");
        EndGameFragment fragment = new EndGameFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(
                R.id.linearLayoutMain, fragment
                ,EndGameFragment.class.getSimpleName())
                .commit();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.linearLayoutMain, fragment,
                EndGameFragment.class.getSimpleName()).commit();
    }
    public void reset(){
        chanceTry = 10;
        guessWord = "";
        ansWords.clear();
        buttonList.clear();
        getWord();
    }


}

package com.roderick.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roderick.hangman.model.Score;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class PlayGame extends AppCompatActivity {
    public String[] words =  {"TEST","COBA","KURAKURA"};
    public ArrayList<Button> buttonList = new ArrayList<Button>();
    public ArrayList<String> ansWords= new ArrayList<String>();
    public String guessWord = "";
    public int chanceTry = 5;
    //default value = easy
    public static String Difficulty = "Easy";
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        Intent intent = getIntent();
        name = intent.getStringExtra("Text");
        getWord();
    }
    //textview starts from 21
    //buttons starts from 100
    //get what word randomed
    public void getWord() {
//        if(Difficulty.equalsIgnoreCase("Easy")){
//
//        }else if(Difficulty.equalsIgnoreCase("Normal")){
//
//        }else{
//
//        }
        Random rnd = ThreadLocalRandom.current();
        for (int i = words.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = words[index];
            words[index] = words[i];
            words[i] = a;
        }
        //take the first word to be guessed
        guessWord = words[0];
        generateWords(guessWord);
        try {
            generateAnswers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void generateWords(String s){
        //lihat panjang katanya, buat textview
        String[] arrayWords = guessWord.split("");
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
        //split the words into something and then do something
        String[] arrayAns = guessWord.split("");
        //or you can just loop it.. dunno
        for(final String s : arrayAns) {
            if (s.equals("")) {
            }
            else {
                if (ansWords.contains(s)) {
                    //kalo sudah ada kata yang sama, abaikan pembuatan buttonnya !
                    ansWords.add(s);
                } else {
                    //buat kata baru, terus ditambahin ke list button biar dishuffle ntar
                    ansWords.add(s);
                    Log.d("String :  "+s,"");
                    String currWord = s;
                    //create button for the answer
                    final Button buttonAns = new Button(this);
                    buttonAns.setText(s);
                    //assign id
                    buttonAns.setId(i + 1);
                    //100 dst
                    //generateUniqueId();
                    final int index = i;
                    //for onclicklistener
                    buttonAns.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonAns.setEnabled(false);
                            //hajar ke kata yang ada
                            Answer(s);
                        }
                    });
                    buttonList.add(buttonAns); // masukkan ke list

                }


            }

        }
        Log.d("banyak button " +String.valueOf(buttonList.size()),"LUL");
        //14 buttons
        int besaran = buttonList.size();
        for(int a = 0;a < 14-besaran;a++){
            boolean ulang = true;
            while(ulang) {
                Random r = new Random();
                //randoming from A-Z
                Thread.sleep(10);
                char c = (char) (r.nextInt(26) + 'A');
                final String testMe = String.valueOf(c);
                //kalo sudah ada, repeat the randomization
                if (ansWords.contains(testMe)) {} else {
                    ulang = false;
                    //create button for the answer
                    final Button buttonAns = new Button(this);
                    buttonAns.setText(testMe);
                    //assign id
                    buttonAns.setId(i+buttonList.size());//100+size dari list yang sudah dibuat (correct answers)
                    //generateUniqueId();
                    final int index = i;
                    //for onclicklistener
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
        //tambah guess try
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
        if (winValue == guessWord.length()) {
            Log.d("asdasd","Win ");
            Bundle bundle = new Bundle();
            bundle.putString("Status", "Win");
            bundle.putString("Chance", String.valueOf(chanceTry));
            bundle.putString("Mode", "Singleplayer");
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
    }
    public void gameOver(){
        Bundle bundle = new Bundle();
        bundle.putString("Status","Lose");
        bundle.putString("Chance",String.valueOf(chanceTry));
        bundle.putString("Mode","Singleplayer");
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
// String regex = "(?<=(.))(?=\\1)";
// look behind (?<=))
// look ahead (?=)).
// is the same (\\1);

//    int id = 1;
//    // Generates and returns a valid id that's not in use
//    public int generateUniqueId(){
//        View v = findViewById(id);
//        while (v != null){
//            v = findViewById(++id);
//        }
//        return id++;
//    }

    public int getGuessWordLength(){
        return guessWord.length();
    }

}

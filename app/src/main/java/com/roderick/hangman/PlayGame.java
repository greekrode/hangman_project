package com.roderick.hangman;

/**
 * Created by Agumon on 7/10/2017.
 */

import android.media.Image;
import android.support.v7.app.ActionBar;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayGame extends AppCompatActivity {
    public String[] words =  {"TEST","COBA","KURAKURA"};
    //collection (?), learn mapping
    // Create an ArrayList to hold the Button objects that we will create
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
    //textview starts from 21
    //buttons starts from 100
    //get what word randomed
    public void getWord() {
        //pick 1 word from array randomly (shuffle then take 1 ?) / or just take 1 randomly lul
        Random rnd = ThreadLocalRandom.current();
        for (int i = words.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = words[index];
            words[index] = words[i];
            words[i] = a;
            //hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh??
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
                tView.setId(++a); //21 dan seterusnya
                //get a better way to do this pls
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
    //generate button for random words (with some predetermined words)
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
        //        Log.d("Current turn : "+String.valueOf(a),"cacca");
                Random r = new Random();
                //randoming from A-Z
                Thread.sleep(10);
                char c = (char) (r.nextInt(26) + 'A');
                final String testMe = String.valueOf(c);
                //kalo sudah ada, repeat the randomization
                Log.d("Generated char = " +testMe," xD");
                if (ansWords.contains(testMe)) {} else {
                    //belum ada char tersebut, generate the button and add to list
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
            ulang= true;
        }
      //  Log.d("banyak button " +String.valueOf(buttonList.size()),"LUL");
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
            //check kena atau tidak
            if(s.equals(String.valueOf(guessWord.charAt(i)))){
                //kena, reveal huruf nya
                TextView textAns = (TextView)findViewById(i+21);
                textAns.setText(s);
                kena++;
                checkWinCondition();
            }else{

            }
            //tidak kena , kurangi chance, ganti image
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
            case 5 :imgView.setImageResource(R.drawable.pic6);
                break;
            case 4: imgView.setImageResource(R.drawable.pic7);
                break;
            case 3: imgView.setImageResource(R.drawable.pic8);
                break;
            case 2: imgView.setImageResource(R.drawable.pic9);
                break;
            case 1: imgView.setImageResource(R.drawable.pic10);
                break;
            case 0:imgView.setBackgroundResource(R.drawable.pic11);
                //show game over screen!
                gameOver();
                break;
        }
    }
    public void checkWinCondition(){
        int winValue = 0;
        //check semua textview, bila sudah terlihat semua maka lakukan sesuatu
        for(int i = 0;i<guessWord.length();i++){
            TextView checkThis = (TextView)findViewById(i+21);
            String wordsToCheck = checkThis.getText().toString();
            if(wordsToCheck.equals(ansWords.get(i))){
                winValue++;
            }
        }
        if(winValue == guessWord.length()){
            //menang \o/
        }
    }
    public void gameOver(){
        //send gameover screen here
        //send data too?
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
    //end of file
}

package com.roderick.hangman;


import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    public String[] words =  {"T E S T","C O B A","K U R A K U R A"};
    //collection (?), learn mapping
    // Create an ArrayList to hold the Button objects that we will create
    public ArrayList<Button> buttonList = new ArrayList<Button>();
    public ArrayList<String> ansWords= new ArrayList<String>();
    public String guessWord = "";
    public int chanceTry = 10;
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
            //idk why do this tho~~~
        }
        //take the first word to be guessed
        guessWord = words[0];
        generateWords(guessWord);
        generateAnswers();
    }
    public void generateWords(String s){
        //lihat panjang katanya, buat textview
        String[] arrayWords = guessWord.split("");
        int a = 20;
        //assign id ke textview , mark as _ first
        for(String r:arrayWords){
            TextView tView = new TextView(this);
            tView.setText("_");
            tView.setId(a++); //21 dan seterusnya
            //get a better way to do this pls
            LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
            tView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(tView);
        }
    }
    //generate button for random words (with some predetermined words)
    int i = 100; // di depan biar bisa cek benar salah
    public void generateAnswers(){
        //split the words into something and then do something
        String[] arrayAns = guessWord.split("");
        //or you can just loop it.. dunno

        for(final String s : arrayAns) {
            if (ansWords.contains(s)) {
                //kalo sudah ada kata yang sama, abaikan pembuatan buttonnya !
            } else {
                //buat kata baru, terus ditambahin ke list button biar dishuffle ntar
                ansWords.add(s);
                String currWord = s;
                //create button for the answer
                final Button buttonAns = new Button(this);
                buttonAns.setText(s);
                //assign id
                buttonAns.setId(i+1);
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
        //max button is 14,welp !

        for(int a = 0;a <= 14-buttonList.size();a++){
            boolean ulang =true;
            while(ulang) {
                Random r = new Random();
                //randoming from A-Z
                char c = (char) (r.nextInt(26) + 'A');
                //kalo sudah ada, repeat the randomization
                if (ansWords.contains(c)) {} else {
                    //belum ada char tersebut, generate the button and add to list
                    ulang = false;
                    final String currWord = String.valueOf(c);
                    //create button for the answer
                    final Button buttonAns = new Button(this);
                    buttonAns.setText(currWord);
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
                            Answer(currWord);
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
        //shuffle buttons !
        Collections.shuffle(buttonList);
        for(int j = 0;j<7;j++){
            bagianAtas.addView(buttonList.get(j));
        }
        for(int j = 7;j<14;j++){
            bagianBawah.addView(buttonList.get(j));
        }
        box.addView(bagianAtas);
        box.addView(bagianBawah);

    }
    public void Answer(String s){
        //tambah guess try
        for(int i = 0;i<=guessWord.length();i++){
            //check kena atau tidak
            if(s.equals(guessWord.charAt(i))){
                //kena, reveal huruf nya
                TextView textAns = (TextView)findViewById(i+20);
                textAns.setText(s);
            }else{
                chanceTry--;
                changeImage();
                //tidak kena , kurangi chance, ganti image
            }
        }
    }
    public void changeImage(){
        ImageView imgView = (ImageView) findViewById(R.id.imageView11);
        switch(chanceTry){
            case 10: imgView.setBackgroundResource(R.drawable.pic1);
                break;
            case 9: imgView.setBackgroundResource(R.drawable.pic2);
                break;
            case 8: imgView.setBackgroundResource(R.drawable.pic3);
                break;
            case 7: imgView.setBackgroundResource(R.drawable.pic4);
                break;
            case 6: imgView.setBackgroundResource(R.drawable.pic5);
                break;
            case 5 : imgView.setBackgroundResource(R.drawable.pic6);
                break;
            case 4: imgView.setBackgroundResource(R.drawable.pic7);
                break;
            case 3: imgView.setBackgroundResource(R.drawable.pic8);
                break;
            case 2: imgView.setBackgroundResource(R.drawable.pic9);
                break;
            case 1: imgView.setBackgroundResource(R.drawable.pic10);
                break;
            case 0:imgView.setBackgroundResource(R.drawable.pic11);
                //show game over screen!
                break;
        }
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

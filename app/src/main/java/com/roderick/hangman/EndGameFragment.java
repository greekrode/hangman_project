package com.roderick.hangman;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.roderick.hangman.model.Score;

import io.realm.Realm;
import io.realm.RealmResults;

public class EndGameFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public int highscore;
    Realm realm;
    public String name;

    private OnFragmentInteractionListener mListener;

    public EndGameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EndGameFragment newInstance(String param1, String param2) {
        EndGameFragment fragment = new EndGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        name =  getArguments().getString("Name");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.retry_game,container,false);
        TextView status = (TextView)view.findViewById(R.id.textView2);
        //ganti value dari textview
        TextView score = (TextView) view.findViewById(R.id.score);
        //update score ke textview
        String strtext = getArguments().getString("Status");
        String strtextMode = getArguments().getString("Mode");
        if(strtext.equals("Win")){
            String times = getArguments().getString("Chance");
            Integer value1 = Integer.parseInt(times);
            value1 = value1 * 100;
            status.setText("You Win!");
            highscore = value1;
            addScore();
        }else{
            status.setText("You Lose!");
            score.setText("0");
        }
        Button resetButton = (Button)view.findViewById(R.id.retry);
        Button mainMenuButton = (Button)view.findViewById(R.id.back);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayGame.class);
                startActivity(intent);
            }
        });
        if(strtextMode.equals("Singleplayer")){}else{
            resetButton.setEnabled(false);
        }
        // Inflate the layout for this fragment
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void addScore(){
        realm.beginTransaction();

        Score score = realm.createObject(Score.class);
        score.setName(name);
        score.setScore(highscore);

        realm.commitTransaction();
    }

    public void updateScore(){
        RealmResults<Score> results = realm.where(Score.class).equalTo("name",name).findAll();

        realm.beginTransaction();

        for(Score score : results){
            score.setScore(highscore);
        }

        realm.commitTransaction();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}

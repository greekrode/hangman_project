package com.roderick.hangman.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by roder on 7/31/2017.
 */

@RealmClass
public class Text extends RealmObject {

    private String words;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}

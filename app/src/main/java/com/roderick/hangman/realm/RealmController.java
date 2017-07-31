package com.roderick.hangman.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.roderick.hangman.model.Score;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by roder on 7/31/2017.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //find all objects in the Book.class
    public RealmResults<Score> getScores() {

        return realm.where(Score.class).findAll();
    }

    //query a single item with the given id
    public Score getScorek(String id) {

        return realm.where(Score.class).equalTo("id", id).findFirst();
    }

    


}

package com.roderick.hangman;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.roderick.hangman.model.Score;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by roder on 8/7/2017.
 */


public class HighScore extends AppCompatActivity {
    Realm realm;

    private ListView lvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

//        lvItem = (ListView)findViewById(R.id.lv_item);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1, footballClubs);
//        lvItem.setAdapter(adapter);
//
//        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "You CLICK : " +footballClubs[position],
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        lvItem = (ListView)findViewById(R.id.lv_item);
        lvItem.setAdapter(new yourAdapter(this, new String[] {"data1","data2"}));
    }

    public void viewRecord(){
        RealmResults<Score> results = realm.where(Score.class).findAll();
    }
}

class yourAdapter extends BaseAdapter {
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;
    public yourAdapter (Context context, String[] data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return data.length;
    }

    @Override
    public Object getItem(int position){
        return data[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(data[position]);
        return vi;
    }

}

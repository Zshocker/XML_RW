package com.example.basedo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Songs_act extends AppCompatActivity {
    LinearLayout Lis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Lis=findViewById(R.id.Rect);
        Async S= new Async();
        Intent it=getIntent();
        String st=it.getStringExtra("lien");
        S.execute(st);
    }
    class Async extends AsyncTask<String,Void, List<Song_Data>>
    {

        @Override
        protected List<Song_Data> doInBackground(String... strings) {
            return new onlineFilesReader().Read_All_SONG_Data_XML(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Song_Data> song_data) {
            super.onPostExecute(song_data);
            for (Song_Data Data : song_data) {
                Add_Song(Data);
            }
        }
    }
    private void Add_Song(Song_Data Song)
    {
        Lis.addView(Prepare_View(Song));
    }
    private View Prepare_View(Song_Data data)
    {
        ViewGroup view=(ViewGroup) getLayoutInflater().inflate(R.layout.song_card,null);
        TextView Text=view.findViewById(R.id.SongName);
        Picasso.get().load(data.getImage()).resize(50,50).into((ImageView) view.findViewById(R.id.IView));
        Text.setText(data.getName());
        view.findViewById(R.id.Card_ma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(data.getHref()));
                startActivity(viewIntent);
            }
        });
        View Ce=view.findViewById(R.id.Card_ma);
        view.removeView(Ce);
        return Ce;
    }
}
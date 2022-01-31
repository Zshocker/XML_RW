package com.example.basedo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class Images_act extends AppCompatActivity {
    LinearLayout Lis;
    String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Lis=findViewById(R.id.Rect);
        Async S= new Async();
        Intent it=getIntent();
        search=it.getStringExtra("search");
        String id="egXeqq-QfTlrztEj7OC0d7p_yAMjSCuO_qitT-Z4oNw";
        String st="https://api.unsplash.com/search/photos/?query="+search+"&client_id="+id;
        S.execute(st);
    }
    class Async extends AsyncTask<String,Void, List<MyImage>>
    {

        @Override
        protected List<MyImage> doInBackground(String... strings) {
            return new onlineFilesReader().Read_Json_pics(strings[0]);
        }

        @Override
        protected void onPostExecute(List<MyImage> data) {
            super.onPostExecute(data);
            for (MyImage  Data : data) {
                Add_Song(Data);
            }
        }
    }
    private void Add_Song(MyImage Song)
    {
        Lis.addView(Prepare_View(Song));
    }
    private View Prepare_View(MyImage data)
    {
        ViewGroup view=(ViewGroup) getLayoutInflater().inflate(R.layout.image_card,null);
        TextView Text=view.findViewById(R.id.description);
        ImageView Image=view.findViewById(R.id.MyImage);
        String temp=data.getDescription();
        if(temp==null)temp=data.getAlt_description();
        if(temp==null)temp=search;
        Text.setText(temp);
        Picasso.get().load(data.getSmall_size_link()).into(Image);
        View Ce=view.findViewById(R.id.Card_ma);
        Ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(data.getNormalLink()));
                startActivity(viewIntent);
            }
        });
        view.removeView(Ce);
        return Ce;
    }
}
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

public class Weather_act extends AppCompatActivity {
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
    class Async extends AsyncTask<String,Void, List<Weather_day>>
    {

        @Override
        protected List<Weather_day> doInBackground(String... strings) {
            return new onlineFilesReader().Read_All_Weather_Data_JSON(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Weather_day> data) {
            super.onPostExecute(data);
            for (Weather_day Data : data) {
                Add_Song(Data);
            }
        }
    }
    public static String tronque(double x,int nbElem)
    {
        String str,prefix,suffix;
        StringTokenizer strs=new StringTokenizer(Double.toString(x),".");
        prefix=strs.nextToken();
        if(!strs.hasMoreTokens()||nbElem==0){
            return prefix;
        }
        str=prefix;
        suffix=strs.nextToken();
        if(suffix.length()>nbElem)suffix=suffix.substring(0,nbElem);
        str+="."+suffix;
        return str;
    }
    private void Add_Song(Weather_day Song)
    {
        Lis.addView(Prepare_View(Song));
    }
    private View Prepare_View(Weather_day data)
    {
        ViewGroup view=(ViewGroup) getLayoutInflater().inflate(R.layout.weather_card,null);
        TextView Text=view.findViewById(R.id.day);
        TextView Text2=view.findViewById(R.id.Temper);
        TextView Text3=view.findViewById(R.id.Wind);
        Text.setText(data.getDay());
        String temp=tronque(data.getTemp(), 2)+" "+Weather_day.UnitTemp;
        Text2.setText(temp);
        temp=tronque(data.getWindSpeed(), 2)+" "+Weather_day.UnitWind;
        Text3.setText(temp);
        View Ce=view.findViewById(R.id.Card_ma);
        view.removeView(Ce);
        return Ce;
    }
}
package com.example.basedo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Show_All_QA extends AppCompatActivity {
    XML_DB DATABase=new XML_DB("Datas.xml",'R');
    List<Data> dataList;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_qa);
        dataList=DATABase.Read_All_Data();
        RecyclerView lis=findViewById(R.id.List_items);
        CustomAdapter Adb=new CustomAdapter(dataList);
        lis.setAdapter(Adb);
        bt=findViewById(R.id.Returne);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }
    public void goBack(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
package com.example.basedo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    XML_DB Db=null;
    AlertDialog Dialg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);


        Create_Dialog();
        findViewById(R.id.GoInsc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialg.show();
            }
        });
        findViewById(R.id.GoToAllRes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ints=new Intent(getBaseContext(), Show_All_QA.class);
                startActivity(ints);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if(resultCode==RESULT_OK)Db=new XML_DB("Datas.xml",'W');
            else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    private void Create_Dialog(){
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        View vi=getLayoutInflater().inflate(R.layout.instr_dialog,null);
        build.setView(vi);
        EditText LastName=vi.findViewById(R.id.LastName);
        EditText FirstName=vi.findViewById(R.id.FirstName);
        build.setTitle("Enter your info").setPositiveButton("Write To XML", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String Fname= FirstName.getText().toString();
                String Lname=LastName.getText().toString();
                if(Fname.equals("")||Lname.equals("")){
                    Show_Error();
                }else {
                    insert_Direct(Fname,Lname);
                    LastName.setText("");
                    FirstName.setText("");
                }
            }
        }).setNegativeButton("Cancel",null);
        Dialg=build.create();
    }
    public void Show_Error(){
            Toast.makeText(this,"Please Provide the two information",Toast.LENGTH_LONG).show();
    }
    public void insert_Direct(String Fname,String Lname)
    {
        if(Db==null)Db=new XML_DB("Datas.xml",'A');
        Db.Write_DATA(new Data(Fname,Lname));
        Toast.makeText(this,"Inserted Data : Last Name "+Lname+" First Name :"+Fname,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        Db.End_Writing();
        super.onDestroy();
    }
}
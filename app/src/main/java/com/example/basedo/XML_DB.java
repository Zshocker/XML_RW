package com.example.basedo;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import androidx.core.app.ActivityCompat;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class XML_DB
{
    String xmlFile;
    FileOutputStream Strem;
    public XML_DB(String xmlFile) {
        this.xmlFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/XML_READ_WRITE/"+ xmlFile;
        try {

            Prepare_File();
        }catch (Exception E){

        }
    }
    private void Prepare_File()
    {
        File F = null;
        try {
            F=new File(xmlFile);
            if(!F.exists())F.createNewFile();
            Start_Writing();
        }
        catch (FileNotFoundException Ep)
        {
        } catch (IOException e) {
            Log.i(null, "Prepare_File: HELL");
            e.printStackTrace();
        }
    }
    private void Start_Writing(){
        try {
            Strem=new FileOutputStream(xmlFile,false);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.flush();
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));
            Strem.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Write_DATA(Data dat)
    {
        try {
            Strem=new FileOutputStream(xmlFile,true);
            StringWriter writer = new StringWriter();
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(writer);
            dat.ToXML(xmlSerializer);
            xmlSerializer.flush();
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));
            Strem.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void End_Writing(){
        StringWriter writer = new StringWriter();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            Strem=new FileOutputStream(xmlFile,true);
            xmlSerializer.setOutput(writer);
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));
            Strem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

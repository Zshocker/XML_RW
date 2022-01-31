package com.example.basedo;

import android.util.JsonReader;
import android.util.Log;
import android.util.MalformedJsonException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class onlineFilesReader
{
    URL uri;
    HttpURLConnection urlConnection=null;
    InputStream Inp=null;
    //Dom Parser
    public List<Song_Data> Read_All_SONG_Data_XML(String Ur)
    {
        List<Song_Data> FullBd=new ArrayList<Song_Data>();
        Song_Data DEE;
        String Image;
        Prepare_File(Ur);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder=factory.newDocumentBuilder();
            Document dom=documentBuilder.parse(Inp);
            Element root=dom.getDocumentElement();
            NodeList Datas=root.getElementsByTagName("entry");
            for (int i = 0; i < Datas.getLength(); i++) {
                Element BigDa=(Element) Datas.item(i);
                String title=BigDa.getElementsByTagName("title").item(0).getTextContent();
                if(title.length()>50){
                    title=BigDa.getElementsByTagName("im:name").item(0).getTextContent();
                }
                String Href=BigDa.getElementsByTagName("id").item(0).getTextContent();
                Node IMG=BigDa.getElementsByTagName("im:image").item(0);
                if(IMG!=null) Image=IMG.getTextContent();
                else Image="";
                DEE=new Song_Data(title,Href,Image);
                FullBd.add(DEE);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Stop_Reading();
        for (Song_Data Data : FullBd) {
            Log.i("He", "Read_All_Data: "+Data.toString());
        }
        return  FullBd;
    }

    private void Prepare_File(String ur)
    {

        try {
            uri=new URL(ur);
            if(Inp==null)
            {
                if(urlConnection==null)
                {
                    if(ur.contains("https"))urlConnection =(HttpsURLConnection)uri.openConnection();
                    else urlConnection=(HttpURLConnection) uri.openConnection();
                    urlConnection.setRequestMethod("GET");
                }
                int a=urlConnection.getResponseCode();
            Inp= urlConnection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Stop_Reading()
    {
        try
        {
            Inp.close();
            urlConnection.disconnect();
            urlConnection=null;
            Inp=null;
        }catch (Exception e){
        }
    }
    public List<Weather_day> Read_All_Weather_Data_JSON(String st)
    {
        List<Weather_day> Data=new ArrayList<Weather_day>();
        double temp=0,wind=0;
        String date = null;
        Prepare_File(st);
        JsonReader reader=new JsonReader(new InputStreamReader(Inp));
        try {
            reader.beginObject();
            while (reader.hasNext()){
            String tempo=reader.nextName();
            if(tempo.equals("consolidated_weather")){
                reader.beginArray();
                while (reader.hasNext()){
                    reader.beginObject();
                    while (reader.hasNext()) {
                        tempo = reader.nextName();
                        if (tempo.equals("applicable_date")) {
                            date = reader.nextString();
                        } else if (tempo.equals("the_temp")) {
                            temp = reader.nextDouble();
                        } else if (tempo.equals("wind_speed")) {
                            wind = reader.nextDouble();
                        } else {
                            reader.skipValue();
                        }
                    }
                    Data.add(new Weather_day(date,wind,temp));
                    reader.endObject();
                }
                reader.endArray();
            }else{
                reader.skipValue();
            }
            }
            reader.endObject();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stop_Reading();
        return  Data;
    }
    public List<MyImage> Read_Json_pics(String st)
    {
        List<MyImage> Data=new ArrayList<MyImage>();
        String description,alt_description,normalLink,small_size_link;
        Prepare_File(st);
        JsonReader reader = new JsonReader(new InputStreamReader(Inp));
        try {
            reader.beginObject();
            while (reader.hasNext()){
            String tempo=reader.nextName();
            if(tempo.equals("results")){
                reader.beginArray();
                while (reader.hasNext()){
                    description=null;
                    alt_description=null;
                    normalLink=null;
                    small_size_link=null;
                    reader.beginObject();
                    while (reader.hasNext()) {
                            tempo = reader.nextName();
                        if (tempo.equals("description")) {
                            try {
                                description = reader.nextString();
                            }catch (Exception e){
                                reader.nextNull();
                            }
                        } else if (tempo.equals("alt_description")) {
                            try {
                            alt_description = reader.nextString();
                            }catch (Exception e){
                                reader.nextNull();
                            }
                        } else if (tempo.equals("urls")) {
                            reader.beginObject();
                            while (reader.hasNext()){
                                tempo= reader.nextName();
                                if(tempo.equals("raw")){
                                    try {
                                        normalLink=reader.nextString();
                                    }catch (Exception e){
                                        reader.nextNull();
                                    }
                                }else if(tempo.equals("small")){
                                    try {
                                        small_size_link=reader.nextString();
                                    }catch (Exception e){
                                        reader.nextNull();
                                    }
                                }else reader.skipValue();
                            }
                            reader.endObject();
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    Data.add(new MyImage(description,alt_description,normalLink,small_size_link));
                }
                reader.endArray();
            }else{
                reader.skipValue();
            }
            }
            reader.endObject();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stop_Reading();
        return  Data;
    }
}

package com.example.basedo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XML_URI
{
    URL uri;
    HttpURLConnection urlConnection=null;
    InputStream Inp=null;
    //Dom Parser
    public List<Song_Data> Read_All_Data(String Ur)
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
                int id=-1;
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
                    urlConnection=(HttpURLConnection) uri.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();
                }
            Inp= urlConnection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Stop_Reading()
    {
        try {
            Inp.close();
            urlConnection.disconnect();
            urlConnection=null;
            Inp=null;
        }catch (Exception e){
        }
    }
}

package com.example.basedo;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XML_DB {
    String xmlFile;
    FileOutputStream Strem;
    FileInputStream Inp;

    public XML_DB(String xmlFile, char Mode) {
        this.xmlFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + xmlFile;
        try {
            Prepare_File(Mode);
        } catch (Exception ignored) {

        }
    }

    public void Write_DATA(Data dat) {
        try {
            Start_Writing();
            StringWriter writer = new StringWriter();
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(writer);
            dat.ToXML(xmlSerializer);
            xmlSerializer.flush();
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));
            End_Writing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Dom Parser
    public List<Data> Read_All_Data() {
        List<Data> FullBd = new ArrayList<Data>();
        Data DEE;
        Start_Reading();
        if(Inp!=null)
        {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document dom = documentBuilder.parse(Inp);
            Element root = dom.getDocumentElement();
            NodeList Datas = root.getElementsByTagName("Data");
            for (int i = 0; i < Datas.getLength(); i++) {
                Node BigDa = Datas.item(i);
                int id = -1;
                if (BigDa.hasAttributes())
                    id = Integer.parseInt(BigDa.getAttributes().getNamedItem("id").getTextContent());
                String FirstName = BigDa.getChildNodes().item(0).getTextContent();
                String LastName = BigDa.getChildNodes().item(1).getTextContent();
                DEE = new Data(id, FirstName, LastName);
                FullBd.add(DEE);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        Stop_Reading();
        return FullBd;
    }

    private void Prepare_File(char Mode) {
        File F;
        try {

            F = new File(xmlFile);
            if (!F.exists()) {
                F.createNewFile();
                Mode = 'W';
            }
            if (Mode == 'W') Init_Writing();//Write override
            else if (Mode == 'R') Start_Reading();//Read
            else if (Mode == 'A') ;//append
        } catch (IOException e) {
            Log.i(null, "Prepare_File: HELL");
            e.printStackTrace();
        }
    }

    private void Start_Reading() {
        try {
            if (Strem != null) {
                Strem.close();
                Strem = null;
            }
            if (Inp == null) Inp = new FileInputStream(xmlFile);
        } catch (Exception E) {
        }
    }

    private void Stop_Reading() {
        try {
            if(Inp!=null)
            {
                Inp.close();
                Inp = null;
            }
        } catch (Exception e) {
        }
    }

    private void Init_Writing() {
        try {
            if (Strem == null) Strem = new FileOutputStream(xmlFile, false);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "Root");
            xmlSerializer.flush();
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Start_Writing() throws IOException {
        List<Data> data = Read_All_Data();
        Init_Writing();
        if (data.size() > 0) {
            StringWriter writer = new StringWriter();
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(writer);
            for (Data dat : data) {
                dat.ToXML(xmlSerializer);
            }
            xmlSerializer.flush();
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));
        }
    }


    private void End_Writing() {
        try {
            if (Strem == null) Strem = new FileOutputStream(xmlFile, true);
            Strem.write("</Root>".getBytes(StandardCharsets.UTF_8));
            Strem.close();
            Strem = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
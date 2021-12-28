package com.example.basedo;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class Data {
    String Fname,Lname;
    int id=-1;
    public Data(){

    }
    public Data(String fname, String lname) {
        Fname = fname;
        Lname = lname;
    }

    public Data(int id,String fname, String lname) {
        Fname = fname;
        Lname = lname;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public void ToXML(XmlSerializer serializer) throws IOException
    {
        serializer.startTag(null,"Data");
        if(id!=-1)serializer.setProperty("id",id);
        serializer.startTag(null,"FirstName").text(Fname).endTag(null,"FirstName");
        serializer.startTag(null,"LastName").text(Lname).endTag(null,"LastName");
        serializer.endTag(null,"Data");
    }
}

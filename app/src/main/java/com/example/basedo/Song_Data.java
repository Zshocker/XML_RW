package com.example.basedo;

public class Song_Data
{
    private String name,href,image;

    public Song_Data(String name, String href, String image) {
        this.name = name;
        this.href = href;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Song_Data{" +
                "name='" + name + '\'' +
                ", href='" + href + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

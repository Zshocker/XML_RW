package com.example.basedo;

public class MyImage
{
    private String description,alt_description,normalLink,small_size_link;

    public MyImage(String description, String alt_description, String normalLink, String small_size_link) {
        this.description = description;
        this.alt_description = alt_description;
        this.normalLink = normalLink;
        this.small_size_link = small_size_link;
    }

    public String getDescription() {
        return description;
    }

    public String getAlt_description() {
        return alt_description;
    }

    public String getNormalLink() {
        return normalLink;
    }

    public String getSmall_size_link() {
        return small_size_link;
    }
}

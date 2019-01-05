package com.city.coding.restaurant3.Model;

public class news {

    //fields
    private String title ;
    private String content ;
    private int newsImage ;


    //constructor
    public news(String title, String content) {
        this.title = title;
        this.content = content;
    }
    //end constructor


    //getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getNewsImage() {
        return newsImage;
    }
    //end getter



    //setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }
    //end setter
}

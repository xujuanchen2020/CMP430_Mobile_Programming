package com.example.cards_and_colors;


public class Sport {

    private String title;
    private String info;
    private int imageResource;

//    Sport (String title, String info){
//        this.title = title;
//        this.info = info;
//    }

    Sport (String title, String info, int imageId){
        this.title = title;
        this.info = info;
        this.imageResource = imageId;
    }

    String getTitle(){
        return title;
    }

    String getInfo(){
        return info;
    }

    int getImageId(){
        return imageResource;
    }
}



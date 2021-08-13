package com.example.jeekota;

public class Model {

    String name;
    String subject;
    String image;
    public Model(){}

    public Model(String name,String subject,String url){
        this.name=name;
        this.subject=subject;
        this.image=url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return image;
    }

    public void setUrl(String url) {
        this.image = url;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

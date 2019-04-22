package com.swm.searchInterface.helloworld;

public class HelloWorldBean {

    private String message;
    private String author;


    public HelloWorldBean(String message,String author) {
        this.message = message;
        this.author = author;
    }

    public String getAuthor() {
        return author + "says hello";
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "message='" + message + '\'' +
                '}';
    }
}

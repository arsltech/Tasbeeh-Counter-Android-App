package com.developer.arsltech.tasbeehcounter;


public class zikar {
    private int id;
    private String zikarName;
    private int counter;
    private int remainingCounter;

    public zikar(int id, String zikarName, int counter, int remainingCounter) {
        this.id = id;
        this.zikarName = zikarName;
        this.counter = counter;
        this.remainingCounter = remainingCounter;
    }

    public int getRemainingCounter() {
        return remainingCounter;
    }

    public void setRemainingCounter(int remainingCounter) {
        this.remainingCounter = remainingCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZikarName() {
        return zikarName;
    }

    public void setZikarName(String zikarName) {
        this.zikarName = zikarName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
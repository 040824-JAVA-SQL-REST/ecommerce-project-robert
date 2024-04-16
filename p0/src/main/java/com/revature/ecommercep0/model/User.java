package com.revature.ecommercep0.model;

public class User {
    private boolean isCPU;
    private int win;
    private int lose;
    private int draw;
    private String name;
    public boolean isCPU() {
        return isCPU;
    }
    public void setCPU(boolean isCPU) {
        this.isCPU = isCPU;
    }
    public int getWin() {
        return win;
    }
    public void setWin(int win) {
        this.win = win;
    }
    public int getLose() {
        return lose;
    }
    public void setLose(int lose) {
        this.lose = lose;
    }
    public int getDraw() {
        return draw;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }    
    

    
}

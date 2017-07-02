package com.sourcey.intelligentmedicalsystem.bean;


/**
 * Created by ly on 2017/6/20.
 */
/**
病例实体类
 */


public class Record {
    private int id;

    private int userid;

    private String time = "";

    private String quest = "";

    private String solution = "";

    private String medicine = "";

    public Record(String quest, String solution, String medicine) {
        this.quest = quest;
        this.solution = solution;
        this.medicine = medicine;
    }

    public Record() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

}

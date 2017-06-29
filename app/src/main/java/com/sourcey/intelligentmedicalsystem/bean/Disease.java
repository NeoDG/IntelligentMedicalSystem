package com.sourcey.intelligentmedicalsystem.bean;

/**
 * Created by ly on 2017/6/23.
 */
/**
疾病实体类
 */

public class Disease {
    private int id;
    private  String disease;
    private String pre;
    private String obj;

    public Disease(String disease, String pre, String obj) {
        this.disease = disease;
        this.pre = pre;
        this.obj = obj;
    }
    public Disease(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}

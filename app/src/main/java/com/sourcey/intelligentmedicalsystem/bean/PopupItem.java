package com.sourcey.intelligentmedicalsystem.bean;

/**
 * Created by ly on 2017/6/22.
 */
/**
问答界面的item实体popup（加入、删除弹出item）
 */

public class PopupItem {
    String text1;
    String text2;

    public PopupItem(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }
    public PopupItem(){
        super();
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}

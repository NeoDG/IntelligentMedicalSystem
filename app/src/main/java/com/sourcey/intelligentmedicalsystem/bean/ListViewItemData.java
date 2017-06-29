package com.sourcey.intelligentmedicalsystem.bean;

/**
 * Created by ly on 2017/6/22.
 */
/**
问答界面的通用item实体：（T：Msg ／PopupItem）
 */
public class ListViewItemData<T> {
    //用来装载不同类型的item数据bean
    T t;
    //item数据bean的类型
    int dataType;

    public ListViewItemData(T t, int dataType) {
        this.t = t;
        this.dataType = dataType;
    }

    public T getT () {
        return t;
    }

    public void setT (T t) {
        this.t = t;
    }

    public int getDataType () {
        return dataType;
    }

    public void setDataType (int dataType) {
        this.dataType = dataType;
    }
}
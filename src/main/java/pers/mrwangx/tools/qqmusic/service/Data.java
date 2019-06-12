package pers.mrwangx.tools.qqmusic.service;

import java.util.List;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/15
 * \* Time: 19:13
 * \* Description:主要为了给mainController提供获取其他controller数据的接口
 **/
public interface Data<T> {

    /**
     * @return
     */
    List<T> getData();

    /**
     * @param data
     */
    void setData(List<T> data);

    /**
     * @param index
     * @return
     */
    T get(int index);

    /**
     * @param e
     */
    void add(T e);

    /**
     * @return
     */
    int getCrtindex();

    /**
     * @param crtindex
     */
    void setCrtindex(int crtindex);

}

package pers.mrwangx.tools.musictool.service;

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
    List<T> getSongs();

    /**
     * @param songs
     */
    void setSongs(List<T> songs);

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

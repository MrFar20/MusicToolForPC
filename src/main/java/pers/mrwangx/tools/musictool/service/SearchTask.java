package pers.mrwangx.tools.musictool.service;

import javafx.concurrent.Task;
import pers.mrwangx.tools.musictool.entity.SongProperty;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/13
 * \* Time: 1:59
 * \* Description:
 **/
public class SearchTask extends Task<SongProperty> {

    private String keyword;

    public SearchTask(String keyword) {
        this.keyword = keyword == null ? "" : keyword;
    }

    @Override
    protected SongProperty call() throws Exception {

        return null;
    }
}

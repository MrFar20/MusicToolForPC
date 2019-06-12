package pers.mrwangx.tools.qqmusic.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.qqmusic.adapter.SongsListViewAdapter;
import pers.mrwangx.tools.qqmusic.service.Data;
import pers.mrwangx.tools.qqmusic.util.FileUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/15
 * \* Time: 1:36
 * \* Description:
 **/
public class MyFavoriteController implements Initializable, Data<Song> {

    public static MyFavoriteController myFavoriteController = null;

    private static final Logger LOGGER = Logger.getLogger("MyFavoriteController");

    private MainController mainController;

    private static final double COLUMNS = 6.1;
    private List<Song> data = new ArrayList<>();
    private int crtindex = -1;
    private SongsListViewAdapter songsListViewAdapter;

    @FXML
    private Pane root;
    @FXML
    private JFXTextField searchWordInput;
    @FXML
    private JFXListView<Parent> songsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myFavoriteController = this;
        initSongsListView();
        initSearch();
    }

    private void initSongsListView() {
        songsListViewAdapter = new SongsListViewAdapter(data, songsListView, this, "/fxml/songsitem.fxml");
    }

    private void initSearch() {

    }



    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Pane getRoot() {
        return root;
    }

    /**
     * 从我的收藏中删除
     * @param s
     * @return
     */
    public boolean removeFromMyFavorite(Song s) {
        if (s != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getSongid().equals(s.getSongid())) {
                    songsListViewAdapter.remove(i);
                    Song crtSong = mainController.getCrtSong();
                    if (s.getSongid().equals(crtSong == null ? null : crtSong.getSongid())) mainController.setToLikeImg();
                    FileUtil.delSongProperty(s.getSongid(), FileUtil.getFavorDir());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加到我的收藏
     * @param s
     * @return
     */
    public boolean addToMyFavorite(Song s) {
        for (Song sp : data) {
            if (sp.getSongid().equals(s.getSongid())) {
                return false;
            }
        }
        songsListViewAdapter.add(s);
        Song crtSong = mainController.getCrtSong();
        if (s.getSongid().equals(crtSong == null ? null : crtSong.getSongid())) mainController.setToLikeFillImg();
        FileUtil.saveSong(s, FileUtil.getFavorDir());
        return true;
    }

    @Override
    public List<Song> getData() {
        return data;
    }

    @Override
    public void setData(List<Song> data) {
        songsListViewAdapter.addAll(data);
    }

    @Override
    public Song get(int index) {
        return data.get(index);
    }

    @Override
    public void add(Song e) {
        songsListViewAdapter.add(e);
    }

    @Override
    public int getCrtindex() {
        return this.crtindex;
    }

    @Override
    public void setCrtindex(int crtindex) {
        this.crtindex = crtindex;
    }
}

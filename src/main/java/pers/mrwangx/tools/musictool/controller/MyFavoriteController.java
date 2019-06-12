package pers.mrwangx.tools.musictool.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.musictool.cell.SongsListViewCell;
import pers.mrwangx.tools.musictool.service.Data;
import pers.mrwangx.tools.musictool.util.FileUtil;

import java.net.URL;
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
    private ObservableList<Song> songs = FXCollections.observableArrayList();
    private int crtindex = -1;

    @FXML
    private Pane root;
    @FXML
    private JFXTextField searchWordInput;
    @FXML
    private JFXListView<Song> songsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myFavoriteController = this;
        initSongsListView();
        initSearch();
    }

    private void initSongsListView() {
        songsListView.setItems(songs);
        songsListView.setCellFactory(param -> new SongsListViewCell(this));
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
            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).getSongid().equals(s.getSongid())) {
                    songs.remove(i);
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
        for (Song sp : songs) {
            if (sp.getSongid().equals(s.getSongid())) {
                return false;
            }
        }
        songs.add(s);
        Song crtSong = mainController.getCrtSong();
        if (s.getSongid().equals(crtSong == null ? null : crtSong.getSongid())) mainController.setToLikeFillImg();
        FileUtil.saveSong(s, FileUtil.getFavorDir());
        return true;
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public void setSongs(List<Song> songs) {
        this.songs.clear();
        this.songs.addAll(songs);
    }

    @Override
    public Song get(int index) {
        return songs.get(index);
    }

    @Override
    public void add(Song e) {
        songs.add(e);
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

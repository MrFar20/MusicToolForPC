package pers.mrwangx.tools.qqmusic.adapter;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.qqmusic.controller.MainController;
import pers.mrwangx.tools.qqmusic.controller.MyFavoriteController;
import pers.mrwangx.tools.qqmusic.service.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * \* Author: MrWangx
 * \* Date: 2019/6/12
 * \* Time: 15:23
 * \* Description:
 **/
public class SongsListViewAdapter {

    private List<Song> songs;
    private JFXListView<Parent> listView;
    private String layoutResource;
    private Data<Song> data;


    public SongsListViewAdapter(List<Song> songs, JFXListView<Parent> listView, Data<Song> data, String layoutResource) {
        this.songs = songs;
        this.listView = listView;
        this.layoutResource = layoutResource;
        this.data = data;
        init();
    }

    private void init() {
        songs.forEach(this::add);
    }

    public void add(Song song) {
        try {
            songs.add(song);
            Parent p = FXMLLoader.load(this.getClass().getResource(layoutResource));
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    MainController.mainController.playMusic(song);
                }
            });
            Label songname = (Label) p.lookup("#songname");
            Label songinfo = (Label) p.lookup("#songinfo");
            Label status = (Label) p.lookup("#status");
            ImageView download = (ImageView) p.lookup("#download");
            ImageView like = (ImageView) p.lookup("#like");
            songname.setText(song.getName());
            songinfo.setText(song.getSinger() + "-" + song.getAlbumname() + "\t" + song.getFormatTime());
            download.setOnMouseClicked(event -> {
                Task<String> downloadTask = new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        updateMessage("建立连接...");
                        try {
                            URL url = new URL(song.REAL_SONG_PLAY_URL());
                            try (InputStream inputStream = url.openStream();
                                 FileOutputStream fout = new FileOutputStream(new File(MainController.mainController.getSavePath(), song.getName() + " - " + song.getSinger()));
                            ) {
                                updateMessage("建立连接成功...");
                                updateMessage("下载中...");
                                int length = 0;
                                byte[] data = new byte[1024];
                                while ((length = inputStream.read(data, 0, data.length)) != -1) {
                                    fout.write(data, 0, length);
                                    fout.flush();
                                }
                                updateMessage("已下载");
                            } catch (Exception e) {
                                updateMessage("下载失败...");
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                status.textProperty().unbind();
                status.textProperty().bind(downloadTask.messageProperty());
                new Thread(downloadTask).start();
            });
            like.setOnMouseClicked(event -> {
                MyFavoriteController.myFavoriteController.addToMyFavorite(song);
            });

            listView.getItems().add(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAll(Collection<? extends Song> songs) {
        songs.forEach(this::add);
    }

    public void remove(int index) {
        songs.remove(index);
        listView.getItems().remove(index);
    }

    public void remove(Song s) {
        int index = songs.indexOf(s);
        remove(index);
    }

    public void removeAll() {
        listView.getItems().clear();
        songs.clear();
    }


}

package pers.mrwangx.tools.qqmusic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import pers.mrwangx.tool.musictool.config.MusicAPIConfig;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.qqmusic.util.QQMusicUtil;

import java.io.Serializable;

/****
 * @author:MrWangx
 * @description
 * @Date 2018/12/24 19:28
 *****/
public class SongProperty<T> extends RecursiveTreeObject<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String musicType;
    protected final SimpleStringProperty name;        //歌名
    protected String songid;                          //歌曲mid
    protected final SimpleStringProperty singer;      //歌手
    protected String albumid;                        //专辑id
    protected final SimpleStringProperty albumname;   //专辑名
    private String imgurl;
    protected final SimpleStringProperty alia;        //专辑副标题
    protected final SimpleStringProperty duration;    //时长
    protected String songPlayUrl;

    public SongProperty(String musicType, String name, String songid, String singer, String albumid, String albumname, String imgurl, String alia, String duration) {
        this.musicType = musicType;
        this.name = new SimpleStringProperty(name);
        this.songid = songid;
        this.singer = new SimpleStringProperty(singer);
        this.albumid = albumid;
        this.albumname = new SimpleStringProperty(albumname);
        this.imgurl = imgurl;
        this.alia = new SimpleStringProperty(alia);
        this.duration = new SimpleStringProperty(duration);
    }

    public SongProperty(Song song) {
        this(song.getMusicType(), song.getName(), song.getSongid(), song.getSinger(), song.getAlbumid(), song.getAlbumname(), song.getImgurl(), song.getAlia(), Integer.toString(song.getDuration()));
    }

    @Override
    public String toString() {
        return "SongProperty{" +
                "musicType='" + musicType + '\'' +
                ", name=" + name.get() +
                ", songid='" + songid + '\'' +
                ", singer=" + singer.get() +
                ", albumid=" + albumid +
                ", albumname=" + albumname.get() +
                ", imgurl='" + imgurl + '\'' +
                ", alia=" + alia.get() +
                ", duration=" + duration.get() +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getSinger() {
        return singer.get();
    }

    public SimpleStringProperty singerProperty() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer.set(singer);
    }

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getAlbumname() {
        return albumname.get();
    }

    public SimpleStringProperty albumnameProperty() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname.set(albumname);
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAlia() {
        return alia.get();
    }

    public SimpleStringProperty aliaProperty() {
        return alia;
    }

    public void setAlia(String alia) {
        this.alia.set(alia);
    }

    public String getDuration() {
        return duration.get();
    }

    public SimpleStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    @JSONField(serialize = false)
    public String SONG_PLAY_URL() {
        return songPlayUrl == null ? songPlayUrl = QQMusicUtil.getSongPlayUrl(this.songid) : songPlayUrl;
    }
}

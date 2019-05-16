package pers.mrwangx.tools.qqmusic.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import pers.mrwangx.tools.qqmusic.util.QQMusicUtil;

import java.io.Serializable;

/****
 * @author:MrWangx
 * @description
 * @Date 2018/12/24 19:28
 *****/
public class SongProperty<T> extends RecursiveTreeObject<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final SimpleStringProperty name;        //歌名
    protected String songmid;                         //歌曲mid
    protected String purl;                            //下载的地址
    protected final SimpleStringProperty singer;      //歌手
    protected final SimpleStringProperty albumname;   //专辑名
    protected Integer albumid;                        //专辑id
    protected final SimpleStringProperty subtitle;    //专辑副标题
    protected final SimpleStringProperty time;        //时长

    public SongProperty(String name, String songmid, String purl, String singer, Integer albumid, String albumname, String subtitle, String time) {
        this.name = new SimpleStringProperty(name);
        this.songmid = songmid;
        this.purl = purl;
        this.singer = new SimpleStringProperty(singer);
        this.albumid = albumid;
        this.albumname = new SimpleStringProperty(albumname);
        this.subtitle = new SimpleStringProperty(subtitle);
        this.time = new SimpleStringProperty(time);
    }

    public SongProperty(Song song) {
        this(song.getName(), song.getSongmid(), song.getPurl(), song.getSinger(), song.getAlbumid(), song.getAlbumname(), song.getSubtitle(), song.getTime());
    }

    @Override
    public String toString() {
        return "SongProperty{" +
                "name=" + name.get() +
                ", songmid='" + songmid + '\'' +
                ", purl='" + purl + '\'' +
                ", singer=" + singer.get() +
                ", albumname=" + albumname.get() +
                ", albumid=" + albumid +
                ", subtitle=" + subtitle.get() +
                ", time=" + time.get() +
                '}';
    }

    public String getDownloadUrl() {
        return QQMusicUtil.DOWNLOAD_BASE_URL + purl;
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

    public String getSongmid() {
        return songmid;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
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

    public Integer getAlbumid() {
        return albumid;
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

    public String getSubtitle() {
        return subtitle.get();
    }

    public SimpleStringProperty subtitleProperty() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.set(subtitle);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}

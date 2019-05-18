package pers.mrwangx.tools.qqmusic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import pers.mrwangx.tools.qqmusic.util.QQMusicUtil;

/****
 * @author:MrWangx
 * @description
 * @Date 2018/12/23 3:33
 *****/
public class Song {

    private String name;        //歌名
    private String songmid;     //歌曲mid
    private String purl;        //下载的相对地址
    private String singer;      //歌手
    private Integer albumid;     //专辑id
    private String albumname;   //专辑名
    private String subtitle;    //专辑副标题
    private String time;        //时长

    public Song() {}

    public Song(String name, String songmid, String purl, String singer, Integer albumid, String albumname, String subtitle, String time) {
        this.name = name;
        this.songmid = songmid;
        this.purl = purl;
        this.singer = singer;
        this.albumid = albumid;
        this.albumname = albumname;
        this.subtitle = subtitle;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", songmid='" + songmid + '\'' +
                ", purl='" + purl + '\'' +
                ", singer='" + singer + '\'' +
                ", albumid=" + albumid +
                ", albumname='" + albumname + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @JSONField(serialize = false)
    public String getDownloadUrl() {
        return QQMusicUtil.DOWNLOAD_BASE_URL + purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongmid() {
        return songmid;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getAlbumid() {
        return albumid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

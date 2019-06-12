package pers.mrwangx.tools.qqmusic.entity;

import javafx.beans.property.SimpleStringProperty;
import pers.mrwangx.tool.musictool.entity.Song;

import java.io.Serializable;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/14
 * \* Time: 1:57
 * \* Description:
 **/
public class SongPropertyV2 extends SongProperty<SongPropertyV2> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SimpleStringProperty status;

    public SongPropertyV2(String musicType, String name, String songid, String singer, String albumid, String albumname, String imgurl, String alia, String duration) {
        super(musicType, name, songid, singer, albumid, albumname, imgurl, alia, duration);
        this.status = new SimpleStringProperty("");
    }

    public SongPropertyV2(Song song) {
        super(song);
        this.status = new SimpleStringProperty("");
    }

    @Override
    public String toString() {
        return "SongPropertyV2{" +
                "status=" + status.get() +
                ", musicType='" + musicType + '\'' +
                ", name=" + name.get() +
                ", songid='" + songid + '\'' +
                ", singer=" + singer.get() +
                ", albumid='" + albumid + '\'' +
                ", albumname=" + albumname.get() +
                ", alia=" + alia.get() +
                ", duration=" + duration.get() +
                '}';
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}

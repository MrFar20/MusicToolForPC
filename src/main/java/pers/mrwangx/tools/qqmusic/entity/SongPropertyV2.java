package pers.mrwangx.tools.qqmusic.entity;

import javafx.beans.property.SimpleStringProperty;

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

    public SongPropertyV2(String name, String songmid, String downloadUrl, String singer, Integer albumid, String albumname, String subtitle, String time) {
        super(name, songmid, downloadUrl, singer, albumid, albumname, subtitle, time);
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
                ", name=" + name.get() +
                ", songmid='" + songmid + '\'' +
                ", purl='" + purl + '\'' +
                ", singer=" + singer.get() +
                ", albumname=" + albumname.get() +
                ", albumid=" + albumid +
                ", subtitle=" + subtitle.get() +
                ", time=" + time.get() +
                '}';
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}

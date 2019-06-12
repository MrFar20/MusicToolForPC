package pers.mrwangx.tools.qqmusic.util;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pers.mrwangx.tool.musictool.MusicAPIHolder;
import pers.mrwangx.tool.musictool.api.impl.QQMusicAPI;
import pers.mrwangx.tool.musictool.config.MusicAPIConfig;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.qqmusic.entity.SongProperty;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/****
 * @author:MrWangx
 * @description
 * @Date 2018/12/23 4:53
 *****/
public class QQMusicUtil {

    public static final String SONG_SEARCH_URL = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.center&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=#{page}&n=#{num}&w=#{keyword}&g_tk=5381&jsonpCallback=MusicJsonCallback7765912334450861&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
    public static final String DOWNLOAD_BASE_URL = "http://isure.stream.qqmusic.qq.com/";
    public static final String SONG_INFO_URL = "https://u.y.qq.com/cgi-bin/musicu.fcg?callback=getplaysongvkey7724607038771364&g_tk=5381&jsonpCallback=getplaysongvkey7724607038771364&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&data={\"req\":{\"module\":\"CDN.SrfCdnDispatchServer\",\"method\":\"GetCdnDispatch\",\"param\":{\"guid\":\"6647183392\",\"calltype\":0,\"userip\":\"\"}},\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"6647183392\",\"songmid\":[\"#{songmid}\"],\"songtype\":[0],\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":0,\"format\":\"json\",\"ct\":20,\"cv\":0}}";
    public static final String ALBUM_IMG_URL = "http://imgcache.qq.com/music/photo/album_300/#{albumid%100}/300_albumpic_#{albumid}_0.jpg";
    public static final String LYRIC_URL = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?callback=MusicJsonCallback_lrc&pcachetime=1557976642637&songmid=#{songmid}&g_tk=1108864830&jsonpCallback=MusicJsonCallback_lrc&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";

    public static final QQMusicAPI QQ_MUSIC_API = (QQMusicAPI) MusicAPIHolder.getAPI(MusicAPIConfig.MUSIC_TYPE_TECENT);


    /**
     * 根据关键词查找歌曲信息
     *
     * @param keyword
     * @param pagenum
     * @param pagesize
     * @return
     * @throws IOException
     */
    public static List<Song> getSongsByKeyword(String keyword, int pagenum, int pagesize) throws IOException {
        return QQ_MUSIC_API.searchSong(keyword, pagenum, pagesize);
    }

    /**
     * @param keyword
     * @param pagenum
     * @param pagesize
     * @return
     * @throws Exception
     */
    public static JSONObject getSongsJsonBykeyword(String keyword, int pagenum, int pagesize) throws IOException {
        return QQ_MUSIC_API.getSongSearchJson(keyword, pagenum, pagesize);
    }

    /**
     * @param songs
     * @param type
     * @return
     */
    public static List<? extends SongProperty> getSongPropertyFromSongs(List<Song> songs, Class<? extends SongProperty> type) {
        List list = new ArrayList<>();
        songs.forEach(e -> {
            try {
                list.add(type.getConstructor(Song.class).newInstance(e));
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        });
        return list;
    }

    public static String getSongPlayUrl(String songid) {
        try {
            return Jsoup.connect(MusicAPIConfig.SONG_PLAY_URL(MusicAPIConfig.MUSIC_TYPE_TECENT, songid) + "&quality=320")
                    .method(Connection.Method.GET)
                    .header("Content-type", "application/json")
                    .ignoreContentType(true)
                    .execute()
                    .url()
                    .toExternalForm()
                    ;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取下载的相对url
     *
     * @param songid
     * @return
     */
    public static String getPurl(String songid) {
        return MusicAPIConfig.SONG_PLAY_URL(MusicAPIConfig.MUSIC_TYPE_TECENT, songid);
    }


}

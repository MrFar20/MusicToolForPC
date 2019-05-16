package pers.mrwangx.tools.qqmusic.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pers.mrwangx.tools.qqmusic.entity.Song;
import pers.mrwangx.tools.qqmusic.entity.SongProperty;

import java.io.IOException;
import java.io.InputStream;
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
        List<Song> songslist = new ArrayList<>();

        //获取歌歌曲信息表的jsonarray
        JSONArray songsinfo = getSongsJsonBykeyword(keyword, pagenum, pagesize).getJSONObject("data").getJSONObject("song").getJSONArray("list");
        //遍历返回的歌曲信息
        for (Object o : songsinfo) {
            JSONObject songinfo = (JSONObject) o;
            try {
                String songmid = songinfo.getString("mid");
                String purl = getPurl(songmid);
                String name = songinfo.getString("name");
                String singer_name = songinfo.getJSONArray("singer").getJSONObject(0).getString("name");
                Integer album_id = songinfo.getJSONObject("album").getInteger("id");
                String album_name = songinfo.getJSONObject("album").getString("name");
                String album_subtitle = songinfo.getJSONObject("album").getString("subtitle");
                String time = songinfo.getString("interval");
                Song song = new Song(name, songmid, purl, singer_name, album_id, album_name, album_subtitle, time);
                songslist.add(song);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return songslist;
    }

    /**
     * @param keyword
     * @param pagenum
     * @param pagesize
     * @return
     * @throws Exception
     */
    public static JSONObject getSongsJsonBykeyword(String keyword, int pagenum, int pagesize) throws IOException {
        //HttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(getSongSearchUrl(keyword, pagenum, pagesize));
//        String data = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
//        ((CloseableHttpClient) httpClient).close();
        //获取返回的json信息
        StringBuffer buffer = new StringBuffer();
        URL url = new URL(getSongSearchUrl(keyword, pagenum, pagesize));
        InputStream input = url.openStream();
        byte[] d = new byte[1024];
        int length = 0;
        while ((length = input.read(d, 0, d.length)) != -1) {
            buffer.append(new String(d, 0, length));
        }
        input.close();
        String data = buffer.substring(buffer.indexOf("(") + 1, buffer.lastIndexOf(")"));
        //获取歌歌曲信息表json信息
        return JSON.parseObject(data);
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

    /**
     * 获取下载的相对url
     * @param songmid
     * @return
     * @throws IOException
     */
    public static String getPurl(String songmid) throws IOException {
        Document document = null;
        document = Jsoup.connect(getSongInfoUrl(songmid)).get();
        String data = document.body().text();
        //获取json信息
        data = data.substring(data.indexOf("(") + 1, data.lastIndexOf(")"));
        //获取purl
        JSONObject jsonObject = JSON.parseObject(data).getJSONObject("req_0");
        String purl = jsonObject.getJSONObject("data").getJSONArray("midurlinfo").getJSONObject(0).getString("purl");
        return purl;
    }

    /**
     * 根据songmid获取歌曲的详细信息url
     * @param songmid
     * @return
     */
    public static String getSongInfoUrl(String songmid) {
        return SONG_INFO_URL.replaceAll("#\\{songmid\\}", songmid);
    }

    /**
     * 获取关键词搜索的url
     * @param keyword
     * @param pagenum
     * @param pagesize
     * @return
     */
    public static String getSongSearchUrl(String keyword, int pagenum, int pagesize) {
        return SONG_SEARCH_URL.replaceAll("#\\{keyword\\}", keyword).replaceAll("#\\{page\\}", Integer.toString(pagenum)).replaceAll("#\\{num\\}", Integer.toString(pagesize));
    }

    /**
     * 根据专辑id获取专辑封面的图片url
     * @param albumid
     * @return
     */
    public static String getAlbumImgUrl(Integer albumid) {
        return ALBUM_IMG_URL.replaceAll("#\\{albumid%100\\}", albumid % 100 + "").replaceAll("#\\{albumid\\}", albumid.toString());
    }

}

package pers.mrwangx.tools.musictool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

//    @Test
//    @Ignore
//    public void QQMusicHNTest() {
//
//        final String baseurl = "http://isure.stream.qqmusic.qq.com/";
//        final Scanner input = new Scanner(System.in);
//
//        //关闭日志输出
//        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
//
//        WebClient webClient = new WebClient();
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setJavaScriptEnabled(true);
//
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//
//        HtmlPage htmlPage = null;
//
//        try {
//            htmlPage = webClient.getPage("https://y.qq.com/portal/search.html#t=song&page=1&w=许嵩");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//        webClient.waitForBackgroundJavaScript(2000);
//        Document doc = Jsoup.parse(htmlPage.asXml());
//        Elements eles = doc.select(".songlist__list .songlist__item");
//        for (Element e : eles) {
//            //获取歌名标签
//            Element e_songname = e.selectFirst(".js_song");
//            //获取专辑标签
//            Element e_albumname = e.selectFirst(".album_name");
//            //歌曲时间
//            Element e_time = e.selectFirst(".songlist__time");
////            System.out.println(String.format("%s\t%s\t%s\t%s",
//////                    e_songname.attr("title"), e_songname.attr("href"),
//////                    e_albumname.attr("title"), e_time.text()));
//            //获取歌曲的id
//            String href = e_songname.attr("href");
//            String song_id = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf(".html"));
//            //获取data req_0
//            String song_mes_url = "https://u.y.qq.com/cgi-bin/musicu.fcg?callback=getplaysongvkey7724607038771364&g_tk=5381&jsonpCallback=getplaysongvkey7724607038771364&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&data={\"req\":{\"module\":\"CDN.SrfCdnDispatchServer\",\"method\":\"GetCdnDispatch\",\"param\":{\"guid\":\"6647183392\",\"calltype\":0,\"userip\":\"\"}},\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"6647183392\",\"songmid\":[\"" + song_id + "\"],\"songtype\":[0],\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":0,\"format\":\"json\",\"ct\":20,\"cv\":0}}";
//            Document document = null;
//            try {
//                document = Jsoup.connect(song_mes_url).get();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            String data = document.body().text();
//            data = data.substring(data.indexOf("(") + 1, data.lastIndexOf(")"));
//            JSONObject jsonObject = JSON.parseObject(data).getJSONObject("req_0");
////            System.out.println(jsonObject);
//
//            //获取purl
//            String purl = jsonObject.getJSONObject("data").getJSONArray("midurlinfo").getJSONObject(0).getString("purl");
//            System.out.println(String.format("%s\t%s\t", e_songname.attr("title"), baseurl + purl));
//
//            //存储音乐
//            File file = new File("F:\\crawler\\music", e_songname.attr("title") + ".m4a");
//            try(InputStream inputStream = (new URL(baseurl + purl)).openStream();
//                FileOutputStream fileOutputStream = new FileOutputStream(file);) {
//                byte[] d = new byte[1024];
//                int length = 0;
//                while ((length = inputStream.read(d, 0, d.length)) != -1) {
//                    fileOutputStream.write(d, 0, length);
//                    fileOutputStream.flush();
//                }
//            } catch (Exception e1) {
//                System.out.println("下载失败");
//            }
//        }
//
//    }

    @Test
    @Ignore
    public void QQMusicJPTest() throws IOException {

//        final String download_baseurl = "http://isure.stream.qqmusic.qq.com/";
//
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("https://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.center&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=1&n=20&w=薛之谦&g_tk=5381&jsonpCallback=MusicJsonCallback7765912334450861&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0");
//        String data = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
//        ((CloseableHttpClient) httpClient).close();
//        data = data.substring(data.indexOf("(") + 1, data.lastIndexOf(")"));
//        JSONObject song_data = JSON.parseObject(data).getJSONObject("data").getJSONObject("song");
//        JSONArray song_list = song_data.getJSONArray("list");
//
//        for (Object o : song_list) {
//            JSONObject jo = (JSONObject) o;
//            String mid = jo.getString("mid");
//            String song_mes_url = "https://u.y.qq.com/cgi-bin/musicu.fcg?callback=getplaysongvkey7724607038771364&g_tk=5381&jsonpCallback=getplaysongvkey7724607038771364&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&data={\"req\":{\"module\":\"CDN.SrfCdnDispatchServer\",\"method\":\"GetCdnDispatch\",\"param\":{\"guid\":\"6647183392\",\"calltype\":0,\"userip\":\"\"}},\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"6647183392\",\"songmid\":[\"" + mid + "\"],\"songtype\":[0],\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":0,\"format\":\"json\",\"ct\":20,\"cv\":0}}";
//            Document document = null;
//            try {
//                document = Jsoup.connect(song_mes_url).get();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            String data1 = document.body().text();
//            data1 = data1.substring(data1.indexOf("(") + 1, data1.lastIndexOf(")"));
//            JSONObject jsonObject = JSON.parseObject(data1).getJSONObject("req_0");
////            System.out.println(jsonObject);
//
//            //获取purl
//            String purl = jsonObject.getJSONObject("data").getJSONArray("midurlinfo").getJSONObject(0).getString("purl");
//
//            String name = jo.getString("name");
//            String singer_name = jo.getJSONArray("singer").getJSONObject(0).getString("name");
//            String album_id = jo.getJSONObject("album").getString("id");
//            String album_name = jo.getJSONObject("album").getString("name");
//            String album_subtitle = jo.getJSONObject("album").getString("subtitle");
//            String time = jo.getString("interval");
//            Song song = new Song(name, download_baseurl + purl, singer_name, album_id, album_name, album_subtitle, time);
//            System.out.println(song);
//        }
//
//        System.out.println(song_list.size());
    }

    @Test
    @Ignore
    public void jsoupTest1() throws IOException {
//        Connection connection  = Jsoup.connect("https://music.163.com/weapi/search/suggest/multimatch?csrf_token=");
//        Connection.Response response = connection.data("params", "/enIMdQemG2boCpunZgzmj2+gjqORYky4ASYQtkD8x/z88CMx3eHvYxlxFZrela0Ipu9hb8/LUjNMhceNvwZHHu/uosA2DBiKeWqkjP0FKw=")
//                .data("encSecKey", "24a5a2e2e698ff98a38b573d47827dfdf1158d12dc97565bffd26f4ebac6c2f26d20079a125b0b777f1c62beb4a7971b75f51621e0384814024fac196935a94f778324ca8a407215a27026d16f72ec2f3e3296a7ef2f37a1d6f82d09087486beeb7a9d06b7aab388ae1d9470083efa755f1435d1e90f05cc965059e0f6789032")
//                .method(Connection.Method.POST).execute();
//        System.out.println(response.body());
        Document doc = Jsoup.connect("https://music.163.com/#").get();
        Elements eles = doc.select("a");
        eles.forEach(e -> System.out.println(e));

    }

    @Test
    @Ignore
    public void test1() throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpEntity httpEntity = httpClient.execute(new HttpPost("http://www.cqooc.com/user/login?username=106172016211995&password=E4E88EDCCC10A2C566E715733D3788A560EE96E51CDB50A609D81A334E732BAC&nonce=622DFFE7700DDD43&cnonce=2A056409D21AC44F&captchaToken=AAABZ9Vln44B.dPtUPQvCJU+/tu3HIEuaLg==.FyGi/nTbgFYfPdm3Icy9vdBhi3We7NJkc0UHAW1j+7s=")).getEntity();
//        System.out.println(EntityUtils.toString(httpEntity));

    }

//    @Test
//    @Ignore
//    public void htmlunitTest() throws IOException, InterruptedException {
//        WebClient webClient = new WebClient();
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        HtmlPage htmlPage = webClient.getPage("https://music.163.com/#/search/m/?s=浪子回头");
//        htmlPage.getBody().getDomElementDescendants().forEach(e -> System.out.println(e.asXml()));
//    }

    @Test
    @Ignore
    public void httpclientTest1() throws IOException {
//        CloseableHttpClient hclient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("https://music.163.com/#/search/m/?s=浪子回头");
//        System.out.println(EntityUtils.toString(hclient.execute(httpGet).getEntity()));
    }

    @Test
    @Ignore
    public void openExplorer() {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.OPEN))
        try {
            desktop.open(new File(System.getProperty("user.home")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void test () throws IOException {
        System.out.println(System.getProperty("java.class.path"));
    }

}

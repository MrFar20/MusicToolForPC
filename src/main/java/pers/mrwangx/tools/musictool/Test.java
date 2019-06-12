package pers.mrwangx.tools.musictool;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * \* Author: MrWangx
 * \* Date: 2019/6/12
 * \* Time: 0:09
 * \* Description:
 **/
public class Test {

    public static void main(String[] args) throws IOException {
        Connection.Response response = Jsoup.connect("http://mobileoc.music.tc.qq.com/M5000039MnYb0qxYhV.mp3?guid=BZQLL&vkey=B4D9DD05246B79ACD61BC59CDB0DBC7DFCDFCADCB5BD3984BDA3883071530BB601BD2D72CA96B920CB003814DDB9F469B9607406116483FC&uin=0&fromtag=8")
                .method(Connection.Method.GET).header("Content-type", "application/json").ignoreContentType(true).execute();
        System.out.println(response.url());
    }

}

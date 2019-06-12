package pers.mrwangx.tools.qqmusic.util;

import com.alibaba.fastjson.JSON;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.qqmusic.App;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/16
 * \* Time: 1:54
 * \* Description:
 **/
public class FileUtil {

    private static final Logger LOGGER = Logger.getLogger("FileUtil");

    /**
     * 获取我的收藏文件夹路径
     *
     * @return
     */
    public static String getFavorDir() {
        return getRuntimeDir() + App.FAVOR_DIR;
    }

    /**
     * 获取设置文件夹路径
     *
     * @return
     */
    public static String getSettingsFilepath() {
        return getRuntimeDir() + App.SETTINGS_FILE;
    }

    /**
     * 获取运行时路径
     *
     * @return
     */
    public static String getRuntimeDir() {
        File file = new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        return decodePath(file.isDirectory() ? file.getAbsolutePath() : file.getParent());
    }

    /**
     * 获取缓存歌曲路径
     *
     * @param songmid
     * @return
     */
    public static File getSongCache(String songmid) {
        File file = new File(getRuntimeDir() + App.CACHE_DIR, songmid);
        return file.exists() ? file : null;
    }

    /**
     * 从本地读取歌曲信息
     *
     * @param dir
     * @return
     */
    public static List<Song> readDataFromFile(File dir) {
        List<Song> data = FXCollections.observableArrayList();
        if (!dir.exists()) {
            dir.mkdirs();
            return data;
        }
        File[] file = dir.listFiles();
        for (File f : file) {
            LOGGER.info("读取[" + f.getAbsolutePath() + "]");
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                data.add(JSON.toJavaObject(JSON.parseObject(reader.readLine()), Song.class));
            } catch (IOException e) {
                LOGGER.info("读取[" + f.getAbsolutePath() + "]失败");
                LOGGER.warning(e + "");
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 歌曲信息存到本地
     *
     * @param song
     * @param dir
     * @return
     */
    public static int saveSong(Song song, String dir) {
        File file = new File(dir + File.separator + song.getSongid() + ".data");
        if (file.exists()) {
            LOGGER.info("存储歌曲信息已存在:" + song);
        } else {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(JSON.toJSONString(song));
                LOGGER.info("存储歌曲信息成功:" + song);
            } catch (IOException e) {
                file.delete();
                LOGGER.info("存储歌曲信息错误:" + song);
                LOGGER.warning(e + "");
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 删除收藏
     *
     * @param songmid
     * @param dir
     * @return
     */
    public static int delSongProperty(String songmid, String dir) {
        File file = new File(dir + File.separator + songmid + ".data");
        if (file.exists()) {
            LOGGER.info((file.delete() ? "删除成功" : "删除失败") + ":" + file.getAbsolutePath());
        } else {
            LOGGER.info("歌曲信息不存在:" + songmid);
        }
        return 0;
    }

    /**
     * 获取配置
     *
     * @param filepath
     * @return
     */
    public static Properties getSettings(String filepath) {
        Properties settings = new Properties();
        File file = new File(filepath);
        if (!file.exists()) {
            File pDir = file.getParentFile();
            try {
                if ((!pDir.exists() ? pDir.mkdirs() : true) && file.createNewFile()) {
                    FileInputStream fin = new FileInputStream(file);
                    FileOutputStream fout = new FileOutputStream(file);
                    settings.load(fin);
                    settings.setProperty(App.KEY_SAVEPATH, App.DEFAULT_SAVEPATH);
                    settings.setProperty(App.KEY_SETCACHE, App.DEFAULT_SETCACHE.toString());
                    settings.setProperty(App.KEY_VOLUME, App.DEFAULT_VOLUME.toString());
                    fin.close();
                    saveSettings(settings, file);
                } else {
                    LOGGER.info("创建配置文件失败");
                }
            } catch (IOException e) {
                LOGGER.info("创建配置文件出错");
                LOGGER.warning(e + "");
                e.printStackTrace();
                return null;
            } finally {

            }
        } else {
            try (FileInputStream fin = new FileInputStream(getSettingsFilepath())) {
                settings.load(fin);
            } catch (IOException e) {
                LOGGER.info("读取设置错误");
                LOGGER.warning(e + "");
                e.printStackTrace();
                return null;
            }
        }
        return settings;
    }

    /**
     * 保存设置
     *
     * @param settings
     * @param file
     */
    public static void saveSettings(Properties settings, File file) throws IOException {
        if (settings != null) {
            FileOutputStream fout = new FileOutputStream(file);
            settings.store(fout, "settings");
            fout.close();
        }
    }


    /**
     * 下载歌曲
     *
     * @param file
     * @param downloadUrl
     * @param status
     */
    public static void downloadSong(File file, String downloadUrl, StringProperty status) {
        status.set("建立连接...");
        try {
            URL url = new URL(downloadUrl);
            try (InputStream inputStream = url.openStream();
                 FileOutputStream fout = new FileOutputStream(file);
            ) {
                status.set("建立连接成功...");
                status.set("下载中...");
                int length = 0;
                byte[] data = new byte[1024];
                while ((length = inputStream.read(data, 0, data.length)) != -1) {
                    fout.write(data, 0, length);
                    fout.flush();
                }
                status.set("已下载");
            } catch (Exception e) {
                status.set("下载失败...");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储cache
     *
     * @param cacheDir
     * @param songmid
     * @param downloadUrl
     */
    public static File saveSongCache(String cacheDir, String songmid, String downloadUrl) {
        File cacheFile = null;
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            URL url = new URL(downloadUrl);
            File file = new File(dir.getCanonicalPath(), songmid);
            try (InputStream input = url.openStream();
            FileOutputStream fout = new FileOutputStream(file)) {
                int length = 0;
                byte[] data = new byte[1024];
                while ((length = input.read(data, 0, data.length)) != -1) {
                    fout.write(data, 0, length);
                    fout.flush();
                }
                cacheFile = file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile;
    }

    /**
     * file转为url
     * @param file
     * @return
     */
    public static String fileToUrlString(File file) {
        try {
            return file.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件路径转码
     * @param filepath
     * @return
     */
    public static String decodePath(String filepath) {
        try {
            return URLDecoder.decode(filepath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return filepath;
    }

}

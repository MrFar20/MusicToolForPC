package pers.mrwangx.tools.musictool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pers.mrwangx.tools.musictool.controller.MainController;
import pers.mrwangx.tools.musictool.controller.MyFavoriteController;
import pers.mrwangx.tools.musictool.controller.SearchController;
import pers.mrwangx.tools.musictool.util.FileUtil;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/12
 * \* Time: 4:26
 * \* Description:
 **/
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static App app = null;
    public static Image qqmusic_logo = null;
    public static Image netease_logo = null;
    public static Image kugou_logo = null;
    public static Image kuwo_logo = null;
    public static Image baidu_logo = null;


    private static final Logger LOGGER = Logger.getLogger("App");

    public static final String FAVOR_DIR = "/data/favorite";                                        //我的收藏信息文件夹
    public static final String SETTINGS_FILE = "/data/settings/settings.prop";                      //配置文件位置
    public static final String CACHE_DIR = "/data/cache";
    public static final String DEFAULT_SAVEPATH = System.getProperty("user.home") + File.separator + "Music";   //默认下载位置
    public static final Integer DEFAULT_VOLUME = 50;                                                            //默认音量大小
    public static final Boolean DEFAULT_SETCACHE = false;                                                       //默认不开启缓存

    public static final String KEY_SAVEPATH = "savePath";
    public static final String KEY_SETCACHE = "setCache";
    public static final String KEY_VOLUME = "volume";

    private Stage stage;

    private MainController mainController;
    private SearchController searchController;
    private MyFavoriteController myFavoriteController;
    private Properties settings;


    @Override
    public void start(Stage primaryStage) throws Exception {
        initResouce();
        stage = primaryStage;
        LOGGER.info(FileUtil.getRuntimeDir());

        searchController = new SearchController();
        settings = FileUtil.getSettings(FileUtil.getSettingsFilepath());
        mainController = new MainController(settings);
        myFavoriteController = new MyFavoriteController();

        FXMLLoader searchFxmlLoader = new FXMLLoader();
        searchFxmlLoader.setLocation(this.getClass().getResource("/fxml/search.fxml"));
        searchFxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        searchFxmlLoader.setController(searchController);
        searchFxmlLoader.load();

        FXMLLoader myFavoriteFxmlLoader = new FXMLLoader();
        myFavoriteFxmlLoader.setLocation(this.getClass().getResource("/fxml/my-favorite.fxml"));
        myFavoriteFxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        myFavoriteFxmlLoader.setController(myFavoriteController);
        myFavoriteFxmlLoader.load();

        //初始化mainController之前设置
        mainController.setSearchController(searchController);
        mainController.setMyFavoriteController(myFavoriteFxmlLoader.getController());
        mainController.setStage(primaryStage);
        mainController.setData(searchController);

        FXMLLoader mainFxmlLoader = new FXMLLoader();
        mainFxmlLoader.setLocation(this.getClass().getResource("/fxml/main.fxml"));
        mainFxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        mainFxmlLoader.setController(mainController);
        mainFxmlLoader.load();

        Parent root = mainFxmlLoader.getRoot();
        root.getStylesheets().add("/css/style.css");
//        root.getStylesheets().add("/css/jfoenix-components.css");

        primaryStage.getIcons().add(new Image("/img/logo-title.png"));
        primaryStage.setTitle("MusicTool");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        //alert组件初始化必须在stage.show()方法之后
        mainController.initAlert();
        myFavoriteController.setSongs(FileUtil.readDataFromFile(new File(FileUtil.getFavorDir())));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        saveFavor();
        LOGGER.info("保存设置");
        FileUtil.saveSettings(settings, new File(FileUtil.getSettingsFilepath()));
        LOGGER.info("保存设置成功");
        System.exit(0);
    }

    private void saveFavor() {
        LOGGER.info("保存收藏");
        String dir = FileUtil.getFavorDir();
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        myFavoriteController.getSongs().forEach(e -> {
            FileUtil.saveSong(e, dir);
        });
        LOGGER.info("保存收藏完成");
    }

    private void initResouce() {
        app = this;
        qqmusic_logo = new Image(this.getClass().getResource("/img/qqmusic_logo.png").toExternalForm());
        netease_logo = new Image(this.getClass().getResource("/img/netease_cloud_music_logo.png").toExternalForm());
        kugou_logo = new Image(this.getClass().getResource("/img/kugou_music_logo.png").toExternalForm());
        kuwo_logo = new Image(this.getClass().getResource("/img/kuwo_music_logo.png").toExternalForm());
        baidu_logo = new Image(this.getClass().getResource("/img/baidu_music_logo.png").toExternalForm());
    }


    public Stage getStage() {
        return stage;
    }
}

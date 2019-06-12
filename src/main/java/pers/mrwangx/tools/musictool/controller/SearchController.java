package pers.mrwangx.tools.musictool.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pers.mrwangx.tool.musictool.entity.Song;
import pers.mrwangx.tools.musictool.cell.SongsListViewCell;
import pers.mrwangx.tools.musictool.service.Data;
import pers.mrwangx.tools.musictool.util.QQMusicUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/****
 * @author:MrWangx
 * @description
 * @Date 2019/3/9 18:05
 *****/
public class SearchController implements Initializable, Data<Song> {

    public static SearchController searchController = null;

    private static final Logger LOGGER = Logger.getLogger("SearchController");

    private static final int PAGE_SIZE = 20;
    private static final int MAX_PAGE_NUM = 20;
    private static final double COLUMNS = 6.1;

    private MainController mainController;

    private int crtindex = -1;
    private int pagenum = 0;
    private boolean isToMaxPage = false;
    private String crtKeyword = null;
    private ObservableList<Song> songs = FXCollections.observableArrayList();

    private Tooltip tooltip = new Tooltip("鼠标左双击播放|右单击收藏|右双击下载");

    @FXML
    private Pane root;
    @FXML
    private JFXTextField keywordInput;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXListView<Song> songsListView;
    @FXML
    private ImageView logo;
    @FXML
    private JFXSpinner spinner;

    @FXML
    private JFXCheckBox choice_tencent;
    @FXML
    private JFXCheckBox choice_netease;
    @FXML
    private JFXCheckBox choice_kugou;
    @FXML
    private JFXCheckBox choice_kuwo;
    @FXML
    private JFXCheckBox choice_baidu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchController = this;
        initSongListView();
        initInputControls();
    }

    private void initSongListView() {
        songsListView.setOnScroll(event -> {
            if (event.getTextDeltaY() < 0 && !isToMaxPage) {
                searchToAdd();
            }
        });
        songsListView.setCellFactory(param -> new SongsListViewCell(this));
        songsListView.setItems(songs);
    }

    private void initInputControls() {
        searchBtn.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                searchToReset();
            }
        });
        keywordInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchToReset();
            }
        });
    }


    private void searchToAdd() {
        search(++pagenum, crtKeyword);
    }

    private void searchToReset() {
        songs.clear();
        crtindex = -1;
        isToMaxPage = false;
        search(pagenum = 0, crtKeyword = keywordInput.getText());
    }

    /**
     * 搜索
     *
     * @param pagenum 第几页
     * @param keyword 关键词
     */
    private void search(int pagenum, String keyword) {
        if (keyword != null && !keyword.matches("^\\s+|$") && !searchBtn.disabledProperty().get() && !isToMaxPage) {
            LOGGER.info("搜索{pagenum:" + pagenum + ",keyword:" + crtKeyword + "}");
            reverse();
            int size = songs.size() - 1;
            Task<List<Song>> task = new Task<List<Song>>() {
                @Override
                protected List<Song> call() throws Exception {
                    return QQMusicUtil.QQ_MUSIC_API.searchSong(keyword, pagenum, PAGE_SIZE);
                }
            };
            task.setOnSucceeded(event -> {
                reverse();
                if (task.getValue() != null) {
                    if (task.getValue().isEmpty()) isToMaxPage = true;
                    songs.addAll(task.getValue());
                    songsListView.scrollTo(size);
                }
            });
            task.setOnFailed(event -> {
                reverse();
            });
            new Thread(task).start();
        }
    }


    private void reverse() {
        songsListView.setDisable(songsListView.isDisabled() ? false : true);
        searchBtn.setDisable(searchBtn.isDisable() ? false : true);
        spinner.setVisible(spinner.isVisible() ? false : true);
    }



    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Pane getRoot() {
        return root;
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public void setSongs(List<Song> songs) {
        this.songs = this.songs;
    }

    @Override
    public Song get(int index) {
        return songs.get(index);
    }

    @Override
    public void add(Song e) {
        songs.add(e);
    }

    @Override
    public int getCrtindex() {
        return crtindex;
    }

    @Override
    public void setCrtindex(int crtindex) {
        this.crtindex = crtindex;
    }
}

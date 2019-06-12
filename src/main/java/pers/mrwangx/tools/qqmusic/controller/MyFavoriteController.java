package pers.mrwangx.tools.qqmusic.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pers.mrwangx.tools.qqmusic.entity.SongProperty;
import pers.mrwangx.tools.qqmusic.entity.SongPropertyV2;
import pers.mrwangx.tools.qqmusic.service.Data;
import pers.mrwangx.tools.qqmusic.util.FileUtil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * \* Author: MrWangx
 * \* Date: 2019/5/15
 * \* Time: 1:36
 * \* Description:
 **/
public class MyFavoriteController implements Initializable, Data<SongPropertyV2> {

    private static final Logger LOGGER = Logger.getLogger("MyFavoriteController");

    private MainController mainController;

    private static final double COLUMNS = 6.1;
    private ObservableList<SongPropertyV2> data = FXCollections.observableArrayList();
    private int crtindex = -1;
    private Tooltip tooltip = new Tooltip("鼠标左双击播放|右单击删除收藏|右双击下载");

    @FXML
    private Pane root;
    @FXML
    private JFXTextField searchWordInput;
    @FXML
    private JFXTreeTableView<SongPropertyV2> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initSearch();
    }

    private void initTable() {
        table.setPrefWidth(root.getPrefWidth());

        table.setColumnResizePolicy(param -> {
            return false;
        });

        JFXTreeTableColumn<SongPropertyV2, String> nameColumn = new JFXTreeTableColumn<>("歌名");
        nameColumn.setPrefWidth(table.getPrefWidth() / COLUMNS);
        nameColumn.setCellValueFactory(param -> {
            if (nameColumn.validateValue(param)) {
                return param.getValue().getValue().nameProperty();
            } else {
                return nameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> singerColumn = new JFXTreeTableColumn<>("歌手");
        singerColumn.setPrefWidth(table.getPrefWidth() / COLUMNS);
        singerColumn.setCellValueFactory(param -> {
            if (singerColumn.validateValue(param)) {
                return param.getValue().getValue().singerProperty();
            } else {
                return singerColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> albumnameColumn = new JFXTreeTableColumn<>("专辑名");
        albumnameColumn.setPrefWidth(table.getPrefWidth() / COLUMNS);
        albumnameColumn.setCellValueFactory(param -> {
            if (albumnameColumn.validateValue(param)) {
                return param.getValue().getValue().albumnameProperty();
            } else {
                return albumnameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> subtitleColumn = new JFXTreeTableColumn<>("歌名");
        subtitleColumn.setPrefWidth(table.getPrefWidth() / COLUMNS);
        subtitleColumn.setCellValueFactory(param -> {
            if (subtitleColumn.validateValue(param)) {
                return param.getValue().getValue().nameProperty();
            } else {
                return subtitleColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> timeColumn = new JFXTreeTableColumn<>("时长");
        timeColumn.setPrefWidth(table.getPrefWidth() / COLUMNS);
        timeColumn.setCellValueFactory(param -> {
            if (timeColumn.validateValue(param)) {
                return param.getValue().getValue().durationProperty();
            } else {
                return timeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> statusColumn = new JFXTreeTableColumn<>("");
        statusColumn.setPrefWidth(table.getPrefWidth() / COLUMNS);
        statusColumn.setCellValueFactory(param -> {
            if (statusColumn.validateValue(param)) {
                return param.getValue().getValue().statusProperty();
            } else {
                return statusColumn.getComputedValue(param);
            }
        });

        //左双击播放|右单机从收藏中移除|右双击下载
        table.setRowFactory(param -> {
            JFXTreeTableRow<SongPropertyV2> row = new JFXTreeTableRow<>();
            row.setTooltip(tooltip);
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && (!row.isEmpty())) {
                    crtindex = row.getIndex();
                    mainController.playMusic(data.get(crtindex));
                    mainController.setData(this);
                } else if (event.getClickCount() == 1 && event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                    removeFromMyFavorite(data.get(row.getIndex()));
                }  else if (event.getClickCount() == 2 && event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                    LOGGER.info("下载" + data.get(row.getIndex()));
                    SongPropertyV2 songProperty = data.get(row.getIndex());
                    Task<Object> downloadTask = new Task<Object>() {
                        @Override
                        protected Object call() throws Exception {
                            FileUtil.downloadSong(new File(mainController.getSavePath(),  songProperty.getName() + " - " + songProperty.getSinger() + ".mp3"), songProperty.SONG_PLAY_URL(), songProperty.statusProperty());
                            return null;
                        }
                    };
                    new Thread(downloadTask).start();
                }
            });
            return row;
        });
        table.getColumns().setAll(nameColumn, singerColumn, albumnameColumn, subtitleColumn, timeColumn, statusColumn);
        final TreeItem<SongPropertyV2> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
    }

    private void initSearch() {
        searchWordInput.textProperty().addListener((observable, oldValue, newValue) -> {
            table.setPredicate(item -> {
                String lowV = newValue.toLowerCase();
                SongPropertyV2 e = item.getValue();
                return e.getName().toLowerCase().contains(lowV) || e.getSinger().toLowerCase().contains(lowV) ||
                        e.getAlbumname().toLowerCase().contains(lowV) || e.getAlia().toLowerCase().contains(lowV);
            });
        });
    }

    @Override
    public ObservableList<SongPropertyV2> getData() {
        return data;
    }

    @Override
    public void setData(ObservableList<SongPropertyV2> data) {
        this.data = data;
    }

    @Override
    public SongPropertyV2 get(int index) {
        return data.get(index);
    }

    @Override
    public void add(SongPropertyV2 e) {
        data.add(e);
    }

    @Override
    public int getCrtindex() {
        return crtindex;
    }

    @Override
    public void setCrtindex(int crtindex) {
        this.crtindex = crtindex;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Pane getRoot() {
        return root;
    }

    /**
     * 从我的收藏中删除
     * @param s
     * @return
     */
    public boolean removeFromMyFavorite(SongPropertyV2 s) {
        if (s != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getSongid().equals(s.getSongid())) {
                    data.remove(i);
                    SongPropertyV2 crtSp = mainController.getCrtSongProperty();
                    if (s.getSongid().equals(crtSp == null ? null : crtSp.getSongid())) mainController.setToLikeImg();
                    FileUtil.delSongProperty(s.getSongid(), FileUtil.getFavorDir());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加到我的收藏
     * @param s
     * @return
     */
    public boolean addToMyFavorite(SongPropertyV2 s) {
        for (SongPropertyV2 sp : data) {
            if (sp.getSongid().equals(s.getSongid())) {
                return false;
            }
        }
        data.add(s);
        SongPropertyV2 crtSp = mainController.getCrtSongProperty();
        if (s.getSongid().equals(crtSp == null ? null : crtSp.getSongid())) mainController.setToLikeFillImg();
        FileUtil.saveSongProperty(s, FileUtil.getFavorDir());
        return true;
    }
}

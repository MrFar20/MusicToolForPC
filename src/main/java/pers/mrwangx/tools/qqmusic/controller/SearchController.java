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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pers.mrwangx.tools.qqmusic.service.Data;
import pers.mrwangx.tools.qqmusic.util.FileUtil;
import pers.mrwangx.tools.qqmusic.util.QQMusicUtil;
import pers.mrwangx.tools.qqmusic.entity.SongPropertyV2;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/****
 * @author:MrWangx
 * @description
 * @Date 2019/3/9 18:05
 *****/
public class SearchController implements Initializable, Data<SongPropertyV2> {

    private static final Logger LOGGER = Logger.getLogger("SearchController");

    private static final int PAGE_SIZE = 20;
    private static final int MAX_PAGE_NUM = 20;
    private static final double COLUMNS = 6.1;

    private MainController mainController;

    private int crtindex = -1;
    private int pagenum = 0;
    private String crtKeyword = null;
    private ObservableList<SongPropertyV2> data = FXCollections.observableArrayList();

    private Tooltip tooltip = new Tooltip("鼠标左双击播放|右单击收藏|右双击下载");

    @FXML
    private Pane root;
    @FXML
    private JFXTextField keywordInput;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXTreeTableView<SongPropertyV2> resultTable;
    @FXML
    private ImageView logo;
    @FXML
    private JFXSpinner spinner;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initInputControls();
    }

    private void initTable() {
        resultTable.setPrefWidth(root.getPrefWidth());

        //滑到末尾加载更多
        resultTable.setOnScroll(event -> {
            if (event.getTextDeltaY() < 0 && pagenum < MAX_PAGE_NUM) {
                searchToAdd();
            }
        });

        resultTable.setColumnResizePolicy(param -> {
            return false;
        });

        JFXTreeTableColumn<SongPropertyV2, String> nameColumn = new JFXTreeTableColumn<>("歌名");
        nameColumn.setPrefWidth(resultTable.getPrefWidth() / COLUMNS);
        nameColumn.setCellValueFactory(param -> {
            if (nameColumn.validateValue(param)) {
                return param.getValue().getValue().nameProperty();
            } else {
                return nameColumn.getComputedValue(param);
            }
        });



        JFXTreeTableColumn<SongPropertyV2, String> singerColumn = new JFXTreeTableColumn<>("歌手");
        singerColumn.setPrefWidth(resultTable.getPrefWidth() / COLUMNS);
        singerColumn.setCellValueFactory(param -> {
            if (singerColumn.validateValue(param)) {
                return param.getValue().getValue().singerProperty();
            } else {
                return singerColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> albumnameColumn = new JFXTreeTableColumn<>("专辑名");
        albumnameColumn.setPrefWidth(resultTable.getPrefWidth() / COLUMNS);
        albumnameColumn.setCellValueFactory(param -> {
            if (albumnameColumn.validateValue(param)) {
                return param.getValue().getValue().albumnameProperty();
            } else {
                return albumnameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> subtitleColumn = new JFXTreeTableColumn<>("歌名");
        subtitleColumn.setPrefWidth(resultTable.getPrefWidth() / COLUMNS);
        subtitleColumn.setCellValueFactory(param -> {
            if (subtitleColumn.validateValue(param)) {
                return param.getValue().getValue().subtitleProperty();
            } else {
                return subtitleColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> timeColumn = new JFXTreeTableColumn<>("时长");
        timeColumn.setPrefWidth(resultTable.getPrefWidth() / COLUMNS);
        timeColumn.setCellValueFactory(param -> {
            if (timeColumn.validateValue(param)) {
                return param.getValue().getValue().timeProperty();
            } else {
                return timeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<SongPropertyV2, String> statusColumn = new JFXTreeTableColumn<>("");
        statusColumn.setPrefWidth(resultTable.getPrefWidth() / COLUMNS);
        statusColumn.setCellValueFactory(param -> {
            if (statusColumn.validateValue(param)) {
                return param.getValue().getValue().statusProperty();
            } else {
                return statusColumn.getComputedValue(param);
            }
        });

        //左双击播放|右单击收藏|右双击下载
        resultTable.setRowFactory(param -> {
            JFXTreeTableRow<SongPropertyV2> row = new JFXTreeTableRow<>();
            row.setTooltip(tooltip);
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && (!row.isEmpty())) {
                    crtindex = row.getIndex();
                    mainController.playMusic(data.get(crtindex));
                    mainController.setData(this);
                } else if (event.getClickCount() == 1 && event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                    mainController.addToMyFavorite(data.get(row.getIndex()));
                } else if (event.getClickCount() == 2 && event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                    LOGGER.info("下载" + data.get(row.getIndex()));
                    SongPropertyV2 songProperty = data.get(row.getIndex());
                    Task<Object> downloadTask = new Task<Object>() {
                        @Override
                        protected Object call() throws Exception {
                            FileUtil.downloadSong(new File(mainController.getSavePath(), songProperty.getName() + " - " + songProperty.getSinger() + ".m4a"), songProperty.getDownloadUrl(), songProperty.statusProperty());
                            return null;
                        }
                    };
                    new Thread(downloadTask).start();
                }
            });
            return row;
        });

        resultTable.getColumns().setAll(nameColumn, singerColumn, albumnameColumn, subtitleColumn, timeColumn, statusColumn);
        final TreeItem<SongPropertyV2> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        resultTable.setRoot(root);
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
        data.clear();
        crtindex = -1;
        search(pagenum = 1, crtKeyword = keywordInput.getText());
    }

    /**
     * 搜索
     *
     * @param pagenum 第几页
     * @param keyword 关键词
     */
    private void search(int pagenum, String keyword) {
        if (!searchBtn.disabledProperty().get()) {
            LOGGER.info("搜索{pagenum:" + pagenum + ",keyword:" + crtKeyword + "}");
            reverse();
            int oldsize = data.size();
            Task<List<SongPropertyV2>> task = new Task<List<SongPropertyV2>>() {
                @Override
                protected List<SongPropertyV2> call() throws Exception {
                    return (List<SongPropertyV2>) QQMusicUtil.getSongPropertyFromSongs(QQMusicUtil.getSongsByKeyword(keyword, pagenum, PAGE_SIZE), SongPropertyV2.class);
                }
            };
            task.setOnSucceeded(event -> {
                data.addAll(task.getValue());
                resultTable.scrollTo(oldsize);
                reverse();
            });
            task.setOnFailed(event -> {
                reverse();
            });
            new Thread(task).start();
        }
    }

    private void reverse() {
        resultTable.setDisable(resultTable.isDisabled() ? false : true);
        searchBtn.setDisable(searchBtn.isDisable() ? false : true);
        spinner.setVisible(spinner.isVisible() ? false : true);
    }

    @Override
    public int getCrtindex() {
        return crtindex;
    }

    @Override
    public void setCrtindex(int crtindex) {
        this.crtindex = crtindex;
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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Pane getRoot() {
        return root;
    }
}

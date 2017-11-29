package controller;

import classes.Tag;
import classes.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArchiveViewController extends Stage implements Initializable{

    @FXML
    private Button refreshBtn, sortBtn, backBtn;
    @FXML
    private ScrollPane taskScrollPane;
    @FXML
    private ScrollPane tagScrollPane;

    private SourceModel sourceModel;
    private Boolean back;

    public void refresh(ActionEvent event) throws IOException{
        sourceModel.sortTasks();
        setTasks();
        setTags();
    }

    public void sort (ActionEvent event) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sortView.fxml"));
        Pane pane = loader.load();
        SortController sortController = loader.getController();
        sortController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        sortController.setX(stage.getX()+50);
        sortController.setY(stage.getY()+50);
        sortController.setResizable(false);
        sortController.setModel(sourceModel);
        sortController.setContent();
        sortController.initOwner(stage);
        sortController.initModality(Modality.APPLICATION_MODAL);
        sortController.showAndWait();

        stage.show();

        if (sortController.isOkClicked()) {
            refresh(new ActionEvent());
        }
    }
    public void openPrintTaskUI(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/printTasks.fxml"));
        Pane pane = loader.load();
        PrintController printController = loader.getController();
        printController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        printController.setX(stage.getX()+50);
        printController.setY(stage.getY()+50);
        printController.setResizable(false);
        printController.setModel(sourceModel);
        printController.setPrintArchive();
        printController.setContent();
        printController.initOwner(stage);
        printController.initModality(Modality.APPLICATION_MODAL);
        printController.setTitle("Print Task");
        printController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        printController.showAndWait();

        stage.show();
    }

    public void back(ActionEvent event) throws IOException {
        back = true;
        this.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back = false;
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    private void setTasks(){
        GridPane paneForScrollPane = new GridPane();

        int y = 0;
        int x = 0;

        for (Task task : sourceModel.getTaskArchList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/archTask.fxml"));
            Pane pane = new Pane();
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArchTaskController archTaskController = loader.getController();
            archTaskController.setTask(task);
            archTaskController.setModel(sourceModel);
            archTaskController.showTags();

            if (x == 6){
                y++;
                x = 0;
            }
            paneForScrollPane.add(pane, x, y);

            x++;
        }
        taskScrollPane.setContent(paneForScrollPane);
    }

    private void setTags(){
        GridPane paneForScrollPane = new GridPane();

        int y = 0;
        int x = 0;

        for (Tag tag : sourceModel.getTagArchList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/archTag.fxml"));
            Pane pane = new Pane();
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArchTagController archTagController = loader.getController();
            archTagController.setTag(tag);

            if (y == 2){
                x++;
                y = 0;
            }
            paneForScrollPane.add(pane, x, y);

            y++;
        }

        tagScrollPane.setContent(paneForScrollPane);
    }

    public Boolean getBack(){
        return back;
    }
}
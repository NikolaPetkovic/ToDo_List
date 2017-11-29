package controller;

import classes.Tag;
import classes.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArchTaskController extends Stage implements Initializable {

    @FXML
    private Label lbName;
    @FXML
    private Label lbDeadline;
    @FXML
    private Label lbDone;
    @FXML
    private VBox vBox;

    private Task task;
    private SourceModel sourceModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void showClickedTask(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showTask.fxml"));
        Pane pane = loader.load();
        ShowTaskController showTaskController = loader.getController();
        showTaskController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        showTaskController.setX(stage.getX()+50);
        showTaskController.setY(stage.getY()+50);
        showTaskController.setResizable(false);
        showTaskController.setTask(sourceModel, task);
        showTaskController.initOwner(stage);
        showTaskController.initModality(Modality.APPLICATION_MODAL);
        showTaskController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        showTaskController.setTitle("Task Information : " + task.getName());
        showTaskController.showAndWait();
        stage.show();
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public void showTags() {
        vBox.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            if (task.gettagIdList().get(i) != 0){
                for (Tag t0 : sourceModel.getTagArchList()) {
                    if (t0.getId() == task.gettagIdList().get(i)){
                        Pane tagpane = new Pane();
                        tagpane.setPrefWidth(25);
                        tagpane.setVisible(true);
                        tagpane.setPrefHeight(100);
                        tagpane.setStyle("-fx-background-color: " + t0.getColor());
                        vBox.getChildren().add(tagpane);
                    }
                }
            }
        }
    }

    public void setTask(Task task) {
        this.task = task;
        lbName.setText(task.getName());
        lbDeadline.setText(task.getDeadline().toString(true,false));
        lbDone.setText(task.getCompletionDate().toString(true,false));
    }
}
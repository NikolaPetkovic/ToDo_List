package controller;

import classes.Date;
import classes.ReTask;
import classes.Tag;
import classes.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReTaskController extends Stage implements Initializable {
    @FXML
    private Label lblName;

    @FXML
    private HBox hBox;

    @FXML
    private Label timeLabel;

    @FXML
    private GridPane gridPane;

    private ReTask reTask;
    private Date date;
    private TaskViewController tvC;
    private SourceModel sourceModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showTags() {
        hBox.getChildren().clear();
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (reTask.getTagList().get(i) != 0) {
                for (Tag t0 : sourceModel.getTagList()) {
                    if (t0.getId() == reTask.getTagList().get(i)) {
                        counter++;
                        Pane tagPane = new Pane();
                        tagPane.setVisible(true);
                        tagPane.setStyle("-fx-background-color: " + t0.getColor());
                        if (counter == 2) {
                            tagPane.setPrefSize(134, 13);
                        } else {
                            tagPane.setPrefSize(135, 13);
                        }
                        hBox.getChildren().add(tagPane);
                    }
                }
            }
        }
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public void setReTask(ReTask reTask, Date date) {
        this.reTask = reTask;
        this.date = date;
        lblName.setText(this.reTask.getName());
        timeLabel.setText(date.toString(false,true));
        if(date.getDone()) {
            gridPane.setOpacity(0.3);
        }
    }

    public void donePressed() throws IOException {
        if(!date.getDone()){
            date.setDone(true);
            convertToArch();
            sourceModel.saveReTasks();
            tvC.refresh();
        }
    }

    public void editPressed (ActionEvent e ) throws IOException {
        if(!date.getDone()) {
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editReTask.fxml"));
            Pane pane = loader.load();
            EditReTaskController editReTaskController = loader.getController();
            editReTaskController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
            editReTaskController.setX(stage.getX() + 50);
            editReTaskController.setY(stage.getY() + 50);
            editReTaskController.setResizable(false);
            editReTaskController.setReTask(sourceModel, reTask);
            editReTaskController.initOwner(stage);
            editReTaskController.initModality(Modality.APPLICATION_MODAL);
            editReTaskController.setTitle("Edit ReTag : " + reTask.getName());
            editReTaskController.showAndWait();
            stage.show();

            if (editReTaskController.isOkClicked()) {
                sourceModel.removeReTask(reTask);
                reTask = editReTaskController.getReTask();
                sourceModel.addReTask(reTask);
                sourceModel.saveReTasks();
                showTags();
                lblName.setText(this.reTask.getName());
                tvC.refresh();
            }

            if (editReTaskController.isDelete()) {
                sourceModel.removeReTask(reTask);
                sourceModel.saveReTasks();
                tvC.refresh();
            }
        }
    }

    private void convertToArch () throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/txt/ProgramIcon.png"));
        alert.setTitle("Question");
        alert.setHeaderText("Do you want to archive this ReTask?");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            sourceModel.archReTask(reTask, date);
            tvC.refresh();
        } else {
            System.out.println("no");
        }
    }

    public void setTVC(TaskViewController tvC) {
        this.tvC = tvC;
    }
}
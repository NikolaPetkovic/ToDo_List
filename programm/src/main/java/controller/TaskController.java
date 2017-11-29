package controller;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskController extends Stage implements Initializable {

    @FXML
    private Label lbName;
    @FXML
    private VBox vBox;

    private Task task;
    private SourceModel sourceModel;
    private TaskViewController tvC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void editTask(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editTask.fxml"));
        Pane pane = loader.load();
        EditTaskController etController = loader.getController();
        etController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        etController.setX(stage.getX()+50);
        etController.setY(stage.getY()+50);
        etController.setResizable(false);
        etController.setTask(sourceModel, task);
        etController.initOwner(stage);
        etController.initModality(Modality.APPLICATION_MODAL);
        etController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        etController.setTitle("Edit Task : " + task.getName());
        etController.showAndWait();
        stage.show();

        if (etController.isOkClicked()) {
            sourceModel.removeTask(task);
            task = etController.getTask();
            sourceModel.addTask(task);
            sourceModel.saveTasks();
            showTags();
            lbName.setText(this.task.getName());
            tvC.refresh();
        }

        if (etController.isDelete()) {
            sourceModel.removeTask(task);
            sourceModel.saveTasks();
            tvC.refresh();
        }
    }

    @FXML
    private void convertToArch(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/txt/ProgramIcon.png"));
        alert.setTitle("Question");
        alert.setHeaderText("Do you want to archive this Task?");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            sourceModel.archTask(task);
            tvC.refresh();
        } else {
            System.out.println("no");
        }
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public void showTags() {
        vBox.getChildren().clear();
        ArrayList<Pane> panes = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (task.gettagIdList().get(i) != 0){
                for (Tag t0 : sourceModel.getTagList()) {
                    if (t0.getId() == task.gettagIdList().get(i)){
                        counter++;
                        Pane tagPane = new Pane();
                        tagPane.setStyle("-fx-background-color: " + t0.getColor());
                        panes.add(tagPane);
                    }
                }
            }
        }

        if (counter == 1){
            panes.get(0).setPrefSize(25, 100);
        } else if (counter == 2){
            panes.get(0).setPrefSize(25, 50);
            panes.get(1).setPrefSize(25, 50);
        } else if (counter == 3){
            panes.get(0).setPrefSize(25, 33);
            panes.get(1).setPrefSize(25, 34);
            panes.get(2).setPrefSize(25, 33);
        } else if (counter == 4){
            panes.get(0).setPrefSize(25, 25);
            panes.get(1).setPrefSize(25, 25);
            panes.get(2).setPrefSize(25, 25);
            panes.get(3).setPrefSize(25, 25);
        }

        if (counter > 0) {
            for(Pane p : panes){
                vBox.getChildren().add(p);

                Pane testPane = (Pane) vBox.getChildren().get(0);
                /*System.out.println(testPane.getWidth());
                System.out.println(testPane.getMaxWidth());
                System.out.println(testPane.getMinWidth());
                System.out.println(testPane.getBorder());
                System.out.println(testPane.getInsets());*/
            }
        }

        if (counter > 0) {
            Pane testPane = (Pane) vBox.getChildren().get(0);
            /*System.out.println(testPane.getWidth());
            System.out.println(testPane.getMaxWidth());
            System.out.println(testPane.getMinWidth());
            System.out.println(testPane.getBorder());
            System.out.println(testPane.getInsets());*/
        }

        if (counter > 1){
            Pane testPane2 = (Pane) vBox.getChildren().get(1);
            /*System.out.println(testPane2.getWidth());
            System.out.println(testPane2.getMaxWidth());
            System.out.println(testPane2.getMinWidth());
            System.out.println(testPane2.getBorder());
            System.out.println(testPane2.getInsets());*/
        }

        if (counter > 2){
            Pane testPane3 = (Pane) vBox.getChildren().get(2);
            /*System.out.println(testPane3.getWidth());
            System.out.println(testPane3.getMaxWidth());
            System.out.println(testPane3.getMinWidth());
            System.out.println(testPane3.getBorder());
            System.out.println(testPane3.getInsets());*/
        }

        if (counter > 3){
            Pane testPane4 = (Pane) vBox.getChildren().get(3);
            /*System.out.println(testPane4.getWidth());
            System.out.println(testPane4.getMaxWidth());
            System.out.println(testPane4.getMinWidth());
            System.out.println(testPane4.getBorder());
            System.out.println(testPane4.getInsets());*/
        }
    }

    public void setTask(Task task) {
        this.task = task;
        lbName.setText(this.task.getName());
    }

    public void setTVC(TaskViewController tvC) {
        this.tvC = tvC;
    }
}
package controller;

import classes.Tag;
import classes.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SourceModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowTaskController extends Stage implements Initializable {
    @FXML
    private Label title;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCreationDate;
    @FXML
    private TextField tfDeadlineDate;
    @FXML
    private TextField tfDoneDate;
    @FXML
    private TextField tfPrio;
    @FXML
    private TextField tfTag0;
    @FXML
    private TextField tfTag1;
    @FXML
    private TextField tfTag2;
    @FXML
    private TextField tfTag3;

    private SourceModel sourceModel;
    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void cancelTask() {
        this.close();
    }

    public void setTask(SourceModel sourceModel, Task task) {
        this.sourceModel = sourceModel;
        this.task = task;
        setContent();
    }
    public void setContent(){
        title.setText(task.getName());
        tfName.setText(task.getName());
        tfCreationDate.setText(task.getCreationDate().toString(true, true));
        tfDeadlineDate.setText(task.getDeadline().toString(true, true));
        tfDoneDate.setText(task.getCompletionDate().toString(true, true));

        if (task.getPriority() == 1) {
            tfPrio.setText("1 - Hoch");
        } else if (task.getPriority() == 2) {
            tfPrio.setText("2 - Mittel");
        } else if (task.getPriority() == 3) {
            tfPrio.setText("3 - Niedrig");
        }

        for (int i = 0; i < 4; i++) {
            for (Tag tag : sourceModel.getTagList()) {
                if (task.gettagIdList().get(i) == tag.getId()){
                    if (i == 0){
                        tfTag0.setText(tag.getName());
                    } else if (i == 1){
                        tfTag1.setText(tag.getName());
                    } else if (i == 2){
                        tfTag2.setText(tag.getName());
                    } else if (i == 3){
                        tfTag3.setText(tag.getName());
                    }
                } else if (tag.getId() == 0) {
                    if (i == 0){
                        tfTag0.setText("-");
                    } else if (i == 1){
                        tfTag1.setText("-");
                    } else if (i == 2){
                        tfTag2.setText("-");
                    } else if (i == 3){
                        tfTag3.setText("-");
                    }
                }
            }
        }
    }
}
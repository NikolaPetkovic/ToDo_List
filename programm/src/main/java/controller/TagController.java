package controller;

import classes.Tag;
import classes.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.Source;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TagController extends Stage implements Initializable {
    @FXML
    private Pane colorPane;
    @FXML
    private Button tagbtn;

    private Tag tag;
    private SourceModel sourceModel;
    private TaskViewController tvC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void editTag(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editTag.fxml"));
        Pane pane = loader.load();
        EditTagController etController = loader.getController();
        etController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        etController.setX(stage.getX()+50);
        etController.setY(stage.getY()+50);
        etController.setResizable(false);
        etController.setTag(sourceModel, tag);
        etController.initOwner(stage);
        etController.initModality(Modality.APPLICATION_MODAL);
        etController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        etController.setTitle("Edit Tag : " + tag.getName());
        etController.showAndWait();
        stage.show();

        if (etController.isSave()) {
            if (etController.isChangeSave()){
                Tag newTag = etController.getTag();
                /*tagbtn.setText(newTag.getName());
                colorPane.setStyle("-fx-background-color: " + newTag.getColor());*/

                sourceModel.removeTag(tag);
                sourceModel.addTag(newTag);
                tag = newTag;
                sourceModel.sortTags();
                sourceModel.saveTags();

                sourceModel.removeArchTagWithId(tag.getId());
                sourceModel.addArchTag(newTag);
                sourceModel.getRwa().writeTagsToArchive(sourceModel.getTagArchList());

                tvC.refresh();
            } else {
                Tag newTag = new Tag(sourceModel.getHighestTagId(), etController.getTag().getColor(), etController.getTag().getName());
                tagbtn.setText(newTag.getName());
                colorPane.setStyle("-fx-background-color: " + newTag.getColor());
                sourceModel.addTag(newTag);

                for (Task task : sourceModel.getTaskList()) {
                    if (task.gettagIdList().contains(tag.getId())){
                        Integer removeId = tag.getId();
                        task.gettagIdList().remove(removeId);
                        Integer addId = newTag.getId();
                        task.gettagIdList().add(addId);
                    }
                }
                sourceModel.saveTasks();
                sourceModel.removeTag(tag);
                tag = newTag;
                sourceModel.sortTags();
                sourceModel.saveTags();
                tvC.refresh();
            }
        }

        if (etController.isDelete()){
            sourceModel.removeTag(tag);
            sourceModel.sortTags();
            sourceModel.saveTags();
            tvC.refresh();
        }

        if (etController.isArch()){
            sourceModel.archTag(tag);
            sourceModel.removeTag(tag);
            sourceModel.saveTags();
            sourceModel.getRwa().writeTagsToArchive(sourceModel.getTagArchList());
            tvC.refresh();
        }
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        tagbtn.setText(tag.getName());
        colorPane.setStyle("-fx-background-color: " + tag.getColor());
    }

    public void setTVC(TaskViewController tvC) {
        this.tvC = tvC;
    }
}
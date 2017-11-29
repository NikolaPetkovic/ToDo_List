package controller;

import classes.Tag;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArchTagController extends Stage implements Initializable {
    @FXML
    private Pane colorPane;
    @FXML
    private Button tagbtn;

    private Tag tag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    private void editTag(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showTag.fxml"));
        Pane pane = loader.load();
        ShowTagController showTagController = loader.getController();
        showTagController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        showTagController.setX(stage.getX()+50);
        showTagController.setY(stage.getY()+50);
        showTagController.setResizable(false);
        showTagController.setTag(tag);
        showTagController.initOwner(stage);
        showTagController.initModality(Modality.APPLICATION_MODAL);
        showTagController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        showTagController.showAndWait();
        stage.show();
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        tagbtn.setText(tag.getName());
        colorPane.setStyle("-fx-background-color: " + tag.getColor());
    }
}
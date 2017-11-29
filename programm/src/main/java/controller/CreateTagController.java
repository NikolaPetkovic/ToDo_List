package controller;

import classes.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SourceModel;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class CreateTagController extends Stage{
    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField nameTextField;

    private SourceModel sourceModel;
    private boolean isOkClicked;

    @FXML
    public void saveTag(javafx.event.ActionEvent event){
        Color color = colorPicker.getValue();
        int red = (int) (color.getRed()*255);
        int green = (int) (color.getGreen()*255);
        int blue = (int) (color.getBlue()*255);
        String hex = String.format("#%02X%02X%02X", red, green, blue);
        int id = sourceModel.getHighestTagId();
        String name = nameTextField.getText().replace(";", "");
        if(!hex.equals("") && !name.equals("")) {
            Tag tag = new Tag(id, hex, name);
            sourceModel.addTag(tag);
            sourceModel.sortTags();
            try {
                sourceModel.getRwa().writeTagsToFile(sourceModel.getTagList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) colorPicker.getScene().getWindow();
            stage.close();

            this.isOkClicked = true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText("Name is missing");
            alert.setContentText("Please fill out all necessary fields");

            alert.showAndWait();
        }
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public boolean isOkClicked(){
        return isOkClicked;
    }
    public void cancel(javafx.event.ActionEvent e) {
        this.close();
    }

}

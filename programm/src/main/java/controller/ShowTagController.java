package controller;

import classes.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SourceModel;

public class ShowTagController extends Stage{
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfColor;
    @FXML
    private Pane pnColor;

    private Tag tag;

    public void back (javafx.event.ActionEvent event){
        this.close();
    }

    public void setTag (Tag tag) {
        this.tag = tag;
        setContent();
    }

    private void setContent(){
        tfName.setText(tag.getName());
        tfColor.setText(tag.getColor());
        pnColor.setStyle("-fx-background-color: " + tag.getColor());
    }
}

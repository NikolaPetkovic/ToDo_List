package controller;

import classes.Date;
import classes.Tag;
import classes.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SortController extends Stage implements Initializable {
    @FXML
    private Label title;
    @FXML
    private ComboBox comboBox1, comboBox2;

    private boolean isOkClicked;
    private SourceModel sourceModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isOkClicked = false;
        title.setText("Sort");

        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("-", "Priority", "Deadline", "Tags");
        comboBox1.setItems(list);
        comboBox2.setItems(list);
    }

    public void setContent(){
        if (sourceModel.getSelect1().equals("Priority") || sourceModel.getSelect1().equals("Deadline") || sourceModel.getSelect1().equals("Tags")){
            comboBox1.getSelectionModel().select(sourceModel.getSelect1());
        } else {
            comboBox1.getSelectionModel().select("-");
        }

        if (sourceModel.getSelect2().equals("Priority") || sourceModel.getSelect2().equals("Deadline") || sourceModel.getSelect2().equals("Tags")){
            comboBox2.getSelectionModel().select(sourceModel.getSelect2());
        } else {
            comboBox2.getSelectionModel().select("-");
        }
    }

    @FXML
    public void sort() {
        if (!comboBox1.getSelectionModel().getSelectedItem().toString().equals("-")){
                if (!comboBox1.getSelectionModel().getSelectedItem().toString().equals(comboBox2.getSelectionModel().getSelectedItem().toString())){
                    try {
                        sourceModel.getRwa().setSortTyp(comboBox1.getSelectionModel().getSelectedItem().toString(), comboBox2.getSelectionModel().getSelectedItem().toString());
                        this.isOkClicked = true;
                        this.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("The first field is the same as the second field");
                    alert.setContentText("Please choose something different for the second field.");

                    alert.showAndWait();
                }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("First field is empty");
            alert.setContentText("Set something other han '-'");

            alert.showAndWait();
        }
    }

    @FXML
    public void cancel() {
        this.close();
    }

    public boolean isOkClicked(){
        return isOkClicked;
    }
    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }
}
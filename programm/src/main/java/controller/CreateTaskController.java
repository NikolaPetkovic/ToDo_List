package controller;

import classes.Date;
import classes.Tag;
import classes.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateTaskController extends Stage implements Initializable {
    @FXML
    private ComboBox priorityComboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private ComboBox tagsComboBox1;
    @FXML
    private ComboBox tagsComboBox2;
    @FXML
    private ComboBox tagsComboBox3;
    @FXML
    private ComboBox tagsComboBox4;
    @FXML
    private TextField deadlineHour;
    @FXML
    private TextField deadlineMinute;

    private SourceModel sourceModel;
    private boolean isOkClicked;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priorityComboBox.getItems().removeAll(priorityComboBox.getItems());
        priorityComboBox.getItems().addAll("1 - High", "2 - Medium", "3 - Low");
        isOkClicked = false;

        deadlineHour.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                deadlineHour.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (deadlineHour.getText().length() > 2) {
                String s = deadlineHour.getText().substring(0, 2);
                deadlineHour.setText(s);
            }
        });


        deadlineMinute.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                deadlineMinute.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (deadlineMinute.getText().length() > 2) {
                String s = deadlineMinute.getText().substring(0, 2);
                deadlineMinute.setText(s);
            }
        });


    }

    public void setContent(){
        ArrayList<String> tagsName = new ArrayList<>();

        for (Tag tag: sourceModel.getTagList()) {
            tagsName.add(tag.getName());
        }

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("-");
        list.addAll(tagsName);

        tagsComboBox1.setItems(list);
        tagsComboBox2.setItems(list);
        tagsComboBox3.setItems(list);
        tagsComboBox4.setItems(list);


        tagsComboBox1.getSelectionModel().select("-");
        tagsComboBox2.getSelectionModel().select("-");
        tagsComboBox3.getSelectionModel().select("-");
        tagsComboBox4.getSelectionModel().select("-");
    }

    @FXML
    public void saveTask() {

        try {
            String name = validateName();
            if(name.equals("")) {
                throw new NullPointerException("NullPointer");
            }

            String priority = priorityComboBox.getSelectionModel().getSelectedItem().toString();
            int prio = Integer.parseInt(priority.substring(0, 1));


            ArrayList<Integer> tagIdList = validateTags();

            Date creationDate = new classes.Date(LocalTime.now().getMinute(), LocalTime.now().getHour(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), false);

            Date deadline = validateDeadline();
            if(deadline == null){
                throw new NullPointerException("NullPointer");
            }

            try {
                Task task = new Task(name, deadline, null, creationDate, prio, false, tagIdList);
                System.out.println(task.toString());
                sourceModel.addTask(task);
                sourceModel.getRwa().writeTasksToFile(sourceModel.getTaskList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            isOkClicked = true;
            this.close();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Necessary fields are empty or wrong");
            alert.setContentText("Please correct them and fill these out");

            alert.showAndWait();
        }

    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    public boolean isOkClicked(){
        return isOkClicked;
    }

    public void cancel(ActionEvent e) {
        this.close();
    }

    private Date validateDeadline() {


        if(deadlineHour.getText() != null && deadlineMinute != null && !deadlineDatePicker.getValue().equals(null)) {
            LocalDate deadlineDate = deadlineDatePicker.getValue();

            LocalTime deadlineTime = LocalTime.of(Integer.parseInt(deadlineHour.getText()), Integer.parseInt(deadlineMinute.getText()));
            LocalDateTime deadlineDateTime = LocalDateTime.of(deadlineDate, deadlineTime);

            if (!deadlineDateTime.isBefore(LocalDateTime.now())) {

                LocalDate convertedDeadline2 = deadlineDate;
                classes.Date deadline = new classes.Date(Integer.parseInt(deadlineMinute.getText()), Integer.parseInt(deadlineHour.getText()), convertedDeadline2.getDayOfMonth(), convertedDeadline2.getMonthValue(), convertedDeadline2.getYear(), false);

                return deadline;
            }else{

            }

        }

        return null;
    }
    public ArrayList<Integer> validateTags (){
        ArrayList<Integer> tagIdList = new ArrayList<>();
        ArrayList<Tag> tagList = sourceModel.getTagList();

        String tag1 = tagsComboBox1.getSelectionModel().getSelectedItem().toString();
        if(!tag1.equals("-")){
            for (Tag tag : tagList ) {
                if(tag1.equals(tag.getName())) {
                    tagIdList.add(tag.getId());
                }
            }
        } else {
            tagIdList.add(0);
        }

        String tag2 = tagsComboBox2.getSelectionModel().getSelectedItem().toString();
        if(!tag2.equals("-")){
            for (Tag tag : tagList ) {
                if(tag2.equals(tag.getName())) {
                    tagIdList.add(tag.getId());
                }
            }
        } else {
            tagIdList.add(0);
        }

        String tag3 = tagsComboBox3.getSelectionModel().getSelectedItem().toString();
        if(!tag3.equals("-")){
            for (Tag tag : tagList ) {
                if(tag3.equals(tag.getName())) {
                    tagIdList.add(tag.getId());
                }
            }
        } else {
            tagIdList.add(0);
        }

        String tag4 = tagsComboBox4.getSelectionModel().getSelectedItem().toString();
        if(!tag4.equals("-")){
            for (Tag tag : tagList ) {
                if(tag4.equals(tag.getName())) {
                    tagIdList.add(tag.getId());
                }
            }
        } else {
            tagIdList.add(0);
        }
        return tagIdList;
    }
    public String validateName(){
        String name;
        if(nameTextField.getText().length() >=4 && nameTextField.getText().length() < 40){
            name = nameTextField.getText();
            name.replace(";","");
            nameTextField.setStyle("-fx-border-color:green; -fx-border-width:2; -fx-border-radius:2;");
            return name;
        }else{
            nameTextField.setStyle("-fx-border-color:red; -fx-border-width:2; -fx-border-radius:2;");
            return null;
        }

    }
}


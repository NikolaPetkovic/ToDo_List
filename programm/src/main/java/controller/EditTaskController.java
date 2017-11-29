package controller;

import classes.Tag;
import classes.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.SourceModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditTaskController extends Stage implements Initializable {
    @FXML
    private Label title;
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

    private boolean save;
    private boolean delete;
    private SourceModel sourceModel;
    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priorityComboBox.getItems().removeAll(priorityComboBox.getItems());
        priorityComboBox.getItems().addAll("1 - High", "2 - Medium", "3 - Low");
        save = false;
        delete = false;
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
        String priority = priorityComboBox.getSelectionModel().getSelectedItem().toString();
        int prio = Integer.parseInt(priority.substring(0,1));

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

        java.sql.Date convertedDeadline = java.sql.Date.valueOf(deadlineDatePicker.getValue());
        LocalDate convertedDeadline2 = convertedDeadline.toLocalDate();
        classes.Date deadline = new classes.Date(Integer.parseInt(deadlineMinute.getText()),Integer.parseInt(deadlineHour.getText()), convertedDeadline2.getDayOfMonth(), convertedDeadline2.getMonthValue(),convertedDeadline2.getYear(),false );

        if (!nameTextField.getText().equals(task.getName())){
            task.setName(nameTextField.getText());
        }

        if (!deadline.equals(task.getDeadline())){
            task.setDeadline(deadline);
        }

        if (prio != task.getPriority()){
            task.setPriority(prio);
        }

        if (!compareArraylists(task.gettagIdList(), tagIdList)){
            task.settagIdList(tagIdList);
        }
        save = true;
        System.out.println(task.toString());
        this.close();
    }

    public void delTask(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Task");
        alert.setHeaderText("Do you want to delete this Task?");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == buttonTypeOne) {
            delete = true;
            this.close();
        }
    }

    @FXML
    public void cancelTask() {
        this.close();
    }

    public void setTask(SourceModel sourceModel, Task task) {
        this.sourceModel = sourceModel;
        this.task = task;
        setContent();
        title.setText(task.getName());
        nameTextField.setText(task.getName());
        deadlineDatePicker.setValue(LocalDate.of(task.getDeadline().getYear(), task.getDeadline().getMonth(), task.getDeadline().getDay()));
        deadlineMinute.setText(task.getDeadline().getMinute() + "");
        deadlineHour.setText(task.getDeadline().getHour() + "");
        if (task.getPriority() == 1) {
            priorityComboBox.getSelectionModel().select("1 - High");
        } else if (task.getPriority() == 2) {
            priorityComboBox.getSelectionModel().select("2 - Medium");
        } else if (task.getPriority() == 3) {
            priorityComboBox.getSelectionModel().select("3 - Low");
        }
        for (int i = 0; i < 4; i++) {
            for (Tag tag: sourceModel.getTagList()) {
                if (task.gettagIdList().get(i) == tag.getId()){
                    if (i == 0){
                        tagsComboBox1.getSelectionModel().select(tag.getName());
                    } else if (i == 1){
                        tagsComboBox2.getSelectionModel().select(tag.getName());
                    } else if (i == 2){
                        tagsComboBox3.getSelectionModel().select(tag.getName());
                    } else if (i == 3){
                        tagsComboBox4.getSelectionModel().select(tag.getName());
                    }
                } else if (tag.getId() == 0) {
                    if (i == 0){
                        tagsComboBox1.getSelectionModel().select("-");
                    } else if (i == 1){
                        tagsComboBox2.getSelectionModel().select("-");
                    } else if (i == 2){
                        tagsComboBox3.getSelectionModel().select("-");
                    } else if (i == 3){
                        tagsComboBox4.getSelectionModel().select("-");
                    }
                }
            }
        }
    }

    public boolean isOkClicked(){
        return save;
    }

    public boolean isDelete(){ return delete; }

    public Task getTask(){
        return task;
    }

    private boolean compareArraylists(ArrayList<Integer> old, ArrayList<Integer> now){
        for(Integer tag : old) {
            if(!now.contains(tag)) return false;
        }
        for(Integer tag : now) {
            if(!old.contains(tag)) return false;
        }
        return true;
    }
}
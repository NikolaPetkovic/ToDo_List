package controller;

import classes.Date;
import classes.ReTask;
import classes.Tag;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.SourceModel;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditReTaskController extends Stage implements Initializable {
    @FXML
    private DatePicker endDateField;
    @FXML
    private TextField nameTextField;

    @FXML
    private CheckBox monCheck;
    @FXML
    private CheckBox tueCheck;
    @FXML
    private CheckBox wedCheck;
    @FXML
    private CheckBox thuCheck;
    @FXML
    private CheckBox friCheck;

    @FXML
    private TextField monTimeHour;
    @FXML
    private TextField tueTimeHour;
    @FXML
    private TextField wedTimeHour;
    @FXML
    private TextField thuTimeHour;
    @FXML
    private TextField friTimeHour;

    @FXML
    private TextField monTimeMinute;
    @FXML
    private TextField tueTimeMinute;
    @FXML
    private TextField wedTimeMinute;
    @FXML
    private TextField thuTimeMinute;
    @FXML
    private TextField friTimeMinute;

    @FXML
    private ComboBox tagsComboBox1;
    @FXML
    private ComboBox tagsComboBox2;
    @FXML
    private ComboBox tagsComboBox3;
    @FXML
    private ComboBox tagsComboBox4;

    private boolean save;
    private boolean delete;
    private SourceModel sourceModel;
    private ReTask reTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        save = false;
        delete = false;
        monTimeHour.disableProperty().bind(Bindings.not(monCheck.selectedProperty()));
        monTimeMinute.disableProperty().bind(Bindings.not(monCheck.selectedProperty()));
        setTextListener(monTimeHour);
        setTextListener(monTimeMinute);
        tueTimeHour.disableProperty().bind(Bindings.not(tueCheck.selectedProperty()));
        tueTimeMinute.disableProperty().bind(Bindings.not(tueCheck.selectedProperty()));
        setTextListener(tueTimeHour);
        setTextListener(tueTimeMinute);
        wedTimeHour.disableProperty().bind(Bindings.not(wedCheck.selectedProperty()));
        wedTimeMinute.disableProperty().bind(Bindings.not(wedCheck.selectedProperty()));
        setTextListener(wedTimeHour);
        setTextListener(wedTimeMinute);
        thuTimeHour.disableProperty().bind(Bindings.not(thuCheck.selectedProperty()));
        thuTimeMinute.disableProperty().bind(Bindings.not(thuCheck.selectedProperty()));
        setTextListener(thuTimeHour);
        setTextListener(thuTimeMinute);
        friTimeHour.disableProperty().bind(Bindings.not(friCheck.selectedProperty()));
        friTimeMinute.disableProperty().bind(Bindings.not(friCheck.selectedProperty()));
        setTextListener(friTimeHour);
        setTextListener(friTimeMinute);
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
    public void saveReTask() {
        try {
            String name = validateName();
            if(name.equals("")) {
                throw new NullPointerException("NullPointer");
            }

            ArrayList<Integer> tagIdList = validateTags();

            Date endDate = validateEndDate();
            if(endDate == null){
                throw new NullPointerException("NullPointer");
            }

            ArrayList<Date> datesFinal = validateDates();
            if(datesFinal.size()== 0){
                throw new NullPointerException("NullPointer");
            }

            if (!nameTextField.getText().equals(reTask.getName())){
                reTask.setName(nameTextField.getText());
            }
            if (!endDate.equals(reTask.getCompletionDate())){
                reTask.setCompletionDate(endDate);
            }
            if (!datesFinal.equals(reTask.getDates())){
                reTask.setDates(datesFinal);
            }
            if (!compareArraylists(reTask.getTagList(), tagIdList)){
                reTask.setTagList(tagIdList);
            }


            save = true;
            System.out.println(reTask.toString());
            this.close();

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Necessary fields are empty or wrong");
            alert.setContentText("Please correct them and fill these out");

            alert.showAndWait();
        }


    }

    public void delReTask(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete ReTask");
        alert.setHeaderText("Do you want to delete this ReTask?");

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

    public void setReTask(SourceModel sourceModel, ReTask reTask) {
        this.sourceModel = sourceModel;
        this.reTask = reTask;
        setContent();
        nameTextField.setText(reTask.getName());
        endDateField.setValue(LocalDate.of(reTask.getCompletionDate().getYear(), reTask.getCompletionDate().getMonth(), reTask.getCompletionDate().getDay()));
        for (Date date: reTask.getDates()) {
            LocalDateTime newDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMinute());
            LocalTime lc = newDate.toLocalTime();
            if(newDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                monCheck.setSelected(true);
                monTimeHour.setText(lc.toString().substring(0,2));
                monTimeMinute.setText(lc.toString().substring(3,5));
            }
            if(newDate.getDayOfWeek() == DayOfWeek.TUESDAY) {
                tueCheck.setSelected(true);
                tueTimeHour.setText(lc.toString().substring(0,2));
                tueTimeMinute.setText(lc.toString().substring(3,5));
            }
            if(newDate.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                wedCheck.setSelected(true);
                wedTimeHour.setText(lc.toString().substring(0,2));
                wedTimeMinute.setText(lc.toString().substring(3,5));
            }
            if(newDate.getDayOfWeek() == DayOfWeek.THURSDAY) {
                thuCheck.setSelected(true);
                thuTimeHour.setText(lc.toString().substring(0,2));
                thuTimeMinute.setText(lc.toString().substring(3,5));
            }
            if(newDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                friCheck.setSelected(true);
                friTimeHour.setText(lc.toString().substring(0,2));
                friTimeMinute.setText(lc.toString().substring(3,5));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (Tag tag: sourceModel.getTagList()) {
                if (reTask.getTagList().get(i) == tag.getId()){
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

    public ReTask getReTask(){
        return reTask;
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
    private Date validateEndDate() {


        if(!endDateField.getValue().equals(null)) {
            LocalDate deadlineDate = endDateField.getValue();



            if (!deadlineDate.isBefore(LocalDate.now())) {

                //java.sql.Date convertedDeadline = java.sql.Date.valueOf();
                LocalDate convertedDeadline2 = deadlineDate;
                classes.Date deadline = new classes.Date(59, 23, convertedDeadline2.getDayOfMonth(), convertedDeadline2.getMonthValue(), convertedDeadline2.getYear(), false);

                return deadline;
            }else{

            }

        }

        return null;
    }
    private ArrayList<Integer> validateTags (){
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
    private String validateName(){
        String name;
        if(nameTextField.getText().length() >=4 && nameTextField.getText().length() < 40){
            name = nameTextField.getText();
            name.replace(";","");
            //nameTextField.setStyle("-fx-border-color:green; -fx-border-width:2; -fx-border-radius:2;");
            return name;
        }else{
            //nameTextField.setStyle("-fx-border-color:red; -fx-border-width:2; -fx-border-radius:2;");
            return null;
        }

    }
    private ArrayList<Date> validateDates(){
        java.sql.Date convertedCompletionDate = java.sql.Date.valueOf(endDateField.getValue());
        LocalDate convertedCompletionDate2 = convertedCompletionDate.toLocalDate();

        LocalDate start = LocalDate.now();
        LocalDate end = convertedCompletionDate2;

        ArrayList<Date> datesFinal = new ArrayList<>();

        if (!monTimeHour.isDisable()) {
            LocalDate nextOrSameMonday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
            // Loop while we have a day in hand (non-null) AND that friday is not after our stop date (isBefore or isEqual the stop date).
            while ((null != nextOrSameMonday) & (!nextOrSameMonday.isAfter(end))) {
                LocalTime test = LocalTime.of(Integer.parseInt(monTimeHour.getText()),Integer.parseInt(monTimeMinute.getText()));
                Date date = new Date(Integer.parseInt(monTimeMinute.getText()), Integer.parseInt(monTimeHour.getText()), nextOrSameMonday.getDayOfMonth(), nextOrSameMonday.getMonthValue(), nextOrSameMonday.getYear(), false);
                datesFinal.add(date);  //  Remember this day.
                nextOrSameMonday = nextOrSameMonday.plusWeeks(1);  // Move to the next day, setting up for next iteration of this loop.
            }
        }
        if (!tueTimeHour.isDisable()) {
            LocalDate nextOrSameTuesday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
            // Loop while we have a day in hand (non-null) AND that day is not after our stop date (isBefore or isEqual the stop date).
            while ((null != nextOrSameTuesday) & (!nextOrSameTuesday.isAfter(end))) {
                LocalTime test = LocalTime.of(Integer.parseInt(tueTimeHour.getText()),Integer.parseInt(tueTimeMinute.getText()));
                Date date = new Date(Integer.parseInt(tueTimeMinute.getText()), Integer.parseInt(tueTimeHour.getText()), nextOrSameTuesday.getDayOfMonth(), nextOrSameTuesday.getMonthValue(), nextOrSameTuesday.getYear(), false);
                datesFinal.add(date);  //  Remember this day.
                nextOrSameTuesday = nextOrSameTuesday.plusWeeks(1);  // Move to the next day, setting up for next iteration of this loop.
            }
        }
        if (!wedTimeHour.isDisable()) {
            LocalDate nextOrSameWednesday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
            // Loop while we have a day in hand (non-null) AND that day is not after our stop date (isBefore or isEqual the stop date).
            while ((null != nextOrSameWednesday) & (!nextOrSameWednesday.isAfter(end))) {
                LocalTime test = LocalTime.of(Integer.parseInt(wedTimeHour.getText()),Integer.parseInt(wedTimeMinute.getText()));
                Date date = new Date(Integer.parseInt(wedTimeMinute.getText()), Integer.parseInt(wedTimeHour.getText()), nextOrSameWednesday.getDayOfMonth(), nextOrSameWednesday.getMonthValue(), nextOrSameWednesday.getYear(), false);
                datesFinal.add(date);  //  Remember this day.
                nextOrSameWednesday = nextOrSameWednesday.plusWeeks(1);  // Move to the next day, setting up for next iteration of this loop.
            }
        }
        if (!thuTimeHour.isDisable()) {
            LocalDate nextOrSameThursday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
            // Loop while we have a day in hand (non-null) AND that day is not after our stop date (isBefore or isEqual the stop date).
            while ((null != nextOrSameThursday) & (!nextOrSameThursday.isAfter(end))) {
                LocalTime test = LocalTime.of(Integer.parseInt(thuTimeHour.getText()),Integer.parseInt(thuTimeMinute.getText()));
                Date date = new Date(Integer.parseInt(thuTimeMinute.getText()), Integer.parseInt(thuTimeHour.getText()), nextOrSameThursday.getDayOfMonth(), nextOrSameThursday.getMonthValue(), nextOrSameThursday.getYear(), false);
                datesFinal.add(date);  //  Remember this day.
                nextOrSameThursday = nextOrSameThursday.plusWeeks(1);  // Move to the next day, setting up for next iteration of this loop.
            }
        }
        if (!friTimeHour.isDisable()) {
            LocalDate nextOrSameFriday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
            // Loop while we have a day in hand (non-null) AND that day is not after our stop date (isBefore or isEqual the stop date).
            while ((null != nextOrSameFriday) & (!nextOrSameFriday.isAfter(end))) {
                LocalTime test = LocalTime.of(Integer.parseInt(friTimeHour.getText()),Integer.parseInt(friTimeMinute.getText()));
                Date date = new Date(Integer.parseInt(friTimeMinute.getText()), Integer.parseInt(friTimeHour.getText()), nextOrSameFriday.getDayOfMonth(), nextOrSameFriday.getMonthValue(), nextOrSameFriday.getYear(), false);
                datesFinal.add(date);  //  Remember this day.
                nextOrSameFriday = nextOrSameFriday.plusWeeks(1);  // Move to the next day, setting up for next iteration of this loop.
            }
        }

        for (Date date : datesFinal) {
            System.out.println(date.toString());
        }
        return datesFinal;
    }
    private void setTextListener (TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (tf.getText().length() > 2) {
                    String s = tf.getText().substring(0, 2);
                    tf.setText(s);
                }
            }
        });
    }
}
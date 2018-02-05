package controller;

import classes.Printer;
import classes.ReTask;
import classes.Tag;
import classes.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.SourceModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class PrintController extends Stage implements Initializable {
    private SourceModel sourceModel;
    private Boolean printReTask = false;
    private Boolean printArchive = false;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private ComboBox tagComboBox;
    private  LocalDate fromDate = null;
    private  LocalDate toDate = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void print(){
            Printer p = new Printer();
            p.setPrintController(this);
        if(printReTask){
            p.printData(sortOutTasks(reTaskToTask(sourceModel.getReTaskList()),sourceModel.getTagList()),sourceModel.getTagList());
        }else if(printArchive) {
            p.printData(sortOutTasks(sourceModel.getTaskArchList(), sourceModel.getTagArchList()), sourceModel.getTagArchList());
            cancel();
        }
        else  {
            p.printData(sortOutTasks(sourceModel.getTaskList(),sourceModel.getTagList()), sourceModel.getTagList());
            cancel();
        }
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;

    }
    private ArrayList<Task> sortOutTasks(ArrayList<Task> arrayList, ArrayList<Tag> tagList){

        ArrayList<Task> list = new ArrayList<>();
        if(!fromDatePicker.getValue().equals(null)) {
            fromDate = fromDatePicker.getValue();
        }

        if(!toDatePicker.getValue().equals(null)) {
            toDate = toDatePicker.getValue();
        }
        for (Task task: arrayList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            formatter.withLocale(Locale.GERMAN);
            String text = task.getDeadline().toString(true,false);
            LocalDate parsedDate = LocalDate.parse(text,formatter);

            if(parsedDate.isAfter(fromDate) && parsedDate.isBefore(toDate))
            {
                if(!tagComboBox.getValue().equals("-")){
                   Tag tag = validateTag(tagList);
                   if(task.gettagIdList().contains(tag.getId())){
                       list.add(task);
                   }
                }else {
                    list.add(task);
                }

            }
        }

        return list;
    }

    public ArrayList<Task> reTaskToTask(ArrayList<ReTask> reTaskList) {
        ArrayList<Task> taskList = new ArrayList<>();
        for (ReTask reTask: reTaskList) {
            taskList.addAll(reTask.toTask());
        }


        return taskList;
    }

    public void setContent(){
        ArrayList<String> tagsName = new ArrayList<>();

        if(printArchive){
            if(sourceModel.getTagArchList().size()!= 0) {
                for (Tag tag : sourceModel.getTagArchList()) {
                    tagsName.add(tag.getName());
                }
            }
        }else {
            if(sourceModel.getTagList().size()!=0) {
                for (Tag tag : sourceModel.getTagList()) {
                    tagsName.add(tag.getName());
                }
            }

        }
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("-");
        list.addAll(tagsName);
        tagComboBox.setItems(list);
        tagComboBox.getSelectionModel().select("-");


    }
    public void setPrintReTask(){
        printReTask = true;
    }
    public void setPrintArchive(){
        printArchive = true;
    }

    public void cancel() {
        this.close();
    }
    private Tag validateTag(ArrayList<Tag> tagList) {
        String tag1 = tagComboBox.getSelectionModel().getSelectedItem().toString();

        for (Tag tag : tagList) {
            if (tag1.equals(tag.getName())) {
                return tag;
            }
        }
        return null;
    }

    public String getFromDate(){
        String s = fromDate.getDayOfMonth()+"."+fromDate.getMonthValue()+"."+fromDate.getYear();
        return s;
    }
    public String getToDate(){
        String s = toDate.getDayOfMonth()+"."+toDate.getMonthValue()+"."+toDate.getYear();
        return s;
    }


}

package controller;

import classes.Date;
import classes.ReTask;
import classes.Tag;
import classes.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskViewController extends Stage  implements Initializable{
    @FXML
    private ScrollPane mondayPane, tuesdayPane, wednesdayPane, thursdayPane, fridayPane;

    @FXML
    private Label weekDateLbl, sortLabel;
    @FXML
    private ScrollPane taskScrollPane;
    @FXML
    private ScrollPane tagScrollPane;

    private SourceModel sourceModel;
    private String week;
    private boolean back;

    public void openReTaskCreationUI(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createReTask.fxml"));
        Pane pane = loader.load();
        CreateReTaskController createReTaskController = loader.getController();
        createReTaskController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        createReTaskController.setX(stage.getX()+50);
        createReTaskController.setY(stage.getY()+50);
        createReTaskController.setResizable(false);
        createReTaskController.setModel(sourceModel);
        createReTaskController.setContent();
        createReTaskController.initOwner(stage);
        createReTaskController.initModality(Modality.APPLICATION_MODAL);
        createReTaskController.setTitle("Create ReTask");
        createReTaskController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        createReTaskController.showAndWait();

        if (createReTaskController.isOkClicked()) {
            setReTasks();
        }

        stage.show();
    }
    public void openTaskCreationUI(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createTask.fxml"));
        Pane pane = loader.load();
        CreateTaskController createTaskController;
        createTaskController = loader.getController();
        createTaskController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        createTaskController.setX(stage.getX()+50);
        createTaskController.setY(stage.getY()+50);
        createTaskController.setResizable(false);
        createTaskController.setModel(sourceModel);
        createTaskController.setContent();
        createTaskController.initOwner(stage);
        createTaskController.initModality(Modality.APPLICATION_MODAL);
        createTaskController.setTitle("Create Task");
        createTaskController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        createTaskController.showAndWait();

        stage.show();

        if (createTaskController.isOkClicked()) {
            setTasks();
        }
    }

    public void openTagCreationUI(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createTag.fxml"));
        Pane pane = loader.load();
        CreateTagController createTagController = loader.getController();
        createTagController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        createTagController.setX(stage.getX()+50);
        createTagController.setY(stage.getY()+50);
        createTagController.setResizable(false);
        createTagController.setModel(sourceModel);
        createTagController.initOwner(stage);
        createTagController.initModality(Modality.APPLICATION_MODAL);
        createTagController.setTitle("Create Tag");
        createTagController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        createTagController.showAndWait();

        stage.show();

        if (createTagController.isOkClicked()) {
            setTags();
        }
    }
    public void openPrintTaskUI(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/printTasks.fxml"));
        Pane pane = loader.load();
        PrintController printController = loader.getController();
        printController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        printController.setX(stage.getX()+50);
        printController.setY(stage.getY()+50);
        printController.setResizable(false);
        printController.setModel(sourceModel);
        printController.setContent();
        printController.initOwner(stage);
        printController.initModality(Modality.APPLICATION_MODAL);
        printController.setTitle("Print Task");
        printController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        printController.showAndWait();

        stage.show();
    }
    public void openPrintReTaskUI(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/printTasks.fxml"));
        Pane pane = loader.load();
        PrintController printController = loader.getController();
        printController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        printController.setX(stage.getX()+50);
        printController.setY(stage.getY()+50);
        printController.setResizable(false);
        printController.setModel(sourceModel);
        printController.setContent();
        printController.setPrintReTask();
        printController.initOwner(stage);
        printController.initModality(Modality.APPLICATION_MODAL);
        printController.setTitle("Print ReTask");
        printController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        printController.showAndWait();

        stage.show();
    }

    public void refresh() throws IOException{
        sourceModel.sortTasks();
        setTasks();
        setTags();
        setReTasks();
        setSortText();
    }

    public void sort (ActionEvent event) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sortView.fxml"));
        Pane pane = loader.load();
        SortController sortController = loader.getController();
        sortController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        sortController.setX(stage.getX()+50);
        sortController.setY(stage.getY()+50);
        sortController.setResizable(false);
        sortController.setModel(sourceModel);
        sortController.setContent();
        sortController.initOwner(stage);
        sortController.initModality(Modality.APPLICATION_MODAL);
        sortController.setTitle("Sort Settings");
        sortController.getIcons().add(new Image("/txt/ProgramIcon.png"));
        sortController.showAndWait();

        stage.show();

        if (sortController.isOkClicked()) {
            refresh();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        week = Date.getCurrentWorkweek();
        weekDateLbl.setText(week);
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    private void setTasks(){
        GridPane paneForScrollPane = new GridPane();

        int y = 0;
        int x = 0;

        for (Task task : sourceModel.getTaskList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
            Pane pane = new Pane();
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TaskController taskController = loader.getController();
            taskController.setTask(task);
            taskController.setModel(sourceModel);
            taskController.showTags();
            taskController.setTVC(this);

            if (x == 6){
                y++;
                x = 0;
            }
            paneForScrollPane.add(pane, x, y);

            x++;
        }
        taskScrollPane.setContent(paneForScrollPane);
    }

    private void setTags(){
        GridPane paneForScrollPane = new GridPane();

        int y = 0;
        int x = 0;

        for (Tag tag : sourceModel.getTagList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tag.fxml"));
            Pane pane = new Pane();
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TagController tagController = loader.getController();
            tagController.setTag(tag);
            tagController.setModel(sourceModel);
            tagController.setTVC(this);

            if (y == 2){
                x++;
                y = 0;
            }
            paneForScrollPane.add(pane, x, y);

            y++;
        }

        tagScrollPane.setContent(paneForScrollPane);
    }

    private void setReTasks(){
        VBox vBoxMon = new VBox(8);
        VBox vBoxTue = new VBox(8);
        VBox vBoxWed = new VBox(8);
        VBox vBoxThu = new VBox(8);
        VBox vBoxFri = new VBox(8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        for (ReTask reTask: sourceModel.getReTaskList()) {

            for (Date date : reTask.getDates() ) {
                LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMinute());

                LocalDate event = localDateTime.toLocalDate();

                LocalDate monday = LocalDate.parse(week.substring(0,8),formatter);
                LocalDate friday = monday.with(DayOfWeek.FRIDAY);

                if (event.compareTo(monday) >= 0 && event.compareTo(friday) <= 0) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reTask.fxml"));
                    Pane pane = new Pane();
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ReTaskController reTaskController = loader.getController();
                    reTaskController.setReTask(reTask, date);
                    reTaskController.setModel(sourceModel);
                    reTaskController.showTags();
                    reTaskController.setTVC(this);

                    if (localDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
                        vBoxMon.getChildren().addAll(pane);
                    }
                    if (localDateTime.getDayOfWeek() == DayOfWeek.TUESDAY) {
                        vBoxTue.getChildren().addAll(pane);
                    }
                    if (localDateTime.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                        vBoxWed.getChildren().addAll(pane);
                    }
                    if (localDateTime.getDayOfWeek() == DayOfWeek.THURSDAY) {
                        vBoxThu.getChildren().addAll(pane);
                    }
                    if (localDateTime.getDayOfWeek() == DayOfWeek.FRIDAY) {
                        vBoxFri.getChildren().addAll(pane);
                    }
                }

            }
            mondayPane.setContent(vBoxMon);
            tuesdayPane.setContent(vBoxTue);
            wednesdayPane.setContent(vBoxWed);
            thursdayPane.setContent(vBoxThu);
            fridayPane.setContent(vBoxFri);

        }
    }

    private void setSortText(){
        if(sourceModel.getSelect2().equals("-")){
            sortLabel.setText("Sort by: " + sourceModel.getSelect1());
        } else {
            sortLabel.setText("Sort by: " + sourceModel.getSelect1() + " & " + sourceModel.getSelect2());
        }
    }

    @FXML
    private void nextWeek(){
        week = Date.getNextWorkweek(week);
        weekDateLbl.setText(week);
        try {
            refresh();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    private void prevWeek(){
        week = Date.getPreviousWorkweek(week);
        weekDateLbl.setText(week);
        try {
            refresh();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void back(){
        back = true;
        this.close();
    }

    public Boolean getBack() {
        return back;
    }
}
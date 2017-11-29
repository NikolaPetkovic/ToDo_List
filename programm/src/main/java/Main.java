import classes.*;
import controller.ArchiveViewController;
import controller.SourceViewController;
import controller.TaskViewController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SourceModel;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    private ReaderWriterArchivist rwa = new ReaderWriterArchivist();
    private SourceModel sourceModel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        primaryStage.setTitle("Willkomen");
        primaryStage.setScene(new Scene(root, 400, 300));
        //primaryStage.setResizable(false);
        System.out.println(rwa.getFileNameFromFile("Source"));
        primaryStage.getIcons().add(new Image("/txt/ProgramIcon.png"));
        primaryStage.setMinWidth(416);
        primaryStage.setMinHeight(338);
        primaryStage.setMaxWidth(1600);
        primaryStage.setMaxHeight(1200);
        primaryStage.show();
    }

    public void getSource(ActionEvent event) {
        try {
            Stage primaryStage = (Stage)((Button)event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sourceView.fxml"));
            Pane pane = loader.load();

            SourceViewController sourceController = loader.getController();
            sourceController.setScene(new Scene(pane, 500, 325));
            sourceController.setX(primaryStage.getX()-100);
            sourceController.setY(primaryStage.getY()-50);
            sourceController.setMinWidth(516);
            sourceController.setMinHeight(363);
            sourceController.setMaxWidth(2000);
            sourceController.setMaxHeight(1300);
            //sourceController.setResizable(false);
            //sourceController.sizeToScene();
            sourceController.setModel(sourceModel);
            sourceController.initOwner(primaryStage);
            sourceController.initModality(Modality.APPLICATION_MODAL);

            primaryStage.hide();

            sourceController.getIcons().add(new Image("/txt/ProgramIcon.png"));
            sourceController.showAndWait();

            System.out.println(sourceController.getHeight());
            System.out.println(sourceController.getWidth());

            if (sourceController.getBack()){
                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTaskView(ActionEvent event) throws IOException {
        if (rwa.getFileNameFromFile("Source").equals("no")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Source not found");
            alert.setHeaderText("No Source folder found");
            alert.setContentText("Please choose a source folder via the Set Repository button");
            alert.showAndWait();
        } else {
            sourceModel.setTagList(sourceModel.getRwa().readTagsFromFile());
            sourceModel.setTaskList(sourceModel.getRwa().readTasksFromFile());
            sourceModel.setTagArchList(sourceModel.getRwa().readTagsFromArchive());
            sourceModel.setTaskArchList(sourceModel.getRwa().readTasksFromArchive());
            sourceModel.setReTaskList(sourceModel.getRwa().readReTasksFromFile());

            Stage primaryStage = (Stage)((Button)event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/taskView.fxml"));
            Pane pane = loader.load();

            TaskViewController taskViewController = loader.getController();
            taskViewController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
            taskViewController.setX(primaryStage.getX()-150);
            taskViewController.setY(primaryStage.getY()-100);
            taskViewController.setResizable(false);
            taskViewController.sizeToScene();
            taskViewController.initOwner(primaryStage);
            taskViewController.initModality(Modality.APPLICATION_MODAL);
            taskViewController.setModel(sourceModel);
            taskViewController.refresh();

            primaryStage.hide();

            taskViewController.getIcons().add(new Image("/txt/ProgramIcon.png"));
            taskViewController.setTitle("ToDo-List");
            taskViewController.showAndWait();

            if (taskViewController.getBack()){
                primaryStage.show();
            }
        }
    }

    public void startArchView(ActionEvent event) throws IOException {
        if (rwa.getFileNameFromFile("Source").equals("no")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Source not found");
            alert.setHeaderText("No Source folder found");
            alert.setContentText("Please choose a source folder via the Set Repository button");
            alert.showAndWait();
        } else {
            sourceModel.setTagList(sourceModel.getRwa().readTagsFromFile());
            sourceModel.setTaskList(sourceModel.getRwa().readTasksFromFile());
            sourceModel.setTagArchList(sourceModel.getRwa().readTagsFromArchive());
            sourceModel.setTaskArchList(sourceModel.getRwa().readTasksFromArchive());

            Stage primaryStage = (Stage)((Button)event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/archiveView.fxml"));
            Pane pane = loader.load();

            ArchiveViewController archiveViewController = loader.getController();
            archiveViewController.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
            archiveViewController.setX(primaryStage.getX()-150);
            archiveViewController.setY(primaryStage.getY()-100);
            archiveViewController.setMaxHeight(550);
            archiveViewController.setMaxWidth(740);

            archiveViewController.setResizable(false);
            archiveViewController.setModel(sourceModel);
            archiveViewController.initOwner(primaryStage);
            archiveViewController.initModality(Modality.APPLICATION_MODAL);
            archiveViewController.refresh(new ActionEvent());

            primaryStage.hide();

            archiveViewController.getIcons().add(new Image("/txt/ProgramIcon.png"));
            archiveViewController.setTitle("Archive");
            archiveViewController.showAndWait();

            if (archiveViewController.getBack()){
                primaryStage.show();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<Tag> tagList = new ArrayList<>();
            ArrayList<Task> taskList = new ArrayList<>();
            ArrayList<ReTask> reTaskList = new ArrayList<>();
            ArrayList<Tag> tagArchList = new ArrayList<>();
            ArrayList<Task> taskArchList = new ArrayList<>();


            if (rwa.getFileNameFromFile("Source").equals("yes")){
                String sourceUrl ="/txt/source.txt";
                URL url = getClass().getResource("/txt/source.txt");
                File file;

                ArrayList<String> repo = new ArrayList<>();

                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
                String line;
                while ((line = br.readLine()) != null) {
                    repo.add(line);
                    System.out.println(line);
                }
                br.close();

                File dir = new File(rwa.getFileNameFromFile("Dir1"));
                if (!dir.exists()){
                    System.out.println("dir1");

                    try {
                        file = new File(url.toURI().getPath());
                        PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
                        pw.println("no");
                        pw.println("");
                        pw.println("");
                        pw.println("");
                        pw.println("");
                        pw.println(repo.get(5));
                        pw.println(repo.get(6));
                        pw.println("");
                        pw.println(repo.get(8));
                        pw.println(repo.get(9));
                        pw.println(repo.get(10));
                        pw.close();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

                repo.clear();

                br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
                while ((line = br.readLine()) != null) {
                    repo.add(line);
                    System.out.println(line);
                }
                br.close();

                dir = new File(rwa.getFileNameFromFile("Dir2"));
                if (!dir.exists()){
                    System.out.println("dir2");

                    try {
                        file = new File(url.toURI().getPath());
                        PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
                        pw.println("no");
                        pw.println(repo.get(1));
                        pw.println(repo.get(2));
                        pw.println(repo.get(3));
                        pw.println(repo.get(4));
                        pw.println("");
                        pw.println("");
                        pw.println(repo.get(7));
                        pw.println("");
                        pw.println(repo.get(9));
                        pw.println(repo.get(10));
                        pw.close();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (rwa.getFileNameFromFile("Source").equals("no")){
                sourceModel = new SourceModel("","",rwa, tagList, taskList ,reTaskList, tagArchList, taskArchList);

            } else {
                sourceModel = new SourceModel(rwa.getFileNameFromFile("Dir1"),rwa.getFileNameFromFile("Dir2"),rwa, tagList,taskList,reTaskList, tagArchList, taskArchList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
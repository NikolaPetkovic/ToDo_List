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

        primaryStage.setTitle("Menu");
        primaryStage.getIcons().add(new Image("/txt/ProgramIcon.png"));
        primaryStage.setScene(new Scene(root, 400, 300));

        primaryStage.setMinWidth(416);
        primaryStage.setMinHeight(338);
        primaryStage.setMaxWidth(1616);
        primaryStage.setMaxHeight(1238);
        //primaryStage.setResizable(false);

        primaryStage.show();
    }

    public void getSource(ActionEvent event) {
        try {
            Stage primaryStage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sourceView.fxml"));
            Pane pane = loader.load();
            SourceViewController sourceController = loader.getController();

            sourceController.setTitle("Select File");
            sourceController.getIcons().add(new Image("/txt/ProgramIcon.png"));
            sourceController.setScene(new Scene(pane, 500, 310));

            sourceController.setX(primaryStage.getX()-100);
            sourceController.setY(primaryStage.getY()-50);
            sourceController.setMinWidth(516);
            sourceController.setMinHeight(348);
            sourceController.setMaxWidth(1516);
            sourceController.setMaxHeight(968);

            sourceController.setModel(sourceModel);
            sourceController.initOwner(primaryStage);
            sourceController.initModality(Modality.APPLICATION_MODAL);

            primaryStage.hide();
            sourceController.showAndWait();

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

            taskViewController.setTitle("ToDo-List");
            taskViewController.getIcons().add(new Image("/txt/ProgramIcon.png"));
            taskViewController.setScene(new Scene(pane, 750, 665));

            taskViewController.setX(primaryStage.getX()-150);
            taskViewController.setY(primaryStage.getY()-100);
            taskViewController.setMinWidth(766);
            taskViewController.setMinHeight(703);
            taskViewController.setMaxWidth(1516);
            taskViewController.setMaxHeight(1348);
            //taskViewController.setResizable(false);

            taskViewController.setModel(sourceModel);
            taskViewController.initOwner(primaryStage);
            taskViewController.initModality(Modality.APPLICATION_MODAL);
            taskViewController.refresh();

            primaryStage.hide();
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

            archiveViewController.setTitle("Archive");
            archiveViewController.getIcons().add(new Image("/txt/ProgramIcon.png"));
            archiveViewController.setScene(new Scene(pane, 750,665));

            archiveViewController.setX(primaryStage.getX()-150);
            archiveViewController.setY(primaryStage.getY()-100);
            archiveViewController.setMinWidth(766);
            archiveViewController.setMinHeight(703);
            archiveViewController.setMaxWidth(1516);
            archiveViewController.setMaxHeight(1348);
            //archiveViewController.setResizable(false);

            archiveViewController.setModel(sourceModel);
            archiveViewController.initOwner(primaryStage);
            archiveViewController.initModality(Modality.APPLICATION_MODAL);
            archiveViewController.refresh(new ActionEvent());

            primaryStage.hide();
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
                }
                br.close();

                File dir = new File(rwa.getFileNameFromFile("Dir1"));
                if (!dir.exists()){
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
                }
                br.close();

                dir = new File(rwa.getFileNameFromFile("Dir2"));
                if (!dir.exists()){
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
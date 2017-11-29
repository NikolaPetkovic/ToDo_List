package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.SourceModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SourceViewController extends Stage implements Initializable {
    private SourceModel sourceModel;
    private boolean isOkClicked;
    private boolean isDir1Chosen;
    private boolean isDir2Chosen;
    private ArrayList<Integer> warnings;
    private String dir1Last;
    private String dir2Last;
    private boolean dir1Good;
    private boolean dir2Good;
    private boolean back;

    @FXML
    private TextField dir1;
    @FXML
    private TextField dir2;
    @FXML
    private Label warningLabel1;
    @FXML
    private Label warningLabel2;

    @FXML
    void actionDirectoryChooser1(ActionEvent event) throws IOException {
        System.out.println("SourceViewController - Directory Chooser Button 1 Pressed");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Node node = (Node) event.getSource();
        File selectedDirectory = directoryChooser.showDialog(node.getScene().getWindow());

        if(selectedDirectory != null){
            dir1.setText(selectedDirectory.getAbsolutePath());
            dir1Last = selectedDirectory.getAbsolutePath();
            isDir1Chosen = true;
            if (sourceModel.getDir1Name().equals(selectedDirectory.getAbsolutePath()) && !sourceModel.getDir1Name().equals("")){
                addWarningIfNotExists(3);
                System.out.println("SourceViewController - Directory Chooser Button 1 True, Dir is the same");
            } else {
                addWarningIfNotExists(1);
                System.out.println("SourceViewController - Directory Chooser Button 1 True");
            }
        }else{
            if(!dir1Last.equals("") && !dir1Last.equals(sourceModel.getDir1Name())){
                dir1.setText(dir1Last);
                isDir1Chosen = true;
                addWarningIfNotExists(5);
                System.out.println("SourceViewController - Directory Chooser 1 no file selected, back to last input");
            } else if (!sourceModel.getDir1Name().equals("")){
                dir1.setText(sourceModel.getDir1Name());
                isDir1Chosen = true;
                addWarningIfNotExists(7);
                System.out.println("SourceViewController - Directory Chooser 1 no file selected, back to original");
            } else {
                dir1.setText("");
                isDir1Chosen = false;
                addWarningIfNotExists(9);
                System.out.println("SourceViewController - Directory Chooser 1 no file selected, set Field empty");
            }
        }
    }

    @FXML
    void actionDirectoryChooser2(ActionEvent event) throws IOException {
        System.out.println("SourceViewController - Directory Chooser Button 2 Pressed");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Node node = (Node) event.getSource();
        File selectedDirectory = directoryChooser.showDialog(node.getScene().getWindow());

        if(selectedDirectory != null){
            dir2.setText(selectedDirectory.getAbsolutePath());
            dir2Last = selectedDirectory.getAbsolutePath();
            isDir2Chosen = true;
            if (sourceModel.getDir2Name().equals(selectedDirectory.getAbsolutePath()) && !sourceModel.getDir2Name().equals("")){
                addWarningIfNotExists(4);
                System.out.println("SourceViewController - Directory Chooser Button 2 True, Dir is the same");
            } else {
                addWarningIfNotExists(2);
                System.out.println("SourceViewController - Directory Chooser Button 2 True");
            }
        }else{
            if(!dir2Last.equals("") && !dir2Last.equals(sourceModel.getDir2Name())){
                dir2.setText(dir2Last);
                isDir2Chosen = true;
                addWarningIfNotExists(6);
                System.out.println("SourceViewController - Directory Chooser 2 no file selected, back to last input");
            } else if (!sourceModel.getDir2Name().equals("")){
                dir2.setText(sourceModel.getDir2Name());
                isDir2Chosen = true;
                addWarningIfNotExists(8);
                System.out.println("SourceViewController - Directory Chooser 2 no file selected, back to original");
            } else {
                dir2.setText("");
                isDir2Chosen = false;
                addWarningIfNotExists(10);
                System.out.println("SourceViewController - Directory Chooser 2 no file selected, set Field empty");
            }
        }
    }

    @FXML
    void actionSave(ActionEvent event) throws IOException {
        if (isDir1Chosen && isDir2Chosen && !findWarning(15) && !findWarning(16)){
            System.out.println("SourceViewController - Action Save Checking isDirChosen both true");
            if (findWarning(9) || findWarning(10)){
                System.out.println("SourceViewController - Action Save Checking Found Error");
                updateWarningLabel();
            } else {
                System.out.println("SourceViewController - Action Save Checking No Errors");

                if (findWarning(1)|| findWarning(5) || findWarning(11)){
                    //Is Text or Selected dir
                    if (findWarning(1) || validateDir(dir1.getText(), 1)) {
                        //Text is Correct or Selected dir
                        if (sourceModel.getRwa().getFileNameFromFile("Source").equals("yes")) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Copy Tasks");
                            alert.setHeaderText("Do you want to copy the old saves to the new folder?");

                            ButtonType buttonTypeOne = new ButtonType("Yes");
                            ButtonType buttonTypeTwo = new ButtonType("No");

                            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeOne) {
                                sourceModel.getRwa().createFilesAndSave1(dir1.getText(), true, false);
                                dir1Good = true;
                            } else {
                                sourceModel.getRwa().createFilesAndSave1(dir1.getText(), false, false);
                                dir1Good = true;
                            }
                        } else {
                            sourceModel.getRwa().createFilesAndSave1(dir1.getText(), false, false);
                            dir1Good = true;
                        }
                    } else{
                        dir1Good = false;
                    }
                } else if (findWarning(3) || findWarning(7) || findWarning(13)) {
                    //Same as Model
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Overwrite Tasks");
                    alert.setHeaderText("Do you want to overwrite the old saves, since it is the same folder?");

                    ButtonType buttonTypeOne = new ButtonType("Yes");
                    ButtonType buttonTypeTwo = new ButtonType("No");

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne) {
                        sourceModel.getRwa().createFilesAndSave1(dir1.getText(), false, true);
                        dir1Good = true;
                    } else {
                        dir1Good = true;
                    }
                } else {
                    dir1Good = false;
                }

                if (findWarning(2)|| findWarning(6) || findWarning(12)){
                    //Is Text or Selected dir
                    if (findWarning(2) || validateDir(dir2.getText(), 2)) {
                        //Text is Correct or Selected dir
                        if (sourceModel.getRwa().getFileNameFromFile("Source").equals("yes")) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Copy Archive");
                            alert.setHeaderText("Do you want to copy the old saves to the new folder?");

                            ButtonType buttonTypeOne = new ButtonType("Yes");
                            ButtonType buttonTypeTwo = new ButtonType("No");

                            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeOne) {
                                sourceModel.getRwa().createFilesAndSave2(dir2.getText(), true, false);
                                dir2Good = true;
                            } else {
                                sourceModel.getRwa().createFilesAndSave2(dir2.getText(), false, false);
                                dir2Good = true;
                            }
                        } else {
                            sourceModel.getRwa().createFilesAndSave2(dir2.getText(), false, false);
                            dir2Good = true;
                        }
                    } else{
                        dir2Good = false;
                    }
                } else if (findWarning(4) || findWarning(8) || findWarning(14)) {
                    //Same as Model
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Overwrite Archive");
                    alert.setHeaderText("Do you want to overwrite the old saves, since it is the same folder?");

                    ButtonType buttonTypeOne = new ButtonType("Yes");
                    ButtonType buttonTypeTwo = new ButtonType("No");

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne) {
                        sourceModel.getRwa().createFilesAndSave2(dir2.getText(), false, true);
                        dir2Good = true;
                    } else {
                        dir2Good = true;
                    }
                } else {
                    dir2Good = false;
                }

                if (dir1Good && dir2Good){
                    System.out.println("SourceViewController - Save action ok");
                    back = true;
                    this.close();
                } else {
                    updateWarningLabel();
                    sourceModel.getRwa().cancelSave();
                }
            }
        } else if (!isDir1Chosen && !isDir2Chosen ){
            System.out.println("SourceViewController - Action Save Checking isDirChosen both false");
            addWarningIfNotExists(15);
            addWarningIfNotExists(16);
        } else if (!isDir1Chosen) {
            System.out.println("SourceViewController - Action Save Checking isDirChosen 1 is false");
            addWarningIfNotExists(15);
        } else if (!isDir2Chosen) {
            System.out.println("SourceViewController - Action Save Checking isDirChosen 2 is false");
            addWarningIfNotExists(16);
        }
    }

    @FXML
    void actionCancel(ActionEvent event) {
        System.out.println("SourceViewController - Cancel action");
        back = true;
        this.close();
    }

    public SourceViewController() {
        this.setTitle("Select File");
        warnings = new ArrayList<>();
        dir1Last = "";
        dir2Last = "";
        System.out.println("SourceViewController - Constructor");
    }

    private void updateWarningLabel() {
        System.out.println("SourceViewController - Updated warning label");

        if (warnings.isEmpty() || findWarning(1) || findWarning(3) || findWarning(11) || findWarning(13)) {
            warningLabel1.setText("");
        } else if (findWarning(5)) {
            warningLabel1.setText("First folder not selected, used the last working folder");
        } else if (findWarning(7)) {
            warningLabel1.setText("First folder not selected, used the folder from the last repository");
        } else if (findWarning(9) || findWarning(15)) {
            warningLabel1.setText("First folder not selected");
        } else if (findWarning(17)) {
            warningLabel1.setText("Text in first field is not a folder");
        }

        if (warnings.isEmpty() || findWarning(2) || findWarning(4) || findWarning(12) || findWarning(14)) {
            warningLabel2.setText("");
        } else if (findWarning(6)) {
            warningLabel2.setText("First folder not selected, used the last working folder");
        } else if (findWarning(8)) {
            warningLabel2.setText("First folder not selected, used the folder from the last repository");
        } else if (findWarning(10) || findWarning(16)) {
            warningLabel2.setText("First folder not selected");
        } else if (findWarning(18)) {
            warningLabel2.setText("Text in first field is not a folder");
        }
    }

    private Boolean findWarning(int findWarning){
        for (Integer warning: warnings) {
            if (warning == findWarning){
                return true;
            }
        }
        return false;
    }

    private void addWarningIfNotExists(int addWarning){
        System.out.println("SourceViewController - Add warning " + addWarning);

        if (addWarning % 2 == 0) System.out.println("durch 2 = true");
        else System.out.println("durch 2 = false");

        if (!warnings.contains(addWarning)) warnings.add(addWarning);

        removeAllWarnings(addWarning);

        updateWarningLabel();
    }

    private void removeAllWarnings(int stay){
        if (stay % 2 == 0) {
            for (Integer warning : warnings) {
                if (warning != stay && warning % 2 == 0){
                    warnings.remove(warning);
                }
            }
        } else {
            for (Integer warning : warnings) {
                if (warning != stay && !(warning % 2 == 0)){
                    warnings.remove(warning);
                }
            }
        }
    }

    private Boolean validateDir (String dir, int i){
        System.out.println("SourceViewController - Action Save Validate Dir " + dir);
        File f = new File(dir);
        if (f.exists() && f.isDirectory()) {
            return true;
        }else {
            if (i == 1){
                addWarningIfNotExists(17);
            } else if (i == 2) {
                addWarningIfNotExists(18);
            }
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back = false;
        dir1.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("SourceViewController - Changed first file text from " + oldValue + " to " + newValue);
            if(newValue.equals("")){
                addWarningIfNotExists(9);
                dir1Last = newValue;
                isDir1Chosen = false;
                System.out.println("SourceViewController - 1 Text is \"\"");
            } else if (newValue.equals(sourceModel.getDir1Name())){
                addWarningIfNotExists(13);
                dir1Last = newValue;
                isDir1Chosen = true;
                System.out.println("SourceViewController - 1 Text is same as Model");
            } else {
                addWarningIfNotExists(11);
                dir1Last = newValue;
                isDir1Chosen = true;
                System.out.println("SourceViewController - 1 Text is new");
            }
        });

        dir2.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("SourceViewController - Changed second file text from " + oldValue + " to " + newValue);
            if(newValue.equals("")){
                addWarningIfNotExists(10);
                dir2Last = newValue;
                isDir2Chosen = false;
                System.out.println("SourceViewController - 2 Text is \"\"");
            } else if (newValue.equals(sourceModel.getDir2Name())){
                addWarningIfNotExists(14);
                dir2Last = newValue;
                isDir2Chosen = true;
                System.out.println("SourceViewController - 2 Text is same as Model");
            } else {
                addWarningIfNotExists(12);
                dir2Last = newValue;
                isDir2Chosen = true;
                System.out.println("SourceViewController - 2 Text is new");
            }
        });
    }

    public void setModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;

        try {
            if (sourceModel.getRwa().getFileNameFromFile("Source").equals("yes")){
                System.out.println("SourceViewController - Dir From Model True");
                this.isOkClicked = false;
                this.isDir1Chosen = true;
                this.isDir2Chosen = true;
            }else {
                System.out.println("SourceViewController - Dir From Model False");
                this.isOkClicked = false;
                this.isDir1Chosen = false;
                this.isDir2Chosen = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dir1.setText(sourceModel.getDir1Name());
        dir1Last = sourceModel.getDir1Name();
        dir2.setText(sourceModel.getDir2Name());
        dir2Last = sourceModel.getDir2Name();
    }

    public boolean getBack(){
        return back;
    }
}
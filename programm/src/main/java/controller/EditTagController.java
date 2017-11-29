package controller;

import classes.Tag;
import classes.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SourceModel;

import java.util.Optional;

public class EditTagController extends Stage{
    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField nameTextField;

    private SourceModel sourceModel;
    private boolean save;
    private boolean changeSave;
    private boolean delete;
    private boolean arch;
    private Tag tag;

    @FXML
    public void saveTag(javafx.event.ActionEvent event){
        Color color = colorPicker.getValue();
        int red = (int) (color.getRed()*255);
        int green = (int) (color.getGreen()*255);
        int blue = (int) (color.getBlue()*255);
        String hex = String.format("#%02X%02X%02X", red, green, blue);

        String name = nameTextField.getText().replace(";", "");

        if (!hex.equals(tag.getColor())){
            tag.setColor(hex);
        }
        if (!name.equals(tag.getName())){
            tag.setName(name);
        }

        Boolean containsArchTag = false;
        for (Tag archTag : sourceModel.getTagArchList()) {
            if(archTag.getId() == tag.getId()){
                containsArchTag = true;
            }
        }

        if (containsArchTag){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Changes : " + tag.getName());
            alert.setHeaderText("This Tag is already archived, should the changes also be saved to the Archive?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();


            if (result.get() == buttonTypeOne) {
                save = true;
                changeSave = true;
                this.close();
            } else {
                save = true;
                changeSave = false;
                this.close();
            }
        } else {
            save = true;
            changeSave = false;
            this.close();
        }
    }

    @FXML
    public void cancel(javafx.event.ActionEvent event){
        this.close();
    }

    @FXML
    public void archTag(javafx.event.ActionEvent event){
        Boolean noTask = true;
        for (Task task : sourceModel.getTaskList()) {
            if (task.gettagIdList().contains(tag.getId())){
                noTask = false;
            }
        }

        if (noTask) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Archive Tag : " + tag.getName());
            alert.setHeaderText("Do you really want to archive this Tag?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                arch = true;
                this.close();
            } else {
                System.out.println("not arch");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Tag still has Tasks");
            alert.setContentText("Please archive or delete the Tasks if you want to proceed.");

            alert.showAndWait();
        }
    }

    @FXML
    public void delTag(javafx.event.ActionEvent event){
        Boolean noTask = true;
        for (Task task : sourceModel.getTaskList()) {
            if (task.gettagIdList().contains(tag.getId())){
                noTask = false;
            }
        }

        if (noTask){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Tag : " + tag.getName());
            alert.setHeaderText("Do you really want to delete this Tag?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                delete = true;
                this.close();
            } else {
                System.out.println("not del");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Tag still has Tasks");
            alert.setContentText("Please archive or delete the Tasks if you want to proceed.");

            alert.showAndWait();
        }
    }

    public void setTag (SourceModel sourceModel, Tag tag) {
        this.tag = tag;
        this.sourceModel = sourceModel;
        nameTextField.setText(this.tag.getName());
        float red = Integer.parseInt(tag.getColor().charAt(1) + "" + tag.getColor().charAt(2), 16);
        float green = Integer.parseInt(tag.getColor().charAt(3) + "" + tag.getColor().charAt(4), 16);
        float blue = Integer.parseInt(tag.getColor().charAt(5) + "" + tag.getColor().charAt(6), 16);
        Color color = new Color(red/255, green/255, blue/255, 1);
        colorPicker.setValue(color);
    }

    public boolean isSave(){
        return save;
    }

    public boolean isDelete(){
        return delete;
    }

    public boolean isArch(){
        return arch;
    }

    public boolean isChangeSave(){
        return changeSave;
    }

    public Tag getTag(){
        return tag;
    }
}

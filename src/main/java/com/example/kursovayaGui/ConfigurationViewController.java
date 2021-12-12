package com.example.kursovayaGui;

import java.io.IOException;
import java.util.ResourceBundle;
import com.example.kursovayaGui.company.classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigurationViewController {


    //поля Fxml
    @FXML
    Button submitButton;
    @FXML
    TextArea errorField;
    @FXML
    TextField memoryVolumeField;
    @FXML
    TextField OSmemoryField;
    @FXML
    TextField maxProcessWorkedField;
    @FXML
    TextField priorityQuantityField;
    @FXML
    TextField coreQuantityField;
    @FXML
    TextField cpuSleepField;

    @FXML
    private void handelSubmitBtn(ActionEvent event){
        //обработка при нажатии на кнопку Submit
        Platform.runLater(()->{
            errorField.setText("");
        });
        //проверка вводимых данных
        //данные должны быть больше 0 и должны быть целочисленные
        if(!tryParseInt(memoryVolumeField.getText()) || Integer.parseInt(memoryVolumeField.getText()) <= 0){
            printError();
            return;
        }
        if(!tryParseInt(OSmemoryField.getText()) || Integer.parseInt(OSmemoryField.getText()) <= 0 ){
            printError();
            return;
        }
        if(!tryParseInt(maxProcessWorkedField.getText()) || Integer.parseInt(maxProcessWorkedField.getText()) <= 0){
            printError();
            return;
        }
        if(!tryParseInt(priorityQuantityField.getText()) || Integer.parseInt(priorityQuantityField.getText()) <= 0){
            printError();
            return;
        }
        if(!tryParseInt(coreQuantityField.getText()) || Integer.parseInt(coreQuantityField.getText()) <= 0){
            printError();
            return;
        }
        if(!tryParseInt(cpuSleepField.getText()) || Integer.parseInt(cpuSleepField.getText()) < 0){
            printError();
            return;
        }
        //если все данные правилньые вносим их в конфигурацию
        Configuration.memoryVolume = Integer.parseInt(memoryVolumeField.getText());
        Configuration.osMemoryVolume = Integer.parseInt(OSmemoryField.getText());
        Configuration.maxProcessWorked = Integer.parseInt(maxProcessWorkedField.getText());
        Configuration.maxPriority = Integer.parseInt(priorityQuantityField.getText());
        Configuration.coreQuantity = Integer.parseInt(coreQuantityField.getText());
        Configuration.cpuSleapField = Integer.parseInt(cpuSleepField.getText());

        try {
            //после внесения данных открываем новое окно и закрываем старое
            Parent root = FXMLLoader.load(getClass().getResource("schedullerView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Scheduler");
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printError(){
        Platform.runLater(new Runnable() {
            //печатаем ошибку при возникновении
            @Override
            public void run() {
                errorField.setText("");
                errorField.setText("Input invalid!");
            }
        });
    }

    boolean tryParseInt(String value) {
        //проверяет возможно ли преобразовать строку в число
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

package com.example.kursovayaGui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import com.example.kursovayaGui.company.classes.Process;
import com.example.kursovayaGui.company.classes.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller implements Initializable{
    public Scheduler scheduler ;
    private static volatile boolean shutDown = false;//определяет завершать потоки или нет
    private final int ROW_HEIGHT = 25;
    private String btnContent="";
    private static volatile boolean isPause = false;

    //поля fxml
    @FXML
    Label subtitleID;
    @FXML
    Label subtitleName;
    @FXML
    Label subtitlePriority;
    @FXML
    Label subtitleMemory;
    @FXML
    Label subtitleTimeIn;
    @FXML
    Label subtitleBurstTime;
    @FXML
    Label subtitleState;

    @FXML
    Button queueBtn;
    @FXML
    Button rejectQueueBtn;
    @FXML
    Button finishedQueueBtn;
    @FXML
    Button memoryBlocksBtn;
    @FXML
    Button startBtn;
    @FXML
    Button stopBtn;
    @FXML
    Button pauseBtn;
    @FXML
    GridPane cpuField;
    @FXML
    Label fieldTitle;
    @FXML
    GridPane contentField;

    @FXML
    private void handelConfigurationBtn(ActionEvent event){
        scheduler.setShutDown(true);
        //остановка раьботы
        ClockGenerator.setShutDown(true);
        deleteAllContent();
        //полная очистка контента
        this.shutDown = true;
        try {
            //после внесения данных открываем новое окно и закрываем старое
            Parent root = FXMLLoader.load(getClass().getResource("configurationView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("configurationStyle.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Configuration");
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setPaneForQueue(){
        //установка названий столбцов для информации о очередях
        Platform.runLater(()->{
            subtitleID.setText("ИД");
            subtitleName.setText("Имя");
            subtitlePriority.setText("Приоритет");
            subtitleMemory.setText("Память");
            subtitleTimeIn.setText("Время прибытия");
            subtitleBurstTime.setText("Время работы");
            subtitleState.setText("Статус");
        });
    }

    private void setPaneForMemoryBlock(){
        //установка названий столбцов для MemoryBlokcs
        Platform.runLater(()->{
            subtitleID.setText("Начало");
            subtitleName.setText("Конец");
            subtitlePriority.setText("Ид процесса");
            subtitleMemory.setText("");
            subtitleBurstTime.setText("");
            subtitleState.setText("");
            subtitleTimeIn.setText("");

        });
    }

    @FXML
    private void handleQueueBtnAction(ActionEvent event){
        btnContent="queue";
        Platform.runLater(()-> {
            fieldTitle.setText("Очередь готовых процессов");
        });
    }

    @FXML
    private void handleRejectQueueBtnAction(ActionEvent event){
        btnContent = "reject";
        Platform.runLater(()->{
            fieldTitle.setText("Отклоненные процессы");
        });
    }

    @FXML
    private void handlefinishedQueueBtnAction(ActionEvent event){
        btnContent = "finished";
        Platform.runLater(()->{
            fieldTitle.setText("Законченные процессы");
        });
    }

    @FXML
    private void handleMemoryBlocksBtnAction(ActionEvent event){
        btnContent = "memoryBlock";
        Platform.runLater(()-> {
            fieldTitle.setText("Блоки Памяти");
        });
    }

    private void printQueue(ArrayList<Process> list){
        //вывод на экран информации о очереди
        for (int i = 0; i < list.size(); i++) {
            RowConstraints row = new RowConstraints(ROW_HEIGHT);
            Platform.runLater(() -> {
                contentField.getRowConstraints().add(row);
                //добавление новой строчки в таблицу
            });
            Label label = new Label();
            label.setText(Integer.toString(list.get(i).getId()));
            GridPane.setHalignment(label, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label, 0, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
            Label label1 = new Label();
            label1.setText(list.get(i).getName());
            GridPane.setHalignment(label1, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label1, 1, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
            Label label2 = new Label();
            label2.setText(Integer.toString(list.get(i).getPriority()));
            GridPane.setHalignment(label2, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label2, 2, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
            Label label3 = new Label();
            label3.setText(Integer.toString(list.get(i).getMemory()));
            GridPane.setHalignment(label3, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label3, 3, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
            Label label4 = new Label();
            label4.setText(Integer.toString(list.get(i).getTimeIn()) + "s");
            GridPane.setHalignment(label4, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label4, 4, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
            Label label5 = new Label();
            label5.setText(Integer.toString(list.get(i).getBurstTime()) + "s");
            GridPane.setHalignment(label5, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label5, 5, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
            Label label6 = new Label();
            label6.setText(list.get(i).getState().toString());
            GridPane.setHalignment(label6, HPos.CENTER);
            Platform.runLater(() -> {
                contentField.add(label6, 6, contentField.getRowCount() - 1);
                //добавление в строчку значения
            });
        }
    }

    private void printContent(){
        //запуск в отдельном потоке
        Thread thread = new Thread( ()->{
            while (!shutDown) {
                ArrayList sctatistic = scheduler.returnStatistic();
                Platform.runLater(()->{
                    //очистка нижнего окна на экране куда выводится инфа о Процессах
                    contentField.getChildren().clear();
                    contentField.getRowConstraints().clear();

                });
                switch (btnContent) {
                    case "queue":
                        setPaneForQueue();
                        //получение данных об очереди
                        ArrayList<Process> queue = (ArrayList<Process>) sctatistic.get(0);
                        printQueue(queue);//печать на экран
                        break;
                    case "reject":
                        setPaneForQueue();
                        //получение даных об очереди отказов
                        ArrayList<Process> rejectQueue = (ArrayList<Process>) sctatistic.get(2);
                        printQueue(rejectQueue);//печать на экран
                    break;

                    case "finished":
                        setPaneForQueue();
                        //получение очереди завершенных процесов
                        ArrayList<Process> finishedQueue = (ArrayList<Process>) sctatistic.get(1);
                        printQueue(finishedQueue);//печать на экран
                        break;
                    case "memoryBlock":
                        setPaneForMemoryBlock();
                        //получение данных о блоках памяти
                        ArrayList<MemoryBlock> memoryBlocks = (ArrayList<MemoryBlock>) sctatistic.get(4);
                        //ниже выполняется печать на экран
                        for(int i =0;i<memoryBlocks.size();i++){
                            RowConstraints row = new RowConstraints(ROW_HEIGHT);
                            Platform.runLater(()->{
                                //добавление новой строки
                                contentField.getRowConstraints().add(row);
                            });
                            Label label = new Label();
                            GridPane.setHalignment(label,HPos.CENTER);
                            label.setText(Integer.toString(memoryBlocks.get(i).getStart()));
                            Platform.runLater(()->{
                                //добавление значения в строку
                                contentField.add(label,0,contentField.getRowCount()-1);
                            });
                            Label label1 = new Label();
                            GridPane.setHalignment(label1,HPos.CENTER);
                            label1.setText(Integer.toString(memoryBlocks.get(i).getEnd()));
                            Platform.runLater(()->{
                                //добавление значения в строку
                                contentField.add(label1,1,contentField.getRowCount()-1);
                            });
                            Label label2 = new Label();
                            GridPane.setHalignment(label2,HPos.CENTER);
                            label2.setText(Integer.toString(memoryBlocks.get(i).getProcessId()));
                            Platform.runLater(()->{
                                //добавление значения в строку
                                contentField.add(label2,2,contentField.getRowCount()-1);
                            });
                        }
                        break;
                    default:
                        break;
                }
                Core[] cores = (Core[]) sctatistic.get(3);
                Platform.runLater(()->{
                    //очистка поля с инф о ядрах,верхнее окно
                    cpuField.getChildren().clear();
                    cpuField.getRowConstraints().clear();
                });
                //печать информации о ядрах
                for(int i = 0;i<cores.length;i++){
                    RowConstraints row = new RowConstraints(ROW_HEIGHT);
                    Platform.runLater(()->{
                        //добавление строки
                        cpuField.getRowConstraints().add(row);
                    });
                    Label label = new Label();
                    GridPane.setHalignment(label,HPos.CENTER);
                    label.setText(Integer.toString(cores[i].getCoreID()));
                    GridPane.setHalignment(label, HPos.CENTER);
                    Platform.runLater(()->{
                        //добавление новой информации
                        cpuField.add(label,0,cpuField.getRowCount()-1);
                    });
                    Label label1 = new Label();
                    GridPane.setHalignment(label1,HPos.CENTER);
                    label1.setText(Boolean.toString(cores[i].isFree()));
                    GridPane.setHalignment(label1, HPos.CENTER);
                    Platform.runLater(()->{
                        //добавление новой информации
                        cpuField.add(label1,1,cpuField.getRowCount()-1);
                    });
                    Label label2 = new Label();
                    GridPane.setHalignment(label,HPos.CENTER);
                    label2.setText(Integer.toString(cores[i].getProcessID()));
                    GridPane.setHalignment(label2, HPos.CENTER);
                    Platform.runLater(()->{
                        //добавление новой информации
                        cpuField.add(label2,2,cpuField.getRowCount()-1);
                    });
                    Label label3 = new Label();
                    GridPane.setHalignment(label,HPos.CENTER);
                    label3.setText(Integer.toString(cores[i].getBurstTime()));
                    GridPane.setHalignment(label3, HPos.CENTER);
                    Platform.runLater(()->{
                        //добавление новой информации
                        cpuField.add(label3,3,cpuField.getRowCount()-1);
                    });
                }
                    //интерфейс обновляется каждые 0.5 секунды
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();//запуск потока
    }

    @FXML
    private void handleStartBtnAction(ActionEvent event){
        this.shutDown = false;
        Scheduler.setShutDown(false);
        if(!isPause){
            //запуск нового потока и начало работы планировщика
            ClockGenerator.setShutDown(false);
            new Thread(scheduler = new Scheduler(Configuration.coreQuantity)).start();
        }else{
            isPause = false;
            //запуск уже существующего планироващика со старыми значениями
            new Thread(scheduler).start();
        }
        printContent();//печать контента
    }

    @FXML
    private void handleStopBtnAction(ActionEvent event){
        scheduler.setShutDown(true);
        //остановка раьботы
        ClockGenerator.setShutDown(true);
        deleteAllContent();
        //полная очистка контента
        this.shutDown = true;
    }

    @FXML
    private void handlePauseBtnAction(ActionEvent event){
        isPause = true;
        scheduler.setShutDown(true);
        //остановка работы
    }

    private void deleteAllContent(){
        Platform.runLater(()-> {
            //удаление всего контента
            btnContent ="";
            contentField.getRowConstraints().remove(0,contentField.getRowCount());
            contentField.getChildren().remove(0,contentField.getChildren().size());
            cpuField.getRowConstraints().remove(0,cpuField.getRowCount());
            cpuField.getChildren().remove(0,cpuField.getChildren().size());
            fieldTitle.setText("");
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
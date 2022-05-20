package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class KottuLabsController {
    public ProgressBar progresProses;
    public Label lblPercentage;
    public AnchorPane kottulabsContext;


    public void initialize() {
        proces();
    }

    private void proces() {


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                for (int i = 0; i <= 100; i++) {
                    updateProgress(i, 100);
                    Thread.sleep(50);
                }
                return null;
            }
        };

        progresProses.progressProperty().bind(task.progressProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        progresProses.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                int presentage = (int) Math.round((Double) newValue * 100);
                lblPercentage.setText(presentage + "%");


                //set Your task for this
                if (presentage == 100) {
                    try {
                        setMUI("Mainlogin");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setMUI(String location) throws IOException {
        Stage stage = (Stage) kottulabsContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.setTitle("Menu Form");
    }
}

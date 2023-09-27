package me.ruslan.dblb2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainForm extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainForm.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);

        stage.setTitle("Lb2");
        stage.setScene(scene);
        stage.setMaxWidth(720);
        stage.setMinWidth(720);
        stage.setMaxHeight(480);
        stage.setMinHeight(480);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by Александр on 26.09.2014.
 */
public class Window extends Application{
    GraphArea gArea;
    Menus menu;
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Menus menu = new Menus();
        primaryStage.setTitle("Graph editor");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setResizable(false);
        gArea = new GraphArea(540, 470, new Controller());
        root.setTop(menu);
        root.setCenter(gArea);
        primaryStage.show();
        //new  SetIDDialog();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

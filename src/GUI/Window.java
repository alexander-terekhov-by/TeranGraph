package GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        primaryStage.setScene(new Scene(root, 455, 440));
        gArea = new GraphArea(400, 400, new Controller());
        root.setTop(menu);
        root.setCenter(gArea);
        primaryStage.show();
        new  SetIDDialog();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

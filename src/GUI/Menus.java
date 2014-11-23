package GUI;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Александр on 26.09.2014.
 */
public class Menus extends MenuBar {

    Menus(final Controller graphController) {
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save file");
        MenuItem open = new MenuItem("Open file");
        file.getItems().addAll(open, save);
        this.getMenus().addAll(file);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph fies (*.gr)", "*.gr"));
                fileChooser.setTitle("Save graph");
                File graphFile = fileChooser.showSaveDialog(null);
                graphController.writeGraph(graphFile);
            }
        });

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open graph");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph fies (*.gr)", "*.gr"));
                File graphFile = fileChooser.showOpenDialog(null);
                graphController.readGraph(graphFile);

            }
        });
    }
}

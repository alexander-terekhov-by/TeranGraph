package GUI;

import controller.Controller;
import graph.HamiltonianCycleFinder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Александр on 26.09.2014.
 */
public class Menus extends MenuBar {
    final Controller graphController;
    Menus(final Controller graphController) {
        this.graphController = graphController;
        Menu file = new Menu("File");
        Menu search = new Menu("Search");
        MenuItem searchByName = new MenuItem("Search vertex by name");
        MenuItem save = new MenuItem("Save file");
        MenuItem open = new MenuItem("Open file");
        search.getItems().addAll(searchByName);
        file.getItems().addAll(open, save);
        this.getMenus().addAll(file, search);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph fies (*.gr)", "*.gr"));
                fileChooser.setTitle("Save graph");
                File graphFile = fileChooser.showSaveDialog(null);
                if (graphFile != null)
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
                if (graphFile != null && graphFile.exists())
                    graphController.readGraph(graphFile);

            }
        });

        searchByName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //new FindByIDDialog();
                graphController.findHamilton();



            }
        });
    }

    /*class FindByIDDialog {
        public  String id;
        Stage primaryStage;
        public FindByIDDialog() {
            primaryStage = new Stage();
            VBox root = new VBox();
            VBox pane = new VBox();
            Label label = new Label("Input ID");
            final TextField field = new TextField();
            primaryStage.setResizable(false);
            primaryStage.setTitle("ID");
            Button search = new Button("Search");
            Button end = new Button("OK");
            pane.getChildren().addAll(label, field, search, end);
            root.getChildren().add(pane);
            pane.setSpacing(5);
            root.setMargin(pane, new Insets(10, 10, 10, 10));
            primaryStage.setScene(new Scene(root, 150, 110));
            primaryStage.show();

            search.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    graphController.getVertexByID(field.getText());
                }
            });

            end.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FindByIDDialog .this.primaryStage.close();
                }
            });


        }
    }*/
}

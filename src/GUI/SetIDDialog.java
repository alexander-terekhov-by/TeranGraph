package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Александр on 18.11.2014.
 */
public class SetIDDialog {
    public  String id;
    Stage primaryStage;
    public SetIDDialog(final GraphArea.OnClickMenu menu) {
        primaryStage = new Stage();
        VBox root = new VBox();
        VBox pane = new VBox();
        Label label = new Label("Input ID");
        final TextField field = new TextField();
        primaryStage.setResizable(false);
        primaryStage.setTitle("ID");
        Button add = new Button("Set");
        pane.getChildren().addAll(label, field, add);
        root.getChildren().add(pane);
        pane.setSpacing(5);
        root.setMargin(pane, new Insets(10, 10, 10, 10));
        primaryStage.setScene(new Scene(root, 150, 90));
        primaryStage.show();

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menu.setID(field.getText());
                SetIDDialog.this.primaryStage.close();
            }
        });

    }
}



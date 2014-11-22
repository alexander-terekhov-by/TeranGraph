package GUI;

import constants.GraphConstants;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 * Created by Александр on 26.09.2014.
 */
public class GraphArea extends BorderPane {
    private double width;
    private double height;
    private double savedX, savedY;
    private DrawingMode mode;
    private GraphCanvas canvas;
    private Controller controller;
    private Image tmpImage;

    public GraphArea(double width, double height, Controller controller) {
        this.controller = controller;
        controller.setView(this);
        mode = DrawingMode.NONE;
        this.width = width;
        this.height = height;
        canvas = new GraphCanvas(width, height);
        cleanCanvas();
        this.setLeft(new ButtonBar());
        HBox canvasBox = new HBox();
        canvasBox.getChildren().add(canvas);
        canvasBox.setMargin(canvas, new Insets(10, 10, 10, 10));
        this.setCenter(canvasBox);
        tmpImage = canvas.snapshot(new SnapshotParameters(), null);
    }

    private void saveState() {
        tmpImage = canvas.snapshot(new SnapshotParameters(), null);
    }

    public void returnToPreviousState() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(tmpImage, 0, 0);
    }

    private void makeArrow(double posX1, double posY1, double posX2, double posY2) {
        double tg = (posY2 - posY1) / (posX2 - posX1);
        /*gc.strokeLine(posX2, posY2, posX2 - 10, posY2 - 10);
        gc.strokeLine(posX2, posY2, posX2 + 10, posY2 + 10);*/

    }

    private GraphicsContext prepareGraphicContextStroke(Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(GraphConstants.LINE_WIDTH);
        return gc;
    }

    public void drawVertex(double posX, double posY, Color color) {
        GraphicsContext gc = prepareGraphicContextStroke(color);
        gc.strokeOval(posX - GraphConstants.VERTEX_RADIUS, posY - GraphConstants.VERTEX_RADIUS, GraphConstants.VERTEX_RADIUS * 2, GraphConstants.VERTEX_RADIUS * 2);
    }

    public void drawArc(double posX1, double posY1, double posX2, double posY2, Color color) {
        GraphicsContext gc = prepareGraphicContextStroke(color);
        gc.strokeLine(posX1, posY1, posX2, posY2);
        gc.setFill(Color.WHITE);
        gc.fillOval(posX1 - GraphConstants.VERTEX_RADIUS + 2, posY1 - GraphConstants.VERTEX_RADIUS + 2, GraphConstants.VERTEX_RADIUS * 2 - 4, GraphConstants.VERTEX_RADIUS * 2 - 4);
        gc.fillOval(posX2 - GraphConstants.VERTEX_RADIUS + 2, posY2 - GraphConstants.VERTEX_RADIUS + 2, GraphConstants.VERTEX_RADIUS * 2 - 4, GraphConstants.VERTEX_RADIUS * 2 - 4);
    }

    private void drawPreArc(double posX1, double posY1, double posX2, double posY2) {
        returnToPreviousState();
        GraphicsContext gc = prepareGraphicContextStroke(GraphConstants.MAIN_COLOR);
        gc.strokeLine(posX1, posY1, posX2, posY2);
    }

    public void cleanCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        saveState();
    }

    private class ButtonBar extends VBox {
        ButtonBar() {
            VBox innerBox = new VBox();
            String resFolder = GraphArea.class.getResource("../imgs").toString();
            Button drawVertex = new Button("", new ImageView(resFolder + "/vertex.png"));
            Button drawArc = new Button("", new ImageView(resFolder + "/arc.png"));
            Button cleanCanvas = new Button("", new ImageView(resFolder + "/eraser.png"));
            Button arrow = new Button("", new ImageView(resFolder + "/cursor.png"));
            innerBox.getChildren().addAll(arrow, drawVertex, drawArc, cleanCanvas);
            innerBox.setSpacing(5);
            this.setMargin(innerBox, new Insets(10, 0, 0, 10));
            this.getChildren().add(innerBox);

            drawVertex.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GraphArea.this.mode = DrawingMode.VERTEX;
                }
            });
            drawArc.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GraphArea.this.mode = DrawingMode.ARC;
                }
            });
            arrow.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GraphArea.this.mode = DrawingMode.NONE;
                }
            });
            cleanCanvas.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controller.cleanCanvas();
                }
            });
        }
    }

    private class GraphCanvas extends Canvas {
        GraphCanvas(double width, double height) {
            super(width, height);
            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    if (mode == DrawingMode.VERTEX && e.getButton() == MouseButton.PRIMARY) {
                        controller.makeVertex(e.getX(), e.getY());
                        saveState();
                    }

                    if (e.getButton() == MouseButton.SECONDARY && mode == DrawingMode.NONE) {
                        if(controller.haveElementOnPos(e.getX(), e.getY()))
                        new OnClickMenu(e.getX(), e.getY()).show(canvas, e.getScreenX(), e.getScreenY());
                    }
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (mode == DrawingMode.ARC) {
                            savedX = e.getX();
                            savedY = e.getY();
                        }
                        if (mode == DrawingMode.NONE) {
                            controller.prepareToMoveVertex(e.getX(), e.getY());
                        }
                    }
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (mode == DrawingMode.ARC) {
                            returnToPreviousState();
                            controller.makeArc(savedX, savedY, e.getX(), e.getY());
                        }
                        if (mode == DrawingMode.NONE) {
                            controller.endVertexMove(e.getX(), e.getY());
                        }
                        saveState();
                    }
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    returnToPreviousState();
                    controller.lightElement(e.getX(), e.getY());
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (mode == DrawingMode.ARC) {
                            drawPreArc(savedX, savedY, e.getX(), e.getY());
                        }
                        if (mode == DrawingMode.NONE) {
                            controller.moveVertex(e.getX(), e.getY());
                        }
                    }
                }
            });
        }
    }

    class OnClickMenu extends ContextMenu {
        OnClickMenu(final double posX, final double posY) {
            MenuItem delete = new MenuItem("Delete");
            MenuItem setID = new MenuItem("Set ID");
            this.getItems().addAll(setID, delete);

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controller.deleteElement(posX, posY);
                }
            });
        }
    }
}




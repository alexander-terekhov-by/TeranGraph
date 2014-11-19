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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        canvasBox.setMargin(canvas, new Insets(10,10,10,10));
        this.setCenter(canvasBox);
        tmpImage = canvas.snapshot(new SnapshotParameters(), null);
    }

    private void saveState(){
        tmpImage = canvas.snapshot(new SnapshotParameters(), null);
    }

    public void returnToPreviousState(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(tmpImage, 0, 0);
    }

    private GraphicsContext prepareGraphicContextStroke(Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(GraphConstants.LINE_WIDTH);
        return gc;
    }

    public void drawVertex(double posX, double posY) {
        GraphicsContext gc = prepareGraphicContextStroke(Color.BLACK);
        gc.strokeOval(posX - GraphConstants.VERTEX_RADIUS, posY - GraphConstants.VERTEX_RADIUS, GraphConstants.VERTEX_RADIUS * 2, GraphConstants.VERTEX_RADIUS * 2);
        tmpImage = canvas.snapshot(new SnapshotParameters(), null);
        saveState();
    }

    public void drawArc(double posX1, double posY1, double posX2, double posY2) {
        returnToPreviousState();
        GraphicsContext gc = prepareGraphicContextStroke(Color.BLACK);
        gc.strokeLine(posX1, posY1, posX2, posY2);
        gc.setFill(Color.WHITE);
        gc.fillOval(posX1 - GraphConstants.VERTEX_RADIUS + 2, posY1 - GraphConstants.VERTEX_RADIUS + 2, GraphConstants.VERTEX_RADIUS * 2 - 4, GraphConstants.VERTEX_RADIUS * 2 - 4);
        gc.fillOval(posX2 - GraphConstants.VERTEX_RADIUS + 2, posY2 - GraphConstants.VERTEX_RADIUS + 2, GraphConstants.VERTEX_RADIUS * 2 - 4, GraphConstants.VERTEX_RADIUS * 2 - 4);
        saveState();
    }

    private void drawPreArc(double posX1, double posY1, double posX2, double posY2) {
        returnToPreviousState();
        GraphicsContext gc = prepareGraphicContextStroke(Color.BLACK);
        gc.strokeLine(posX1, posY1, posX2, posY2);
    }

    public void cleanCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        saveState();
    }

    public void lightVertex(double posX, double posY) {
        GraphicsContext gc = prepareGraphicContextStroke(Color.RED);
        gc.strokeOval(posX - GraphConstants.VERTEX_RADIUS, posY - GraphConstants.VERTEX_RADIUS, GraphConstants.VERTEX_RADIUS * 2, GraphConstants.VERTEX_RADIUS * 2);

    }


    private class ButtonBar extends VBox {
        Button drawVertex;
        Button drawArc;
        Button cleanCanvas;
        String resFolder;

        ButtonBar() {
            resFolder = GraphArea.class.getResource("../imgs").toString();
            drawVertex = new Button("", new ImageView(resFolder + "/vertex.png"));
            drawArc = new Button("", new ImageView(resFolder + "/arc.png"));
            cleanCanvas = new Button("", new ImageView(resFolder + "/eraser.png"));
            this.getChildren().addAll(drawVertex, drawArc, cleanCanvas);
            this.setMargin(drawVertex, new Insets(10, 0, 0, 0));
            this.setSpacing(5);
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
                    if (mode == DrawingMode.VERTEX) {
                        controller.makeVertex(e.getX(), e.getY());
                    }
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    savedX = e.getX();
                    savedY = e.getY();
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    //controller.lightVertex(e.getX(), e.getY());
                    if (mode == DrawingMode.ARC) {
                        controller.makeArc(savedX, savedY, e.getX(), e.getY());
                    }

                }
            });
            this.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    controller.lightVertex(e.getX(), e.getY());
                }
            });
            this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    controller.lightVertex(e.getX(), e.getY());
                    if (mode == DrawingMode.ARC) {
                        drawPreArc(savedX, savedY, e.getX(), e.getY());
                    }
                }
            });
        }
    }
}


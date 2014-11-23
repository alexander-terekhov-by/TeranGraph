package controller;

import GUI.GraphArea;
import com.google.gson.Gson;
import constants.GraphConstants;
import fileWorker.IOFile;
import graph.Arc;
import graph.Graph;
import graph.Vertex;

import java.awt.*;
import java.io.*;

import static java.lang.Math.pow;

public class Controller {
    private Vertex movedVertex;
    private Graph graph;
    private GraphArea view;
    private IOFile ioFile;

    public Controller() {
        graph = new Graph();
        ioFile = new IOFile();
    }

    public void setView(GraphArea view) {
        this.view = view;
    }

    public void makeVertex(double x, double y) {
        if (isVertexCanBePlaced(x, y)) {
            Vertex newVertex = new Vertex(x, y);
            graph.addVertex(newVertex);
            view.drawVertex(x, y, newVertex.getName(), GraphConstants.MAIN_COLOR);
        }
    }

    private boolean isVertexCanBePlaced(double x, double y) {
        for (Vertex oneVertexFromGraph : graph.getAllVertexes()) {
            double xC = oneVertexFromGraph.getX();
            double yC = oneVertexFromGraph.getY();
            if (((pow(x - xC, 2) + pow(y - yC, 2)) < pow(GraphConstants.VERTEX_RADIUS * 2, 2)))
                return false;
        }
        return true;
    }

    private Vertex getVertexByCoordinates(double x, double y) {
        for (Vertex oneVertexFromGraph : graph.getAllVertexes()) {
            double xC = oneVertexFromGraph.getX();
            double yC = oneVertexFromGraph.getY();
            if (((pow(x - xC, 2) + pow(y - yC, 2)) < pow(GraphConstants.VERTEX_RADIUS, 2)))
                return oneVertexFromGraph;
        }
        return null;
    }

    public void prepareToMoveVertex(double x, double y) {
        movedVertex = getVertexByCoordinates(x, y);
        drawGraphWithoutVertex(movedVertex);
    }

    public void moveVertex(double x, double y) {
        if (movedVertex != null) {
            for (Arc arc : movedVertex.getInputArcs()) {
                view.drawArc(arc.getMainVertex().getX(), arc.getMainVertex().getY(), x, y, GraphConstants.MAIN_COLOR);
            }
            for (Arc arc : movedVertex.getOutputArcs()) {
                view.drawArc(x, y, arc.getSecondVertex().getX(), arc.getSecondVertex().getY(), GraphConstants.MAIN_COLOR);
            }
            view.drawVertex(x, y, movedVertex.getName(), GraphConstants.MAIN_COLOR);
        }
    }

    public void endVertexMove(double x, double y) {
        if (movedVertex != null) {
            movedVertex.setX(x);
            movedVertex.setY(y);
            movedVertex = null;
            drawGraphWithoutVertex(null);
        }
    }

    public void setVertexId(String id, double x, double y) {
        Vertex vertex = getVertexByCoordinates(x, y);
        vertex.setName(id);
        drawGraphWithoutVertex(null);
    }

    public void makeArc(double x1, double y1, double x2, double y2) {
        Vertex vertex1 = getVertexByCoordinates(x1, y1);
        Vertex vertex2 = getVertexByCoordinates(x2, y2);
        if (vertex2 != null && vertex1 != null && !vertex1.identical(vertex2)) {
            view.drawArc(vertex1.getX(), vertex1.getY(), vertex2.getX(), vertex2.getY(), GraphConstants.MAIN_COLOR);
            Arc newArc = new Arc(vertex1, vertex2);
            graph.addArc(newArc);
            vertex1.addOutputArc(newArc);
            vertex2.addInputArc(newArc);
        }
    }

    public void drawPreArc(double x1, double y1, double x2, double y2) {
        Vertex vertex1 = getVertexByCoordinates(x1, y1);
        if (vertex1 != null) {
            view.drawPreArc(vertex1.getX(), vertex1.getY(), x2, y2);
        }
    }

    private Arc getArcByCoordinates(double x, double y) {
        for (Arc oneArcFromGraph : graph.getAllArcs()) {
            if (checkPointOnLineSegment(oneArcFromGraph.getMainVertex(), oneArcFromGraph.getSecondVertex(), new Point((int) x, (int) y)))
                return oneArcFromGraph;
        }
        return null;
    }

    private boolean checkPointOnLineSegment(Vertex first, Vertex second, Point point) {
        double xMin = Math.min(first.getX(), second.getX());
        double xMax = Math.max(first.getX(), second.getX());
        double yMin = Math.min(first.getY(), second.getY());
        double yMax = Math.max(first.getY(), second.getY());
        if ((point.getX() < xMin || point.getX() > xMax) && (point.getY() < yMin || point.getY() > yMax)) {
            return false;
        } else {
            double a = (point.getX() - first.getX()) / (second.getX() - first.getX());
            double b = (point.getY() - first.getY()) / (second.getY() - first.getY());
            return Math.abs(a - b) < 0.2;
        }
    }

    public void cleanCanvas() {
        view.cleanCanvas();
        graph = new Graph();
    }

    public void lightElement(double x, double y) {
        Vertex vertex = getVertexByCoordinates(x, y);
        Arc arc = getArcByCoordinates(x, y);
        if (vertex != null) {
            view.drawVertex(vertex.getX(), vertex.getY(), vertex.getName(), GraphConstants.TMP_COLOR);
        } else if (arc != null) {
            view.drawArc(arc.getMainVertex().getX(), arc.getMainVertex().getY(), arc.getSecondVertex().getX(), arc.getSecondVertex().getY(), GraphConstants.TMP_COLOR);
        }
    }

    public void drawGraphWithoutVertex(Vertex removedVertex) {
        view.cleanCanvas();
        for (Vertex vertex : graph.getAllVertexes()) {
            if (vertex != removedVertex) {
                for (Arc arc : vertex.getOutputArcs()) {
                    if (arc.getSecondVertex() != removedVertex)
                        view.drawArc(arc.getMainVertex().getX(), arc.getMainVertex().getY(), arc.getSecondVertex().getX(), arc.getSecondVertex().getY(), GraphConstants.MAIN_COLOR);
                }
                view.drawVertex(vertex.getX(), vertex.getY(), vertex.getName(), GraphConstants.MAIN_COLOR);
            }
        }
    }

    public boolean haveElementOnPos(double x, double y) {
        return (getVertexByCoordinates(x, y) != null || getArcByCoordinates(x, y) != null);
    }

    public void deleteElement(double x, double y) {
        graph.deleteVertex(getVertexByCoordinates(x, y));
        graph.deleteArc(getArcByCoordinates(x, y));
        drawGraphWithoutVertex(null);

    }

    public void writeGraph(File file)  {
        try {
            ioFile.writeGraph(graph,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readGraph(File file) {
        try {
            graph = ioFile.openGraphFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        drawGraphWithoutVertex(null);
    }
}

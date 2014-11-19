package controller;

import GUI.GraphArea;
import constants.GraphConstants;
import graph.Graph;
import graph.Vertex;

import static java.lang.Math.pow;

public class Controller {
    private Graph graph;
    private GraphArea view;

    public Controller() {
        graph = new Graph();
    }

    public void setView(GraphArea view) {
        this.view = view;
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

    public void cleanCanvas() {
        view.cleanCanvas();
        graph = new Graph();
    }

    public void makeVertex(double x, double y) {
        if (isVertexCanBePlaced(x, y)) {
            Vertex newVertex = new Vertex(x, y);
            graph.addVertex(newVertex);
            view.drawVertex(x, y);

        }
    }

    public void lightVertex(double x, double y) {
        Vertex vertex = getVertexByCoordinates(x, y);
        if (vertex != null) {
            view.lightVertex(vertex.getX(), vertex.getY());
        } else view.returnToPreviousState();
    }

    public void makeArc(double x1, double y1, double x2, double y2) {
        Vertex vertex1 = getVertexByCoordinates(x1, y1);
        Vertex vertex2 = getVertexByCoordinates(x2, y2);
        if (vertex2 != null && vertex1 != null && !vertex1.identical(vertex2)) {
            view.drawArc(vertex1.getX(), vertex1.getY(), vertex2.getX(), vertex2.getY());
            vertex1.addNeighbour(vertex2);
            vertex2.addNeighbour(vertex1);
        }

    }
}

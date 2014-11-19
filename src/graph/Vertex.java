package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 26.09.2014.
 */
public class Vertex {


    private double x;
    private double y;
    private String name;
    private List<Vertex> neighbouringVertexes;

    public Vertex(double posX, double posY) {
        x = posX;
        y = posY;
        neighbouringVertexes = new ArrayList<Vertex>();
    }

    public Vertex(double posX, double posY, String name) {
        this(posX, posY);
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean identical(Vertex vertex) {
        return (vertex.getX() == x && vertex.getY() == y);
    }

    public void addNeighbour(Vertex neighbour){
        neighbouringVertexes.add(neighbour);
    }
}

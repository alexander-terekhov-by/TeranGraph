package graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Александр on 26.09.2014.
 */
public class Vertex implements Serializable {

    static int id = 0;
    private double x;
    private double y;
    private String name;
    private List<Arc> inputArcs;
    private List<Arc> outputArcs;


    public Vertex(double posX, double posY) {
        x = posX;
        y = posY;
        inputArcs = new LinkedList<Arc>();
        outputArcs = new LinkedList<Arc>();
        name = "" + id;
        id++;
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

    public void addOutputArc(Arc arc){
        outputArcs.add(arc);
    }

    public void addInputArc(Arc arc){
        inputArcs.add(arc);
    }

    public List<Arc> getInputArcs() {
        return inputArcs;
    }

    public List<Arc> getOutputArcs() {
        return outputArcs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  "V" + name;
    }


}

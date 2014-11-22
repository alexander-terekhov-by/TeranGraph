package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Александр on 27.09.2014.
 */
public class Graph {
    private List<Vertex> allVertexes = new LinkedList <Vertex>();
    private List<Arc> allArcs = new LinkedList<Arc>();


    public void addVertex(Vertex vertex){
            allVertexes.add(vertex);
    }
    public void addArc(Arc arc){
        allArcs.add(arc);
    }
    public List<Vertex> getAllVertexes(){
        return allVertexes;
    }
    public List<Arc> getAllArcs(){
        return allArcs;
    }
    public void deleteArc(Arc arc){
        allArcs.remove(arc);
    }
    public void deleteVertex(Vertex vertex){
        allVertexes.remove(vertex);
    }
}

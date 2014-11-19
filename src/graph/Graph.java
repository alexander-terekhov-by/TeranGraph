package graph;

import java.util.ArrayList;

/**
 * Created by Александр on 27.09.2014.
 */
public class Graph {
    private ArrayList<Vertex> allVertexes = new ArrayList <Vertex>();
    public void addVertex(Vertex vertex){
            allVertexes.add(vertex);
    }

    public ArrayList<Vertex> getAllVertexes(){
        return allVertexes;
    }

}

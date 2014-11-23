package graph;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Александр on 27.09.2014.
 */
public class Graph implements Serializable {
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
        if (arc != null) {
            allArcs.remove(arc);
            arc.getSecondVertex().getInputArcs().remove(arc);
            arc.getMainVertex().getOutputArcs().remove(arc);
            /*for (Vertex vertex : allVertexes){
                vertex.getOutputArcs().remove(arc);
                vertex.getInputArcs().remove(arc);
            }*/
        }
    }
    public  void deleteVertex(Vertex vertex){
        if(vertex != null) {

            for(Arc arc : vertex.getInputArcs()){
                arc.getMainVertex().getOutputArcs().remove(arc);
            }
            for(Arc arc : vertex.getOutputArcs()){
                arc.getSecondVertex().getInputArcs().remove(arc);
            }
            allArcs.removeAll(vertex.getInputArcs());
            allArcs.removeAll(vertex.getOutputArcs());
            allVertexes.remove(vertex);
            System.out.println("vertex was removed:" + vertex);
            //System.out.println("all vertexes:" + allVertexes.toString());
        }
    }

    @Override
    public String toString() {
        return "Graph{" +
                "allVertexes=" + allVertexes +
                ", \n allArcs=" + allArcs +
                '}';
    }
}

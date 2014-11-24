package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Александр on 24.11.2014.
 */
public class HamiltonianCycleFinder {
    List<Vertex> hamiltonCycle;
    List<Vertex> resHamiltonCycle;
    Vertex firstVertex;
    int countOfVertex;

    public List<Vertex> getCycle() {
        return resHamiltonCycle;
    }

    public HamiltonianCycleFinder(Graph graph) {
        hamiltonCycle = new LinkedList<Vertex>();
        firstVertex = graph.getAllVertexes().get(0);
        countOfVertex = graph.getAllVertexes().size();
        step(firstVertex);
    }
    private List<Vertex> nextVertexes(Vertex vertex) {
        List<Vertex> neighbours = new LinkedList<Vertex>();
        for (Arc outArc : vertex.getOutputArcs())
            neighbours.add(outArc.getSecondVertex());
        return neighbours;

    }
    private void step(Vertex vertex) {
        hamiltonCycle.add(vertex);
        if (nextVertexes(vertex).contains(firstVertex) && hamiltonCycle.size() == countOfVertex) {
            resHamiltonCycle =  hamiltonCycle;
            return;
        }
        for (Vertex nextVertex : nextVertexes(vertex))
            if (!hamiltonCycle.contains(nextVertex)) {
                step(nextVertex);
            }
    }
}

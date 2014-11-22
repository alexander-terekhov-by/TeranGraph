package graph;

/**
 * Created by Александр on 19.11.2014.
 */
public class Arc {
    private Vertex mainVertex;
    private Vertex secondVertex;
    private String name;

    public Arc(Vertex vertex1, Vertex vertex2) {
        this.secondVertex = vertex2;
        this.mainVertex = vertex1;
    }

    public Vertex getSecondVertex() {
        return secondVertex;
    }

    public Vertex getMainVertex() {
        return mainVertex;
    }
}

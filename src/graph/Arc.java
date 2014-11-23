package graph;

import java.io.Serializable;

/**
 * Created by Александр on 19.11.2014.
 */
public class Arc implements Serializable {
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


    @Override
    public String toString() {
        return "{"
                 + mainVertex +
                ", " + secondVertex +
                '}';
    }
}

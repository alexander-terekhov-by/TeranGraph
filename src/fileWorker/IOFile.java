package fileWorker;

import com.google.gson.Gson;
import graph.Graph;

import java.io.*;

/**
 * Created by Александр on 23.11.2014.
 */
public class IOFile {

    public Graph openGraphFile(File file) throws IOException, ClassNotFoundException {
        Gson g = new Gson();
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Graph graph = (Graph) ois.readObject();
        ois.close();
        return graph;
    }

    public void writeGraph(Graph graph, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(graph);
        oos.flush();
        oos.close();
    }
}

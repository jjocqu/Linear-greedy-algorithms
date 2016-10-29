package gretig;

import java.io.*;
import java.util.ArrayList;

public class GraphReader {

    private InputStream in;

   //constructor for tests only
    public GraphReader(String filename) {
        try {
            in = new BufferedInputStream(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GraphReader() {
        in = new BufferedInputStream(System.in);
    }

    public ArrayList<Graph> readGraphs() {
        int bytes_per_num ;
        int nodes;
        int edges;

        ArrayList<Graph> graphs = new ArrayList<>();

        try {
            in.skip(7); //read >>SEC<<
            bytes_per_num = in.read();

            while (bytes_per_num != -1) { //while not EOF
                nodes = 0;
                edges = 0;

                for (int i = 0; i < bytes_per_num; i++) { //calculate nodes
                    nodes += in.read() * Math.pow(256, i);
                }

                for (int i = 0; i < bytes_per_num; i++) { //calculate edges
                    edges += in.read() * Math.pow(256, i);
                }

                Graph graph = new Graph(nodes, edges);

                for (int i = 0; i < nodes; i++) { //read every node and its edges
                    int num;
                    ArrayList<Integer> temp = new ArrayList<>(); //temp list with all edges
                    do {
                        num = 0;
                        for (int j = 0; j < bytes_per_num; j++) {
                            num += in.read() * Math.pow(256, j);
                        }
                        if (num != 0) {
                            temp.add(num - 1); //add edge with number 'num-1' (edges are numbered started from 1, we need them from 0)
                        }
                    } while (num != 0);
                    graph.addNode(i, temp); //all edges from node added -> add node to graph
                }
                bytes_per_num = in.read();

                graphs.add(graph);
                //graph.printGraph();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graphs;
    }
}

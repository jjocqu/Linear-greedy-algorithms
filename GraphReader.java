package gretig;

import java.io.*;
import java.util.ArrayList;

public class GraphReader {

    public ArrayList<Graph> readGraphs(String filename) throws FileNotFoundException {
        //BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader stdin = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        int bytes_per_num = 0;
        int nodes = 0;
        int edges = 0;

        ArrayList<Graph> graphs = new ArrayList<>();

        try {
            stdin.skip(7); //read >>SEC<<
            bytes_per_num = stdin.read();

            while (bytes_per_num != -1) { //while not EOF
                nodes = 0;
                edges = 0;

                for (int i = 0; i < bytes_per_num; i++) { //calculate nodes and edges
                    nodes += stdin.read() * Math.pow(256, 8 * i);
                    edges += stdin.read() * Math.pow(256, 8 * i);
                }

                Graph graph = new Graph(nodes);

                for (int i = 0; i < nodes; i++) { //read every node and its edges
                    int num;
                    ArrayList<Integer> temp = new ArrayList<>(); //temp list with all edges
                    do {
                        num = 0;
                        for (int j = 0; j < bytes_per_num; j++) {
                            num += stdin.read() * Math.pow(256, 8 * j);
                        }
                        if (num != 0) {
                            temp.add(num - 1); //add edge with number 'num-1' (edges are numbered started from 1, we need them from 0)
                        }
                    } while (num != 0);
                    graph.addNode(i, temp); //all edges from node added -> add node to graph
                }
                bytes_per_num = stdin.read();

                graphs.add(graph);
                //graph.printGraph();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graphs;
    }
}

package gretig;

import java.io.*;
import java.util.ArrayList;

public class Dominantie {

    public static void main(String[] args) throws FileNotFoundException {

        GraphReader reader = new GraphReader();
        ArrayList<Graph> graphs = reader.readGraphs();

        for (Graph g: graphs) {
            ArrayList<Integer> result = g.findDominance();
            for (int node : result) {
                System.out.print(node + " ");
            }
            System.out.println();
        }

        /*for (Graph g : graphs) {
            System.out.println("printing graph: ");
            g.printGraph();
            System.out.println();
        }*/
    }
}

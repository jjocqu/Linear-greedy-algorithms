package gretig;

import java.io.*;
import java.util.ArrayList;

public class Dominantie {

    public static void main(String[] args) throws FileNotFoundException {

        GraphReader reader = new GraphReader();
        ArrayList<Graph> graphs = reader.readGraphs("src\\gretig\\grafen\\alle_5.sec");

        Graph g = graphs.get(0);

        ArrayList<Integer> result = g.findDominance();

        System.out.println(result);

        /*for (Graph g : graphs) {
            System.out.println("printing graph: ");
            g.printGraph();
            System.out.println();
        }*/
    }
}

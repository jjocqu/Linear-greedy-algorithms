package gretig;

import java.util.ArrayList;

public class Dominantie {

    public static void main(String[] args) {

        GraphReader reader = new GraphReader();
        ArrayList<Graph> graphs = reader.readGraphs();

        for (Graph g: graphs) {
            ArrayList<Integer> result = g.findDominance();
            for (int node : result) {
                System.out.print((node+1) + " "); //nodes from 1..n
            }
            System.out.println();
        }
    }
}

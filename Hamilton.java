package gretig;

import java.util.ArrayList;

public class Hamilton {

    public static void main(String[] args) {

        GraphReader reader = new GraphReader();
        ArrayList<Graph> graphs = reader.readGraphs();

        for (Graph g: graphs) {
            ArrayList<Integer> result = g.findHamilton();
            if (result == null) {
                System.out.println("geen cykel gevonden");
            }
            for (int node : result) {
                System.out.print((node+1) + " "); //nodes from 1..n
            }
            System.out.println();
        }
    }
}

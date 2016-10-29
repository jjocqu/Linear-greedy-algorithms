package gretig;

import java.util.ArrayList;

public class Graph {

    private int numberOfNodes;
    private int numberOfEdges;

    //combinatorisch ingebed
    private ArrayList<ArrayList<Integer>> nodes_neighbours;

    //tijdens inlezen zijn niet alle toppen bekend
    //toppen waarvan er maar 1 boog bekend is worden hier opgeslagen
    private int[] unkownEdges;

    public Graph(int nodes, int edges) {
        numberOfNodes = nodes;
        numberOfEdges = edges;

        unkownEdges = new int[edges];

        for (int i = 0; i < edges; i++) {
            unkownEdges[i] = -1; //because 0 is a valid node
        }

        nodes_neighbours = new ArrayList<>(nodes);
        for (int i = 0; i < nodes; i++) {
            nodes_neighbours.add(new ArrayList<>());
        }
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void addNode(int node, ArrayList<Integer> edges) {
        for (int i = 0; i < edges.size(); i++) {
            int index = edges.get(i);
            if (unkownEdges[index] != -1) { //2e top van boog gevonden
                //add to nodes_neighbours
                int neighbour = unkownEdges[index];
                addNeighbour(node, neighbour);
            } else { //add node and edge to lists
                unkownEdges[index] = node;
            }
        }
    }

    private void addNeighbour(int node, int neighbour) {
        nodes_neighbours.get(node).add(neighbour);
        nodes_neighbours.get(neighbour).add(node);
    }

    public ArrayList<Integer> getNeighbours(int node) {
        return nodes_neighbours.get(node);
    }

    public void printGraph() {
        //System.out.println(nodes.size() + " " + edges.size()); //must both be zero
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.println("neighbours node " + i + ": ");
            for (int j : getNeighbours(i)) {
                System.out.println(j + " ");
            }
            System.out.println();
        }
    }

    /**
     * uses counting sort to sort list in O(n)
     * @return sorted list
     */
    private ArrayList<Integer> sortGraph() {
        ArrayList<Integer> result = new ArrayList<>(numberOfNodes);
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>(numberOfNodes);

        for (int i = 0; i < numberOfNodes; i++) {
            temp.add(new ArrayList<>());
        }

        for (int i = 0; i < numberOfNodes; i++) { //add every node to
            int size = nodes_neighbours.get(i).size();
            temp.get(size).add(i); //add node i at list size
        }

        for (int i = temp.size()-1; i >= 0; i--) { //sort from big to small
            for (int node : temp.get(i)) {
                result.add(node);
            }
        }

        return result;
    }

    
    //first loop from 1..n
    //second loop frop n..1
    public ArrayList<Integer> findDominance() {
        ArrayList<Integer> sorted = sortGraph();
        int n = sorted.size();
        ArrayList<Integer> result = new ArrayList<>(n);
        int freq[] = new int[n];
        int zeros = n;

        int counter = 0;
        while(zeros != 0) {
            int x = sorted.get(counter);
            counter++;
            result.add(x);
            for (int neighbour : getNeighbours(x)) {
                if (freq[neighbour] == 0) {
                    zeros--;
                }
                freq[neighbour]++;
            }
            if (freq[x] == 0) {
                zeros--;
            }
            freq[x]++;
        }

        //try to remove nodes from result (while maintaining dominance)
        for (int i = result.size()-1; i >= 0; i--) {
            boolean undo = false;
            int x = result.get(i);
            if (freq[x] != 1) {
                result.remove((Integer) x); //tell java to remove object i, not index i
                freq[x]--;
                for (int neighbour : getNeighbours(x)) {
                    freq[neighbour]--;
                    if (freq[neighbour] == 0) { //undo this iteration, i must stay in result
                        undo = true;
                    }
                }
            }
            if (undo) { //undo the removing of i
                result.add(x);
                freq[x]++;
                for (int neighbour : getNeighbours(x)) {
                    freq[neighbour]++;
                }
            }
        }

        return result;
    }

}

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
     * large_>small
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

    //returns result or null when no hamilton cycle is found
    public ArrayList<Integer> findHamilton() {
        ArrayList<Integer> result = new ArrayList<>(numberOfNodes);
        int[] removed = new int[numberOfNodes];
        boolean[] inHamilton = new boolean[numberOfNodes];

        //current node
        int cur = 0;

        //find node with least neighbours
        for (int i = 0; i < numberOfNodes; i++) {
            if (getNeighbours(i).size() == 2) { //quit if 2 neighbours
                cur = i;
                break;
            }
            if (getNeighbours(i).size() < getNeighbours(cur).size()) { //new min found
                cur = i;
            }
        }

        //try to find hamilton
        for (int i = 0; i < numberOfNodes-1; i++) {
            result.add(cur);

            //update lists
            inHamilton[cur] = true;
            for (int neighbour : getNeighbours(cur)) {
                removed[neighbour]++; //'remove' cur with neighbours
            }

            //find next node: node with least neighbours
            int min = -1;
            boolean nodeFound = false;
            for (int neighbour : getNeighbours(cur)) {
                if (!inHamilton[neighbour]) {
                    //number of neighbours not in cycle yet = all neighbours - removed neighbours
                    if (getNeighbours(neighbour).size()-removed[neighbour] == 1) { //quit if 1 neighbour
                        //if only 1 neighbour left in starting node
                        //make sure we don't go to that node unless it is the last node
                        if (i != numberOfNodes -1) {
                            if (getNeighbours(result.get(0)).size() - 1 == removed[result.get(0)]
                                    && !getNeighbours(result.get(0)).contains(neighbour)) {
                                min = neighbour;
                                nodeFound = true;
                                break;
                            }
                        } else {
                            min = neighbour;
                            nodeFound = true;
                            break;
                        }
                    }

                    if (min == -1 || getNeighbours(neighbour).size()-removed[neighbour] < getNeighbours(min).size()-removed[min]) {
                        //if only 1 neighbour left in starting node
                        //make sure we don't go to that node unless it is the last node
                        if (i != numberOfNodes - 2) {
                            if (!(getNeighbours(result.get(0)).size() - 1 == removed[result.get(0)]
                                    && getNeighbours(result.get(0)).contains(neighbour))) {
                                min = neighbour;
                                nodeFound = true;
                            }
                        } else {
                            min = neighbour;
                            nodeFound = true;
                        }
                    }
                }
            }
            if (nodeFound) {
                cur = min;
            } else { //no new node found -> no cycle found
                return null;
            }
        }

        //add last node
        result.add(cur);

        //check if last en first node are neighbours -> cycle
        if (getNeighbours(result.get(0)).contains(result.get(numberOfNodes-1))) {
            return result;
        }

        return null;
    }
}

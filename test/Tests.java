package gretig.test;

import gretig.Graph;
import gretig.GraphReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class Tests {

    private long solutionQuality = 0;

    private long cycles = 0;
    private long total_graphs = 0;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    //@Test
    public void testFindDominance1() throws Exception {
        solutionQuality = 0;
        GraphReader reader;

        reader = new GraphReader("src\\gretig\\grafen\\alle_5.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\alle_6.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\alle_7.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\alle_8.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf1.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf2.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf3.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf4.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf5.sec");
        checkDominance(reader.readGraphs());
        System.out.println("solution quality small graphs: " + solutionQuality);
    }

    //@Test
    public void testFindDominance2() throws Exception {
        solutionQuality = 0;
        GraphReader reader;

        ArrayList<Graph> result;
        long start;
        long end;

        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf1.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf2.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf3.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf4.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf5.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf6.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf7.sec");
        checkDominance(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf8.sec");
        checkDominance(reader.readGraphs());

        System.out.println("solution quality testset: " + solutionQuality);
    }

    public void checkDominance(ArrayList<Graph> graphs) {
        for (Graph g: graphs) {
            ArrayList<Integer> solution = g.findDominance();
            if (!isDominant(g, solution)) { //print error
                g.printGraph();
                System.out.println(solution);
            }
            assertTrue(isDominant(g, solution));
            solutionQuality += solution.size();
        }
    }

    public boolean isDominant(Graph graph, ArrayList<Integer> solution) {

        Set<Integer> set = new HashSet<>();

        for (int node : solution) {
            set.add(node);
            for (int neighbour : graph.getNeighbours(node)) {
                set.add(neighbour);
            }
        }

        for (int node = 0; node < graph.getNumberOfNodes(); node++) { //set must contain all nodes in graph
            if (!set.contains(node)) {
                return false;
            }
        }

        return true;
    }


    @Test
    public void testFindHamilton() {
        GraphReader reader;

        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_05.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_06.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_07.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_08.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_09.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_10.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_11.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_alle_12.sec");
        checkHamilton(reader.readGraphs());

        System.out.println("found: " + cycles + " out of: " + total_graphs + " cycles");

        //reset for nonham graphs
        cycles = 0;
        total_graphs = 0;

        reader = new GraphReader("src\\gretig\\grafen\\triang_nonham_1.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_nonham_2.sec");
        checkHamilton(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\triang_nonham_3.sec");
        checkHamilton(reader.readGraphs());

        System.out.println("nonham: found: " + cycles + " out of: " + total_graphs + " cycles");
    }

    public void checkHamilton(ArrayList<Graph> graphs) {
        total_graphs += graphs.size();
        for (Graph g: graphs) {
            ArrayList<Integer> solution = g.findHamilton();
            boolean valid = isHamilton(g, solution);
            if (!valid) { //print error
                g.printGraph();
                System.out.println(solution);
            }
            assertTrue(valid);
        }
    }

    public boolean isHamilton(Graph graph, ArrayList<Integer> solution) {

        if (solution == null) { //no cycle found is always correct
            return true;
        }

        if (solution.size() != graph.getNumberOfNodes()) {
            return false;
        }

        //every node must be neighbour of next node in solution (and last of first)
        for (int i = 0; i < solution.size(); i++) {
            if (!graph.getNeighbours(solution.get(i)).contains(solution.get((i + 1) % solution.size()))) {
                return false;
            }
        }

        //every node must be in solution
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            if (!solution.contains(i)) {
                return false;
            }
        }

        cycles++;
        return true;
    }
}
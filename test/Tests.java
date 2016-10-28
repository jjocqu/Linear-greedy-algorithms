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

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindDominance() throws Exception {
        solutionQuality = 0;
        GraphReader reader;

        reader = new GraphReader("src\\gretig\\grafen\\alle_5.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\alle_6.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\alle_7.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\alle_8.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf1.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf2.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf3.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf4.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen\\graaf5.sec");
        checkGraphs(reader.readGraphs());
        System.out.println("solution quality: " + solutionQuality);
    }

    @Test
    public void testFindDominance2() throws Exception {
        solutionQuality = 0;
        GraphReader reader;

        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf1.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf2.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf3.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf4.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf5.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf6.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf7.sec");
        checkGraphs(reader.readGraphs());
        reader = new GraphReader("src\\gretig\\grafen_testset\\graaf8.sec");
        checkGraphs(reader.readGraphs());

        System.out.println("solution quality testset: " + solutionQuality);
    }

    public void checkGraphs(ArrayList<Graph> graphs) {
        for (Graph g: graphs) {
            ArrayList<Integer> solution = g.findDominance();
            if (!isDominant(g, solution)) {
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
}
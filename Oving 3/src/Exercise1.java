import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Fjogen
 * Date: 14.11.13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public class Exercise1 {
    /*
    Network flow.
     */

    private static int vertices;
    private static int source;
    private static int sink;
    private static int[][] neighbour;
    private static int[][] capacity;

    public static void main(String[] args) throws FileNotFoundException {
        Exercise1 run = new Exercise1();
        //run.readInput("test5-in.txt");
        run.readInput("Oving3-input.txt");
        EdmondsKarp maxFlow = new EdmondsKarp(capacity, neighbour, source, sink, vertices);
    }

    public void readInput(String fileName) throws FileNotFoundException {
        /*
        The program is to read its input from a designated file. The filename is given to the
        program, together with the name of an output file, as command line arguments. The
        input file contains:
        - First a line with the number of vertices m.
        - Then m lines with m numbers each (a matrix) defining the capacities between
        each pair of vertices. The number on line i, in column j is the capacity of the
        flow from vertex i to vertex j, in other words the capacity of the edge (i, j) in
        the graph.
        The vertices are numbered 1 through m, with 1 as the source and m as the sink. Note
        that there may be a positive capacity both from a vertex v to a vertex u, and from u
        back to v. On the diagonal all capacities are 0. There are no edges going into the
        source or out of the sink (the first column and the last line contain only zeros).
         */
        Scanner in = new Scanner(new File(fileName));
        int x = 0;
        int y = 0;
        source = 0;
        sink = 0;
        try {
            vertices = in.nextInt();
            capacity = new int[vertices][vertices];
            neighbour = new int[vertices][vertices];
            sink = vertices-1;
            while(in.hasNext()) {
                int number = in.nextInt();
                if(y == vertices) {
                    x++;
                    y = 0;
                }
                capacity[x][y] = number;
                if(number != 0) {
                	for(int i=0;i<vertices;i++) {
                		if(neighbour[x][i]==0) {
                			neighbour[x][i] = y;
                			//System.out.println("Adding "+y+" to "+x+"'s neighbour list.");
                			break;
                		}
                	}
                }
                //System.out.println("Adding "+number+" to capacity["+x+"]["+y+"].");
                y++;
            }
        } finally {
            in.close();
        }
    }

    public void writeOutput() {

    }
}

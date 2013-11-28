import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fjogen
 * Date: 28.11.13
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */

public class EdmondsKarp {
    int[][] C;
    int[][] E;
    int[][] F;
    int[] P; //Parent table
    int[] M; //Capacity of found path to node.
    boolean[] cut;
    int s;
    int t;
    int f;
    int n;
    int count;

    /**
     *
     * @param C Capacity matrix
     * @param E Neighbour lists
     * @param s Source
     * @param t Sink
     * @param n Amount of vertices
     */
    public EdmondsKarp(int[][] C, int[][] E, int s, int t, int n) {
        this.C = C;
        this.E = E;
        this.s = s;
        this.t = t;
        this.n = n;
        f = 0;
        count = 0;
        F = new int[n][n];
        P = new int[n];
        M = new int[n];
        cut = new boolean[n];
        doEdmondsKarp();
        prettyPrint();
    }

    public void doEdmondsKarp() {
        while(true) {
            int m = doBFS();
            count++;
            if(m==0) {
                break;
            }
            f += m;
            /*
            Backtrack search, and write flow.
             */
            int v = t;
            while(v!=s) {
                int u = P[v];
                F[u][v] += m;
                if(F[u][v]!=0) {
                    System.out.println("Node is now: "+v);
                }
                //F[v][u] -= m;
                v = u;
            }
        }
    }

    private int doBFS() {
        Arrays.fill(P, -1);
        P[s] = -2; //Make sure source is not rediscovered.
        M[s] = Integer.MAX_VALUE;
        Queue<Integer> Q = new LinkedList<Integer>();
        Q.offer(s);
        while(Q.size()>0) {
            int u = Q.poll();
            for(int v : E[u]) {
                /*
                If there is available capacity, and v is not seen before in search.
                 */
                if(C[u][v] - F[u][v] > 0 && P[v] == -1) {
                    P[v] = u;
                    M[v] = Math.min(M[u], C[u][v] - F[u][v]);
                    if(v != t) {
                        Q.offer(v);
                    } else {
                        return M[t];
                    }
                }
            }
        }
        return 0;
    }

    public void prettyPrint() {
        //Collections.sort(cut);
        System.out.println(f);
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                System.out.print(F[i][j]+" ");
            }
            System.out.println();
        }
//        for(int i=0;i<P.length;i++) {
//            System.out.print(P[i]+" ");
//        }
        System.out.println(findCut());
        System.out.println(count-1);
    }

    private String findCut() {
        String theCut = "";
        cut[s] = true; //source skal være med.
        locateCut(s); //starter det rekursive kallet ved sourcen.
        for (int i = 1; i <= n; i++) {
            if (cut[i-1])
                theCut = theCut + i + " ";
        }
        return theCut;
    }

    private void locateCut(int current) {
        for (int v: E[current]) {
            if(C[current][v] - F[current][v] > 0 && !cut[v]) {
                cut[v] = true;
                locateCut(v);
            }
        }
    }
}

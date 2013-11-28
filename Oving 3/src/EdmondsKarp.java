import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
    int[] P;
    int s;
    int t;
    int f;
    int n;

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
        F = new int[n][n];
        P = new int[n];
        doEdmondsKarp();
        prettyPrint();
    }

    public void doEdmondsKarp() {
        while(true) {
            int m = doBFS();
            if(m==0) {
                break;
            }
            f += m;
            int v = t;
            while(v!=s) {
                int u = P[v];
                F[u][v] += m;
                F[v][u] -= m;
                v = u;
            }
        }
    }

    private int doBFS() {
        Arrays.fill(P, -1);
        P[s] = -2;
        int[] M = new int[n];
        M[s] = Integer.MAX_VALUE;
        Queue<Integer> Q = new LinkedList<Integer>();
        Q.offer(s);
        while(Q.size()>0) {
            int u = Q.poll();
            for(int v : E[u]) {
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
        System.out.println(f);
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                System.out.print(F[i][j]+" ");
            }
            System.out.println();
        }
    }
}

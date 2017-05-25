import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by michiline on 24/05/2017.
 */
public class NodeRank {

    public static class Matrix {

        // return n-by-n identity matrix I
        public static double[][] identity(int n) {
            double[][] a = new double[n][n];
            for (int i = 0; i < n; i++)
                a[i][i] = 1;
            return a;
        }

        // matrix-vector multiplication (y = A * x)
        public static double[] multiply(double[][] a, double[] x) {
            int m = a.length;
            int n = a[0].length;
            if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
            double[] y = new double[m];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    y[i] += a[i][j] * x[j];
            return y;
        }

    }

    public static class Node {
        // value
        double r;
        // out degree
        int d;

        public Node (int r, int d) {
            this.r = r;
            this.d = d;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("lab4A_primjeri/btest2/R.in"));

        String line = br.readLine();
        String[] split = line.split(" ");

        // number of nodes
        int n = Integer.parseInt(split[0]);
        // beta
        double b = Double.parseDouble(split[1]);
        // init r(t) vector
        double[] r = new double[n];
        for (int i = 0; i < n; i++) {
            r[i] = 1.0 / n;
        }
        // r(t+1)
        double[] rn = Arrays.copyOf(r, r.length);
        int len;


        Map<Integer, int[]> m = new HashMap<>();

        for (int i = 0; i < n; i++) {
            line = br.readLine();
            int[] d = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            m.put(i,d);
            len = split.length;
            for (int j = 0; j < len; j++) {
                System.out.println(b * r[i] / len);
                rn[d[j]] += b * r[i] / len;
            }
        }
        r = Arrays.copyOf(rn, rn.length);
        rn = new double[n];
        for (int i = 0; i < n; i++) {
            rn[i] = 1.0 / n;
        }

        System.out.println();
    }
}

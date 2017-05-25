import java.io.*;
import java.util.*;

/**
 * Created by michiline on 24/05/2017.
 */
public class NodeRank {

    static final int ITERATIONS = 100;

    public static void main(String[] args) throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("examples/ttest2/R.in"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String[] split = line.split(" ");

        // number of nodes
        int n = Integer.parseInt(split[0]);
        // beta
        double b = Double.parseDouble(split[1]);
        // key = iteration; value = rank vector
        Map<Integer, double[]> r = new HashMap<>();
        // key = node; value = nodes that go into this node
        Map<Integer, List<Integer>> inputs = new HashMap<>();
        // degrees
        int[] d = new int[n];

        // init r(t) vector
        double[] rt = new double[n];
        for (int i = 0; i < n; i++) {
            rt[i] = 1.0 / n;
        }
        r.put(0, rt);
        // r(t+1)
        double[] rn;
        // init inputs
        for (int i = 0; i < n; i++) {
            inputs.put(i, new ArrayList<>());
        }

        // each line contains node outputs
        // need to calculate node inputs
        for (int i = 0; i < n; i++) {
            line = br.readLine();
            int[] outNodes = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            d[i] = outNodes.length;
            for (int outNode : outNodes) {
                inputs.get(outNode).add(i);
            }
        }

        for (int k = 0; k < ITERATIONS; k++) {
            rt = r.get(k);
            rn = new double[n];
            for (int j = 0; j < n; j++) {
                List<Integer> input = inputs.get(j);
                double sum = 0;
                for (int i : input) {
                    sum += rt[i] / d[i];
                }
                rn[j] = b * sum + (1 - b) / n;
            }
            r.put(k + 1, rn);
        }

        int q = Integer.parseInt(br.readLine());

        for (int i = 0; i < q; i++) {
            int[] values = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            System.out.printf("%.10f\n", r.get(values[1])[values[0]]);
        }
        System.out.println();
    }
}

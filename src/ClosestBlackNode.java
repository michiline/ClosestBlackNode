import java.io.*;
import java.util.*;

/**
 * Created by michiline on 26/05/2017.
 */
public class ClosestBlackNode {

    public static class Node {
        boolean black;
        ArrayList<Integer> neighbours;

        Node(boolean black) {
            this.black = black;
            this.neighbours = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("examples/stest2/R.in"));
        //BufferedWriter bw = new BufferedWriter(new FileWriter("examples/moj.out"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String[] split = line.split(" ");
        int n = Integer.parseInt(split[0]);
        int e = Integer.parseInt(split[1]);

        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int black = Integer.parseInt(br.readLine());
            nodes.put(i, new Node(black == 1));
        }
        for (int i = 0; i < e; i++) {
            split = br.readLine().split(" ");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            nodes.get(a).neighbours.add(b);
            nodes.get(b).neighbours.add(a);
        }

        int dist;
        boolean found;
        int foundId;
        Set<Integer> secondNeighbours = new TreeSet<>();
        Queue<Integer> queue;
        for (int i = 0; i < n; i++) {
            if (nodes.get(i).black) {
                System.out.printf("%d %d\n", i, 0);
                //bw.write(i + " 0\n");
                //bw.flush();
                continue;
            }
            found = false;
            foundId = -1;
            dist = 1;
            queue = new LinkedList<>();
            List<Integer> neighbours = nodes.get(i).neighbours;
            Collections.sort(neighbours);
            secondNeighbours.clear();
            for (int j = 0; j < neighbours.size(); j++) {
                List<Integer> temp = nodes.get(neighbours.get(j)).neighbours;
                secondNeighbours.addAll(temp);
            }
            for (int k = 0; k < 10; k++) {
                queue.addAll(neighbours);
                for (Integer id; (id = queue.poll()) != null; ) {
                    if (nodes.get(id).black) {
                        found = true;
                        foundId = id;
                        break;
                    }
                }
                if (found) {
                    break;
                }
                dist++;
                neighbours = new ArrayList<>(secondNeighbours);
                secondNeighbours.clear();
                for (int j = 0; j < neighbours.size(); j++) {
                    List<Integer> temp = nodes.get(neighbours.get(j)).neighbours;
                    secondNeighbours.addAll(temp);
                }
            }
            if (found) {
                //bw.write(foundId + " " + dist + "\n");
                System.out.printf("%d %d\n", foundId, dist);
            } else {
                System.out.printf("%d %d\n", -1, -1);
                //bw.write("-1 -1\n");
            }
            //bw.flush();
        }
        //bw.close();

    }


}

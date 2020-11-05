
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  Luokka solmuille
 *  
 */
public class Node {

    private float x;
    private float y;

    // Varastoi solmun naapurit
    ArrayList<Node> neighbours = new ArrayList();
    // Varastoi naapurien etäisyydet
    ArrayList<Float> distances = new ArrayList();
    // Onko solmussa käyty
    private boolean visited;

    // METODIT
    public void addNextClosest(ArrayList list) {
        int index = 0;
        float distance = Float.MAX_VALUE;

        for (int i = 0; i < list.size(); i++) {
            Node n = (Node) list.get(i);
            if (!this.equals(n) && !this.neighbours.contains(n)) {
                float xDif = this.x - n.x;
                float yDif = this.y - n.y;
                float tempDistance = (float) Math.sqrt((Math.pow(xDif, 2) + Math.pow(yDif, 2)));
                if (tempDistance < distance) {
                    distance = tempDistance;
                    index = i;
                }
            }
        }
        this.neighbours.add((Node) list.get(index));
        this.distances.add(distance);
    }

    public void degrees(ArrayList l, PrintWriter wr) {
        int inDegrees = 0;
        int outDegrees = neighbours.size();

        for (int i = 0; i < l.size(); i++) {
            Node n = (Node) l.get(i);
            if (!n.equals(this)) {
                for (Node neighbour : n.neighbours) {
                    if (neighbour.equals(this)) {
                        inDegrees++;
                    }
                }
            }
        }
        String line = this + ", Out: " + outDegrees + ", In: " + inDegrees;
        wr.println(line);
    }

    public Node(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  TIETA6 harjoitustyö
 *  Ohjelma lukee pisteiden kkordinaatit tiedostosta ja luo näistä graafin.
 *  Toteutettu kohdat 1-7 + 9
 *
 *  Tehtävä 9: GPS paikannus mahdollisesti?
 */
public class T2018 {

    private String line;
    private float[] x;
    private float[] y;

    private ArrayList<Node> nodes = new ArrayList();

    public static void main(String[] args) {
        T2018 t = new T2018();
        t.readInput();
        t.initializeNodes();
        t.initializeClosests();

        t.printAllBFS();
        t.resetAllVisited();

        t.printAllDFS();
        t.resetAllVisited();

        t.printAllDegrees();
        t.resetAllVisited();
        
        t.deleteNode(0);

        T2018 t2 = new T2018();
        t2.readInput();
        t2.initializeNodes();
        t2.initializeClosests();
        t2.makeGraphGreatAgain();

    }

    // METODIT
    // Suoritetaan tulostus leveyshaun mukaisesti kaikille solmuille
    private void printAllBFS() {
        try (PrintWriter writer = new PrintWriter("BFS.txt")) {
            for (int i = 0; i < nodes.size(); i++) {
                if (!nodes.get(i).isVisited()) {
                    breathPrint(i, writer);
                    writer.println();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(T2018.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Suoritetaan tulost syvyyshaun mukaan kaikille solmuille
    private void printAllDFS() {
        try (PrintWriter writer = new PrintWriter("DFS.txt")) {
            for (int i = 0; i < nodes.size(); i++) {
                if (!nodes.get(i).isVisited()) {
                    depthPrint(nodes.get(i), writer);
                    writer.println();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(T2018.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Tulostaa kaikkien solmujen asteet
    private void printAllDegrees() {
        try (PrintWriter writer = new PrintWriter("Degrees.txt")) {
            for (int i = 0; i < nodes.size(); i++) {
                Node n = nodes.get(i);
                n.degrees(nodes, writer);
                writer.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(T2018.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Luetaan koordinaatit tiedostosta
    private void readInput() {

        x = new float[400];
        y = new float[400];
        try {
            BufferedReader br = new BufferedReader(new FileReader("Tdata.txt"));

            for (int i = 0; i < 400; i++) {
                line = br.readLine();
                String[] values = line.split(",");
                x[i] = Float.parseFloat(values[0]);
                y[i] = Float.parseFloat(values[1]);
                System.out.print(x[i] + " , " + y[i] + "\n");

            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    // Luetaan koordinaatit taulukoista ja lisätään listaan node-oliot
    private void initializeNodes() {
        for (int i = 0; i < y.length && i < x.length; i++) {
            if (x[i] != 0 && y[i] != 0) {
                nodes.add(new Node(x[i], y[i]));
            }
        }
    }

    private void initializeClosests() {
        for (Node n : nodes) {
            n.addNextClosest(nodes);
            n.addNextClosest(nodes);
        }
    }

    private void resetAllVisited() {
        for (Node n : nodes) {
            n.setVisited(false);
        }
    }

    // Käy solmut läpi leveyssuunnassa aloittaen listan nodes i alkiosta.
    private void breathPrint(int i, PrintWriter writer) throws FileNotFoundException {
        LinkedList l = new LinkedList();
        Node n = nodes.get(i);
        n.setVisited(true);
        l.add(n);

        while (!l.isEmpty()) {
            n = (Node) l.remove();
            String line = "Y:" + n.getY() + " X:" + n.getX() + " node:" + n;
            writer.println(line);
            n.setVisited(true);
            for (Node neighbour : n.neighbours) {
                if (!neighbour.isVisited()) {
                    l.add(neighbour);
                    neighbour.setVisited(true);
                }
            }
        }
    }

    // Suorittaa syvyyshaun alkiosta n aloittaen
    private void breathSearch(Node n) {
        LinkedList q = new LinkedList();
        n.setVisited(true);
        q.add(n);

        while (!q.isEmpty()) {
            n = (Node) q.remove();
            for (Node neighbour : n.neighbours) {
                if (!neighbour.isVisited()) {
                    neighbour.setVisited(true);
                    q.add(neighbour);
                }
            }
        }
    }

    // Tulostaa solmut syvyyshaun mukaisesti
    private void depthPrint(Node n, PrintWriter wr) throws FileNotFoundException {
        n.setVisited(true);
        String line = "Y:" + n.getY() + " X:" + n.getX() + " node:" + n;
        wr.println(line);
        for (Node neighbour : n.neighbours) {
            if (!neighbour.isVisited()) {
                depthPrint(neighbour, wr);
            }
        }
    }

    // Poistetaan noodi indeksistä i, etsitään uudet lähimmäiset ja tulostetaan leveyshaku.
    private void deleteNode(int i) {
        Node temp = nodes.remove(i);

        // Etsitään solmut joilla poistettu solmu naapurina ja poistetaan viite ja etäisyys listasta. Etsitään tilalle uusi naapuri.
        for (Node node : nodes) {
            for (int j = 0; j < node.neighbours.size(); j++) {
                Node neighbour = node.neighbours.get(j);
                if (neighbour.equals(temp)) {
                    node.neighbours.remove(j);
                    node.distances.remove(j);
                    break;
                }
            }
        }

        // Tulostetaan leveyshaun mukaisesti kaikki solmut
       try (PrintWriter writer = new PrintWriter("DIM.txt")) {
            for (int x = 0; x < nodes.size(); x++) {
                if (!nodes.get(x).isVisited()) {
                    breathPrint(x, writer);
                    writer.println();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(T2018.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Lisätään kaikille solmuille naapuri niin kauan kunnes jokaisesta solmusta päästään kaikkiin muihin solmuihin
    private void makeGraphGreatAgain() {
        boolean isGreat = false;

        while (!isGreat) {
            if (isComplete(nodes)) {
                isGreat = true;
            } else {
                for (Node n : nodes) {
                    n.addNextClosest(nodes);
                }
            }
        }
        
        resetAllVisited();
        
        try (PrintWriter writer = new PrintWriter("COMP.txt")) {
            for (int i = 0; i < nodes.size(); i++) {
                if (!nodes.get(i).isVisited()) {
                    depthPrint(nodes.get(i), writer);
                    writer.println();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(T2018.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Tutkitaan leveyshaulla päästäänkö jokaisesta solmusta kaikkiin solmuihin
    private boolean isComplete(ArrayList<Node> nodes) {
        boolean complete = true;

        outerloop:
        for (Node n : nodes) {
            resetAllVisited();
            breathSearch(n);
            for (Node m : nodes) {
                if (m.isVisited() != true) {
                    complete = false;
                    break outerloop;
                }
            }
        }
        return complete;
    }

    // Jäi kesken. Loppui into.
    private void minSpanTree() {
        ArrayList<Node> minSpanTree = new ArrayList();

        // Etsitään eka node-pari
        float tempDistance = Float.MAX_VALUE;
        int index = 0;
        int jndex = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = 0; j < n.neighbours.size(); j++) {
                if (n.distances.get(j) < tempDistance) {
                    tempDistance = n.distances.get(j);
                    index = i;
                    jndex = j;
                }
            }
        }
        minSpanTree.add(nodes.get(index));
        minSpanTree.add(nodes.get(index).neighbours.get(jndex));

        // Lisätään minSpantreehen nodeja
        Boolean nodeFound = true;
        while (nodeFound) {
            nodeFound = false;
            tempDistance = Float.MAX_VALUE;
            index = 0;
            jndex = 0;
            for (int i = 0; i < minSpanTree.size(); i++) {
                Node n = minSpanTree.get(i);
                for (int j = 0; j < n.neighbours.size(); j++) {
                    if (!minSpanTree.contains(n.neighbours.get(j)) && n.distances.get(j) < tempDistance) {
                        tempDistance = n.distances.get(j);
                        index = i;
                        jndex = j;
                        nodeFound = true;
                    }
                }
            }
            if (nodeFound) {
                minSpanTree.add(nodes.get(index).neighbours.get(jndex));
            }
        }
    }

    // Jäi kesken. Loppui into.
    private void printSpanTree(ArrayList list) throws FileNotFoundException {
        resetAllVisited();
        PrintWriter writer = new PrintWriter("MSP.txt");
        LinkedList q = new LinkedList();
        Node n = (Node) list.get(0);
        n.setVisited(true);
        q.add(n);

        while (!q.isEmpty()) {
            n = (Node) q.remove();
            String line = "Y:" + n.getY() + " X:" + n.getX() + " node:" + n;
            writer.println(line);
            for (Node neighbour : n.neighbours) {
                if (!neighbour.isVisited() && list.contains(neighbour)) {
                    q.add(neighbour);
                    neighbour.setVisited(true);
                }
            }
        }
    }

}

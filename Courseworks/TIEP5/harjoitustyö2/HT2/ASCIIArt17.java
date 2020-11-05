/*
 * Niko Sainio (sainio.niko.l@student.uta.fi)
 ASCIIArt17
 * Ohjelma lataa ascii-grafiikan tekstitiedostosta jonka jälkeen voidaan suorittaa erilaisia operaatioita.
 *
 */

import java.io.*; import java.util.*;

public class ASCIIArt17 {

    // MENUKOMENNOT
    private static final String QUIT = "quit";
    private static final String PRINT = "print";
    private static final String ROTATERIGHT = "rotater";
    private static final String ROTATELEFT = "rotatel";
    private static final String RESET = "reset";
    private static final String INFO = "info";
    private static final String RECOLOUR = "recolour";

    // Annetaan komentoriviparametrit metodille joka luo ja täyttää taulukon.
    // Tervehditään käyttäjää ja pyydetään komento.
    public static void main(String[] args){
        System.out.println("-----------------------");
        System.out.println("| A S C I I A r t 1 7 |");
        System.out.println("-----------------------");

        // Hyväksytään vain yksi komentoriviparametri
        if (args.length > 0 && args.length <= 1) {
            char[][] taulukko = luoTaulukko(args[0]);
            
            // Jos taulukko saatiin tehtyä niin edetään ohjelmassa.
            if (taulukko != null) {
                // "Komentorivi".
                boolean quit = false;
                do {
                    System.out.println("Please, enter a command:");
                    String komento = In.readString();
                    if (komento.equals(QUIT)) {
                        quit = true;
                    }
                    else if (komento.equals(PRINT)) {
                        tulostaTaulukko(taulukko);
                    }
                    else if (komento.equals(ROTATERIGHT)) {
                        taulukko = turnWise(taulukko);
                    }
                    else if (komento.equals(ROTATELEFT)) {
                        taulukko = turnAnti(taulukko);
                    }
                    else if (komento.equals(RESET)) {
                        taulukko = luoTaulukko(args[0]);
                    }
                   else if (komento.equals(INFO)) {
                        tutkiTaulukko(taulukko);
                    }
                    else if (komento.contains(RECOLOUR)) {
                        taulukko = vaihdaMerkit(taulukko, komento.charAt(9), komento.charAt(11));
                    }
                }
                while (quit != true);
            }
        }
        else System.out.println ("Invalid command-line argument!");

        System.out.println("Bye, see you soon.");
    }

    // Metodi tutkii tiedoston mitat, luo ja täyttää taulukon.
    public static char[][] luoTaulukko(String s) {
        int rivit = 0;
        int pituus = 0;
        try {
            File tiedosto = new File(s);

            // Tutkitaan mitat ja luodaan oikean kokoinen taulukko
            Scanner lukija = new Scanner(tiedosto);
            while (lukija.hasNextLine()) {
                String mjono = lukija.nextLine();
                if(pituus < mjono.length()) pituus = mjono.length();
                rivit = rivit + 1;
            }
            char[][] t = new char[rivit][pituus];
            lukija.close();

            // Kopioidaan tiedoston merkit taulukkoon.
            Scanner lukija2 = new Scanner(tiedosto);
            while (lukija2.hasNextLine()) {
                for (int i = 0; i < t.length; i++) {
                    String mjono = lukija2.nextLine();
                    for (int j = 0; j < t[i].length; j++) {
                        t[i][j] = mjono.charAt(j);
                    }
                }
            }
            return t;
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid command-line argument!");
        }
        catch (Exception e) {
            System.out.println("Invalid command-line argument!");
        }
        return null;
        }



    // Metodi tulostaa taulukon mikäli se ei ole tyhjä.
    public static void tulostaTaulukko(char[][] t) {
        if (t != null) {
            for (int i = 0; i < t.length; i++) {
                System.out.println (t[i]);
            }
        }
        else;
    }

    // Metodi kääntää kuvan myötäpäivään
    public static char[][] turnWise(char[][] t) {
        if (t != null) {
            char[][] valitaulu = new char[t[0].length][t.length];

            for (int y = 0; y < t.length; y++) {
                for (int x = 0; x < t[0].length; x++) {
                    valitaulu[x][valitaulu[0].length - 1 - y] = t[y][x];
                }
            }
            return valitaulu;
        }
        else return t;
    }

    // Metodi kääntää kuvan vastapäivään.
    public static char[][] turnAnti(char[][] t) {
        if (t != null) {
            char[][] valitaulu = new char[t[0].length][t.length];

            for (int y = 0; y < t.length; y++) {
                for (int x = 0; x < t[0].length; x++) {
                    valitaulu[valitaulu.length - 1 - x][y] = t[y][x];
                }
            }
            return valitaulu;
        }
        else return t;
    }

    // Metodi tutkii montako kertaa merkki esiintyy kuvassa ja tulostaa tuloksen.
    public static void tutkiTaulukko(char[][] t) {
        if (t != null) {
            char[] merkit = new char[]
                {'#', '@', '&', '$', '%', 'x', '*', 'o', '|', '!', ';', ':', '\'', ',', '.', ' '};
            int[] n = new int[merkit.length];
            System.out.println(t.length + " x " + t[0].length);

            // Tutkitaan montako kertaa merkki esiintyy kuvassa
            for (int i = 0; i < merkit.length; i++) {
                for (int y = 0; y < t.length; y++) {
                    for (int x = 0; x < t[y].length; x++) {
                        if(merkit[i] == t[y][x]) {
                        n[i] = n[i] + 1;
                        }
                    }
                }
            }

            // Tulostetaan merkit ja vastaavat lukumäärät
            for (int j = 0; j < merkit.length; j++) {
                System.out.print (merkit[j] + " ");
                System.out.println (n[j]);
            }
        }
    }

    // Metodi vaihtaa kuvasta a merkit b merkkiin.
    public static char[][] vaihdaMerkit(char[][] t, char a, char b) {
        if (t != null) {
            for (int y = 0; y < t.length; y++) {
                for (int x = 0; x < t[y].length; x++) {
                    if(t[y][x] == a) t[y][x] = b;
                }
            }
            return t;
        }
        else return null;
    }
}

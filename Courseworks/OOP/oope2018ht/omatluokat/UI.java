package oope2018ht.omatluokat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import oope2018ht.apulaiset.In;
import oope2018ht.tiedostot.Kuva;
import oope2018ht.tiedostot.Video;
import oope2018ht.viestit.Viesti;

/**
 * Luokka sis‰lt‰‰ komentoikkunan k‰yttˆliittym‰n kokonaisuudessaan.
 * <p>
 * Attribuutteina komennot joita k‰ytt‰j‰ kutsuu.
 *
 * @author Niko Sainio (sainio.niko.l@student.uta.fi)
 */
public class UI {

    // Komennot vakioituina
    final static private String ADD = "add";
    final static private String CATALOG = "catalog";
    final static private String TREE = "tree";
    final static private String SELECT = "select";
    final static private String NEW = "new";
    final static private String REPLY = "reply";
    final static private String LIST = "list";
    final static private String HEAD = "head";
    final static private String TAIL = "tail";
    final static private String FIND = "find";
    final static private String EMPTY = "empty";
    final static private String EXIT = "exit";

// METODIT
    // Tiedoston lukumetodi
    /**
     * Metodilla luetaan tiedoston tiedot.
     *
     * @param nimi Tiedoston nimi
     * @return String joka sis‰lt‰‰ tiedoston tiedot
     */
    public static String lueTiedosto(String nimi) {
        BufferedReader br = null;
        String tiedot = null;
        try {
            br = new BufferedReader(new FileReader(nimi));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);

                line = br.readLine();
            }
            tiedot = sb.toString();
        } catch (IOException e) {

        } finally {
            try {
                br.close();
            } catch (NullPointerException | IOException n) {
            }
        }
        return tiedot;

    }

    // Erottaa komennosta itse viestin
    /**
     * Erottaa itse kommentin komentorivikomennosta
     *
     * @param teksti String joka sis‰lt‰‰ viestin + mahdollisen tiedoston
     * @return String joka sis‰lt‰‰ viestin tekstin
     */
    public static String erotaViesti(String teksti) {
        String teksti2 = "";

        for (int i = 0; i < teksti.length(); i++) {
            if (teksti.charAt(i) == '&') {
                break;
            } else {
                teksti2 = teksti2 + teksti.charAt(i);
            }
        }
        String viesti = teksti2.substring(0, teksti2.length() - 1);

        return viesti;
    }

    // Itse valikko
    public static void suorita() {

        // Tervehdit??n k?ytt?j??
        System.out.println("Welcome to S.O.B.");

        // Lippumuuttuja jolla poistutaan p??silmukasta
        boolean poistu = false;
        // Luodaan alue jonne langat tallennetaan
        AlueHallinta alue = new AlueHallinta();

        // Pyydet‰‰n k‰ytt‰j‰lt‰ komentoa niin kauan, kuin ei anneta "exit"
        while (!poistu) {
            System.out.print(">");
            String komento = In.readString();
            String[] split1 = komento.split(" ");
            String[] split2 = komento.split(" ", 2);

            // Lis‰‰ uuden langan, mik‰li tekstiosuus ja komennon rakenne oikeanlainen
            if (split2[0].equals(ADD)
                    && split2[1].length() > 0
                    && !split2[1].equals(" ")
                    && split2.length == 2) {
                String teksti = split2[1];
                alue.uusiLanka(teksti);

                // Listaa alueen langat ja n‰ytt‰‰ niiss‰ olevien viestien lukum‰‰r‰n
            } else if (komento.equals(CATALOG) && alue.haeLangatLista() != null) {
                alue.tulostaLangat();

                // Valitsee langan johon ketjukomennot kohdistetaan
            } else if (split1[0].equals(SELECT) && split2.length == 2) {
                try {
                    int luku = Integer.parseInt(split2[1]);

                    if (luku > 0 && luku <= alue.haeLangatLista().koko() && split2.length == 2) {
                        alue.valitseLanka(luku);
                    } else {
                        System.out.println("Error!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error!");
                }

                // Lis‰t‰‰n ketjuun viesti, mik‰li lanka on valittu ja komento oikeanlainen
            } else if (split2[0].equals(NEW)
                    && split2[1].length() > 0
                    && !split2[1].equals(" ")
                    && alue.haeValittuLanka() != null) {
                // Mik‰li ei komento sis‰lt‰‰ tai ei sis‰ll‰ tiedostoa
                if (!split1[split1.length - 1].contains("&")) {
                    alue.haeValittuLanka().haeKetjuHallinta().uusiKetju(split2[1], null);
                } else {
                    // Kasataan viestin kommentti stringtaulukon alkioista
                    String teksti = "";
                    for (int i = 1; i < split1.length - 1; i++) {
                        teksti = teksti + " " + split1[i];
                    }
                    String trim = teksti.trim();

                    // Kokeillaan lukea komennossa annetun tiedoston tiedot
                    String tiedostonimi = split1[split1.length - 1].substring(1);
                    String tiedot = lueTiedosto(tiedostonimi);
                    // Jos saatiin luettua niin luodaan sen mukainen tiedosto-olio ja luodaan/lis‰t‰‰n viesti
                    if (tiedot != null && !tiedot.equals("") && !tiedot.equals(" ")) {
                        String[] tiedostoSplit = tiedot.split(" ");
                        if (tiedostoSplit[0].equals("Kuva")) {
                            Kuva kuva = new Kuva(tiedostonimi,
                                    Integer.valueOf(tiedostoSplit[1]),
                                    Integer.valueOf(tiedostoSplit[2]),
                                    Integer.valueOf(tiedostoSplit[3]));

                            alue.haeValittuLanka().haeKetjuHallinta().uusiKetju(trim, kuva);
                        } else {
                            Video video = new Video(tiedostonimi,
                                    Integer.valueOf(tiedostoSplit[1]),
                                    Double.parseDouble(tiedostoSplit[2]));

                            alue.haeValittuLanka().haeKetjuHallinta().uusiKetju(trim, video);
                        }
                    } else {
                        System.out.println("Error!");
                    }
                }

                // Vastataan ketjussa olevaan viestiin
            } else if (split1[0].equals(REPLY) && split1.length >= 3 && alue.haeValittuLanka() != null) {
                // Mihin viestiin halutaan vastata
                String[] taulu = komento.split(" ", 3);
                int alkio = Integer.parseInt(taulu[1]);

                // Tutkitaan onko komennon lukuarvo ja kommentti oikeanlainen
                if (taulu[2].length() > 0
                        && !taulu[2].equals(" ")
                        && alkio > 0
                        && alkio < alue.haeValittuLanka().haeKetjuHallinta().haeVastausTunnus()) {

                    // Onko komennossa tiedosto ja toimitaan sen mukaan
                    if (!split1[split1.length - 1].contains("&")) {
                        alue.haeValittuLanka().haeKetjuHallinta().uusiVastaus(alkio, taulu[2], null);
                    } else {
                        String viesti = erotaViesti(taulu[2]);

                        // Koitetaan lukea annetun tiedoston tiedot
                        String tiedostonimi = split1[split1.length - 1].substring(1);
                        String tiedot = lueTiedosto(tiedostonimi);
                        // Jos luku onnistui niin luodaan tiedosto ja luodaan/lis‰t‰‰n viesti
                        if (tiedot != null && !tiedot.equals("") && !tiedot.equals(" ")) {
                            String[] tiedostoSplit = tiedot.split(" ");
                            if (tiedostoSplit[0].equals("Kuva")) {
                                Kuva kuva = new Kuva(tiedostonimi,
                                        Integer.valueOf(tiedostoSplit[1]),
                                        Integer.valueOf(tiedostoSplit[2]),
                                        Integer.valueOf(tiedostoSplit[3]));

                                alue.haeValittuLanka().haeKetjuHallinta().uusiVastaus(alkio, viesti, kuva);
                            } else {
                                Video video = new Video(tiedostonimi,
                                        Integer.valueOf(tiedostoSplit[1]),
                                        Double.parseDouble(tiedostoSplit[2]));

                                alue.haeValittuLanka().haeKetjuHallinta().uusiVastaus(alkio, viesti, video);
                            }
                        } else {
                            System.out.println("Error!");
                        }
                    }
                } else {
                    System.out.println("Error!");
                }

                // Tulostetaan ketjun viestit listana
            } else if (komento.equals(LIST) && alue.haeValittuLanka() != null) {
                System.out.println("=");
                System.out.println("== " + alue.haeValittuLanka().toString()
                        + " ("
                        + alue.haeValittuLanka().haeKetjuHallinta().haeLista().koko()
                        + " messages)"
                );
                System.out.println("===");
                alue.haeValittuLanka().haeKetjuHallinta().tulostaKetju();

                // Tulostetaan ketjun viestit puumuodossa
            } else if (komento.equals(TREE) && alue.haeValittuLanka() != null) {
                System.out.println("=");
                System.out.println("== " + alue.haeValittuLanka().toString()
                        + " ("
                        + alue.haeValittuLanka().haeKetjuHallinta().haeLista().koko()
                        + " messages)"
                );
                System.out.println("===");
                alue.haeValittuLanka().haeKetjuHallinta().tulostaPuuna();

                // Tulostetaan n vanhinta viesti‰
            } else if (split1[0].equals(HEAD)
                    && split1.length == 2
                    && Integer.parseInt(split1[1]) > 0
                    && Integer.parseInt(split1[1]) <= alue.haeValittuLanka().haeKetjuHallinta().haeLista().koko()
                    && alue.haeValittuLanka() != null) {
                alue.haeValittuLanka().haeKetjuHallinta().tulostaVanhimmat(Integer.parseInt(split1[1]));

                // Tulostetaan n uusinta viesti‰
            } else if (split1[0].equals(TAIL)
                    && split1.length == 2
                    && Integer.parseInt(split1[1]) > 0
                    && Integer.parseInt(split1[1]) <= alue.haeValittuLanka().haeKetjuHallinta().haeLista().koko()
                    && alue.haeValittuLanka() != null) {
                alue.haeValittuLanka().haeKetjuHallinta().tulostaUusimmat(Integer.parseInt(split1[1]));

                // Etsit‰‰n ketjun viesteist‰ vastaavanlaista osuutta
            } else if (split2[0].equals(FIND)
                    && split2[1].length() > 0
                    && !split2[1].equals(" ")
                    && split2.length == 2) {
                alue.haeValittuLanka().haeKetjuHallinta().etsiKetjusta(split2[1]);

                // Tyhjennet‰‰n viestin kommentti ja tiedosto
            } else if (split2[0].equals(EMPTY)
                    && Integer.parseInt(split2[1]) > 0
                    && Integer.parseInt(split2[1]) <= alue.haeValittuLanka().haeKetjuHallinta().haeVastausTunnus()
                    && alue.haeValittuLanka().haeKetjuHallinta().haeKetju() != null) {
                Viesti alkio = alue.haeValittuLanka().haeKetjuHallinta().etsiViesti(Integer.parseInt(split2[1]));
                if (alkio != null) {
                    alkio.tyhjenna();
                } else {
                    System.out.println("Error!");
                }

                // Poistutaan ohjelmasta
            } else if (komento.equals(EXIT)) {
                System.out.println("Bye! See you soon.");
                poistu = true;

            } else {
                System.out.println("Error!");
            }

        }
    }
}

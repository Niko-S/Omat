package oope2018ht.omatluokat;

import oope2018ht.omalista.OmaLista;
import oope2018ht.viestit.Viesti;

/**
 * Luokka hallinnoi alueta jotka sis‰lt‰v‰t ketjut
 * 
 * @author Niko Sainio (sainio.niko.l@student.uta.fi)
 */
public class AlueHallinta {

    // ATTRIBUUTIT
    /**  Lanka johon viestit ja vastaukset lis‰t‰‰n ja ketjuoperaatiot suoritetaan */
    private Viesti valittuLanka;
    /** Kasvava tunnus langalle */
    private int langanTunnus = 1;
    /** Lista jolle langat tallennetaan */
    private OmaLista langatLista = new OmaLista();

    // AKSESSORIT
    // Getteri
    public OmaLista haeLangatLista() {
        return langatLista;
    }

    public void asetaValittuLanka(Viesti valittuLanka) {
        this.valittuLanka = valittuLanka;
    }
    public Viesti haeValittuLanka() {
        return valittuLanka;
    }

    // METODIT
    /** Luodaan Viestiolio keskustelualueelle
     * 
     * @param teksti Viestin kommentti
     */
    public void uusiLanka(String teksti) {
        // Luodaan viestiolio ja lis‰t‰‰n se, jos onnistu niin tunnus kasvaa
        Viesti viesti = new Viesti(langanTunnus, teksti, null, null);
        boolean onnistuko = langatLista.lisaa(viesti);
        viesti.asetaKetjuHallinta(new KetjuHallinta());
        valitseLanka(langanTunnus);

        if (onnistuko) {
            langanTunnus++;
        }
    }

    /** 
     * Tulostaan lankojen aloitusviestit
     */
    public void tulostaLangat() {
        for (int i = 0; i < langatLista.koko(); i++) {
            Viesti lanka = (Viesti) langatLista.alkio(i);
            System.out.println(lanka.toString() + " (" + lanka.haeKetjuHallinta().haeLista().koko() + " messages)");
        }

    }

    /** 
     * Valitaan lanka johon viestit ja vastaukset lis‰t‰‰n, sek‰ ketjukomennot kohdistetaan
     * @param indeksi Luku joka vastaa langan tunnusta
     */
    public void valitseLanka(int indeksi) {
        if (indeksi <= langatLista.koko() && indeksi > 0) {
            valittuLanka = (Viesti) langatLista.alkio(indeksi - 1);
        } else {
            System.out.println("Error!");
        }
    }
}

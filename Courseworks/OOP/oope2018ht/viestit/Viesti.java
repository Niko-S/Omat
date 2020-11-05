/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  Tehtävä x.
 *  Ohjelma.....
 */
package oope2018ht.viestit;

import oope2018ht.omatluokat.KetjuHallinta;
import oope2018ht.apulaiset.*;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.*;

public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {
    // ATTRIBUUTIT

    // Viestin tunnisteena toimiva järjestysluku
    private int tunniste;

    // Viestin teksti
    private String teksti;

    // Viite edelliseen viestiin
    private Viesti edellinenViesti;

    // Viite tiedostoon mikäli on
    private Tiedosto tiedosto;

    // Viite vastaukset sisältävään listaan
    private OmaLista vastaukset;

    // Viite ketjunhallintaan
    private KetjuHallinta ketjuHallinta;

    // RAKENTAJA
    public Viesti(int jarjestysLuku, String teksti, Viesti edellinenViesti, Tiedosto tiedosto) {
        asetaTunniste(jarjestysLuku);
        asetaTeksti(teksti);
        asetaEdellinenViesti(edellinenViesti);
        asetaTiedosto(tiedosto);
        vastaukset = new OmaLista();

    }

    // AKSESSORIT
    @Getteri
    public int haeTunniste() {
        return tunniste;
    }

    @Setteri
    public void asetaTunniste(int jarjestysLuku) {
        if (jarjestysLuku >= 1) {
            this.tunniste = jarjestysLuku;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public String haeTeksti() {
        return teksti;
    }

    @Setteri
    public void asetaTeksti(String teksti) {
        if (teksti != null && teksti.length() > 0) {
            this.teksti = teksti;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public Viesti haeEdellinenViesti() {
        return edellinenViesti;
    }

    @Setteri
    public void asetaEdellinenViesti(Viesti edellinenViesti) {
        this.edellinenViesti = edellinenViesti;
    }

    @Getteri
    public Tiedosto haeTiedosto() {
        return tiedosto;
    }

    @Setteri
    public void asetaTiedosto(Tiedosto tiedosto) {
        this.tiedosto = tiedosto;
    }

    @Getteri
    public OmaLista haeVastaukset() {
        return vastaukset;
    }

    @Setteri
    public void asetaVastaukset(OmaLista vastaukset) {
        if (vastaukset != null) {
            this.vastaukset = vastaukset;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public KetjuHallinta haeKetjuHallinta() {
        return ketjuHallinta;
    }

    public void asetaKetjuHallinta(KetjuHallinta ketju) {
        this.ketjuHallinta = ketju;
    }

    // METODIT
    // Korvataan Comparable rajapinnan compareTo
    @Override
    public int compareTo(Viesti olio) {
        if (tunniste > olio.tunniste) {
            return 1;
        } else if (tunniste == olio.tunniste) {
            return 0;
        } else {
            return -1;
        }
    }

    // Korvataan Object luokan equals
    @Override
    public boolean equals(Object olio) {
        if (olio instanceof Viesti) {
            return (tunniste == ((Viesti) olio).tunniste);
        } else {
            return false;
        }
    }

    // Korvataan Object luokan toString
    @Override
    public String toString() {
        if (tiedosto != null) {
            return "#" + tunniste + " " + teksti + " (" + tiedosto.toString() + ")";
        } else {
            return "#" + tunniste + " " + teksti;
        }
    }

    // Etsitään parametria vastaava alkio vastauslistasta. Kutsutaan OmaLista:n hae metodia.
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if (haettava != null) {
            Object hae = vastaukset.hae((Object) haettava);
            return (Viesti) hae;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // Lisätään vastaus vastauslistaan mikäli identtistä ei löydy listasta.
    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        if (hae(lisattava) == null) {
            vastaukset.lisaa(lisattava);
        } else {
            throw new IllegalArgumentException();
        }
    }

    // "Poistetaan" viesti.
    @Override
    public void tyhjenna() {
        teksti = POISTETTUTEKSTI;
        if (tiedosto != null) {
            tiedosto = null;
        }
    }

}

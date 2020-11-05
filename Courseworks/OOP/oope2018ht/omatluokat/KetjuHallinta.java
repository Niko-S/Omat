package oope2018ht.omatluokat;

import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.viestit.Viesti;

/**
 * Luokka joka hallinnoi ketjujen sisäistä viestintää
 * 
 * @author Niko Sainio (sainio.niko.l@student.uta.fi)
 */

public class KetjuHallinta {

    // ATTRIBUUTIT
    /** Viestien kasvava tunnus */
    private static int vastausTunnus = 1;
    /** Viite OmaListaan joka sisältää langan ensimmäisen tason viestit */
    private OmaLista ketju = new OmaLista();
    /** Lista jonne tallennetaan kaikki langan vastaukset tulostusta ym. varten */
    private OmaLista lista = new OmaLista();
    /** Puutulostuksen syvennyksen pituus */
    private final int TASONSYVYYS = 3;

    // AKSESSORIT
    public OmaLista haeKetju() {
        return ketju;
    }

    public int haeVastausTunnus() {
        return vastausTunnus;
    }

    public OmaLista haeLista() {
        return lista;
    }

    // METODIT
    /** 
     * Luodaan ketjun omalistaan uusi Viestialkio
     * 
     * @param teksti Viestin kommentti
     * @param tiedosto Viestin tiedosto
     */
    public void uusiKetju(String teksti, Tiedosto tiedosto) {
        Viesti viesti;
        if (tiedosto == null) {
            viesti = new Viesti(vastausTunnus, teksti, null, null);
        } else {
            viesti = new Viesti(vastausTunnus, teksti, null, tiedosto);
        }
        boolean onnistuko = ketju.lisaa(viesti);

        if (onnistuko) {
            vastausTunnus++;
            lista.lisaaLoppuun(viesti);
        }
    }
    
    /** 
     * Lisätään lankaan vastaus
     * @param alkio Viesti johon vastataan
     * @param teksti Viestin kommentti
     * @param tiedosto Viestin tiedosto
     */
    public void uusiVastaus(int alkio, String teksti, Tiedosto tiedosto) {
        Viesti viestiAlkio = null;
        // Tutkitaan löytyykö replyn luvun omaava viesti listasta
        for (int i = 0; i < lista.koko(); i++) {
            int tunniste = ((Viesti) lista.alkio(i)).haeTunniste();
            if (tunniste == alkio) {
                viestiAlkio = (Viesti) lista.alkio(i);
                break;
            }
        }
        // Jos viesti löytyi listasta niin listään vastaus
        if (viestiAlkio != null) {
            Viesti viesti = null;
            if (tiedosto == null) {
                viesti = new Viesti(vastausTunnus, teksti, viestiAlkio, null);
            } else {
                viesti = new Viesti(vastausTunnus, teksti, viestiAlkio, tiedosto);
            }
            viestiAlkio.lisaaVastaus(viesti);
            lista.lisaaLoppuun(viesti);
            vastausTunnus++;
        }
        else System.out.println("Error!");
    }

    /**
     * Tulostetaan ketjun viestit listana
     */
    public void tulostaKetju() {
        for (int i = 0; i < lista.koko(); i++) {
            System.out.println(lista.alkio(i).toString());
        }
    }
    
    /**
     * Tulostetaan n vanhinta viestiä
     * @param luku n
     */
    public void tulostaVanhimmat(int luku) {
        for (int i = 0; i < luku; i++) {
            System.out.println(lista.alkio(i).toString());
        }
    }

    /**
     * Tulostetaan n uusinta viestiä
     * @param luku n
     */
    public void tulostaUusimmat(int luku) {
        for (int i = lista.koko() - luku; i < lista.koko(); i++) {
            System.out.println(lista.alkio(i).toString());
        }
    }

    /**
     * Tulostaa ketjun viestit puumuodossa
     * Käytetään apuna {@link #tulostaPuuna(oope2018ht.viestit.Viesti, int) tulostaPuuna} metodia
     */
    public void tulostaPuuna() {
        if (ketju != null) {
            // Käydään läpi viestiketjun oksaviestit säilyttävä lista.
            int i = 0;
            while (i < ketju.koko()) {
                // Kutsutaan rekursiivista tulostusalgoritmia .
                tulostaPuuna((Viesti) ketju.alkio(i), 0);
                i++;
            }
        }
    }
    
    /**
     * Rekursiivinen tulostusalgoritmi
     * @param viesti Ketjun Viesti -olio
     * @param syvyys Tulostussyvyys joka kasvaa rekursion mukana
     */
    public void tulostaPuuna(Viesti viesti, int syvyys) {
        // Tulostetaan annetun syvyinen sisennys.
        for (int i = 0; i < syvyys; i++) {
            System.out.print(" ");
        }
        // Tulostetaan viestin merkkijonoesitys.
        System.out.println(viesti.toString());
        // Asetetaan apuviite viestin vastaukset sÃ¤ilÃ¶vÃ¤Ã¤n listaan.
        OmaLista vastaukset = viesti.haeVastaukset();
        // Tulostetaan vastaukset rekursiivisesti. Metodista palataan,
        // kun vastauslista on tyhjä.
        int j = 0;
        while (j < vastaukset.koko()) {
            tulostaPuuna((Viesti) vastaukset.alkio(j), syvyys + TASONSYVYYS);
            j++;
        }
    }

    /** 
     * Etsii ketjun kommentista / tiedostonimestä vastaavuutta
     * @param etsittava String jota etsitään
     */
    public void etsiKetjusta(String etsittava) {
        if (ketju != null) {
            for (int i = 0; i < lista.koko(); i++) {
                boolean tiedLoyty = false;
                boolean loyty = false;
                Viesti alkio = (Viesti) lista.alkio(i);
                loyty = alkio.haeTeksti().contains(etsittava);
                if (alkio.haeTiedosto() != null) {
                    tiedLoyty = alkio.haeTiedosto().toString().contains(etsittava);
                }
                if (loyty || tiedLoyty) {
                    System.out.println(lista.alkio(i).toString());
                }
            }
        }
    }

    /**
     * Etsii ketjusta Viestioliota jolla vastaava tunnus
     * @param alkio Luku jonka vastaavaa tunnusta etsitään
     * @return Löydetyn Viestiolion tai null
     */
    public Viesti etsiViesti(int alkio) {
        Viesti viestiAlkio = null;
        for (int i = 0; i < lista.koko(); i++) {
            int tunniste = ((Viesti) lista.alkio(i)).haeTunniste();
            if (tunniste == alkio) {
                viestiAlkio = (Viesti) lista.alkio(i);
                break;
            }
        }
        return viestiAlkio;
    }
}

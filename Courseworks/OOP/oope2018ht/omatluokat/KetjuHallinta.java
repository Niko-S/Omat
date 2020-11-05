package oope2018ht.omatluokat;

import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.viestit.Viesti;

/**
 * Luokka joka hallinnoi ketjujen sis�ist� viestint��
 * 
 * @author Niko Sainio (sainio.niko.l@student.uta.fi)
 */

public class KetjuHallinta {

    // ATTRIBUUTIT
    /** Viestien kasvava tunnus */
    private static int vastausTunnus = 1;
    /** Viite OmaListaan joka sis�lt�� langan ensimm�isen tason viestit */
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
     * Lis�t��n lankaan vastaus
     * @param alkio Viesti johon vastataan
     * @param teksti Viestin kommentti
     * @param tiedosto Viestin tiedosto
     */
    public void uusiVastaus(int alkio, String teksti, Tiedosto tiedosto) {
        Viesti viestiAlkio = null;
        // Tutkitaan l�ytyyk� replyn luvun omaava viesti listasta
        for (int i = 0; i < lista.koko(); i++) {
            int tunniste = ((Viesti) lista.alkio(i)).haeTunniste();
            if (tunniste == alkio) {
                viestiAlkio = (Viesti) lista.alkio(i);
                break;
            }
        }
        // Jos viesti l�ytyi listasta niin list��n vastaus
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
     * Tulostetaan n vanhinta viesti�
     * @param luku n
     */
    public void tulostaVanhimmat(int luku) {
        for (int i = 0; i < luku; i++) {
            System.out.println(lista.alkio(i).toString());
        }
    }

    /**
     * Tulostetaan n uusinta viesti�
     * @param luku n
     */
    public void tulostaUusimmat(int luku) {
        for (int i = lista.koko() - luku; i < lista.koko(); i++) {
            System.out.println(lista.alkio(i).toString());
        }
    }

    /**
     * Tulostaa ketjun viestit puumuodossa
     * K�ytet��n apuna {@link #tulostaPuuna(oope2018ht.viestit.Viesti, int) tulostaPuuna} metodia
     */
    public void tulostaPuuna() {
        if (ketju != null) {
            // K�yd��n l�pi viestiketjun oksaviestit s�ilytt�v� lista.
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
        // Asetetaan apuviite viestin vastaukset säilövään listaan.
        OmaLista vastaukset = viesti.haeVastaukset();
        // Tulostetaan vastaukset rekursiivisesti. Metodista palataan,
        // kun vastauslista on tyhj�.
        int j = 0;
        while (j < vastaukset.koko()) {
            tulostaPuuna((Viesti) vastaukset.alkio(j), syvyys + TASONSYVYYS);
            j++;
        }
    }

    /** 
     * Etsii ketjun kommentista / tiedostonimest� vastaavuutta
     * @param etsittava String jota etsit��n
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
     * @param alkio Luku jonka vastaavaa tunnusta etsit��n
     * @return L�ydetyn Viestiolion tai null
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

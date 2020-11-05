/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  
 *  
 */

package oope2018ht.omalista;

import fi.uta.csjola.oope.lista.LinkitettyLista;
import oope2018ht.apulaiset.Ooperoiva;

public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista> {

    // Etsitään alkio joka vastaa haettavaa attribuuttia ja palautetaan viite tähän alkioon.
    public Object hae(Object haettava) {
        // Suoritetaan haku mikäli lista ei ole tyhja ja attribuutti ei ole null
        if (!onkoTyhja() && haettava != null) {
            Object olio = null;
            for (int i = 0; i < koko(); i++) {
                if (alkio(i).equals(haettava)) {
                    olio = alkio(i);
                    break;
                }
            }
            return olio;
        } else {
            return null;
        }
    }

    // Lisätään alkio listaan kasvavassa järjestyksessä. Palauttaa true/false onnistuiko lisäys.
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean lisaa(Object uusi) {
        boolean onnistu = false;
        boolean suurin = true;
        // Tarkistetaan ettei parametri ole null
        if (uusi != null) {
            // Jos ei tyhjä niin edetään, muutoin lisätään alkuun
            if (!onkoTyhja()) {
                // Varmistetaan, että alkiot samaa tyyppiä
                if (alkio(0).getClass().equals(uusi.getClass())) {
                    // Lisätään uusi alkio listan etu tai takakapäähän jos vain yksi alkio listassa.
                    if (koko() == 1) {
                        if (((Comparable) alkio(0)).compareTo(uusi) == -1) {
                            lisaaLoppuun(uusi);
                            onnistu = true;
                        } else {
                            lisaaAlkuun(uusi);
                            onnistu = true;
                        }
                    } else {
                        // Tutkitaan listaa kunnes kohdataan ensimmäinen suurempi luku ja lisätään alkio.
                        for (int i = 0; i < koko(); i++) {
                            if (((Comparable) alkio(i)).compareTo(uusi) == 1) {
                                lisaa(i, uusi);
                                onnistu = true;
                                suurin = false;
                                break;
                            }
                        }
                        // Mikäli uusi alkio on suurempi, kuin kaikki luvut.
                        if (suurin) {
                            lisaaLoppuun(uusi);
                            onnistu = true;
                        }
                    }
                }
            } else {
                lisaaAlkuun(uusi);
                onnistu = true;
            }
        }
        return onnistu;
    }

    // Kopioidaan listan n ekaa alkiota uuteen listaan.
    @Override
    public OmaLista annaAlku(int n) {
        if (koko() > 0 && n > 0 && n <= koko()) {
            OmaLista uusi = new OmaLista();
            for (int i = 0; i < n; i++) {
                uusi.lisaaLoppuun(alkio(i));
            }
            return uusi;
        } else {
            return null;
        }
    }

    // Kopioidaan listan n viimeistä alkiota uuteen listaan.
    @Override
    public OmaLista annaLoppu(int n) {
        if (koko() > 0 && n > 0 && n <= koko()) {
            OmaLista uusi = new OmaLista();
            for (int i = koko() - n; i < koko(); i++) {
                uusi.lisaaLoppuun(alkio(i));
            }
            return uusi;
        } else {
            return null;
        }
    }
    
}

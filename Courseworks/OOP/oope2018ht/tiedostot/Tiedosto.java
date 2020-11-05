/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  
 *  
 */
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.*;



public abstract class Tiedosto {

    // ATTRIBUUTIT
    // Tiedoston nimi
    private String nimi;
    // Tiedoston koko
    private int koko;

    // RAKENTAJA
    public Tiedosto(String nimi, int koko) {
        asetaNimi(nimi);
        asetaKoko(koko);
    }

    // AKSESSORIT
    @Getteri
    public String haeNimi() {
        return nimi;
    }

    @Setteri
    public void asetaNimi(String nimi) {
        if (nimi != null && nimi.length() > 0) {
            this.nimi = nimi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public int haeKoko() {
        return koko;
    }

    @Setteri
    public void asetaKoko(int koko) {
        if (koko > 0) {
            this.koko = koko;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // METODIT
    // Korvataan Object -luokan toString.
    @Override
    public String toString() {
        return nimi + " " + koko + " B";
    }

}

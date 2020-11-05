/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  
 *  
 */
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.*;



public class Video extends Tiedosto {

    // ATTRIBUUTIT
    // Videon pituus (aika)
    private double pituus;

    // RAKENTAJA
    public Video(String nimi, int koko, double pituus) {
        super(nimi, koko);
        asetaPituus(pituus);
    }

    // AKSESSORIT
    @Getteri
    public double haePituus() {
        return pituus;
    }

    @Setteri
    public void asetaPituus(double pituus) {
        if (pituus > 0) {
            this.pituus = pituus;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // METODIT
    // Korvataan toString. Kutsutaan yliluokan toString.
    @Override
    public String toString() {
        return super.toString() + " " + pituus + " s";
    }
}

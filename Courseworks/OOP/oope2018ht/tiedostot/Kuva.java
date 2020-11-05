/*
 *  Niko Sainio (sainio.niko.l@student.uta.fi)
 *  
 *  
 */
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.*;


public class Kuva extends Tiedosto {

    // ATTRIBUUTIT
    // Kuvan mitat
    private int korkeus;
    private int leveys;

    // RAKENTAJA
    public Kuva(String nimi, int koko, int leveys, int korkeus) {
        super(nimi, koko);
        asetaLeveys(leveys);
        asetaKorkeus(korkeus);
    }

    // AKSESSORIT
    @Getteri
    public int haeKorkeus() {
        return korkeus;
    }

    @Setteri
    public void asetaKorkeus(int korkeus) {
        if (korkeus >= 1) {
            this.korkeus = korkeus;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public int haeLeveys() {
        return leveys;
    }

    @Setteri
    public void asetaLeveys(int leveys) {
        if (leveys >= 1) {
            this.leveys = leveys;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // METODIT
    // Korvataan toString. Kutsutaan yliluokan toStringiä.
    @Override
    public String toString() {
        return super.toString() + " " + leveys + "x" + korkeus;
    }
}
